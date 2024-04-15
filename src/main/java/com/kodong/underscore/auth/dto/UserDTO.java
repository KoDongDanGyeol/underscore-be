package com.kodong.underscore.auth.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private String role;
    private String name;
    private String username;

    public UserDTO(String role, String name, String username) {
        this.role = role;
        this.name = name;
        this.username = username;
    }

    public UserDTO(String role, String username) {
        this.role = role;
        this.username = username;
    }
}
