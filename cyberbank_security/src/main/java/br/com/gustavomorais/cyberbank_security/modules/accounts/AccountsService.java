package br.com.gustavomorais.cyberbank_security.modules.accounts;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class AccountsService {

    private final AccountsRepository accountsRepository;

    public AccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    public AccountsEntity createAccount(AccountsEntity account) {
        // Garante que o número é único antes de salvar
        while (accountsRepository.existsByAccountNumber(account.getAccountNumber())) {
            // Gera outro número se o atual já existir
            account.prePersist();
        }

        return accountsRepository.save(account);
    }

    // Para o método Get /all-accounts
    public List<AccountsEntity> getAllAccounts() {
        return accountsRepository.findAll();
    }

    // NOVO: busca pelo combo Agência + Conta + CPF
    public AccountsEntity findByAgencyAndAccountAndCpf(String agencyNumber, Integer accountNumber, String cpf) {
        return accountsRepository
                .findByAgencyNumberAndAccountNumberAndCpf(agencyNumber, accountNumber, cpf)
                .orElse(null);
    }

    public boolean validarSenhaEToken(UUID accountId, String senha, String token) throws Exception {
    AccountsEntity account = accountsRepository.findById(accountId)
            .orElseThrow(() -> new Exception("Conta não encontrada"));

    // Verifica se senha e token conferem
    return senha.equals(account.getPassword()) && token.equals(account.getTokenSecurity());
     }

     public AccountsEntity findById(UUID id) {
     return accountsRepository.findById(id).orElse(null);
     }

     // Cadastrar ou atualizar a chave Pix
    public void registerOrUpdatePixKey(Integer accountNumber, String pixKey) {
        Optional<AccountsEntity> accountOpt = accountsRepository.findByAccountNumber(accountNumber);

        if (accountOpt.isEmpty()) {
            throw new RuntimeException("Conta não encontrada");
        }

        AccountsEntity account = accountOpt.get();
        account.setPixKey(pixKey);
        accountsRepository.save(account);
    }

     
}
