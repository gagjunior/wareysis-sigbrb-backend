package br.com.wareysis.sigbrb.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ExceptionResponseDto(

        String erro,
        String message,
        String status,
        LocalDateTime timestamp

) implements Serializable {

}
