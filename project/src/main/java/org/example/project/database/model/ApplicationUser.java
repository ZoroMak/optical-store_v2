package org.example.project.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "javaschema", name = "user")
@Getter
@Setter
public class ApplicationUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int userId;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "birthday")
    private LocalDate birthday;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Column(name = "role")
    private Set<Role> authorities;
    @Column(name = "support_id")
    private String supportId;
    @Column(name = "tockenJWT")
    private String tokenJWT;


    public ApplicationUser() {
        super();
        this.authorities = new HashSet<>();
    }

    public ApplicationUser(String name, String surName, String email, String password, LocalDate birthday, Set<Role> authorities, String supportId) {
        super();
        this.name = name;
        this.surName = surName;
        this.email = email;
        this.password = password;
        this.birthday = birthday;
        this.authorities = authorities;
        this.supportId = supportId;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
