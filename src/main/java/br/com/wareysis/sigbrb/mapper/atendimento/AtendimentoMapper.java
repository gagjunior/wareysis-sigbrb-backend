package br.com.wareysis.sigbrb.mapper.atendimento;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoCreateDto;
import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;

@Component
public class AtendimentoMapper {

    public Atendimento toEntity(AtendimentoCreateDto createDto) {

        return Atendimento.builder()
                .idProfissional(createDto.idProfissional())
                .idServico(createDto.idServico())
                .dhInicio(createDto.dhInicio())
                .idCliente(createDto.idCliente())
                .nomeCliente(createDto.nomeCliente())
                .idStatusAtendimento(createDto.idStatusAtendimento())
                .idStatusPagamento(createDto.idStatusPagamento())
                .idMetodoPagamento(createDto.idMetodoPagamento())
                .build();

    }

}
