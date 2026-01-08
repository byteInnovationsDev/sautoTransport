package com.autoTransport.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;

    @Column(name = "user_id", nullable = false, unique = true)
    private String userId;

    @Column(name = "user_pass", nullable = false)
    private String userPass;

    // constructors
    public User() {}

    public User(String userId, String userPass) {
        this.userId = userId;
        this.userPass = userPass;
    }

    // getters & setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }
}

