package br.com.wareysis.sigbrb.controller.usuario;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wareysis.sigbrb.dto.endpoint.PagedResponse;
import br.com.wareysis.sigbrb.dto.endpoint.PaginationDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioCreateDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioUpdateDto;
import br.com.wareysis.sigbrb.entity.usuario.Usuario;
import br.com.wareysis.sigbrb.service.usuario.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioCreateDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<Usuario> update(@Valid @RequestBody UsuarioUpdateDto dto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {

        service.delete(java.util.UUID.fromString(id));

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Usuario>> findAll(@ModelAttribute PaginationDto paginationDto) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(paginationDto));
    }

    @GetMapping("/nome/{nomeCompleto}")
    public ResponseEntity<PagedResponse<Usuario>> findByNomeCompleto(
            @PathVariable(name = "nomeCompleto") String nomeCompleto,
            @ModelAttribute PaginationDto paginationDto
    ) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByNomeCompleto(nomeCompleto, paginationDto));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable(name = "id") String id) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(UUID.fromString(id)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> findByEmail(@PathVariable(name = "email") String email) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByEmail(email));
    }

}
