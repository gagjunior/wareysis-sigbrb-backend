package br.com.wareysis.sigbrb.entity.tipos;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "TIPOS_PERFIL_USUARIO")
@Immutable
public class TipoPerfilUsuario extends AbstractEntityTipo implements Serializable {

    public TipoPerfilUsuario(String id, String nome) {

        super(id, nome);
    }

}
