package br.com.wareysis.sigbrb.controller.atendimento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Atendimento> create(@Valid @RequestBody AtendimentoRequestDto createDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(createDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Atendimento> update(@PathVariable(name = "id") String id, @RequestBody AtendimentoRequestDto requestDto) {

        return ResponseEntity.ok(service.update(id, requestDto));
    }

}
