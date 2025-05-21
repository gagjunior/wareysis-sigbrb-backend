package br.com.wareysis.sigbrb.entity.tipos;

import java.io.Serializable;

import org.springframework.data.annotation.Immutable;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "TIPOS_STATUS_PAGAMENTO")
@Immutable
public class TipoStatusPagamento extends AbstractEntityTipo implements Serializable {

    public TipoStatusPagamento(String id, String nome) {

        super(id, nome);
    }

}
