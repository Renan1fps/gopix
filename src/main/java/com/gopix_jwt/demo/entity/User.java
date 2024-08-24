package com.gopix_jwt.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String document;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "userReceive", cascade = CascadeType.ALL)
    private List<Transaction> transactionsReceived;

    @JsonIgnore
    @OneToMany(mappedBy = "userProvider", cascade = CascadeType.ALL)
    private List<Transaction> transactionsProvided;

    public User(Long id, String email, String password, String document , List<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.document = document;
        this.roles = roles;
    }

    public User() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Transaction> getTransactionsReceived() {
        return transactionsReceived;
    }

    public void setTransactionsReceived(List<Transaction> transactionsReceived) {
        this.transactionsReceived = transactionsReceived;
    }

    public List<Transaction> getTransactionsProvided() {
        return transactionsProvided;
    }

    public void setTransactionsProvided(List<Transaction> transactionsProvided) {
        this.transactionsProvided = transactionsProvided;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }
}