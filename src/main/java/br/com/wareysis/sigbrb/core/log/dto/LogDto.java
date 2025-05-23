package br.com.wareysis.sigbrb.core.log.dto;

public record LogDto(
        String operacao,

        String uuidLoggedInUser,

        String idDocument,

        String collectionName,

        Object jsonRequest

) {

}
