package br.com.wareysis.sigbrb.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class AtendimentoException extends RuntimeException {

    private final HttpStatus status;

    public AtendimentoException(String message, HttpStatus status) {

        super(message);
        this.status = status;
    }
}
