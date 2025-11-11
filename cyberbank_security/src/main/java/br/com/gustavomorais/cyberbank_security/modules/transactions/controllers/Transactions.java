package br.com.gustavomorais.cyberbank_security.modules.transactions.controllers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsEntity;
import br.com.gustavomorais.cyberbank_security.modules.transactions.TransactionsEntity;
import br.com.gustavomorais.cyberbank_security.modules.transactions.TransactionsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.UUID;

import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsRepository;
import br.com.gustavomorais.cyberbank_security.modules.accounts.MailgunService;


@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class Transactions {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private MailgunService mailgunService;

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

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<?> getByAccount(@PathVariable String accountNumber) {
        try {
            var result = transactionsService.findByAccountNumber(accountNumber);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro ao buscar transações: " + e.getMessage());
        }
    }

    @PostMapping("/analyze")
    public ResponseEntity<?> analyze(@RequestBody TransactionsEntity transaction) {
    try {
        // Buscar conta pagadora
        AccountsEntity pagador = accountsRepository.findByAccountNumber(
                Integer.parseInt(transaction.getAccountPayment()))
            .orElseThrow(() -> new RuntimeException("Conta pagadora não encontrada."));

        // Buscar conta recebedora
        AccountsEntity recebedor = accountsRepository.findByAccountNumber(
                Integer.parseInt(transaction.getAccountReceivable()))
            .orElseThrow(() -> new RuntimeException("Conta recebedora não encontrada."));

        // Valor da transação
        int valor = transaction.getValue();
        LocalDateTime agora = LocalDateTime.now();

        
        //  VERIFICAÇÃO 1 - Horário e valor alto
        if (agora.getHour() >= 23 && valor > 2000) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "Bloqueada por Motivos de Horário e Valor Alto",
                "message", "Transaçôes Acima de R$2.000 e Após as 23h são Bloqueadas Por motivos de Seguranças"
            ));
        }

        
        // 💰 VERIFICAÇÃO 2 - Valor > 50% da renda mensal
        
        double rendaMensal = Double.parseDouble(pagador.getRendaMensal().replace(",", "."));
        if (valor >= rendaMensal * 0.7) {
            String uuid = UUID.randomUUID().toString();

            pagador.setTokenSecurityTransactions(uuid);
            accountsRepository.save(pagador);

            // Enviar e-mail com código (Mailgun)
            mailgunService.enviarEmailVerificacaoTransacao(
                pagador.getEmail(), pagador.getName(), uuid, valor
            );

            return ResponseEntity.ok(Map.of(
                "status", "202",
                "message", "Essa Transação é de Alto Valor, insira o Token enviado ao seu E-mail para Prosseguir"
            ));
        }

        
        // VERIFICAÇÃO 3 - Índice de risco do destinatário
        int riskIndex = recebedor.getRiskIndex();

        if (riskIndex >= 900) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "500",
                "message", "Para a Sua Segurança Essa Transação Está Bloqueada, o Destinatário Possui Indice de Risco Alto"
            ));
        } else if (riskIndex >= 700) {
            String uuid = UUID.randomUUID().toString();
            pagador.setTokenSecurityTransactions(uuid);
            accountsRepository.save(pagador);

            mailgunService.enviarEmailVerificacaoTransacao(
                pagador.getEmail(), pagador.getName(), uuid, valor
            );

            return ResponseEntity.ok(Map.of(
                "status", "202",
                "message", "O Destinatário Possui Indice de Risco Alto, insira o Token enviado ao seu E-mail para Prosseguir"
            ));
        } else if (riskIndex > 500) {
            return ResponseEntity.ok(Map.of(
                "status", "201",
                "message", "O Destinatário Possui um Indice de Risco, você tem certeza que deseja Finalizar essa Transação?"
            ));
        }

       
        // APROVADA
        return ResponseEntity.ok(Map.of(
            "status", "200",
            "message", "Transação aprovada"
        ));

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(Map.of(
            "status", "ERROR",
            "message", e.getMessage()
        ));
    }
}

@PostMapping("/verify-token")
public ResponseEntity<?> verifyToken(@RequestBody Map<String, String> payload) {
    try {
        String accountNumber = payload.get("accountNumber");
        String token = payload.get("token");

        AccountsEntity conta = accountsRepository.findByAccountNumber(Integer.parseInt(accountNumber))
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));

        if (conta.getTokenSecurityTransactions() != null && conta.getTokenSecurityTransactions().equals(token)) {
            // Token correto → limpa e salva
            conta.setTokenSecurityTransactions("Token Expirado");
            accountsRepository.save(conta);

            return ResponseEntity.ok(Map.of(
                "status", 200,
                "message", "Código confirmado. Transação liberada."
            ));
        } else {
            return ResponseEntity.badRequest().body(Map.of(
                "status", 400,
                "message", "Token incorreto ou expirado."
            ));
        }

    } catch (Exception e) {
        return ResponseEntity.internalServerError().body(Map.of(
            "status", "ERROR",
            "message", e.getMessage()
        ));
    }
}




}
