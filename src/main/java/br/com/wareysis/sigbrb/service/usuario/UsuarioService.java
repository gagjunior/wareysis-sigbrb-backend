package br.com.wareysis.sigbrb.service.usuario;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.UserRecord;

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

}
