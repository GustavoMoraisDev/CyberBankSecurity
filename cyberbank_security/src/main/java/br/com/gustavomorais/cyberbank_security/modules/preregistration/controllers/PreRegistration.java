package br.com.gustavomorais.cyberbank_security.modules.preregistration.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import br.com.gustavomorais.cyberbank_security.modules.preregistration.utils.CreatePreRegistrationUtils;
import br.com.gustavomorais.cyberbank_security.modules.preregistration.PreRegistrationEntity;
import jakarta.validation.Valid;

// Controlador para gerenciar rotas API de pré-cadastro
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/preRegistration")
public class PreRegistration {

    @Autowired
    private CreatePreRegistrationUtils createPreRegistrationUtils;

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

    

}