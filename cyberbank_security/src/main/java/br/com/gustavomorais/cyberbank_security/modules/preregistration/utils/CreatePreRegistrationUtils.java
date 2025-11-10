package br.com.gustavomorais.cyberbank_security.modules.preregistration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavomorais.cyberbank_security.modules.preregistration.PreRegistrationEntity;
import br.com.gustavomorais.cyberbank_security.modules.preregistration.PreRegistrationRepository;

@Service
public class CreatePreRegistrationUtils {
    @Autowired
    private PreRegistrationRepository preRegistrationRepository; 

    public PreRegistrationEntity execute(PreRegistrationEntity PreRegistrationEntity) {
    // Verificando se já existe um usuário com o mesmo CPF ou Email, se existir lança uma exceção/erro
        this.preRegistrationRepository
        .findByCpfOrEmail(PreRegistrationEntity.getCpf(), PreRegistrationEntity.getEmail())
        .ifPresent((user) -> {
            throw new RuntimeException("Esse usuário já possui cadastro conosco, entre em contato com o Suporte!");
        });

        // Salvando a entidade de pré-cadastro no banco de dados
        return this.preRegistrationRepository.save(PreRegistrationEntity);
        
    }
}