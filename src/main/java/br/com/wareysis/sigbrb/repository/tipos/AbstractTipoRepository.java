package br.com.wareysis.sigbrb.repository.tipos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractTipoRepository<T> extends JpaRepository<T, String> {

    List<T> findAllByNomeContainingIgnoreCase(String nome);

}
