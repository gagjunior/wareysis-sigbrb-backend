package br.com.wareysis.sigbrb.dto.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import br.com.wareysis.sigbrb.dto.tipos.TipoPerfilDto;

public record UsuarioResponseDto(

        UUID id,
        String email,
        String nomeCompleto,
        String cpf,
        String telefone,
        Boolean emailVerificado,
        Boolean habilitado,
        Boolean alterarSenha,
        List<TipoPerfilDto> perfis,
        LocalDateTime dhCriacao,
        LocalDateTime dhAlteracao

) implements Serializable {

}
