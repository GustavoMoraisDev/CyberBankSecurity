package br.com.gustavomorais.cyberbank_security.modules.administration;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Repositório para a entidade Administration

public interface AdministrationRepository extends JpaRepository<AdministrationEntity, Long> {

    // Validadando se já existe cadastro com o CPF ou Email
    Optional<AdministrationEntity> findByCpfOrEmail(String cpf, String email);

     Optional<AdministrationEntity> findByEmailAndCpfAndPassword(
        String email, String cpf, String password
     );
}