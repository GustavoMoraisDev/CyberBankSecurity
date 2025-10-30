package br.com.gustavomorais.cyberbank_security.modules.administration;
 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

// Entidade de pré-cadastro
 @Data
 @Entity(name = "administration")
 public class AdministrationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "Esse campo Nome deve conter apenas letras e espaços")
    private String name;
    
    @CPF(message = "Esse campo CPF deve conter um CPF válido")
    private String cpf;

    private String dataNascimento;

    @Email(message = "Esse campo Email deve conter um email válido")
    private String email;

    // Deve inserir um telefone correto com 9 digitos
    @Pattern(regexp = "\\d{11}", message = "O telefone deve ter exatamente 11 dígitos")
    private String phoneNumber;

    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;



}