package br.com.wareysis.sigbrb.dto.servico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServicoDto(

        UUID id,

        @NotNull(message = "Nome do serviço não pode ser null")
        @NotBlank(message = "Nome do serviço não pode ser vazio ou em branco")
        String nome,

        @Min(value = 1, message = "Valor do serviço deve ser maior que zero")
        @NotNull(message = "Valor do serviço não pode ser null")
        BigDecimal valor,

        @Min(value = 1, message = "Tempo do serviço deve ser maior que zero")
        @NotNull(message = "Tempo do serviço não pode ser null")
        Short tempo,

        Boolean disponivel,

        LocalDateTime dhCriacao,

        LocalDateTime dhAlteracao

) {

}
