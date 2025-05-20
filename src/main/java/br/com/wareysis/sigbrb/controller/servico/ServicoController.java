package br.com.wareysis.sigbrb.controller.servico;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wareysis.sigbrb.dto.servico.ServicoDto;
import br.com.wareysis.sigbrb.dto.servico.ServicoUpdateDto;
import br.com.wareysis.sigbrb.service.servico.ServicoService;
import br.com.wareysis.sigbrb.service.usuario.UsuarioAuthService;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/servico")
@RequiredArgsConstructor
public class ServicoController {

    private final ServicoService service;

    private final UsuarioAuthService usuarioAuthService;

    @PostMapping
    ResponseEntity<ServicoDto> create(@Valid @RequestBody ServicoDto dto) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping
    ResponseEntity<ServicoDto> update(@Valid @RequestBody ServicoUpdateDto dto) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity.ok(service.update(dto));
    }

    @GetMapping
    ResponseEntity<List<ServicoDto>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/nome/{nome}")
    ResponseEntity<List<ServicoDto>> findByNome(@PathVariable(name = "nome") String nome) {

        return ResponseEntity.ok(service.findByNome(nome));
    }

    @GetMapping("/{id}")
    ResponseEntity<ServicoDto> findById(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findById(id));
    }

}
