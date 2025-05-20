package br.com.wareysis.sigbrb.service.usuario;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfilId;
import br.com.wareysis.sigbrb.exception.UsuarioException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioAuthService {

    private final UsuarioPerfilService usuarioPerfilService;

    public void adminUserIsRequired() {

        UUID loggedInUserId = getLoggedInUserId();

        if (!loggedInUserIsAdmin()) {
            throw new UsuarioException("Acesso negado para usuário: %s".formatted(loggedInUserId), HttpStatus.UNAUTHORIZED);
        }

    }

    private boolean loggedInUserIsAdmin() {

        UUID loggedInUserId = getLoggedInUserId();
        UsuarioPerfilId usuarioPerfilId = new UsuarioPerfilId(loggedInUserId, "ADM");

        return usuarioPerfilService.existsUsuarioPerfilById(usuarioPerfilId);
    }

    private UUID getLoggedInUserId() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new UsuarioException("Usuário não autenticado", HttpStatus.UNAUTHORIZED);
        }

        return UUID.fromString(auth.getPrincipal().toString());

    }

}
