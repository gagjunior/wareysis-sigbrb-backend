package br.com.wareysis.sigbrb.controller.tipos;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wareysis.sigbrb.dto.tipos.TipoPerfilDto;
import br.com.wareysis.sigbrb.service.tipos.TipoPerfilService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipos/perfil")
@RequiredArgsConstructor
public class TipoPerfilController {

    private final TipoPerfilService service;

    @GetMapping
    public ResponseEntity<List<TipoPerfilDto>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<TipoPerfilDto>> findByNome(@PathVariable(name = "nome") String nome) {

        return ResponseEntity.ok(service.findByNome(nome));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TipoPerfilDto> findById(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findById(id));
    }

}
