package com.tntteam.tntdropbox.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CommentInputDTO {
    @NotNull(message = "body parameter text is required")
    @NotEmpty(message = "Comment should not be empty")
    private String text;

    public CommentInputDTO() {}

    public CommentInputDTO(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CommentInputDTO{" +
                "text='" + text + '\'' +
                '}';
    }
}
