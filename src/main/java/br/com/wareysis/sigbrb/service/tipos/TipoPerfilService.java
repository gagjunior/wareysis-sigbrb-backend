package br.com.wareysis.sigbrb.service.tipos;

import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.entity.tipos.TipoPerfilUsuario;
import br.com.wareysis.sigbrb.mapper.tipos.TipoPerfilMapper;
import br.com.wareysis.sigbrb.repository.tipos.TipoPerfilRepository;

@Service
public class TipoPerfilService extends AbstractTipoService<TipoPerfilUsuario> {

    TipoPerfilService(TipoPerfilRepository repository, TipoPerfilMapper mapper) {

        super(repository, mapper);
    }

}
