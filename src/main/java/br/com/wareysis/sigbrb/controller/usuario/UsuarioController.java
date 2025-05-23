package br.com.wareysis.sigbrb.controller.usuario;

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
import br.com.wareysis.sigbrb.dto.usuario.UsuarioResponseDto;
import br.com.wareysis.sigbrb.dto.usuario.UsuarioUpdateDto;
import br.com.wareysis.sigbrb.service.usuario.UsuarioAuthService;
import br.com.wareysis.sigbrb.service.usuario.UsuarioService;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    private final UsuarioAuthService usuarioAuthService;

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping
    public ResponseEntity<UsuarioResponseDto> update(@Valid @RequestBody UsuarioUpdateDto dto) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {

        usuarioAuthService.adminUserIsRequired();

        service.delete(java.util.UUID.fromString(id));

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping
    public ResponseEntity<PagedResponse<UsuarioResponseDto>> findAll(@ModelAttribute PaginationDto paginationDto) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll(paginationDto));
    }

    @GetMapping("/nome/{nomeCompleto}")
    public ResponseEntity<PagedResponse<UsuarioResponseDto>> findByNomeCompleto(@PathVariable(name = "nomeCompleto") String nomeCompleto,
            @ModelAttribute PaginationDto paginationDto
    ) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByNomeCompleto(nomeCompleto, paginationDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable(name = "id") String id) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioResponseDto> findByEmail(@PathVariable(name = "email") String email) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByEmail(email));
    }

}
