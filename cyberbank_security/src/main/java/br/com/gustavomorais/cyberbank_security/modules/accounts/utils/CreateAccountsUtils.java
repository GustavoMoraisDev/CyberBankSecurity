package br.com.gustavomorais.cyberbank_security.modules.accounts.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsEntity;
import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsRepository;



@Service
public class CreateAccountsUtils {
    @Autowired
    private AccountsRepository accountsRepository;

    public AccountsEntity execute(AccountsEntity accountsEntity) {
        // Verificando se já existe uma conta com o mesmo número de conta, se existir lança uma exceção/erro
        this.accountsRepository
        .findByCpfOrEmail(accountsEntity.getCpf(), accountsEntity.getEmail())
        .ifPresent((account) -> {   
            throw new RuntimeException("Usuário já existe");
        });

        // Salvando a entidade de conta no banco de dados
        return this.accountsRepository.save(accountsEntity);
        
    }
}
    