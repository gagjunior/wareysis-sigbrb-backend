package br.com.wareysis.sigbrb.dto.usuario;

import java.io.Serializable;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record UsuarioUpdateDto(
        @NotNull
        UUID id,
        String email,
        String nomeCompleto,
        String cpf,
        String telefone,
        Boolean habilitado,
        Boolean alterarSenha
) implements Serializable {

}
