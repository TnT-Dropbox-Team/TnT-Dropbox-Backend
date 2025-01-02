package com.tntteam.tntdropbox.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GroupInputDTO {

    @NotNull(message = "body parameter name is required")
    @Size(min = 3, max = 100, message = "Length of name should be between 3 and 100")
    private String name;

    public GroupInputDTO() {}

    public GroupInputDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroupInputDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
