package br.com.gustavomorais.cyberbank_security.modules.administration.dto;

public class LoginRequestAdm {
    private String email;
    private String cpf;
    private String password;

    public String getEmail() {return email; }
    public void setEmail(String email) {this.email = email; }

    public String getCpf() {return cpf; }
    public void setCpf(String cpf) {this.cpf = cpf; }

    public String getPassword() {return password; }
    public void setPasswor(String password) {this.password = password; }


}