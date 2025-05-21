package br.com.wareysis.sigbrb.service.tipos;

import java.util.List;

import org.springframework.http.HttpStatus;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.entity.tipos.AbstractEntityTipo;
import br.com.wareysis.sigbrb.exception.TipoPerfilException;
import br.com.wareysis.sigbrb.mapper.tipos.AbstractTipoMapper;
import br.com.wareysis.sigbrb.repository.tipos.AbstractTipoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractTipoService<T extends AbstractEntityTipo> {

    private final AbstractTipoRepository<T> repository;

    private final AbstractTipoMapper<T> mapper;

    public List<TipoDto> findAll() {

        return repository
                .findAll().stream().map(mapper::toDto).toList();

    }

    public List<TipoDto> findByNome(String nome) {

        return repository
                .findAllByNomeContainingIgnoreCase(nome).stream().map(mapper::toDto).toList();
    }

    public TipoDto findById(String id) {

        return repository
                .findById(id).map(mapper::toDto)
                .orElseThrow(() -> new TipoPerfilException("Tipo de perfil com ID: %s não encontrado".formatted(id), HttpStatus.BAD_REQUEST));
    }

}
