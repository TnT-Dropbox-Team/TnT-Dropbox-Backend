package com.tntteam.tntdropbox.services;

import org.springframework.stereotype.Service;

@Service
public class GroupService {
    public String getAllGroups() {
        return "Seznam vseh skupin";
    }
    public String createGroup() {
        return "Ustvari novo skupino";
    }
    public String updateGroup(long id) {
        return "Posodobi podatke o skupini: " + id;
    }
    public String deleteGroup(long id) {
        return "Izbriši skupino: " + id;
    }
    public String leaveGroup(long id) {
        return "zapusti skupino: " + id;
    }
}
