package br.com.wareysis.sigbrb.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class UsuarioException extends RuntimeException {

    private final HttpStatus status;

    public UsuarioException(String message, HttpStatus status) {

        super(message);
        this.status = status;
    }
}
