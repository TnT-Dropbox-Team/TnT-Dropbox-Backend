package com.tntteam.tntdropbox.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FileAddDTO {
    @NotNull(message = "File name can not be null")
    @NotEmpty(message = "File name can not be empty")
    private String name;

    @NotNull(message = "File data can not be null")
    private byte[] data;

    public FileAddDTO() {}

    public FileAddDTO(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileAddDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
