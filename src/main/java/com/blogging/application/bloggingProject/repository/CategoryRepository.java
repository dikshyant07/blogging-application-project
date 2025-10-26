package com.blogging.application.bloggingProject.repository;

import com.blogging.application.bloggingProject.models.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Modifying
    @Transactional
    @Query("DELETE FROM Category c WHERE c.name=:name")
    int deleteByName(@Param(value = "name") String name);

    Optional<Category> findByName(String name);

}
