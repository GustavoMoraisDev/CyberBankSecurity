package br.com.gustavomorais.cyberbank_security.modules.administration;

import org.springframework.stereotype.Service;


@Service
public class AdministrationService {
    private final AdministrationRepository administrationRepository;

    public AdministrationService(AdministrationRepository administrationRepository) {
        this.administrationRepository = administrationRepository;
    }

    public AdministrationEntity findByEmailAndCpfAndPassword(String email, String cpf, String password) {
        return administrationRepository.findByEmailAndCpfAndPassword(email, cpf, password).orElse(null);
    }

    public AdministrationEntity findById(long id) {
        return administrationRepository.findById(id).orElse(null);
    }

}
