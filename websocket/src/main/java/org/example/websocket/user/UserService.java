package org.example.websocket.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.websocket.config.MainServiceConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(MainServiceConfiguration.class)
@Transactional
public class UserService {

    private final UserRepository repository;
    private final RestTemplate restTemplate;
    private final MainServiceConfiguration mainServiceConfiguration;

    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
        log.info("User: {} saved", user);
    }

    public boolean checkTokenInDB(User user) {
        return repository.existsByUsernameAndTokenJWT(user.getUsername(), user.getTokenJWT());
    }

    public void disconnectUser(User user) {
        var storedUser = repository.findById(user.getUsername())
                .orElse(null);

        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    public ResponseEntity<Void> sendGetRequest(User user) {
        if (user.getTokenJWT() == null || user.getUsername() == null) {
            return ResponseEntity.badRequest().build();
        }

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("tokenJWT", user.getTokenJWT());
        requestBody.put("username", user.getUsername());

        try {
            // Выполняем POST-запрос
            ResponseEntity<Void> response = restTemplate.exchange(mainServiceConfiguration.getUrl(), HttpMethod.POST, new HttpEntity<>(requestBody), Void.class);

            // Проверяем статус ответа
            if (response.getStatusCode() == HttpStatus.OK) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(response.getStatusCode()).build();
            }
        } catch (RestClientException e) {
            log.error("Error sending POST request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public List<User> findConnectedUsers(String username) {
        User user = repository.findById(username).get();

        if (user.getAuthority().equals("ADMIN")) {
            return repository.findAllBySupportEmail(user.getUsername());
        }
        List<User> list = repository.findAllByUsername(user.getSupportEmail());
        return list;
    }
}
