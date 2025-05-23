package br.com.wareysis.sigbrb.service.atendimento;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wareysis.sigbrb.core.enumerations.CrudOperations;
import br.com.wareysis.sigbrb.core.log.dto.LogDto;
import br.com.wareysis.sigbrb.core.log.dto.LogInterface;
import br.com.wareysis.sigbrb.core.service.firebase.FirestoreLogService;
import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoRequestDto;
import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;
import br.com.wareysis.sigbrb.entity.servico.Servico;
import br.com.wareysis.sigbrb.exception.AtendimentoException;
import br.com.wareysis.sigbrb.mapper.atendimento.AtendimentoMapper;
import br.com.wareysis.sigbrb.repository.atendimento.AtendimentoRepository;
import br.com.wareysis.sigbrb.service.servico.ServicoService;
import br.com.wareysis.sigbrb.service.usuario.UsuarioAuthService;
import br.com.wareysis.sigbrb.service.usuario.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtendimentoService implements LogInterface {

    private final AtendimentoRepository repository;

    private final AtendimentoMapper mapper;

    private final UsuarioService usuarioService;

    private final ServicoService servicoService;

    private final UsuarioAuthService usuarioAuthService;

    private final FirestoreLogService firestoreLogService;

    private static final String COLLECTION_NAME = "atendimentos";

    @Transactional
    public Atendimento create(AtendimentoRequestDto createDto) {

        Atendimento atendimento = persist(createDto);

        createLogFirebase(atendimento, CrudOperations.CREATE);

        return atendimento;

    }

    @Transactional
    public Atendimento update(String id, AtendimentoRequestDto requestDto) {

        Atendimento atendimento = findById(UUID.fromString(id));

        updateAtendimentoDetais(atendimento, requestDto);

        createLogFirebase(atendimento, CrudOperations.UPDATE);

        return repository.save(atendimento);

    }

    public Atendimento findById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new AtendimentoException("Atendimento com ID: %s não existe".formatted(id), HttpStatus.BAD_REQUEST));
    }

    @Override
    public void createLogFirebase(Object object, CrudOperations operacao) {

        UUID uuidLoggedInUser = usuarioAuthService.getLoggedInUserUuid();
        Atendimento atendimento = (Atendimento) object;

        try {

            LogDto logDto = new LogDto(
                    operacao.name(),
                    uuidLoggedInUser.toString(),
                    atendimento.getId().toString(),
                    COLLECTION_NAME,
                    atendimento
            );

            firestoreLogService.addLogFirestore(logDto);

        } catch (Exception e) {
            throw new AtendimentoException("Não foi possivel salvar o log: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private void updateAtendimentoDetais(Atendimento atendimento, AtendimentoRequestDto requestDto) {

        if (requestDto.idProfissional() != null) {
            atendimento.setIdProfissional(requestDto.idProfissional());
        }

        if (requestDto.idServico() != null) {
            atendimento.setIdServico(requestDto.idServico());
        }

        if (requestDto.dhInicio() != null) {
            atendimento.setDhInicio(requestDto.dhInicio());
            calculateDhFim(atendimento);
        }

        if (requestDto.idCliente() != null) {
            atendimento.setIdCliente(requestDto.idCliente());
        }

        if (requestDto.nomeCliente() != null && !requestDto.nomeCliente().isBlank()) {
            atendimento.setNomeCliente(requestDto.nomeCliente());
        }

        if (requestDto.idStatusAtendimento() != null && !requestDto.idStatusAtendimento().isBlank()) {
            atendimento.setIdStatusAtendimento(requestDto.idStatusAtendimento());
        }

        if (requestDto.idStatusPagamento() != null && !requestDto.idStatusPagamento().isBlank()) {
            atendimento.setIdStatusPagamento(requestDto.idStatusPagamento());
        }

        if (requestDto.idMetodoPagamento() != null && !requestDto.idMetodoPagamento().isBlank()) {
            atendimento.setIdMetodoPagamento(requestDto.idMetodoPagamento());
        }
    }

    private Atendimento persist(AtendimentoRequestDto requestDto) {

        usuarioService.findById(requestDto.idProfissional());
        servicoService.findById(requestDto.idServico());

        if (!usuarioService.isProfUser(requestDto.idProfissional())) {
            throw new AtendimentoException("ID %s não é de um profissional válido".formatted(requestDto.idProfissional()), HttpStatus.BAD_REQUEST);
        }

        try {

            Atendimento atendimento = mapper.toEntity(requestDto);
            calculateDhFim(atendimento);

            return repository.saveAndFlush(atendimento);

        } catch (Exception e) {
            throw new AtendimentoException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void calculateDhFim(Atendimento atendimento) {

        Servico servico = servicoService.findById(atendimento.getIdServico());
        LocalDateTime dhFim = atendimento.getDhInicio().plusMinutes(servico.getTempo());
        atendimento.setDhFim(dhFim);

    }

}
