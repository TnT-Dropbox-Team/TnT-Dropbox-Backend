package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.dtos.GroupDTO;
import com.tntteam.tntdropbox.dtos.GroupInputDTO;
import com.tntteam.tntdropbox.dtos.SimpleUserDTO;
import com.tntteam.tntdropbox.models.User;
import com.tntteam.tntdropbox.services.GroupService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Profile({"group", "test"})
@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping
    public List<GroupDTO> getAllGroups() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return groupService.getAllGroups(user.getId());
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("{id}")
    public GroupDTO getGroup(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return groupService.getGroup(id, user.getId());
    }
    @SecurityRequirement(name = "TnTSecurityScheme")
    @GetMapping("{id}/members")
    public List<SimpleUserDTO> getGroupMembers(@PathVariable Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return groupService.getGroupMembers(id, user.getId());
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupDTO createGroup(@Valid @RequestBody GroupInputDTO groupInput) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return groupService.createGroup(user.getId(), groupInput);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PutMapping("/{id}")
    public GroupDTO addGroupMember(@PathVariable long id, @Valid @RequestBody GroupInputDTO groupInput) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return groupService.updateGroup(id, user.getId(), groupInput);
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @PutMapping("/{id}/add/{userId}")
    public SimpleUserDTO addGroupMember(@PathVariable long id, @PathVariable long userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return groupService.addGroupMember(id, userId, user.getId());
    }

    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGroup(@PathVariable long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        groupService.deleteGroup(id, user.getId());
    }
    @SecurityRequirement(name = "TnTSecurityScheme")
    @DeleteMapping("/{id}/remove/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeGroupMember(@PathVariable long id, @PathVariable long userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        groupService.removeGroupMember(id, userId, user.getId());
    }
}
