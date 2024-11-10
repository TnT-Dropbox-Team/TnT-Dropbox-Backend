package com.tntteam.tntdropbox.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {
    @GetMapping
    public String getAllGroups() {
        return "Seznam vseh skupin";
    }
    @PostMapping()
    public String createGroup() {
        return "Ustvari novo skupino";
    }
    @PutMapping("/{id}")
    public String updateGroup(@PathVariable long id) {
        return "Posodobi podatke o skupini: " + id;
    }
    @DeleteMapping("/{id}")
    public String deleteGroup(@PathVariable long id) {
        return "Izbriši skupino: " + id;
    }
    @DeleteMapping("/{id}/leave")
    public String leaveGroup(@PathVariable long id) {
        return "zapusti skupino: " + id;
    }
}
