package com.email.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@SuperBuilder
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    @Email(message = "Please provide the valid main id")
    private String email;
    private String password;
    private boolean isEnabled;
}
