package br.com.wareysis.sigbrb.entity.usuario;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record UsuarioPerfilId(

        @Column(name = "ID_USUARIO")
        UUID idUsuario,

        @Column(name = "ID_PERFIL")
        String idPerfil

) implements Serializable {}
