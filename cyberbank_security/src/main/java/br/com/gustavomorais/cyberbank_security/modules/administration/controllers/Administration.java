package br.com.gustavomorais.cyberbank_security.modules.administration.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;




import br.com.gustavomorais.cyberbank_security.modules.administration.utils.CreateAdministrationUtils;
import br.com.gustavomorais.cyberbank_security.modules.administration.AdministrationEntity;
import br.com.gustavomorais.cyberbank_security.modules.administration.AdministrationService;
import br.com.gustavomorais.cyberbank_security.modules.administration.dto.LoginRequestAdm;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/administration")
public class Administration {

    public final AdministrationService administrationService;

    public Administration(AdministrationService administrationService) {
        this.administrationService = administrationService;
    }

    @Autowired
    private CreateAdministrationUtils createAdministrationUtils;

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Object> create (@Valid @RequestBody AdministrationEntity administrationEntity) {
        try {
            var result = this.createAdministrationUtils.execute(administrationEntity);
            return ResponseEntity.ok().body(result);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Verificar administrador pelo combo Emil, cpf e senha
    @CrossOrigin(origins = "*")
    @PostMapping("/verificar")
    public ResponseEntity<Object> verificarCliente(@RequestBody LoginRequestAdm request) {
        try {
            var cliente = administrationService.findByEmailAndCpfAndPassword(
                request.getEmail(),
                request.getCpf(),
                request.getPassword()
            );

            if (cliente == null) {
                return ResponseEntity.status(404)
                .body("Dados Incorretos, Verifique seus dados.");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("id", cliente.getId());
            response.put("name", cliente.getName()); // Nome do cliente para boas-vindas

            return ResponseEntity.ok(response);

            
        } catch (Exception e) {
           return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
   @GetMapping("/{id}")
   public ResponseEntity<Object> getAdministrationById(@PathVariable("id") Long id) {
    try {
        AdministrationEntity administration = administrationService.findById(id);
        if (administration == null) {
            return ResponseEntity.status(404).body("Adm não encontrada");
        }

        return ResponseEntity.ok(administration);
        
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
   }


    


    
}