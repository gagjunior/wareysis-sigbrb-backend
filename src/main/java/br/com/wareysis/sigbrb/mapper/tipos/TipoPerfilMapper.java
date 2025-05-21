package br.com.wareysis.sigbrb.mapper.tipos;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.TipoPerfil;

@Component
public class TipoPerfilMapper extends AbstractTipoMapper<TipoPerfil> {

    @Override
    public TipoPerfil toEntity(TipoDto dto) {

        return new TipoPerfil(dto.id(), dto.nome());
    }
}
