package org.example.project.auth.service;

import jakarta.transaction.Transactional;
import org.example.project.auth.dto.LoginResponseDTO;
import org.example.project.database.model.ApplicationUser;
import org.example.project.database.model.Role;
import org.example.project.database.repo.ApplicationUserRepository;
import org.example.project.database.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    @Autowired
    private ApplicationUserRepository applicationUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    public ApplicationUser registerUser(String name, String surname,
                                        String email, String password,
                                        String dateOfBirth) {
        String encodedPassword = passwordEncoder.encode(password);
        Role userRole = roleRepository.findByAuthority("USER").get();

        Set<Role> authorities = new HashSet<>();

        authorities.add(userRole);

        String supportId = applicationUserRepository.getFreeSupportId();

        if (supportId == null) {
            supportId = applicationUserRepository.findFirstADMINUserEmail();
        }

        return applicationUserRepository.save(new ApplicationUser(name, surname, email, encodedPassword, LocalDate.parse(dateOfBirth), authorities, supportId));
    }

    public LoginResponseDTO loginUser(String email, String password) {

        try{

            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            String token = tokenService.generateJwt(auth);

            ApplicationUser user = applicationUserRepository.findByEmail(email).get();
            updateTokenJWT(user, token);

            return new LoginResponseDTO(user, token);

        }catch (AuthenticationException e){
            return new LoginResponseDTO(null, "");
        }
    }

    private void updateTokenJWT(ApplicationUser user, String token) {

        if (user != null) {
            user.setTokenJWT(token);
            applicationUserRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean checkToken(String username, String token) {
        ApplicationUser user = applicationUserRepository.findByEmail(username).get();
        return user.getTokenJWT().equals(token);
    }
}
