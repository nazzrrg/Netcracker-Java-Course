package com.github.nazzrrg.simplespringangularapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private long id;
    @Column(name = "Name")
    private final String name;
    @Column(name = "Email")
    private final String email;

    public User() {
        name = email = "";
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
