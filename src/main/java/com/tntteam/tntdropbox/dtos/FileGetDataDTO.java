package com.tntteam.tntdropbox.dtos;

import java.util.Arrays;

public class FileGetDataDTO {
    private byte[] data;

    public FileGetDataDTO() {}

    public FileGetDataDTO(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileGetDataDTO{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
