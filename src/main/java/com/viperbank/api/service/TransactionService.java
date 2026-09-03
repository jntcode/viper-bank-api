package com.viperbank.api.service;

import com.viperbank.api.dto.TransactionDTO;
import com.viperbank.api.dto.TransferDTO;
import com.viperbank.api.model.Account;
import com.viperbank.api.model.Transaction;
import com.viperbank.api.model.User;
import com.viperbank.api.repository.TransactionRepository;
import com.viperbank.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<TransactionDTO> findByUserId(Long userId) {
        return transactionRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(TransactionDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public TransactionDTO deposit(Long userId, BigDecimal amount, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado com id: " + userId));

        Account account = user.getAccount();
        account.setBalance(account.getBalance().add(amount));

        Transaction transaction = Transaction.builder()
                .user(user)
                .type(Transaction.TransactionType.DEPOSIT)
                .description(description != null ? description : "Deposito")
                .amount(amount)
                .balanceAfter(account.getBalance())
                .build();

        userRepository.save(user);
        transactionRepository.save(transaction);

        return TransactionDTO.fromEntity(transaction);
    }

    @Transactional
    public TransactionDTO withdraw(Long userId, BigDecimal amount, String description) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario nao encontrado com id: " + userId));

        Account account = user.getAccount();

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo atual: R$ " + account.getBalance());
        }

        account.setBalance(account.getBalance().subtract(amount));

        Transaction transaction = Transaction.builder()
                .user(user)
                .type(Transaction.TransactionType.WITHDRAW)
                .description(description != null ? description : "Saque")
                .amount(amount)
                .balanceAfter(account.getBalance())
                .build();

        userRepository.save(user);
        transactionRepository.save(transaction);

        return TransactionDTO.fromEntity(transaction);
    }

    @Transactional
    public void transfer(TransferDTO transferDTO) {
        if (transferDTO.getFromUserId().equals(transferDTO.getToUserId())) {
            throw new RuntimeException("Nao e possivel transferir para si mesmo");
        }

        User fromUser = userRepository.findById(transferDTO.getFromUserId())
                .orElseThrow(() -> new RuntimeException("Usuario remetente nao encontrado"));

        User toUser = userRepository.findById(transferDTO.getToUserId())
                .orElseThrow(() -> new RuntimeException("Usuario destinatario nao encontrado"));

        Account fromAccount = fromUser.getAccount();
        Account toAccount = toUser.getAccount();

        if (fromAccount.getBalance().compareTo(transferDTO.getAmount()) < 0) {
            throw new RuntimeException("Saldo insuficiente. Saldo atual: R$ " + fromAccount.getBalance());
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDTO.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(transferDTO.getAmount()));

        String desc = transferDTO.getDescription() != null ? transferDTO.getDescription() : "Transferencia";

        Transaction outTransaction = Transaction.builder()
                .user(fromUser)
                .type(Transaction.TransactionType.TRANSFER_OUT)
                .description(desc + " -> " + toUser.getName())
                .amount(transferDTO.getAmount())
                .balanceAfter(fromAccount.getBalance())
                .build();

        Transaction inTransaction = Transaction.builder()
                .user(toUser)
                .type(Transaction.TransactionType.TRANSFER_IN)
                .description(desc + " <- " + fromUser.getName())
                .amount(transferDTO.getAmount())
                .balanceAfter(toAccount.getBalance())
                .build();

        userRepository.save(fromUser);
        userRepository.save(toUser);
        transactionRepository.save(outTransaction);
        transactionRepository.save(inTransaction);
    }
}
