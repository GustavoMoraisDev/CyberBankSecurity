package br.com.gustavomorais.cyberbank_security.modules.accounts.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ValidarSenhaDTO {

    @NotBlank(message = "Senha não pode ser vazia")
    private String senha;

    @NotBlank(message = "Token não pode ser vazio")
    private String token;
}
