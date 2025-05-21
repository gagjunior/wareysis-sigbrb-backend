package br.com.wareysis.sigbrb.service.tipos;

import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.entity.tipos.TipoMetodoPagamento;
import br.com.wareysis.sigbrb.mapper.tipos.TipoMetodoPagamentoMapper;
import br.com.wareysis.sigbrb.repository.tipos.TipoMetodoPagamentoRepository;

@Service
public class TipoMetodoPagamentoService extends AbstractTipoService<TipoMetodoPagamento> {

    TipoMetodoPagamentoService(TipoMetodoPagamentoRepository repository, TipoMetodoPagamentoMapper mapper) {

        super(repository, mapper);
    }

}
