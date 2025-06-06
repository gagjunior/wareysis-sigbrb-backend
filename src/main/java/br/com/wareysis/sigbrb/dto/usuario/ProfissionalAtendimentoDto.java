package br.com.wareysis.sigbrb.dto.usuario;

import java.util.UUID;

public record ProfissionalAtendimentoDto(
        UUID idProfissional,

        String nomeProfissional
) {

}
