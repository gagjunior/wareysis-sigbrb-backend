package br.com.wareysis.sigbrb.service.atendimento;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.wareysis.sigbrb.core.dto.log.LogDto;
import br.com.wareysis.sigbrb.core.service.firebase.FirestoreLogService;
import br.com.wareysis.sigbrb.dto.atentimento.AtendimentoCreateDto;
import br.com.wareysis.sigbrb.entity.atendimento.Atendimento;
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
public class AtendimentoService {

    private final AtendimentoRepository repository;

    private final AtendimentoMapper mapper;

    private final UsuarioService usuarioService;

    private final ServicoService servicoService;

    private final UsuarioAuthService usuarioAuthService;

    private final FirestoreLogService firestoreLogService;

    private static final String COLLECTION_NAME = "atendimentos";

    @Transactional
    public Atendimento create(AtendimentoCreateDto createDto) {

        Atendimento atendimento = persist(createDto);

        UUID uuidLoggedInUser = usuarioAuthService.getLoggedInUserUuid();
        createLogFirebase(atendimento, uuidLoggedInUser, "CREATE");

        return atendimento;

    }

    private Atendimento persist(AtendimentoCreateDto createDto) {

        usuarioService.findById(createDto.idProfissional());
        servicoService.findById(createDto.idServico());

        if (!usuarioService.isProfUser(createDto.idProfissional())) {
            throw new AtendimentoException("ID %s não é de um profissional válido".formatted(createDto.idProfissional()), HttpStatus.BAD_REQUEST);
        }

        try {

            return repository.saveAndFlush(mapper.toEntity(createDto));

        } catch (Exception e) {
            throw new AtendimentoException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void createLogFirebase(Atendimento atendimento, UUID uuidLoggedInUser, String operacao) {

        try {

            LogDto logDto = new LogDto(
                    operacao,
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

}
