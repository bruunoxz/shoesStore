package com.brunohenrique.store.infra;

import com.brunohenrique.store.exceptions.DataAlreadyRegistered;
import com.brunohenrique.store.exceptions.DataNotFoundException;
import com.brunohenrique.store.exceptions.InputValidationException;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.UnsupportedEncodingException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataAlreadyRegistered.class)
    private ResponseEntity<RestErrorMessage> dataAlreadyRegisteredHandler(DataAlreadyRegistered exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.CONFLICT, exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(threatResponse);
    }

    @ExceptionHandler(InputValidationException.class)
    private ResponseEntity<RestErrorMessage> inputValidationHandler(InputValidationException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(threatResponse);
    }

    @ExceptionHandler(DataNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(DataNotFoundException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(threatResponse);
    }

    @ExceptionHandler(MessagingException.class)
    private ResponseEntity<RestErrorMessage> messagingHandler(MessagingException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar mensagem de e-mail");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    private ResponseEntity<RestErrorMessage> unsupportedEncodingHandler(UnsupportedEncodingException exception){
        RestErrorMessage threatResponse = new RestErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, "Erro de codificação de caractere não suportado.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(threatResponse);
    }
}
