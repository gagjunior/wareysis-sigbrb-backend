package br.com.wareysis.sigbrb.core.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {

    @Column(name = "DH_CRIACAO")
    LocalDateTime dhCriacao;

    @Column(name = "DH_ALTERACAO")
    LocalDateTime dhAlteracao;

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
