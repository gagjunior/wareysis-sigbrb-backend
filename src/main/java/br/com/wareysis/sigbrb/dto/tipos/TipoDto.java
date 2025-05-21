package br.com.wareysis.sigbrb.dto.tipos;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public record TipoDto(

        @NotBlank
        String id,

        String nome

) implements Serializable {}
