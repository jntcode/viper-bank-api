package com.viperbank.api.controller;

import com.viperbank.api.dto.TransactionDTO;
import com.viperbank.api.dto.TransferDTO;
import com.viperbank.api.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Tag(name = "Transactions Controller", description = "API para gerenciar transacoes bancarias.")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/users/{userId}/transactions")
    @Operation(summary = "Listar transacoes do usuario", description = "Retorna o historico de transacoes de um usuario.")
    public ResponseEntity<List<TransactionDTO>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.findByUserId(userId));
    }

    @PostMapping("/users/{userId}/deposit")
    @Operation(summary = "Depositar", description = "Realiza um deposito na conta do usuario.")
    public ResponseEntity<TransactionDTO> deposit(
            @PathVariable Long userId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.deposit(userId, amount, description));
    }

    @PostMapping("/users/{userId}/withdraw")
    @Operation(summary = "Sacar", description = "Realiza um saque na conta do usuario.")
    public ResponseEntity<TransactionDTO> withdraw(
            @PathVariable Long userId,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String description) {
        return ResponseEntity.ok(transactionService.withdraw(userId, amount, description));
    }

    @PostMapping("/transfers")
    @Operation(summary = "Transferir", description = "Transfere valor de um usuario para outro.")
    public ResponseEntity<Void> transfer(@RequestBody TransferDTO transferDTO) {
        transactionService.transfer(transferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
