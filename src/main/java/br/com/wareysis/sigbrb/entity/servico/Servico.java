package br.com.wareysis.sigbrb.entity.servico;

import java.math.BigDecimal;
import java.util.UUID;

import br.com.wareysis.sigbrb.core.entity.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SERVICOS")
public class Servico extends AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    private BigDecimal valor;

    private Short tempo;

    private Boolean disponivel;

}
