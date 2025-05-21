package br.com.wareysis.sigbrb.controller.tipos;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wareysis.sigbrb.dto.tipos.TipoDto;
import br.com.wareysis.sigbrb.service.tipos.TipoMetodoPagamentoService;
import br.com.wareysis.sigbrb.service.usuario.UsuarioAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipos/metodo-pagamento")
@RequiredArgsConstructor
public class TipoMetodoPagamentoController {

    private final TipoMetodoPagamentoService service;

    private final UsuarioAuthService usuarioAuthService;

    @GetMapping
    public ResponseEntity<List<TipoDto>> findAll() {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<TipoDto>> findByNome(@PathVariable(name = "nome") String nome) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity.ok(service.findByNome(nome));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<TipoDto> findById(@PathVariable(name = "id") String id) {

        usuarioAuthService.adminUserIsRequired();

        return ResponseEntity.ok(service.findById(id));
    }

}
