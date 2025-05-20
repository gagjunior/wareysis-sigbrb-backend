package br.com.wareysis.sigbrb.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ServicoException extends RuntimeException {

    private final HttpStatus status;

    public ServicoException(String message, HttpStatus status) {

        super(message);
        this.status = status;
    }
}
