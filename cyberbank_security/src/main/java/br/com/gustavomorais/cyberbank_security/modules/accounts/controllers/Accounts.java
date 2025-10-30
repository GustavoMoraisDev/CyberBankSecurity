package br.com.gustavomorais.cyberbank_security.modules.accounts.controllers;

import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsEntity;
import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsRepository;
import br.com.gustavomorais.cyberbank_security.modules.accounts.AccountsService;
import br.com.gustavomorais.cyberbank_security.modules.accounts.MailgunService;
import br.com.gustavomorais.cyberbank_security.modules.accounts.utils.CreateAccountsUtils;
import br.com.gustavomorais.cyberbank_security.modules.accounts.dto.LoginRequest;
import br.com.gustavomorais.cyberbank_security.modules.accounts.dto.ValidarSenhaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.gustavomorais.cyberbank_security.modules.accounts.dto.PixKeyRequestDTO;
import java.util.UUID;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class Accounts {

    public final AccountsService accountsService;
    public final MailgunService mailgunService;
    public final AccountsRepository accountsRepository;


    public Accounts(AccountsService accountsService, MailgunService mailgunService, AccountsRepository accountsRepository ) {
        this.accountsService = accountsService;
        this.mailgunService = mailgunService;
        this.accountsRepository = accountsRepository;
    }

    @Autowired
    private CreateAccountsUtils createAccountsUtils;

    

    // Criar nova conta
    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody AccountsEntity accountsEntity) {
        try {
            var result = this.createAccountsUtils.execute(accountsEntity);
            

            // Envia e-mail após criar a conta
                mailgunService.enviarEmailContaAprovada(
                accountsEntity.getEmail(),
                accountsEntity.getName(),
                accountsEntity.getAgencyNumber(),
                accountsEntity.getAccountNumber(),
                accountsEntity.getPassword(),
                accountsEntity.getTokenSecurity()
        );

        return ResponseEntity.ok().body(result);

        } catch (Exception e) { 
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar todas as contas
    @CrossOrigin(origins = "*")
    @GetMapping
    public ResponseEntity<Object> getAllAccounts() {
        try {
            var result = this.accountsService.getAllAccounts();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Verificar cliente pelo combo Agência + Conta + CPF
    @CrossOrigin(origins = "*")
    @PostMapping("/verificar")
    public ResponseEntity<Object> verificarCliente(@RequestBody LoginRequest request) {
        try {
            var cliente = accountsService.findByAgencyAndAccountAndCpf(
                    request.getAgencyNumber(),
                    request.getAccountNumber(),
                    request.getCpf()
            );

            if (cliente == null) {
                return ResponseEntity.status(404)
                        .body("Conta não encontrada. Verifique seus dados.");
            }

            // Retorna apenas dados que serão usados na próxima tela
            Map<String, Object> response = new HashMap<>();
            response.put("id", cliente.getId());
            response.put("name", cliente.getName()); // Nome do cliente para boas-vindas

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

     @CrossOrigin(origins = "*")
    @PostMapping("/validar-senha/{id}")
    public ResponseEntity<Object> validarSenha(@PathVariable("id") UUID accountId,
                                           @Valid @RequestBody ValidarSenhaDTO dto) {
    try {
        AccountsEntity account = accountsService.findById(accountId);
        if (account == null) {
            return ResponseEntity.status(404).body("Conta não encontrada.");
        }

        boolean valid = accountsService.validarSenhaEToken(accountId, dto.getSenha(), dto.getToken());
        if (!valid) {
            return ResponseEntity.badRequest().body("Senha ou token inválidos.");
        }

        // Retorna toda a entidade (toda a linha do banco)
        return ResponseEntity.ok(account);

    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

   }    

   @CrossOrigin(origins = "*")
   @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable("id") UUID id) {
    try {
        AccountsEntity account = accountsService.findById(id);
        if (account == null) {
            return ResponseEntity.status(404).body("Conta não encontrada");
        }
        return ResponseEntity.ok(account);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }

    @CrossOrigin(origins = "*")
     @GetMapping("/pix")
      public ResponseEntity<Object> getByPixKey(@RequestParam String pixKey) {
        try {
          var accountOpt = accountsRepository.findByPixKey(pixKey);

        if (accountOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Nenhuma conta encontrada com essa chave Pix");
        }

        return ResponseEntity.ok(accountOpt.get());
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{accountNumber}/pix")
    public ResponseEntity<Object> registerOrUpdatePixKey(
    @PathVariable Integer accountNumber,
    @RequestBody PixKeyRequestDTO requestDTO 
     ) {
        try {
        
        String pixKey = requestDTO.getPixKey(); 
        
        accountsService.registerOrUpdatePixKey(accountNumber, pixKey);
        
        return ResponseEntity.ok("Chave Pix cadastrada/atualizada com sucesso");
      } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
      }
    }
    
}
