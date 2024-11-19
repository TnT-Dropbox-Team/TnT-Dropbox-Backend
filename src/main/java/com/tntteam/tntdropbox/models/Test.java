package com.tntteam.tntdropbox.models;

import jakarta.persistence.*;

@Entity
@Table
public class Test {
    @Id
    @SequenceGenerator(
            name = "test_sequence",
            sequenceName = "test_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "test_sequence"
    )
    private Long id;
    private String message;

    public Test() {}

    public Test(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
