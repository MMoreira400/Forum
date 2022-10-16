package com.forun.Forum.config;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice

public class ErroHandler {
    private MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroUsuarioDTO> handle(MethodArgumentNotValidException exception){
        List<ErroUsuarioDTO> erroDTOList = new ArrayList<>();
        List<FieldError> fieldErrors =  exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e ->{
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErroUsuarioDTO erro = new ErroUsuarioDTO(e.getField(),mensagem);
            erroDTOList.add(erro);
        });
        return erroDTOList;
    }

}
