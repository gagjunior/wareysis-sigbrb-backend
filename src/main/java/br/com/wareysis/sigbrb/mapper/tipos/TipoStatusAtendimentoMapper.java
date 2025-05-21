package br.com.wareysis.sigbrb.mapper.tipos;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.TipoStatusAtendimento;

@Component
public class TipoStatusAtendimentoMapper extends AbstractTipoMapper<TipoStatusAtendimento> {

    @Override
    public TipoStatusAtendimento toEntity(TipoDto dto) {

        return new TipoStatusAtendimento(dto.id(), dto.nome());
    }
}
