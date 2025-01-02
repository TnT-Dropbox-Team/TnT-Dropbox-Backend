package com.tntteam.tntdropbox.dtos;

import java.time.LocalDateTime;

public class GroupDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private SimpleUserDTO admin;
    private int members;

    public GroupDTO(Long id, String name, LocalDateTime createdAt, SimpleUserDTO admin, int members) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.admin = admin;
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SimpleUserDTO getAdmin() {
        return admin;
    }

    public void setAdmin(SimpleUserDTO admin) {
        this.admin = admin;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", members=" + members +
                '}';
    }
}
