package com.thanaphat.Recipe.Sharing.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thanaphat.Recipe.Sharing.Utilty.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @JsonIgnore
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}
