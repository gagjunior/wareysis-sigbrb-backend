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
@Table(name = "TIPOS_METODO_PAGAMENTO")
@Immutable
public class TipoMetodoPagamento extends AbstractEntityTipo implements Serializable {

    public TipoMetodoPagamento(String id, String nome) {

        super(id, nome);
    }

}
