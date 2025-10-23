package com.blogging.application.bloggingProject.models;

import com.blogging.application.bloggingProject.enums.Gender;
import com.blogging.application.bloggingProject.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "user")
    @JsonBackReference
    private List<Post> posts;
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments;
    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Like> likes;

}
