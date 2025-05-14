package br.com.wareysis.sigbrb.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TipoPerfilException extends RuntimeException {

    private final HttpStatus status;

    public TipoPerfilException(String message, HttpStatus status) {

        super(message);
        this.status = status;
    }
}
