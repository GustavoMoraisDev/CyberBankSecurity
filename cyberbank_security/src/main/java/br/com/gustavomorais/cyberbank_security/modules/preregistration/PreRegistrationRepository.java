package br.com.gustavomorais.cyberbank_security.modules.preregistration;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repositório para a entidade de pré-cadastro
public interface PreRegistrationRepository extends JpaRepository<PreRegistrationEntity, Long> {

    // Validadando se já existe cadastro com o CPF ou Email
    Optional<PreRegistrationEntity> findByCpfOrEmail(String cpf, String email);

    //Buscando por Status 1- Pendente
    List<PreRegistrationEntity> findByStatus(String status);

    Optional<PreRegistrationEntity> findByCpf(String cpf);

}
