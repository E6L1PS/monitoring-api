package ru.ylab.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String username;
    private String password;
    private Role role;

    public boolean usernameIsValid() {
        return username != null && username.matches("\\w{5,15}");
    }

    public boolean passwordIsValid() {
        return password != null && username.matches("\\w{5,15}");
    }
}