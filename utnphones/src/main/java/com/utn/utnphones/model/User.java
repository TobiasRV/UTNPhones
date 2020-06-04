package com.utn.utnphones.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.utn.utnphones.model.enums.UserRole;
import com.utn.utnphones.model.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "users")

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastname;

    @Column(nullable = false, unique = true)
    private Integer dni;

    @JoinColumn(name = "id_city")
    @ManyToOne(fetch = FetchType.EAGER)
    private City city;

    @Column(nullable = false, length = 50)
    private String address;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user")
    private List<Line> lines;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus status;
}
