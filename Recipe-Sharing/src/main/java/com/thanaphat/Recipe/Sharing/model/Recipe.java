package com.thanaphat.Recipe.Sharing.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    private String description;
    private String image;
    private boolean vegetarian;
    private LocalDateTime createdAt;
    private List<Long> likes = new ArrayList<>();
}
