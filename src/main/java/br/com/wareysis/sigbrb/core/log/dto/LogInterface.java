package br.com.wareysis.sigbrb.core.log.dto;

import br.com.wareysis.sigbrb.core.enumerations.CrudOperations;

public interface LogInterface {

    void createLogFirebase(Object usuario, CrudOperations operacao);

}
