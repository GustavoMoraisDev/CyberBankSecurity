package br.com.gustavomorais.cyberbank_security.modules.preregistration.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import br.com.gustavomorais.cyberbank_security.modules.preregistration.utils.CreatePreRegistrationUtils;
import br.com.gustavomorais.cyberbank_security.modules.preregistration.PreRegistrationEntity;
import br.com.gustavomorais.cyberbank_security.modules.preregistration.PreRegistrationService;
import jakarta.validation.Valid;
import java.util.Map;

// Controlador para gerenciar rotas API de pré-cadastro
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/preRegistration")
public class PreRegistration {

    @Autowired
    private CreatePreRegistrationUtils createPreRegistrationUtils;

    public PreRegistration(PreRegistrationService preRegistrationService) {
        this.preRegistrationService = preRegistrationService;
    }

    @Autowired
    private PreRegistrationService preRegistrationService;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody PreRegistrationEntity preRegistrationEntity) {
        try {
            var result = this.createPreRegistrationUtils.execute(preRegistrationEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) { 
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/pendentes")
    public ResponseEntity<Object> getPreRegistrationsAtivas() {
      try {
        var result = preRegistrationService.findByStatus("1");
        return ResponseEntity.ok().body(result);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
   }

   @CrossOrigin(origins = "*")
@PutMapping("/analysis")
public ResponseEntity<Object> updateStatus(@RequestBody Map<String, String> body) {
    try {
        String cpf = body.get("cpf");
        String status = body.get("status");

        if (cpf == null || status == null) {
            return ResponseEntity.badRequest().body("CPF e status são obrigatórios");
        }

        // Busca o pré-cadastro pelo CPF
        PreRegistrationEntity preRegistration = preRegistrationService.findByCpf(cpf);

        if (preRegistration == null) {
            return ResponseEntity.badRequest().body("Pré-cadastro não encontrado para o CPF informado");
        }

        // Atualiza o status
        preRegistration.setStatus(status);

        // Salva a atualização
        PreRegistrationEntity updated = preRegistrationService.save(preRegistration);

        return ResponseEntity.ok(updated);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Erro ao atualizar status: " + e.getMessage());
    }
}

    

}