package br.com.gustavomorais.cyberbank_security.modules.accounts.dto;

public class LoginRequest {
    private String agencyNumber;
    private Integer accountNumber;
    private String cpf;

    // getters e setters
    public String getAgencyNumber() { return agencyNumber; }
    public void setAgencyNumber(String agencyNumber) { this.agencyNumber = agencyNumber; }

    public Integer getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Integer accountNumber) { this.accountNumber = accountNumber; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
}
