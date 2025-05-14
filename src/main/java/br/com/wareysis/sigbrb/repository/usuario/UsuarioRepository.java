package br.com.wareysis.sigbrb.repository.usuario;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wareysis.sigbrb.entity.usuario.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Page<Usuario> findByNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable pageable);

    Optional<Usuario> findByEmail(String email);

}
