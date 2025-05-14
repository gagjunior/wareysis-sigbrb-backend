package br.com.wareysis.sigbrb.entity.usuario;

import java.util.UUID;

import br.com.wareysis.sigbrb.core.entity.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
public class Usuario extends AbstractEntity {

    @Id
    private UUID id;

    private String email;

    private String nomeCompleto;

    private String cpf;

    private String telefone;

    private Boolean emailVerificado;

    private Boolean habilitado;

    private Boolean alterarSenha;

}
