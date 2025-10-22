package com.blogging.application.bloggingProject.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@AllArgsConstructor
@Builder
public class Comment extends AuditingBoth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonManagedReference
    private Post post;
    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
