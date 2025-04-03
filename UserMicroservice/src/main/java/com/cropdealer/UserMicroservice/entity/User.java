package com.cropdealer.UserMicroservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable=false , unique = true)
    private String username;

    @Column(nullable=false,unique= true)
    private String password;

    @Column(nullable=false,unique=true)
    private String email;

    private String role; // framer - dealer admain

}
