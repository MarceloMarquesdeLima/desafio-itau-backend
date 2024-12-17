package br.com.mqs.desafio.itau.mqs.transacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;


@RestController
@RequestMapping(value = "/transacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransacaoController {

    private static Logger logger = LoggerFactory.getLogger(TransacaoController.class);

    @PostMapping
    public ResponseEntity<String> adicionar(@RequestBody TransacaoRequest transacaoRequest){
        logger.info("Adicionando transacao");

        try {
            validarTransacao(transacaoRequest);
        }catch (IllegalArgumentException illegalArgumentException){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    private void validarTransacao(TransacaoRequest transacaoRequest) {
        if(transacaoRequest.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O Valor da transação é inválido!");
        }
        if (transacaoRequest.getDataHora().isAfter(OffsetDateTime.now())){
            throw new IllegalArgumentException("Data da transação inválida!");
        }
    }
}
