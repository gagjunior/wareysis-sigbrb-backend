package br.com.wareysis.sigbrb.dto.servico;

import java.math.BigDecimal;
import java.util.UUID;

public record ServicoAtendimentoDto(

        UUID id,

        String nome,

        BigDecimal valor,

        Short tempo
) {

}
