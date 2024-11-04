package org.example.project.database.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(schema = "javaschema", name = "persistent_session")
@Getter
@Setter
public class PersistentSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "series")
    private String series;
    @Column(name = "username")
    private String userName;
    @Column(name = "token")
    private String token;
    @Column(name = "last_used")
    @UpdateTimestamp
    private LocalDateTime lastUsed;
}
