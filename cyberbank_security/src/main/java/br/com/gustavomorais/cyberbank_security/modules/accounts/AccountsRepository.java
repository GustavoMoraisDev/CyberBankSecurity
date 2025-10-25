package br.com.gustavomorais.cyberbank_security.modules.accounts;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<AccountsEntity, Long> {

    boolean existsByAccountNumber(Integer accountNumber);

    // Método para encontrar uma conta pelo CPF ou Email
    Optional<AccountsEntity> findByCpfOrEmail(String cpf, String email);

    // NOVO: método para buscar pelo combo Agência + Conta + CPF
    Optional<AccountsEntity> findByAgencyNumberAndAccountNumberAndCpf(
            String agencyNumber, Integer accountNumber, String cpf
    );
}
