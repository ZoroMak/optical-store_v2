package org.example.project.database.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("datasource")
public class DataSourceConfiguration {
    private final String url;
    private final String login;
    private final String password;
}
