package br.com.wareysis.sigbrb.repository.atendimento;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

}
