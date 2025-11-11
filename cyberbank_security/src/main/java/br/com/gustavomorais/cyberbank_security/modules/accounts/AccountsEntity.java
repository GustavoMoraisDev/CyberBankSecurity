package br.com.gustavomorais.cyberbank_security.modules.accounts;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID; // Importação para usar UUID

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import jakarta.persistence.Id;

@Data
@Entity(name = "accounts")
public class AccountsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    
    private Integer accountNumber;

    private String agencyNumber;

    @PrePersist
    public void prePersist() {
        // Gera um número de 5 dígitos caso ainda não exista
        if (accountNumber == null) {
            Random random = new Random();
            this.accountNumber = 10000 + random.nextInt(90000);
        }

        // 1. Inicializa o saldo como 0 se for nulo
        // Para valores monetários, o ideal seria usar java.math.BigDecimal
        if (this.saldo == null) {
            this.saldo = 0; // Se Number for um Integer ou Double/Float, isso funciona
        }
        
        // 2. Gera o tokenSecurity como um UUID se for nulo
        if (this.tokenSecurity == null) {
            // Gera um UUID aleatório e o converte para String
            this.tokenSecurity = UUID.randomUUID().toString();
        }



        if (agencyNumber == null) {
            this.agencyNumber = "0001";
        }

        if (limitePix == null) {
            this.limitePix = "1.500";
        }

        if (pixKey == null) {
            this.pixKey = "Chave Não Cadastrada";
        }

        if (riskIndex == null) {
            this.riskIndex = 0;
        }

        if (status == null) {
            this.status = "1";
        }

        if (tokenSecurityTransactions == null) {
            this.tokenSecurityTransactions = "Expirado";
        }

    }

    

    @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ ]+$", message = "Esse campo Nome deve conter apenas letras e espaços")
    private String name;
    
    @CPF(message = "Esse campo CPF deve conter um CPF válido")
    private String cpf;

    private String dataNascimento;

    @Email(message = "Esse campo Email deve conter um email válido")
    private String email;

    @Pattern(regexp = "\\d{11}", message = "O telefone deve ter exatamente 11 dígitos")
    private String phoneNumber;
    
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

    private String limitePix;

    private String pixKey;

    private Integer riskIndex;

    // Saldo inicializado como 0 em @PrePersist
    private Integer saldo;

    // Senha inserida pelo cliente
    private String password;
    
    // Token de segurança gerado automaticamente em @PrePersist
    private String tokenSecurity;

    private String tokenSecurityTransactions;

    // Status sempre deve ser 1,2 ou 3 que significam 1 - Ativo 2 - Inativo
    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}