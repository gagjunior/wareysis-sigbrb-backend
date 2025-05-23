package br.com.wareysis.sigbrb.mapper.tipos;

import org.springframework.stereotype.Component;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.TipoPerfilUsuario;

@Component
public class TipoPerfilMapper extends AbstractTipoMapper<TipoPerfilUsuario> {

    @Override
    public TipoPerfilUsuario toEntity(TipoDto dto) {

        return new TipoPerfilUsuario(dto.id(), dto.nome());
    }
}
