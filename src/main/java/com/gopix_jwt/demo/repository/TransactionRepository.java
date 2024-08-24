package com.gopix_jwt.demo.repository;

import com.gopix_jwt.demo.entity.Transaction;
import com.gopix_jwt.demo.entity.User;
import com.gopix_jwt.demo.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserReceiveOrUserProvider(User userReceive, User userProvider);
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.userProvider.id = :userId AND t.type = :type")
    Optional<Double> sumAmountByUserAndType(@Param("userId") Long userId, @Param("type") TransactionType type);
}