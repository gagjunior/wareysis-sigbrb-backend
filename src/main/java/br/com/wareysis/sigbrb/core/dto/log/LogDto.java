package br.com.wareysis.sigbrb.core.dto.log;

public record LogDto(
        String operacao,

        String uuidLoggedInUser,

        String idDocument,

        String collectionName,

        Object jsonRequest

) {

}
