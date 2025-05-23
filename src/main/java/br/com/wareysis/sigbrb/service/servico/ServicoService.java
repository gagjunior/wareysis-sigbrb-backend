package br.com.wareysis.sigbrb.service.servico;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wareysis.sigbrb.core.enumerations.CrudOperations;
import br.com.wareysis.sigbrb.core.log.dto.LogDto;
import br.com.wareysis.sigbrb.core.log.dto.LogInterface;
import br.com.wareysis.sigbrb.core.service.firebase.FirestoreLogService;
import br.com.wareysis.sigbrb.dto.servico.ServicoDto;
import br.com.wareysis.sigbrb.dto.servico.ServicoUpdateDto;
import br.com.wareysis.sigbrb.entity.servico.Servico;
import br.com.wareysis.sigbrb.exception.ServicoException;
import br.com.wareysis.sigbrb.mapper.servico.ServicoMapper;
import br.com.wareysis.sigbrb.repository.servico.ServicoRepository;
import br.com.wareysis.sigbrb.service.usuario.UsuarioAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServicoService implements LogInterface {

    private final ServicoRepository repository;

    private final ServicoMapper mapper;

    private final FirestoreLogService firestoreLogService;

    private final UsuarioAuthService usuarioAuthService;

    private static final String COLLECTION_NAME = "servicos";

    @Transactional
    public ServicoDto create(ServicoDto dto) {

        if (repository.existsByNomeIgnoreCase(dto.nome())) {
            throw new ServicoException("Já existe um serviço com o nome: %s".formatted(dto.nome()), HttpStatus.CONFLICT);
        }

        Servico servico = mapper.toEntity(dto);
        servico.setDisponivel(true);

        Servico newServico = repository.save(servico);

        createLogFirebase(newServico, CrudOperations.CREATE);

        return mapper.toDto(newServico);
    }

    @Transactional
    public ServicoDto update(ServicoUpdateDto dto) {

        Servico servico = findById(dto.id());

        updateServicoDetais(servico, dto);

        createLogFirebase(servico, CrudOperations.UPDATE);

        return mapper.toDto(repository.save(servico));

    }

    @Transactional
    public void delete(UUID id) {

        Servico servico = findById(id);

        createLogFirebase(servico, CrudOperations.DELETE);

        repository.delete(servico);
    }

    public List<ServicoDto> findAll() {

        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public List<ServicoDto> findByNome(String nome) {

        return repository.findAllByNomeContainingIgnoreCase(nome).stream().map(mapper::toDto).toList();
    }

    public ServicoDto findById(String id) {

        return mapper.toDto(findById(UUID.fromString(id)));
    }

    public Servico findById(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new ServicoException("Serviço com ID: %s não existe".formatted(id), HttpStatus.BAD_REQUEST));

    }

    private void updateServicoDetais(Servico servico, ServicoUpdateDto dto) {

        if (dto.nome() != null && !dto.nome().isBlank()) {
            servico.setNome(dto.nome());
        }

        if (dto.valor() != null) {
            servico.setValor(dto.valor());
        }

        if (dto.tempo() != null) {
            servico.setTempo(dto.tempo());
        }

        if (dto.disponivel() != null) {
            servico.setDisponivel(dto.disponivel());
        }
    }

    @Override
    public void createLogFirebase(Object object, CrudOperations operacao) {

        if (!(object instanceof Servico servico)) {
            throw new ServicoException("Object não é uma instancia de Servico", HttpStatus.BAD_REQUEST);
        }

        UUID uuidLoggedInUser = usuarioAuthService.getLoggedInUserUuid();
        LogDto logDto = new LogDto(
                operacao.name(),
                uuidLoggedInUser.toString(),
                servico.getId().toString(),
                COLLECTION_NAME,
                servico
        );
        try {
            firestoreLogService.addLogFirestore(logDto);
        } catch (Exception e) {
            throw new ServicoException("Não foi possivel salvar o log: %s".formatted(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
