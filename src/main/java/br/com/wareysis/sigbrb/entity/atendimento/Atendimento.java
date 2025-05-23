package br.com.wareysis.sigbrb.entity.atendimento;

import java.time.LocalDateTime;
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
@Table(name = "ATENDIMENTOS")
public class Atendimento extends AbstractEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID idProfissional;

    private UUID idServico;

    private LocalDateTime dhInicio;

    private LocalDateTime dhFim;

    private UUID idCliente;

    private String nomeCliente;

    private String idStatusAtendimento;

    private String idStatusPagamento;

    private String idMetodoPagamento;

}
