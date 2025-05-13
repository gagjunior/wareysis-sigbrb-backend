package br.com.wareysis.sigbrb.service.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.sigbrb.entity.usuario.Usuario;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioCreateDto;
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

}
