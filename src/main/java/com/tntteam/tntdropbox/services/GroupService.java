package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.GroupDTO;
import com.tntteam.tntdropbox.dtos.GroupInputDTO;
import com.tntteam.tntdropbox.dtos.NotificationInputDTO;
import com.tntteam.tntdropbox.dtos.SimpleUserDTO;
import com.tntteam.tntdropbox.exceptions.conflict.ConflictException;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.Group;
import com.tntteam.tntdropbox.models.Notification;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.GroupRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final WebClient webClient;
    private String notificationServiceUrl = "http://localhost:8085/notifications";

    public GroupService(GroupRepository groupRepository, UserRepository userRepository, WebClient webClient) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.webClient = webClient;
    }

    public List<GroupDTO> getAllGroups(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getGroups().stream()
                .map(group -> {
                    User admin = group.getAdmin();
                    SimpleUserDTO adminDTO = new SimpleUserDTO(
                            admin.getId(),
                            admin.getUsername(),
                            admin.getFirstName(),
                            admin.getLastName()
                    );
                    return new GroupDTO(
                            group.getId(),
                            group.getName(),
                            group.getCreatedAt(),
                            adminDTO,
                            group.getUsers().size()
                    );
                })
                .toList();
    }

    public GroupDTO getGroup(long groupId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (!group.getUsers().contains(user)) {
            throw new ForbiddenException("You do not have permission to access this group");
        }
        User admin = group.getAdmin();
        SimpleUserDTO adminDTO = new SimpleUserDTO(admin.getId(), admin.getUsername(),
                admin.getFirstName(), admin.getLastName());
        return new GroupDTO(
                group.getId(), group.getName(), group.getCreatedAt(), adminDTO, group.getUsers().size()
        );
    }

    public List<SimpleUserDTO> getGroupMembers(long groupId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (!group.getUsers().contains(user)) {
            throw new ForbiddenException("You do not have permission to access this group");
        }
        return group.getUsers().stream()
                .map(member -> new SimpleUserDTO(
                                member.getId(),
                                member.getUsername(),
                                member.getFirstName(),
                                member.getLastName()
                        )
                )
                .toList();
    }

    public GroupDTO createGroup(long userId, GroupInputDTO groupInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (groupRepository.existsByName(groupInput.getName())) {
            throw new ConflictException("Group with this name already exists");
        }

        Group group = new Group();
        group.setName(groupInput.getName());
        group.setCreatedAt(LocalDateTime.now());
        group.setAdmin(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        group.setUsers(users);
        groupRepository.save(group);
        if (user.getGroups() == null) user.setGroups(new ArrayList<>());
        user.getGroups().add(group);
        userRepository.save(user);

        User admin = group.getAdmin();
        SimpleUserDTO adminDTO = new SimpleUserDTO(admin.getId(), admin.getUsername(),
                admin.getFirstName(), admin.getLastName());
        return new GroupDTO(
                group.getId(), group.getName(), group.getCreatedAt(), adminDTO, group.getUsers().size()
        );
    }

    public GroupDTO updateGroup(long groupId, long userId, GroupInputDTO groupInput) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (user != group.getAdmin()) {
            throw new ForbiddenException("You do not have permission to update this group");
        }
        if (!group.getName().equals(groupInput.getName()) && groupRepository.existsByName(groupInput.getName())) {
            throw new ConflictException("Group with this name already exists");
        }
        group.setName(groupInput.getName());
        groupRepository.save(group);

        User admin = group.getAdmin();
        SimpleUserDTO adminDTO = new SimpleUserDTO(admin.getId(), admin.getUsername(),
                admin.getFirstName(), admin.getLastName());
        return new GroupDTO(
                group.getId(), group.getName(), group.getCreatedAt(), adminDTO, group.getUsers().size()
        );
    }

    public SimpleUserDTO addGroupMember(long groupId, long memberId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (user != group.getAdmin()) {
            throw new ForbiddenException("You do not have permission to add member to this group");
        }
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (group.getUsers().contains(member)) {
            throw new ConflictException("The user is already member of this group");
        }
        group.getUsers().add(member);
        groupRepository.save(group);
        member.getGroups().add(group);
        userRepository.save(member);

        sendNotification(
                memberId,
                "Welocome to the group",
                "You have been added to the group: " + group.getName()
        );

        return new SimpleUserDTO(member.getId(), member.getUsername(),
                member.getFirstName(), member.getLastName());

    }

    public void deleteGroup(long groupId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        if (user != group.getAdmin()) {
            throw new ForbiddenException("You do not have permission to delete this group");
        }
        for (User member : group.getUsers()) {
            member.getGroups().remove(group);
            userRepository.save(member);
        }
        groupRepository.delete(group);
    }

    public void removeGroupMember(long groupId, long memberId, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (user != member && user != group.getAdmin()) {
            throw new ForbiddenException("You do not have permission to remove member from this group");
        }
        if (!group.getUsers().contains(member)) {
            throw new ConflictException("The user is not member of this group");
        }
        if (member == group.getAdmin()) {
            throw new ConflictException("Group Admin cannot be removed");
        }

        group.getUsers().remove(member);
        groupRepository.save(group);
        member.getGroups().remove(group);
        userRepository.save(member);
    }

    public void sendNotification(long userId, String title, String message) {
        NotificationInputDTO notificationInput = new NotificationInputDTO(title, message);
        String url = this.notificationServiceUrl + "/to/" + userId;
        webClient.post()
                .uri(url)
                .bodyValue(notificationInput)
                .retrieve()
                .bodyToMono(Notification.class)
                .doOnTerminate(() -> System.out.println("Notification sent"))
                .subscribe();
    }
}
