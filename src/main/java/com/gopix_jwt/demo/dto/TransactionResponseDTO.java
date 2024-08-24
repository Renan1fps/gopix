package com.gopix_jwt.demo.dto;

import com.gopix_jwt.demo.enums.TransactionType;

import java.time.LocalDateTime;

public record TransactionResponseDTO(Long transactionId, String documentReceive, String userProviderName, Double amount,
                                     TransactionType type, LocalDateTime date, String category) {
}

