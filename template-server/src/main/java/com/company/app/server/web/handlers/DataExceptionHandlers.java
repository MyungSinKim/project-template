package com.company.app.server.web.handlers;

import com.company.app.client.web.dtos.ErrorCodes;
import com.company.app.client.web.dtos.errors.ErrorDto;
import com.company.app.client.web.dtos.errors.ValidationErrorDto;
import com.company.app.server.domain.model.Constraints.DataUniqueConstraint;
import com.github.rozidan.springboot.logger.Loggable;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Idan Rozenfeld
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class DataExceptionHandlers {

    private final List<DataUniqueConstraint> uniqueConstraintList;

    @Autowired(required = false)
    public DataExceptionHandlers(List<DataUniqueConstraint> uniqueConstraintList) {
        this.uniqueConstraintList = uniqueConstraintList;
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ErrorDto<Void> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex) {
        return new ErrorDto.ErrorDtoBuilder<Void>().errorCode(ErrorCodes.NOT_FOUND).build();
    }

    @Loggable
    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorDto<ValidationErrorDto> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        if (uniqueConstraintList != null && ex.getCause() != null && ex.getCause() instanceof ConstraintViolationException) {
            Optional<DataUniqueConstraint> matchCons = uniqueConstraintList.stream().filter((cons) ->
                    ((ConstraintViolationException) ex.getCause()).getConstraintName().contains(cons.getConstraintName())).findFirst();
            if (matchCons.isPresent()) {
                return new ErrorDto.ErrorDtoBuilder<ValidationErrorDto>()
                        .errorCode(ErrorCodes.DATA_VALIDATION)
                        .errors(Collections.singleton(ValidationErrorDto.builder()
                                .errorCode("UNIQUE")
                                .fieldName(matchCons.get().getFieldName()).build()))
                        .build();
            }
        }

        return new ErrorDto.ErrorDtoBuilder<ValidationErrorDto>()
                .errorCode(ErrorCodes.UNKNOWN)
                .build();
    }

}
