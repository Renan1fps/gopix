package com.gopix_jwt.demo.entity;

import com.gopix_jwt.demo.enums.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity(name = "Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_receive", referencedColumnName = "id")
    private User userReceive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user_provider", referencedColumnName = "id")
    private User userProvider;

    private LocalDateTime date;

    private Double amount;

    private TransactionType type;

    private String category;

    public Transaction() {
    }

    public Transaction(User userReceive, User userProvider, LocalDateTime date, Double amount, TransactionType type, String category) {
        this.userReceive = userReceive;
        this.userProvider = userProvider;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(User userReceive) {
        this.userReceive = userReceive;
    }

    public User getUserProvider() {
        return userProvider;
    }

    public void setUserProvider(User userProvider) {
        this.userProvider = userProvider;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
