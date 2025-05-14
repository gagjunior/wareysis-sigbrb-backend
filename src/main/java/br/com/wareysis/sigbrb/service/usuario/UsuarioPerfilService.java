package br.com.wareysis.sigbrb.service.usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.dto.tipos.TipoPerfilDto;
import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfil;
import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfilId;
import br.com.wareysis.sigbrb.exception.TipoPerfilException;
import br.com.wareysis.sigbrb.repository.usuario.UsuarioPerfilRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioPerfilService {

    private final UsuarioPerfilRepository repository;

    public List<UsuarioPerfil> create(UUID idUsuario, List<TipoPerfilDto> perfis) {

        try {

            if (perfis == null || perfis.isEmpty()) {
                throw new TipoPerfilException("Lista de perfis não pode ser null ou vazia", HttpStatus.BAD_REQUEST);
            }

            List<UsuarioPerfil> usuarioPerfilList = new ArrayList<>();

            perfis.forEach(perfil -> {

                UsuarioPerfil usuarioPerfil = UsuarioPerfil.builder()
                        .id(new UsuarioPerfilId(idUsuario, perfil.id()))
                        .build();

                usuarioPerfilList.add(usuarioPerfil);

            });

            return repository.saveAllAndFlush(usuarioPerfilList);

        } catch (Exception e) {

            throw new TipoPerfilException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public List<UsuarioPerfil> findByUsuarioId(UUID idUsuario) {

        return repository.findById_IdUsuario(idUsuario).orElse(Collections.emptyList());

    }

}
