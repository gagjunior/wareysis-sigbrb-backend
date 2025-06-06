package br.com.wareysis.sigbrb.controller.atendimento;

import java.util.List;

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
import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoResponseDto;
import br.com.wareysis.sigbrb.service.atendimento.AtendimentoService;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService service;

    @PostMapping
    ResponseEntity<AtendimentoResponseDto> create(@Valid @RequestBody AtendimentoRequestDto createDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(createDto));
    }

    @PutMapping("/{id}")
    ResponseEntity<AtendimentoResponseDto> update(@PathVariable(name = "id") String id, @RequestBody AtendimentoRequestDto requestDto) {

        return ResponseEntity.ok(service.update(id, requestDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable(name = "id") String id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    ResponseEntity<AtendimentoResponseDto> findById(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    ResponseEntity<List<AtendimentoResponseDto>> findAll() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/servico/{id}")
    ResponseEntity<List<AtendimentoResponseDto>> findByServicoId(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findByIdServico(id));

    }

    @GetMapping("/servico/nome/{nomeServico}")
    ResponseEntity<List<AtendimentoResponseDto>> findAllByNomeServicoContainingIgnoreCase(@PathVariable(name = "nomeServico") String nomeServico) {

        return ResponseEntity.ok(service.findAllByNomeServicoContainingIgnoreCase(nomeServico));

    }

    @GetMapping("/profissional/{id}")
    ResponseEntity<List<AtendimentoResponseDto>> findByProfissionalId(@PathVariable(name = "id") String id) {

        return ResponseEntity.ok(service.findByIdProfissional(id));

    }

    @GetMapping("/profissional/nome/{nomeProfissional}")
    ResponseEntity<List<AtendimentoResponseDto>> findByNomeProfissionalContainingIgnoreCase(@PathVariable(name = "nomeProfissional") String nomeProfissional) {

        return ResponseEntity.ok(service.findByNomeProfissionalContainingIgnoreCase(nomeProfissional));
    }

}
