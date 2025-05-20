package br.com.wareysis.sigbrb.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ExceptionResponseDto> handleUsuarioException(UsuarioException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(TipoPerfilException.class)
    public ResponseEntity<ExceptionResponseDto> handleUsuarioException(TipoPerfilException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(ServicoException.class)
    public ResponseEntity<ExceptionResponseDto> handleUsuarioException(ServicoException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    private ResponseEntity<ExceptionResponseDto> buildResponseEntity(HttpStatus status, String message) {

        return ResponseEntity
                .status(status)
                .body(new ExceptionResponseDto(status.getReasonPhrase(), message, status.name(), LocalDateTime.now()));
    }

}
