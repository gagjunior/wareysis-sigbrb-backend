package br.com.wareysis.sigbrb.mapper.tipos;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.TipoStatusPagamento;

@Component
public class TipoStatusPagamentoMapper extends AbstractTipoMapper<TipoStatusPagamento> {

    @Override
    public TipoStatusPagamento toEntity(TipoDto dto) {

        return new TipoStatusPagamento(dto.id(), dto.nome());
    }
}
