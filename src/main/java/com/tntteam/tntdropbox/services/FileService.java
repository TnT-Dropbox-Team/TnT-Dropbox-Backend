package com.tntteam.tntdropbox.services;

import com.tntteam.tntdropbox.dtos.FileAddDTO;
import com.tntteam.tntdropbox.dtos.FileGetDTO;
import com.tntteam.tntdropbox.exceptions.forbidden.ForbiddenException;
import com.tntteam.tntdropbox.exceptions.resourceNotFound.ResourceNotFoundException;
import com.tntteam.tntdropbox.models.File;
import com.tntteam.tntdropbox.models.Group;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.repositories.FileRepository;
import com.tntteam.tntdropbox.repositories.GroupRepository;
import com.tntteam.tntdropbox.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    public FileService(FileRepository fileRepository, UserRepository userRepository, GroupRepository groupRepository) {
        this.fileRepository = fileRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
    }

    public Page<FileGetDTO> getAllUserFiles(Long userId, String searchQuery, String fileType, int page, int size, String[] sort) {
        if(!userRepository.existsById(userId))
            throw new ResourceNotFoundException("User with id " + userId + " not found");

        searchQuery = (searchQuery == null || searchQuery.trim().isEmpty()) ? "%" : "%" + searchQuery.trim() + "%";
        fileType = (fileType == null || fileType.trim().isEmpty()) ? "%" : "%" + fileType.trim() + "%";
        Pageable pageable = buildPageable(page, size, sort);
        Page<File> filePage = fileRepository.findByUserIdAndNameLikeAndTypeLike(userId, searchQuery, fileType, pageable);

        return filePage.map(file -> new FileGetDTO(
                file.getId(),
                file.getName(),
                file.getSize(),
                file.getType(),
                file.getCreatedAt(),
                file.getUpdatedAt()
        ));
    }

    private Pageable buildPageable(int page, int size, String[] sort) {
        if (sort == null || sort.length == 0) {
            return PageRequest.of(page, size, Sort.unsorted());
        }

        List<Sort.Order> orders = new ArrayList<>();
        for (int i = 0; i < sort.length; i += 2) {
            String field = sort[i];
            String direction = sort[i + 1];
            orders.add(new Sort.Order(Sort.Direction.fromString(direction), field));
        }

        return PageRequest.of(page, size, Sort.by(orders));
    }

    public Page<FileGetDTO> getAllGroupFiles(Long groupId, String searchQuery,
                                       String fileType, int page, int size, String[] sort) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!groupRepository.existsById(groupId))
            throw new ResourceNotFoundException("Group with id " + groupId + " not found");

        boolean isMember = groupRepository.existsByIdAndUsers_Id(groupId, authenticatedUser.getId());
        if (!isMember) {
            throw new ForbiddenException("You are not a member of this group");
        }

        searchQuery = (searchQuery == null || searchQuery.trim().isEmpty()) ? "%" : "%" + searchQuery.trim() + "%";
        fileType = (fileType == null || fileType.trim().isEmpty()) ? "%" : "%" + fileType.trim() + "%";
        Pageable pageable = buildPageable(page, size, sort);
        Page<File> filePage = fileRepository.findByGroupIdAndNameLikeAndTypeLike(groupId, searchQuery, fileType, pageable);

        return filePage.map(file -> new FileGetDTO(
                file.getId(),
                file.getName(),
                file.getSize(),
                file.getType(),
                file.getCreatedAt(),
                file.getUpdatedAt()
        ));
    }
    public FileGetDTO getFile(long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        File file = fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File with id " + id + " not found"));

        if (!file.getUser().getId().equals(authenticatedUser.getId())
                && (file.getGroup() == null || file.getGroup().getUsers() == null
                || file.getGroup().getUsers().stream().noneMatch(
                        member -> member.getId().equals(authenticatedUser.getId())))) {
                throw new ForbiddenException("You are not authorized to access this file");
        }

        return new FileGetDTO(
                file.getId(),
                file.getName(),
                file.getSize(),
                file.getType(),
                file.getCreatedAt(),
                file.getUpdatedAt());
    }

    public FileGetDTO uploadNewFile(FileAddDTO fileAddDTO) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        File file = new File();
        file.setName(fileAddDTO.getName());
        file.setFileData(fileAddDTO.getData().getBytes());
        file.setSize((long) fileAddDTO.getData().getBytes().length);
        file.setType(detectFileType(fileAddDTO.getName()));
        file.setCreatedAt(LocalDateTime.now());
        file.setUpdatedAt(LocalDateTime.now());
        file.setUser(authenticatedUser);

        File savedFile = fileRepository.save(file);

        return new FileGetDTO(
                savedFile.getId(),
                savedFile.getName(),
                savedFile.getSize(),
                savedFile.getType(),
                savedFile.getCreatedAt(),
                savedFile.getUpdatedAt()
        );
    }

    private String detectFileType(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        }
        return "unknown";
    }

    public FileGetDTO linkFileToGroup(Long fileId, Long groupId) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new ResourceNotFoundException("File with ID " + fileId + " not found")
        );

        if (!file.getUser().getId().equals(authenticatedUser.getId())) {
            throw new ForbiddenException("You are not authorized to link this file to a group");
        }

        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with ID " + groupId + " not found")
        );

        boolean isMember = group.getUsers().stream()
                .anyMatch(user -> user.getId().equals(authenticatedUser.getId()));
        if (!isMember) {
            throw new ForbiddenException("You are not a member of this group and cannot link files to it");
        }

        if (file.getGroup() != null) {
            throw new IllegalStateException("File with ID " + fileId + " is already linked to a group");
        }

        file.setGroup(group);
        file.setUpdatedAt(LocalDateTime.now());

        File updatedFile = fileRepository.save(file);

        return new FileGetDTO(
                updatedFile.getId(),
                updatedFile.getName(),
                updatedFile.getSize(),
                updatedFile.getType(),
                updatedFile.getCreatedAt(),
                updatedFile.getUpdatedAt()
        );
    }

    public void deleteFile(Long id) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        File file = fileRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("File with id " + id + " not found"));
        if (!file.getUser().getId().equals(authenticatedUser.getId())) {
            throw new ForbiddenException("You are not authorized to access this file");
        }
        fileRepository.deleteById(id);
    }
}