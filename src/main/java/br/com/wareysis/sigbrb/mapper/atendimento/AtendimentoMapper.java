package br.com.wareysis.sigbrb.mapper.atendimento;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoRequestDto;
import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;

@Component
public class AtendimentoMapper {

    public Atendimento toEntity(AtendimentoRequestDto createDto) {

        return Atendimento.builder()
                .idProfissional(createDto.idProfissional())
                .idServico(createDto.idServico())
                .dhInicio(createDto.dhInicio())
                .idCliente(createDto.idCliente())
                .nomeCliente(createDto.nomeCliente())
                .idStatusAtendimento(createDto.idStatusAtendimento() == null ? "AG" : createDto.idStatusAtendimento())
                .idStatusPagamento(createDto.idStatusPagamento() == null ? "EA" : createDto.idStatusPagamento())
                .idMetodoPagamento(createDto.idMetodoPagamento() == null ? "DN" : createDto.idMetodoPagamento())
                .build();

    }

}
