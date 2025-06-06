package br.com.wareysis.sigbrb.dto.atentimento;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.wareysis.sigbrb.dto.servico.ServicoAtendimentoDto;
import br.com.wareysis.sigbrb.dto.usuario.ProfissionalAtendimentoDto;

public record AtendimentoResponseDto(
        UUID id,

        ProfissionalAtendimentoDto profissional,

        ServicoAtendimentoDto servico,

        LocalDateTime dhInicio,

        LocalDateTime dhFim,

        UUID idCliente,

        String nomeCliente,

        String idStatusAtendimento,

        String idStatusPagamento,

        String idMetodoPagamento,

        LocalDateTime dhCriacao,

        LocalDateTime dhAlteracao

) {

}
