package org.example.project.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDTO {

    private String tokenJWT;
    private String username;
}
