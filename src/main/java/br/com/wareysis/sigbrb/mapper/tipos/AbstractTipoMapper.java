package br.com.wareysis.sigbrb.mapper.tipos;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.AbstractEntityTipo;

public abstract class AbstractTipoMapper<T extends AbstractEntityTipo> {

    public TipoDto toDto(T tipo) {

        return new TipoDto(tipo.getId(), tipo.getNome());
    }

    public abstract T toEntity(TipoDto dto);

}
