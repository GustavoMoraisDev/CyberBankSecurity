package br.com.gustavomorais.cyberbank_security.modules.transactions;

import java.util.HashMap;
import java.util.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsEntity;
import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsRepository;

@Service
public class TransactionsService {

    private final TransactionsRepository transactionsRepository;
    private final AccountsRepository accountsRepository;

    public TransactionsService(TransactionsRepository transactionsRepository,
                               AccountsRepository accountsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }

    @Transactional
    public TransactionsEntity create(TransactionsEntity transaction) {
        // Busca conta pagadora
        AccountsEntity pagador = accountsRepository.findByAccountNumber(
                Integer.parseInt(transaction.getAccountPayment()))
            .orElseThrow(() -> new RuntimeException("Conta pagadora não encontrada"));

        // Busca conta recebedora
        AccountsEntity recebedor = accountsRepository.findByAccountNumber(
                Integer.parseInt(transaction.getAccountReceivable()))
            .orElseThrow(() -> new RuntimeException("Conta recebedora não encontrada"));

        // Impede transferência para a própria conta
        if (pagador.getAccountNumber().equals(recebedor.getAccountNumber())) {
            throw new RuntimeException("Não é permitido transferir para a mesma conta");
        }

        // Verifica saldo suficiente
        if (pagador.getSaldo() < transaction.getValue()) {
            throw new RuntimeException("Saldo insuficiente");
        }

        // Atualiza saldos
        pagador.setSaldo(pagador.getSaldo() - transaction.getValue());
        recebedor.setSaldo(recebedor.getSaldo() + transaction.getValue());

        // Salva as duas contas atualizadas
        accountsRepository.save(pagador);
        accountsRepository.save(recebedor);

        // Salva a transação
        return transactionsRepository.save(transaction);
    }

    public Map<String, Object> findByAccountNumber(String accountNumber) {
        
        List<TransactionsEntity> saidas = transactionsRepository.findByAccountPayment(accountNumber);

        
        List<TransactionsEntity> entradas = transactionsRepository.findByAccountReceivable(accountNumber);

        
        Map<String, Object> result = new HashMap<>();
        result.put("entradas", entradas);
        result.put("saidas", saidas);
        return result;
    }


}
