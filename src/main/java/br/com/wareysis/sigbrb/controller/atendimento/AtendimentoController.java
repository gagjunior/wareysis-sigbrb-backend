package br.com.wareysis.sigbrb.controller.atendimento;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoRequestDto;
import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;
import br.com.wareysis.sigbrb.service.atendimento.AtendimentoService;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService service;

    @PostMapping
    ResponseEntity<Atendimento> create(@Valid @RequestBody AtendimentoRequestDto createDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(createDto));
    }

    @PutMapping("/{id}")
    ResponseEntity<Atendimento> update(@PathVariable(name = "id") String id, @RequestBody AtendimentoRequestDto requestDto) {

        return ResponseEntity.ok(service.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<Atendimento> findById(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findById(UUID.fromString(id)));
    }

    @GetMapping
    ResponseEntity<List<Atendimento>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/servico/{id}")
    ResponseEntity<List<Atendimento>> findByServicoId(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findByIdServico(id));

    }

    @GetMapping("/profissional/{id}")
    ResponseEntity<List<Atendimento>> findByProfissionalId(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findByIdProfissional(id));

    }

}
