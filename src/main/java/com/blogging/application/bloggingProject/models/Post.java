package com.blogging.application.bloggingProject.models;

import com.blogging.application.bloggingProject.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends AuditingBoth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private Status status;
    @OneToMany(mappedBy = "post")
    @JsonBackReference
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "post")
    @JsonBackReference
    private List<Like> likes;

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tags;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonManagedReference
    private User user;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @JsonManagedReference
    private Category category;

}
