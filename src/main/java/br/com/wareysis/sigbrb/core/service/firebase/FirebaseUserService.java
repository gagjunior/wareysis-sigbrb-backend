package br.com.wareysis.sigbrb.core.service.firebase;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.AuthErrorCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.UpdateRequest;

import br.com.wareysis.sigbrb.dto.usuario.UsuarioCreateDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioUpdateDto;
import br.com.wareysis.sigbrb.exception.UsuarioException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FirebaseUserService {

    public UserRecord createUserInFirebase(UsuarioCreateDto dto) {

        log.info("FIREBASE: Salvar usuário: {}", dto.nomeCompleto());

        if (userAlreadyExistsInFirestore(dto.email())) {
            throw new UsuarioException("Usuário com e-mail: %s já existe".formatted(dto.email()), HttpStatus.CONFLICT);
        }

        try {

            UserRecord.CreateRequest request = new UserRecord.CreateRequest();

            request.setUid(UUID.randomUUID().toString());
            request.setEmail(dto.email());
            request.setPassword(dto.senha());
            request.setDisplayName(dto.nomeCompleto());
            request.setEmailVerified(false);
            request.setDisabled(false);

            return FirebaseAuth.getInstance().createUser(request);

        } catch (Exception e) {

            throw new UsuarioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    public UserRecord updateUserInFirebase(String email, UsuarioUpdateDto dto) {

        if (!userAlreadyExistsInFirestore(email)) {
            throw new UsuarioException("Usuário com e-mail: %s não existe".formatted(dto.email()), HttpStatus.NOT_FOUND);
        }

        UserRecord.UpdateRequest request = new UpdateRequest(dto.id().toString());

        if (StringUtils.isNotBlank(dto.email())) {
            request.setEmail(dto.email());
        }

        if (StringUtils.isNotBlank(dto.nomeCompleto())) {
            request.setDisplayName(dto.nomeCompleto());
        }

        if (StringUtils.isNotBlank(dto.telefone())) {
            request.setPhoneNumber(dto.telefone());
        }

        if (dto.habilitado() != null) {
            request.setDisabled(!dto.habilitado());
        }

        try {

            return FirebaseAuth.getInstance().updateUser(request);

        } catch (FirebaseAuthException e) {

            throw new UsuarioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public void deleteUserInFirebase(String email) {

        try {

            log.info("FIREBASE: Apagar usuário e-mail: {}", email);

            if (!userAlreadyExistsInFirestore(email)) {
                throw new UsuarioException("Usuário com e-mail: %s não existe".formatted(email), HttpStatus.NOT_FOUND);
            }

            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            FirebaseAuth.getInstance().deleteUser(userRecord.getUid());

        } catch (FirebaseAuthException e) {

            throw new UsuarioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public boolean userAlreadyExistsInFirestore(String email) {

        try {

            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            return (userRecord != null && userRecord.getEmail().equals(email));

        } catch (FirebaseAuthException e) {

            if (e.getAuthErrorCode().equals(AuthErrorCode.EMAIL_NOT_FOUND) || e.getAuthErrorCode().equals(AuthErrorCode.USER_NOT_FOUND)) {

                return false;

            }

            throw new UsuarioException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

}
