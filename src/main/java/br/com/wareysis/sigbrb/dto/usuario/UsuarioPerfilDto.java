package br.com.wareysis.sigbrb.dto.usuario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioPerfilDto(

        UUID idUsuario,
        String nomeUsuario,
        String idPerfil,
        String nomePerfil,
        LocalDateTime dhCriacao,
        LocalDateTime dhAlteracao

) implements Serializable {

}
