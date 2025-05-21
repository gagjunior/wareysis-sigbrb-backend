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
@Table(name = "TIPOS_STATUS_ATENDIMENTO")
@Immutable
public class TipoStatusAtendimento extends AbstractEntityTipo implements Serializable {

    public TipoStatusAtendimento(String id, String nome) {

        super(id, nome);
    }

}
