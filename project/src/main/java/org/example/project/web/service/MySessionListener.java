package org.example.project.web.service;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.project.database.model.PersistentSession;
import org.example.project.database.repo.PersistentSessionRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Transactional
@RequiredArgsConstructor
public class MySessionListener implements HttpSessionListener {
    private PersistentSessionRepository persistentSessionRepository;

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {


        String sessionId = se.getSession().getId();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        PersistentSession persistentSession = new PersistentSession();
        persistentSession.setSeries(sessionId);
        persistentSession.setUserName(username);

        persistentSession.setLastUsed(LocalDateTime.now());

        persistentSessionRepository.save(persistentSession);
    }
}
