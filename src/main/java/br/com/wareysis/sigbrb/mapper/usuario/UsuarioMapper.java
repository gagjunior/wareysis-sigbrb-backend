package br.com.wareysis.sigbrb.mapper.usuario;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.google.firebase.auth.UserRecord;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.dto.usuario.ProfissionalAtendimentoDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioResponseDto;
import br.com.wareysis.sigbrb.entity.usuario.Usuario;

@Component
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

    public UsuarioResponseDto toResponseDto(Usuario usuario, List<TipoDto> perfis) {

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

    public ProfissionalAtendimentoDto toProfissionalAtendimentoDto(Usuario usuario) {

        return new ProfissionalAtendimentoDto(
                usuario.getId(),
                usuario.getNomeCompleto()
        );
    }

}
