package br.com.gustavomorais.cyberbank_security.modules.candidate;
 
 import java.util.UUID;
 import lombok.Data;

 @Data
 public class CandidateEntity {

    private UUID contaId;
    private String name;
    private String cpf;
    private String dataNascimento;
    private String email;
    private String phoneNumber;
    private String estadoCivil;
    private String profissao;
    private String rendaMensal;
    private String cep;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
    private String complemento;
    



}