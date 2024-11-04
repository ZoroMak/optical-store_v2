package org.example.websocket.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping("/data")
    public ResponseEntity<String> receiveData(@RequestBody User user) {
        if (service.checkTokenInDB(user)) {
            return ResponseEntity.ok("Message received and checkToken OK");
        }

        ResponseEntity<Void> response = service.sendGetRequest(user);

        if (response.getStatusCode() == HttpStatus.OK) {
            service.saveUser(user);
            return ResponseEntity.ok("Message received and checkToken OK");
        } else {
            return ResponseEntity.badRequest().body("Message received but checkToken failed");
        }
    }

    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload User user) {
        service.saveUser(user);
        log.info("User: {} saved", user);
        return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnectUser(@Payload User user) {
        service.disconnectUser(user);
        log.info("User: {} disconnected", user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers(@RequestParam String username) {
        log.info("Get connected users");
        return ResponseEntity.ok(service.findConnectedUsers(username));
    }
}
