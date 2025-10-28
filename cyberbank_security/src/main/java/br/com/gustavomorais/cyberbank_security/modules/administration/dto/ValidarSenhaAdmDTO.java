package br.com.gustavomorais.cyberbank_security.modules.administration.dto;

import jakarta.validation.constraints.NotBlank;

public class ValidarSenhaAdmDTO {

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

}

