package com.blogging.application.bloggingProject.repository;

import com.blogging.application.bloggingProject.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
