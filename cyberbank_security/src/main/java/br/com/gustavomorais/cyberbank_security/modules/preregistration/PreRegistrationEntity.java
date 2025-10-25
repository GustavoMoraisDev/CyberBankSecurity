package br.com.gustavomorais.cyberbank_security.modules.preregistration;
 

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
 @Entity(name = "pre_registration")
 public class PreRegistrationEntity {

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
    
    private String estadoCivil;
    
    private String profissao;

    @Pattern(regexp = "^[\\d.,]+$", message = "Esse campo Renda Mensal deve conter apenas números e pode incluir '.' e ','")
    private String rendaMensal;

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "Esse campo CEP deve estar no formato XXXXX-XXX")
    private String cep;
    
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;

    // Status do Pré Cadastro sempre deve ser 1,2 ou 3 que significam 1 - Pendente 2 - Aprovado 3 - Rejeitado
    @Pattern(regexp = "^[1-3]$", message = "Esse campo Status deve conter apenas os valores 1, 2 ou 3")
    private String status;

    private String password;
    
    @CreationTimestamp
    private LocalDateTime createdAt;



}