package br.com.wareysis.sigbrb.service.tipos;

import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.entity.tipos.TipoStatusAtendimento;
import br.com.wareysis.sigbrb.mapper.tipos.TipoStatusAtendimentoMapper;
import br.com.wareysis.sigbrb.repository.tipos.TipoStatusAtendimentoRepository;

@Service
public class TipoStatusAtendimentoService extends AbstractTipoService<TipoStatusAtendimento> {

    TipoStatusAtendimentoService(TipoStatusAtendimentoRepository repository, TipoStatusAtendimentoMapper mapper) {

        super(repository, mapper);
    }

}
