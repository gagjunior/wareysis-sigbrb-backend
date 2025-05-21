package br.com.wareysis.sigbrb.entity.tipos;

import br.com.wareysis.sigbrb.core.entity.AbstractEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractEntityTipo extends AbstractEntity {

    @Id
    @Column(name = "ID")
    String id;

    @Column(name = "NOME")
    String nome;

}
