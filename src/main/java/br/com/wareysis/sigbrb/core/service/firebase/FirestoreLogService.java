package br.com.wareysis.sigbrb.core.service.firebase;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.WriteResult;

import br.com.wareysis.sigbrb.core.config.FirebaseConfig;
import br.com.wareysis.sigbrb.core.dto.log.LogDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirestoreLogService {

    private final ObjectMapper objectMapper;

    private static final String SUB_COLLECTION_LOG_NAME = "logs";

    public void addLogFirestore(LogDto logDto) throws ExecutionException, InterruptedException, JsonProcessingException {

        Map<String, Object> logData = getLogData(logDto);

        ApiFuture<WriteResult> result = FirebaseConfig.getDb()
                .collection(logDto.collectionName())
                .document(logDto.idDocument())
                .collection(SUB_COLLECTION_LOG_NAME)
                .document()
                .set(logData);

        log.info("FIREBASE: Adicionando log ao atendimento: {} -> {}", logDto, result.get().getUpdateTime());

    }

    private Map<String, Object> getLogData(LogDto logDto) throws JsonProcessingException {

        objectMapper.registerModule(new JavaTimeModule());
        String requestData = objectMapper.writeValueAsString(logDto.jsonRequest());

        return Map.of(
                "operacao", logDto.operacao(),
                "dhRegistro", LocalDateTime.now().toString(),
                "idUsuario", logDto.uuidLoggedInUser(),
                "requestData", requestData
        );

    }

}
