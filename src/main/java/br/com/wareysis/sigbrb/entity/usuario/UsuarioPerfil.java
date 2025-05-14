package br.com.wareysis.sigbrb.entity.usuario;

import br.com.wareysis.sigbrb.core.entity.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "USUARIOS_PERFIL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioPerfil extends AbstractEntity {

    @EmbeddedId
    private UsuarioPerfilId id;

}
