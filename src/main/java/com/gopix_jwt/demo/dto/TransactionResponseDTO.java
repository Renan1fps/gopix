package com.gopix_jwt.demo.dto;

import java.time.LocalDateTime;

public record TransactionResponseDTO(Long transactionId, String userReceiveName, String userProviderName, Double amount, String type, LocalDateTime date) {
}

