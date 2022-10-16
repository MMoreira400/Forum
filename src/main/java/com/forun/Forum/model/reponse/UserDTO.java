package com.forun.Forum.model.reponse;

import com.forun.Forum.model.entites.User;

import java.util.Optional;

public class UserDTO {

    private Long id;
    private String email;
    private String username;

    public UserDTO(User user) {
        this.id = user.getAutoId();
        this.email = user.getEmail();
        this.username = user.getUsername();
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

}
