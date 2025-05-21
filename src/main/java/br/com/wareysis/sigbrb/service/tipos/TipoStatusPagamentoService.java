package br.com.wareysis.sigbrb.service.tipos;

import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.entity.tipos.TipoStatusPagamento;
import br.com.wareysis.sigbrb.mapper.tipos.TipoStatusPagamentoMapper;
import br.com.wareysis.sigbrb.repository.tipos.TipoStatusPagamentoRepository;

@Service
public class TipoStatusPagamentoService extends AbstractTipoService<TipoStatusPagamento> {

    TipoStatusPagamentoService(TipoStatusPagamentoRepository repository, TipoStatusPagamentoMapper mapper) {

        super(repository, mapper);
    }

}
