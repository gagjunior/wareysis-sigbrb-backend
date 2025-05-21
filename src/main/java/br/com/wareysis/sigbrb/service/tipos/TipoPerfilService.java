package br.com.wareysis.sigbrb.service.tipos;

import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.entity.tipos.TipoPerfil;
import br.com.wareysis.sigbrb.mapper.tipos.TipoPerfilMapper;
import br.com.wareysis.sigbrb.repository.tipos.TipoPerfilRepository;

@Service
public class TipoPerfilService extends AbstractTipoService<TipoPerfil> {

    TipoPerfilService(TipoPerfilRepository repository, TipoPerfilMapper mapper) {

        super(repository, mapper);
    }

}
