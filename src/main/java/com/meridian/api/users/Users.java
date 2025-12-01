package com.meridian.api.users;

import jakarta.persistence.*;

import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor
public class Users {

    @SequenceGenerator(name = "idx_seq", sequenceName = "idx_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idx_seq")
    @Id
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
