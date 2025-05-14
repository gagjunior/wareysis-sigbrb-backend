package br.com.wareysis.sigbrb.service.usuario;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.sigbrb.dto.endpoint.PagedResponse;
import br.com.wareysis.sigbrb.dto.endpoint.PaginationDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioCreateDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioUpdateDto;
import br.com.wareysis.sigbrb.entity.usuario.Usuario;
import br.com.wareysis.sigbrb.exception.UsuarioException;
import br.com.wareysis.sigbrb.mapper.usuario.UsuarioMapper;
import br.com.wareysis.sigbrb.repository.usuario.UsuarioRepository;
import br.com.wareysis.sigbrb.service.firebase.FirebaseUserService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private FirebaseUserService firebaseUserService;

    @Autowired
    private UsuarioMapper mapper;

    public Usuario create(UsuarioCreateDto dto) {

        UserRecord userRecord = firebaseUserService.createUserInFirebase(dto);

        Usuario usuario = mapper.fromUserRecord(userRecord);

        return repository.save(usuario);

    }

    public Usuario update(UsuarioUpdateDto dto) {

        Usuario usuario = repository.findById(dto.id())
                .orElseThrow(() -> new UsuarioException("Usuário com ID: %s não localizado".formatted(dto.id()), HttpStatus.BAD_REQUEST));

        UserRecord userRecord = firebaseUserService.updateUserInFirebase(usuario.getEmail(), dto);

        usuario.setEmail(userRecord.getEmail());
        usuario.setNomeCompleto(userRecord.getDisplayName());
        usuario.setTelefone(userRecord.getPhoneNumber());
        usuario.setHabilitado(!userRecord.isDisabled());

        if (dto.cpf() != null && StringUtils.isNotBlank(dto.cpf())) {
            usuario.setCpf(dto.cpf());
        }

        if (dto.alterarSenha() != null) {
            usuario.setAlterarSenha(dto.alterarSenha());
        }

        return repository.save(usuario);

    }

    public void delete(UUID id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário com ID: %s não localizado".formatted(id), HttpStatus.BAD_REQUEST));

        firebaseUserService.deleteUserInFirebase(usuario.getEmail());

        repository.deleteById(id);

    }

    public PagedResponse<Usuario> findAll(PaginationDto paginationDto) {

        Page<Usuario> page = repository.findAll(paginationDto.toPageable());

        return new PagedResponse<>(page);
    }

    public PagedResponse<Usuario> findByNomeCompleto(String nomeCompleto, PaginationDto paginationDto) {

        Page<Usuario> page = repository.findByNomeCompletoContainingIgnoreCase(nomeCompleto, paginationDto.toPageable());

        return new PagedResponse<>(page);

    }

    public Usuario findByEmail(String email) {

        return repository.findByEmail(email)
                .orElseThrow(() -> new UsuarioException("Usuário com e-mail: %s não existe".formatted(email), HttpStatus.BAD_REQUEST));
    }

    public Usuario findById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário com id: %s não existe".formatted(id), HttpStatus.BAD_REQUEST));
    }

}
