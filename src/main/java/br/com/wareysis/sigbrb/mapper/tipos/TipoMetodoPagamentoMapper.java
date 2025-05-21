package br.com.wareysis.sigbrb.mapper.tipos;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.TipoMetodoPagamento;

@Component
public class TipoMetodoPagamentoMapper extends AbstractTipoMapper<TipoMetodoPagamento> {

    @Override
    public TipoMetodoPagamento toEntity(TipoDto dto) {

        return new TipoMetodoPagamento(dto.id(), dto.nome());
    }
}
