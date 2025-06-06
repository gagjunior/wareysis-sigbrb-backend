package br.com.wareysis.sigbrb.mapper.servico;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.servico.ServicoAtendimentoDto;
import br.com.wareysis.sigbrb.dto.servico.ServicoDto;
import br.com.wareysis.sigbrb.entity.servico.Servico;

@Component
public class ServicoMapper {

    public Servico toEntity(ServicoDto dto) {

        return Servico.builder()
                .id(dto.id())
                .nome(dto.nome())
                .valor(dto.valor())
                .tempo(dto.tempo())
                .disponivel(dto.disponivel())
                .build();
    }

    public ServicoDto toDto(Servico servico) {

        return new ServicoDto(
                servico.getId(),
                servico.getNome(),
                servico.getValor(),
                servico.getTempo(),
                servico.getDisponivel(),
                servico.getDhCriacao(),
                servico.getDhAlteracao()
        );
    }

    public ServicoAtendimentoDto toAtendimentoDto(Servico servico) {

        return new ServicoAtendimentoDto(
                servico.getId(),
                servico.getNome(),
                servico.getValor(),
                servico.getTempo()
        );
    }

}
