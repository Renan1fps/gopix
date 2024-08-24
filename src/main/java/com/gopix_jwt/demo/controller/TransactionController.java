package com.gopix_jwt.demo.controller;

import com.gopix_jwt.demo.dto.CreateTransactionDTO;
import com.gopix_jwt.demo.dto.TransactionResponseDTO;
import com.gopix_jwt.demo.entity.Transaction;
import com.gopix_jwt.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<Transaction> create(@RequestBody CreateTransactionDTO transactionDTO) {
        Transaction transaction = transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponseDTO>> getAll() {
        List<TransactionResponseDTO> transactions = transactionService.getUserTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody CreateTransactionDTO transactionDTO) {
        Transaction transaction = transactionService.deposit(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
