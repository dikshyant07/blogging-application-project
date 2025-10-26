package com.blogging.application.bloggingProject.repository;

import com.blogging.application.bloggingProject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsById(Long id);
}
