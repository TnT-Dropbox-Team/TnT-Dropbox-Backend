package com.tntteam.tntdropbox.controllers;

import com.tntteam.tntdropbox.services.GroupService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public String getAllGroups() {
        return groupService.getAllGroups();
    }
    @PostMapping()
    public String createGroup() {
        return groupService.createGroup();
    }
    @PutMapping("/{id}")
    public String updateGroup(@PathVariable long id) {
        return groupService.updateGroup(id);
    }
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable long id) {
        return groupService.deleteGroup(id);
    }
    @DeleteMapping("/{id}/leave")
    public String leaveGroup(@PathVariable long id) {
        return groupService.leaveGroup(id);
    }
}
