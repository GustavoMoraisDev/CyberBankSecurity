package br.com.gustavomorais.cyberbank_security.modules.administration.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.gustavomorais.cyberbank_security.modules.administration.AdministrationEntity;
import br.com.gustavomorais.cyberbank_security.modules.administration.AdministrationRepository;

@Service
public class CreateAdministrationUtils {
    @Autowired
    private AdministrationRepository administrationRepository;

    public AdministrationEntity execute(AdministrationEntity AdministrationEntity) {
        // Verificando se já existe um usuário com o mesmo CPF ou Email, se existir lança uma exceção/erro
        this.administrationRepository
        .findByCpfOrEmail(AdministrationEntity.getCpf(), AdministrationEntity.getEmail())
        .ifPresent((user) -> {
            throw new RuntimeException("Esse Usuário Adm já existe");
        });

        return this.administrationRepository.save(AdministrationEntity);

    }
}
