package org.example.websocket.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class User {
    @Id
    private String username;
    private String fullName;
    private Status status;
    private String tokenJWT;
    private String authority;
    private String supportEmail;
}
