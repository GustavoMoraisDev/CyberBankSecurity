package br.com.gustavomorais.cyberbank_security.modules.transactions.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gustavomorais.cyberbank_security.modules.transactions.TransactionsEntity;
import br.com.gustavomorais.cyberbank_security.modules.transactions.TransactionsService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class Transactions {

    private final TransactionsService transactionsService;

    public Transactions(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TransactionsEntity transaction) {
        try {
            TransactionsEntity savedTransaction = transactionsService.create(transaction);
            return ResponseEntity.ok(savedTransaction);
        } catch (RuntimeException e) {
            // Erros previstos (saldo insuficiente, conta não encontrada, etc.)
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            // Erros genéricos ou inesperados
            return ResponseEntity.internalServerError().body("Erro ao criar transação: " + e.getMessage());
        }
    }
}
