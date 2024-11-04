package org.example.project.aop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("email.from.store")
public class EmailPropertiesConfiguration {

    private final String login;
    private final String password;
}
