package br.com.wareysis.sigbrb.entity.usuario;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USUARIOS")
public class Usuario {

    @Id
    private UUID id;

    private String email;

    private String nomeCompleto;

    private String cpf;

    private String telefone;

    private Boolean emailVerificado;

    private Boolean habilitado;

    private Boolean alterarSenha;

    private LocalDateTime dhCriacao;

    private LocalDateTime dhAlteracao;

    @PrePersist
    public void prePersist() {

        dhCriacao = LocalDateTime.now();
        dhAlteracao = dhCriacao;
    }

    @PreUpdate
    public void preUpdate() {

        dhAlteracao = LocalDateTime.now();
    }

}
