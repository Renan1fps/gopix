package com.gopix_jwt.demo.service;

import com.gopix_jwt.demo.dto.CreateTransactionDTO;
import com.gopix_jwt.demo.dto.TransactionResponseDTO;
import com.gopix_jwt.demo.entity.Transaction;
import com.gopix_jwt.demo.entity.User;
import com.gopix_jwt.demo.enums.TransactionType;
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
        User userReceive = userRepository.findByDocument(dto.userReceiveDocument())
                .orElseThrow(() -> new BadRequestException("Destinatario não encontrado"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userProvider = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));

        if (dto.type().equals(TransactionType.TRANSFER)) {
            Double availableBalance = calculateAvailableBalance(userProvider.getId());
            if (availableBalance < dto.amount()) {
                throw new BadRequestException("Saldo insuficiente para transaferencias.");
            }
            Transaction transactionProvider = new Transaction(
                    userProvider,
                    userReceive,
                    LocalDateTime.now(),
                    dto.amount(),
                    TransactionType.DEPOSIT,
                    dto.category()
            );
            Transaction transactionReceive = new Transaction(
                    userReceive,
                    userProvider,
                    LocalDateTime.now(),
                    dto.amount(),
                    dto.type(),
                    dto.category()
            );
            transactionRepository.save(transactionProvider);
            return transactionRepository.save(transactionReceive);
        }

        Transaction transaction = new Transaction(
                userReceive,
                userProvider,
                LocalDateTime.now(),
                dto.amount(),
                dto.type(),
                dto.category()
        );
        return transactionRepository.save(transaction);
    }

    public Double calculateAvailableBalance(Long userId) {
        Double deposits = transactionRepository.sumAmountByUserAndType(userId, TransactionType.DEPOSIT).orElse(0.0);
        Double transfers = transactionRepository.sumAmountByUserAndType(userId, TransactionType.TRANSFER).orElse(0.0);
        return deposits - transfers;
    }

    public List<TransactionResponseDTO> getUserTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException("User not found"));

        List<Transaction> transactions = transactionRepository.findByUserProvider(user);

        return transactions.stream()
                .map(transaction -> new TransactionResponseDTO(
                        transaction.getId(),
                        transaction.getUserReceive() != null ? transaction.getUserReceive().getDocument() : null,
                        transaction.getUserProvider().getEmail(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getDate(),
                        transaction.getCategory()
                ))
                .collect(Collectors.toList());
    }

    public Transaction deposit(CreateTransactionDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userProvider = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new BadRequestException("Conta não encontrada"));

        Transaction transaction = new Transaction(
                null,
                userProvider,
                LocalDateTime.now(),
                dto.amount(),
                TransactionType.DEPOSIT,
                dto.category()
        );
        return transactionRepository.save(transaction);
    }
}
