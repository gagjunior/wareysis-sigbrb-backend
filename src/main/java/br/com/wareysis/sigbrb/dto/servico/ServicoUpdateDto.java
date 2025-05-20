package br.com.wareysis.sigbrb.dto.servico;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ServicoUpdateDto(

        @NotNull(message = "ID do serviço não pode ser null")
        UUID id,

        String nome,

        BigDecimal valor,

        Short tempo,

        Boolean disponivel

) {

}
