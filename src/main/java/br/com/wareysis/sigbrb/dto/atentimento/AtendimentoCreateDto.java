package br.com.wareysis.sigbrb.dto.atentimento;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AtendimentoCreateDto(

        @NotNull(message = "ID do profissional é obrigatório")
        UUID idProfissional,

        @NotNull(message = "ID do serviço é obrigatório")
        UUID idServico,

        @NotNull(message = "Data do agendamento é obrigatória")
        LocalDateTime dhInicio,

        UUID idCliente,

        @NotBlank(message = "Nome do cliente não pode ser null ou em branco")
        String nomeCliente,

        String idStatusAtendimento,

        String idStatusPagamento,

        String idMetodoPagamento
) {

    public AtendimentoCreateDto {

        if (idStatusAtendimento == null) {
            idStatusAtendimento = "AG";
        }

        if (idStatusPagamento == null) {
            idStatusPagamento = "EA";
        }

        if (idMetodoPagamento == null) {
            idMetodoPagamento = "DN";
        }
    }

}
