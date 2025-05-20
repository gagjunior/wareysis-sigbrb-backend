package br.com.wareysis.sigbrb.repository.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wareysis.sigbrb.entity.servico.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, UUID> {

    boolean existsByNomeIgnoreCase(String nome);

    List<Servico> findAllByNomeContainingIgnoreCase(String nome);
}
