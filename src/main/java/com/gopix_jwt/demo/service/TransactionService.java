package com.gopix_jwt.demo.service;

import com.gopix_jwt.demo.dto.CreateTransactionDTO;
import com.gopix_jwt.demo.dto.TransactionResponseDTO;
import com.gopix_jwt.demo.entity.Transaction;
import com.gopix_jwt.demo.entity.User;
import com.gopix_jwt.demo.errors.BadRequestException;
import com.gopix_jwt.demo.repository.TransactionRepository;
import com.gopix_jwt.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transaction createTransaction(CreateTransactionDTO dto) {
        User userReceive = userRepository.findById(dto.userReceiveId())
                .orElseThrow(() -> new BadRequestException("User receiver not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userProvider = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User provider not found"));

        if ("transfer".equalsIgnoreCase(dto.type())) {
            Double availableBalance = calculateAvailableBalance(userProvider.getId());
            if (availableBalance < dto.amount()) {
                throw new BadRequestException("Insufficient balance to complete the transfer");
            }
        }

        Transaction transaction = new Transaction(
                userReceive,
                userProvider,
                LocalDateTime.now(),
                dto.amount(),
                dto.type()
        );
        return transactionRepository.save(transaction);
    }

    public Double calculateAvailableBalance(Long userId) {
        Double deposits = transactionRepository.sumAmountByUserAndType(userId, "deposit").orElse(0.0);
        Double transfers = transactionRepository.sumAmountByUserAndType(userId, "transfer").orElse(0.0);
        return deposits - transfers;
    }

    public List<TransactionResponseDTO> getUserTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserReceiveOrUserProvider(user, user);

        return transactions.stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getUserReceive().getEmail(),
                        transaction.getUserProvider().getEmail(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getDate()
                ))
                .collect(Collectors.toList());
    }
}
