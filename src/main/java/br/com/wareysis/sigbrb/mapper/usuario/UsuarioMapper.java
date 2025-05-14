package br.com.wareysis.sigbrb.mapper.usuario;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.sigbrb.dto.tipos.TipoPerfilDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioResponseDto;
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

    public UsuarioResponseDto toResponseDto(Usuario usuario, List<TipoPerfilDto> perfis) {

        return new UsuarioResponseDto(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNomeCompleto(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.getEmailVerificado(),
                usuario.getHabilitado(),
                usuario.getAlterarSenha(),
                perfis,
                usuario.getDhCriacao(),
                usuario.getDhAlteracao()
        );
    }

}
