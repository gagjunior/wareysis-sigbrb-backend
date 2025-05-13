package br.com.wareysis.sigbrb.dto.usuario;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioCreateDto(

        @NotBlank(message = "Nome não pode ser null ou em branco")
        String nomeCompleto,

        @NotBlank(message = "E-mail não pose ser null ou em branco")
        @Email(message = "E-mail inválido, verificar")
        String email,

        @NotBlank(message = "Senha não pode ser null ou em branco")
        String senha

) implements Serializable {

}
