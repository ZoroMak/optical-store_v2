package org.example.websocket.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAllBySupportEmail(String supportEmail);
    List<User> findAllByUsername(String username);
    boolean existsByUsernameAndTokenJWT(String username, String tokenJWT);
}
