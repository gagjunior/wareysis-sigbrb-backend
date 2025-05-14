package br.com.wareysis.sigbrb.mapper.tipos;

import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.dto.tipos.TipoPerfilDto;
import br.com.wareysis.sigbrb.entity.tipos.TipoPerfil;

@Service
public class TipoPerfilMapper {

    public TipoPerfilDto toDto(TipoPerfil tipoPerfil) {

        return new TipoPerfilDto(tipoPerfil.getId(), tipoPerfil.getNome());
    }

    public TipoPerfil toEntity(TipoPerfilDto dto) {

        return TipoPerfil.builder()
                .id(dto.id())
                .nome(dto.nome())
                .build();

    }

}
