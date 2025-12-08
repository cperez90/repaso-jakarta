package org.daw.repasojdbc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 25, name = "username")
    private String userName;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false, length = 25, name = "lastname")
    private String lastName;

    @Column(nullable = false,length = 150)
    private String passwordHash;

    @Column(nullable = false)
    private Timestamp created_at;

    public User(String userName, String name, String lastName, String passwordHash){
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.created_at = new Timestamp(System.currentTimeMillis());
    }
}
