package br.com.wareysis.sigbrb.service.usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.sigbrb.core.enumerations.CrudOperations;
import br.com.wareysis.sigbrb.core.log.dto.LogDto;
import br.com.wareysis.sigbrb.core.log.dto.LogInterface;
import br.com.wareysis.sigbrb.core.service.firebase.FirebaseUserService;
import br.com.wareysis.sigbrb.core.service.firebase.FirestoreLogService;
import br.com.wareysis.sigbrb.dto.endpoint.PagedResponse;
import br.com.wareysis.sigbrb.dto.endpoint.PaginationDto;
import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioCreateDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioResponseDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioUpdateDto;
import br.com.wareysis.sigbrb.entity.usuario.Usuario;
import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfil;
import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfilId;
import br.com.wareysis.sigbrb.exception.UsuarioException;
import br.com.wareysis.sigbrb.mapper.usuario.UsuarioMapper;
import br.com.wareysis.sigbrb.repository.usuario.UsuarioRepository;
import br.com.wareysis.sigbrb.service.tipos.TipoPerfilService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService implements LogInterface {

    private final UsuarioRepository repository;

    private final UsuarioPerfilService usuarioPerfilService;

    private final FirebaseUserService firebaseUserService;

    private final UsuarioMapper mapper;

    private final TipoPerfilService tipoPerfilService;

    private final FirestoreLogService firestoreLogService;

    private final UsuarioAuthService usuarioAuthService;

    private static final String COLLECTION_NAME = "usuarios";

    @Transactional
    public UsuarioResponseDto create(UsuarioCreateDto dto) {

        UserRecord userRecord = firebaseUserService.createUserInFirebase(dto);

        try {

            Usuario usuario = repository.saveAndFlush(mapper.fromUserRecord(userRecord));

            List<UsuarioPerfil> perfilList = usuarioPerfilService.createPerfisUsuario(usuario.getId(), dto.perfis());

            createLogFirebase(usuario, CrudOperations.CREATE);

            return createResponseDto(usuario, perfilList);

        } catch (Exception e) {

            firebaseUserService.deleteUserInFirebase(dto.email());

            throw new UsuarioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Transactional
    public UsuarioResponseDto update(UsuarioUpdateDto dto) {

        Usuario usuario = repository.findById(dto.id())
                .orElseThrow(() -> new UsuarioException("Usuário com ID: %s não localizado".formatted(dto.id()), HttpStatus.BAD_REQUEST));

        UserRecord userRecord = firebaseUserService.updateUserInFirebase(usuario.getEmail(), dto);

        usuario.setEmail(userRecord.getEmail());
        usuario.setNomeCompleto(userRecord.getDisplayName());
        usuario.setTelefone(userRecord.getPhoneNumber());
        usuario.setHabilitado(!userRecord.isDisabled());

        if (StringUtils.isNotBlank(dto.cpf())) {
            usuario.setCpf(dto.cpf());
        }

        if (dto.alterarSenha() != null) {
            usuario.setAlterarSenha(dto.alterarSenha());
        }

        repository.saveAndFlush(usuario);

        createLogFirebase(usuario, CrudOperations.UPDATE);

        return createResponseDto(usuario, usuarioPerfilService.findPerfisByIdUsuario(usuario.getId()));

    }

    @Transactional
    public void delete(UUID id) {

        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário com ID: %s não localizado".formatted(id), HttpStatus.BAD_REQUEST));

        firebaseUserService.deleteUserInFirebase(usuario.getEmail());

        createLogFirebase(usuario, CrudOperations.DELETE);

        repository.deleteById(id);

    }

    public PagedResponse<UsuarioResponseDto> findAll(PaginationDto paginationDto) {

        Page<Usuario> page = repository.findAll(paginationDto.toPageable());

        Page<UsuarioResponseDto> pageResponseDto = page.map(usuario -> createResponseDto(usuario, usuarioPerfilService.findPerfisByIdUsuario(usuario.getId())));

        return new PagedResponse<>(pageResponseDto);
    }

    public PagedResponse<UsuarioResponseDto> findByNomeCompleto(String nomeCompleto, PaginationDto paginationDto) {

        Page<Usuario> page = repository.findByNomeCompletoContainingIgnoreCase(nomeCompleto, paginationDto.toPageable());

        Page<UsuarioResponseDto> pageResponseDto = page.map(usuario -> createResponseDto(usuario, usuarioPerfilService.findPerfisByIdUsuario(usuario.getId())));

        return new PagedResponse<>(pageResponseDto);

    }

    public UsuarioResponseDto findByEmail(String email) {

        Usuario usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsuarioException("Usuário com e-mail: %s não existe".formatted(email), HttpStatus.BAD_REQUEST));

        return createResponseDto(usuario, usuarioPerfilService.findPerfisByIdUsuario(usuario.getId()));
    }

    public UsuarioResponseDto findById(String id) {

        Usuario usuario = findById(UUID.fromString(id));

        return createResponseDto(usuario, usuarioPerfilService.findPerfisByIdUsuario(usuario.getId()));
    }

    public Usuario findById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário com id: %s não existe".formatted(id), HttpStatus.BAD_REQUEST));

    }

    public boolean isProfUser(UUID idProfissional) {

        UsuarioPerfilId usuarioPerfilId = new UsuarioPerfilId(idProfissional, "PROF");

        return usuarioPerfilService.existsUsuarioPerfilById(usuarioPerfilId);
    }

    @Override
    public void createLogFirebase(Object object, CrudOperations operacao) {

        if (!(object instanceof Usuario usuario)) {
            throw new UsuarioException("Object não é uma instancia de Usuario: %s".formatted(object.getClass().getName()), HttpStatus.BAD_REQUEST);
        }

        UUID uuidLoggedInUser = operacao.equals(CrudOperations.CREATE) ? usuario.getId() : usuarioAuthService.getLoggedInUserUuid();

        try {

            LogDto logDto = new LogDto(
                    operacao.name(),
                    uuidLoggedInUser.toString(),
                    usuario.getId().toString(),
                    COLLECTION_NAME,
                    usuario
            );

            firestoreLogService.addLogFirestore(logDto);

        } catch (Exception e) {
            throw new UsuarioException("Não foi possivel salvar o log: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private UsuarioResponseDto createResponseDto(Usuario usuario, List<UsuarioPerfil> perfilList) {

        List<TipoDto> tipoPerfilList = new ArrayList<>();

        perfilList.forEach(perfil -> tipoPerfilList.add(tipoPerfilService.findById(perfil.getId().idPerfil())));

        return mapper.toResponseDto(usuario, tipoPerfilList);

    }

}
