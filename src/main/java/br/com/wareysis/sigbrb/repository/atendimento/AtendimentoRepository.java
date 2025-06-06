package br.com.wareysis.sigbrb.repository.atendimento;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, UUID> {

    List<Atendimento> findAllByIdProfissional(UUID idProfissional);

    List<Atendimento> findAllByIdCliente(UUID idCliente);

    List<Atendimento> findAllByIdServico(UUID idServico);

    @Query("""
            SELECT a FROM Atendimento a 
                JOIN Servico s ON a.idServico = s.id 
                WHERE LOWER(s.nome) LIKE LOWER(CONCAT('%', :nomeServico, '%'))
            """)
    List<Atendimento> findAllByNomeServicoContainingIgnoreCase(String nomeServico);

    @Query("""
            SELECT a FROM Atendimento a 
                JOIN Usuario u ON a.idProfissional = u.id 
                WHERE LOWER(u.nomeCompleto) LIKE LOWER(CONCAT('%', :nomeProfissional, '%'))
            """)
    List<Atendimento> findByNomeProfissionalContainingIgnoreCase(String nomeProfissional);

}
