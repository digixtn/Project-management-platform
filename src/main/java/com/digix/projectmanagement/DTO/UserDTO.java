package com.digix.projectmanagement.DTO;

import com.digix.projectmanagement.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int userId;
    private String username;
    // ... other user details

    // ... getters and setters ...

    public UserDTO() {} // Needed for jackson

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        // ... set other fields
    }
}