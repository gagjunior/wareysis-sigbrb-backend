package br.com.wareysis.sigbrb.service.atendimento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wareysis.sigbrb.core.enumerations.CrudOperations;
import br.com.wareysis.sigbrb.core.log.dto.LogDto;
import br.com.wareysis.sigbrb.core.log.dto.LogInterface;
import br.com.wareysis.sigbrb.core.service.firebase.FirestoreLogService;
import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoRequestDto;
import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoResponseDto;
import br.com.wareysis.sigbrb.dto.servico.ServicoAtendimentoDto;
import br.com.wareysis.sigbrb.dto.usuario.ProfissionalAtendimentoDto;
import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;
import br.com.wareysis.sigbrb.entity.servico.Servico;
import br.com.wareysis.sigbrb.exception.AtendimentoException;
import br.com.wareysis.sigbrb.mapper.atendimento.AtendimentoMapper;
import br.com.wareysis.sigbrb.mapper.servico.ServicoMapper;
import br.com.wareysis.sigbrb.mapper.usuario.UsuarioMapper;
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

    private final ServicoMapper servicoMapper;

    private static final String COLLECTION_NAME = "atendimentos";

    private final UsuarioMapper usuarioMapper;

    @Transactional
    public AtendimentoResponseDto create(AtendimentoRequestDto createDto) {

        Atendimento atendimento = persist(createDto);

        createLogFirebase(atendimento, CrudOperations.CREATE);

        return createResponseDto(atendimento);

    }

    @Transactional
    public AtendimentoResponseDto update(String id, AtendimentoRequestDto requestDto) {

        Atendimento atendimento = findById(UUID.fromString(id));

        updateAtendimentoDetais(atendimento, requestDto);

        createLogFirebase(atendimento, CrudOperations.UPDATE);

        return createResponseDto(repository.save(atendimento));

    }

    @Transactional
    public void delete(String id) {

        Atendimento atendimento = findById(UUID.fromString(id));

        repository.delete(atendimento);

        createLogFirebase(atendimento, CrudOperations.DELETE);

    }

    private Atendimento findById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new AtendimentoException("Atendimento com ID: %s não existe".formatted(id), HttpStatus.BAD_REQUEST));
    }

    public AtendimentoResponseDto findById(String id) {

        Atendimento atendimento = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new AtendimentoException("Atendimento com ID: %s não existe".formatted(id), HttpStatus.BAD_REQUEST));

        return createResponseDto(atendimento);

    }

    public List<AtendimentoResponseDto> findAll() {

        return repository.findAll().parallelStream().map(this::createResponseDto).toList();
    }

    public List<AtendimentoResponseDto> findByIdServico(String idServico) {

        return repository.findAllByIdServico(UUID.fromString(idServico)).parallelStream().map(this::createResponseDto).toList();
    }

    public List<AtendimentoResponseDto> findByIdProfissional(String idProfissional) {

        return repository.findAllByIdProfissional(UUID.fromString(idProfissional)).parallelStream().map(this::createResponseDto).toList();
    }

    public List<AtendimentoResponseDto> findAllByNomeServicoContainingIgnoreCase(String nomeServico) {

        return repository.findAllByNomeServicoContainingIgnoreCase(nomeServico).parallelStream().map(this::createResponseDto).toList();
    }

    public List<AtendimentoResponseDto> findByNomeProfissionalContainingIgnoreCase(String nomeProfissional) {

        return repository.findByNomeProfissionalContainingIgnoreCase(nomeProfissional).parallelStream().map(this::createResponseDto).toList();
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

    private AtendimentoResponseDto createResponseDto(Atendimento atendimento) {

        ServicoAtendimentoDto servico = servicoMapper.toAtendimentoDto(servicoService.findById(atendimento.getIdServico()));
        ProfissionalAtendimentoDto profissional = usuarioMapper.toProfissionalAtendimentoDto(usuarioService.findById(atendimento.getIdProfissional()));

        return mapper.toResponseDto(atendimento, profissional, servico);

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
