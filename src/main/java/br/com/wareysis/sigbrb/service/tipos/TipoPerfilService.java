package br.com.wareysis.sigbrb.service.tipos;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.dto.tipos.TipoPerfilDto;
import br.com.wareysis.sigbrb.exception.TipoPerfilException;
import br.com.wareysis.sigbrb.mapper.tipos.TipoPerfilMapper;
import br.com.wareysis.sigbrb.repository.tipos.TipoPerfilRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipoPerfilService {

    private final TipoPerfilRepository repository;

    private final TipoPerfilMapper mapper;

    public List<TipoPerfilDto> findAll() {

        return repository
                .findAll().stream().map(mapper::toDto).toList();

    }

    public List<TipoPerfilDto> findByNome(String nome) {

        return repository
                .findAllByNomeContainingIgnoreCase(nome).stream().map(mapper::toDto).toList();
    }

    public TipoPerfilDto findById(String id) {

        return repository
                .findById(id).map(mapper::toDto)
                .orElseThrow(() -> new TipoPerfilException("Tipo de perfil com ID: %s não encontrado".formatted(id), HttpStatus.BAD_REQUEST));
    }

}
