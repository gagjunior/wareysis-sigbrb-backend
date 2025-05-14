package br.com.wareysis.sigbrb.repository.tipos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wareysis.sigbrb.entity.tipos.TipoPerfil;

@Repository
public interface TipoPerfilRepository extends JpaRepository<TipoPerfil, String> {

    List<TipoPerfil> findAllByNomeContainingIgnoreCase(String nome);

}
