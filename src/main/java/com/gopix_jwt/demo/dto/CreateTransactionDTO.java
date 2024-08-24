package com.gopix_jwt.demo.dto;

import com.gopix_jwt.demo.enums.TransactionType;

public record CreateTransactionDTO(String userReceiveDocument, Double amount, TransactionType type, String category) {}

