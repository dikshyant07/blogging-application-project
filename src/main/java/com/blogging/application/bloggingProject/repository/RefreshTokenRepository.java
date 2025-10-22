package com.blogging.application.bloggingProject.repository;

import com.blogging.application.bloggingProject.models.RefreshToken;
import com.blogging.application.bloggingProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);
}
