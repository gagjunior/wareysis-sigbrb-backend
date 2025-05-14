package br.com.wareysis.sigbrb.repository.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfil;
import br.com.wareysis.sigbrb.entity.usuario.UsuarioPerfilId;

@Repository
public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, UsuarioPerfilId> {

    List<UsuarioPerfil> findById_IdPerfil(String idPerfil);

    Optional<List<UsuarioPerfil>> findById_IdUsuario(UUID idUsuario);

}
