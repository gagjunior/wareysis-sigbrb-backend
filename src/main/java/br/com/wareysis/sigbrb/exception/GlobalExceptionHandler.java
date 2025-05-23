package br.com.wareysis.sigbrb.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioException.class)
    public ResponseEntity<ExceptionResponseDto> handleUsuarioException(UsuarioException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(TipoPerfilException.class)
    public ResponseEntity<ExceptionResponseDto> handleTipoPerfilException(TipoPerfilException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(ServicoException.class)
    public ResponseEntity<ExceptionResponseDto> handleServicoException(ServicoException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(AtendimentoException.class)
    public ResponseEntity<ExceptionResponseDto> handleAtendimentoException(AtendimentoException e) {

        return buildResponseEntity(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponseDto> handleDataIntegrityViolationException(DataIntegrityViolationException e) {

        return buildResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        AtomicReference<String> errorMessage = new AtomicReference<>("");
        errors.forEach((field, message) -> errorMessage.set(errorMessage + "%s: %s; ".formatted(field, message)));

        return buildResponseEntity(HttpStatus.BAD_REQUEST, errorMessage.get());
    }

    private ResponseEntity<ExceptionResponseDto> buildResponseEntity(HttpStatus status, String message) {

        return ResponseEntity
                .status(status)
                .body(new ExceptionResponseDto(status.getReasonPhrase(), message, status.name(), LocalDateTime.now()));
    }

}
