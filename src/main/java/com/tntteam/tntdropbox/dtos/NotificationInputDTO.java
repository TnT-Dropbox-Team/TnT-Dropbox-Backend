package com.tntteam.tntdropbox.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NotificationInputDTO {

    @NotNull(message = "body parameter title is required")
    @Size(min = 3, max = 100, message = "Length of title should be between 3 and 100")
    private String title;

    @NotNull(message = "body parameter body is required")
    @NotEmpty(message = "Notification body should not be empty")
    private String body;

    public NotificationInputDTO(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NotificationInputDTO{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
