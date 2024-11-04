package org.example.websocket.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("main.auth")
public class MainServiceConfiguration {
    private final String url;
}
