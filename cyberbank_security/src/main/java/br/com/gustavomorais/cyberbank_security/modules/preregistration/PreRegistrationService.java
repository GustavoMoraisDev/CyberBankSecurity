package br.com.gustavomorais.cyberbank_security.modules.preregistration;

import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class PreRegistrationService {
     private final PreRegistrationRepository preRegistrationRepository;

    public PreRegistrationService(PreRegistrationRepository preRegistrationRepository) {
        this.preRegistrationRepository = preRegistrationRepository;
    }

    public List<PreRegistrationEntity> findByStatus(String status) {
        return preRegistrationRepository.findByStatus(status);
    }

    public PreRegistrationEntity findByCpf(String cpf) {
    return preRegistrationRepository.findByCpf(cpf).orElse(null);
    }

    public PreRegistrationEntity save(PreRegistrationEntity preRegistration) {
    return preRegistrationRepository.save(preRegistration);
    }
}
