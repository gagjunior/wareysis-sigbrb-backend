package br.com.wareysis.sigbrb.mapper.usuario;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.sigbrb.entity.usuario.Usuario;

@Service
public class UsuarioMapper {

    public Usuario fromUserRecord(UserRecord userRecord) {

        return Usuario.builder()
                .id(UUID.fromString(userRecord.getUid()))
                .email(userRecord.getEmail())
                .nomeCompleto(userRecord.getDisplayName())
                .emailVerificado(userRecord.isEmailVerified())
                .habilitado(!userRecord.isDisabled())
                .telefone(userRecord.getPhoneNumber())
                .build();
    }

}
