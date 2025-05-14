package br.com.wareysis.sigbrb.entity.tipos;

import org.springframework.data.annotation.Immutable;

import br.com.wareysis.sigbrb.core.entity.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TIPOS_PERFIL")
@Immutable
public class TipoPerfil extends AbstractEntity {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NOME")
    private String nome;

}
