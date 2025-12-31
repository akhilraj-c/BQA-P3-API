package com.mindteck.common.exceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mindteck.common.ErrorResponseBuilder.Data;
import com.mindteck.common.ErrorResponseBuilder.ErrorResponse;
import com.mindteck.common.ErrorResponseBuilder.ResponseBuilder;
import com.mindteck.common.constants.Enum.ErrorCode;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ResponseBuilder responseBuilder;


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception,
                                                                   HttpServletRequest request,
                                                                   HttpServletResponse response) {

        Data data = null;
        if(exception instanceof InvalidFormatException) {
             data = Data.builder()
                    .code(ErrorCode.INVALID_FORMAT.getCode())
                    .message(ErrorCode.INVALID_FORMAT.getDescription())
                    .errorDetails(exception.getMessage())
                    .build();
        }
        else {
            data = Data.builder()
                    .code(ErrorCode.SERVICE_UNAVAILABLE.getCode())
                    .message(ErrorCode.SERVICE_UNAVAILABLE.getDescription())
                    .errorDetails(exception.getMessage())
                    .build();
        }
        return new ResponseEntity<>(responseBuilder.getIndividualResponse(data), HttpStatus.OK);
    }


    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<ErrorResponse> handleEmptyInputException(EmptyInputException emptyInputException) {

        Data data = Data.builder()
                .code(emptyInputException.getCode())
                .message(emptyInputException.getDescription())
                .build();
        return new ResponseEntity<>(responseBuilder.getIndividualResponse(data),
                HttpStatus.OK);
    }





    @ExceptionHandler(ControllerException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            ControllerException controllerException) {
        List<HashMap<String, String>> error = new ArrayList<>();
       if(null != controllerException.getBindingErrors()) {
           for (FieldError fieldError : controllerException.getBindingErrors().getFieldErrors()) {
               HashMap<String, String> fieldErrors = new HashMap<>();
               fieldErrors.put("rejectedValue",  String.valueOf(fieldError.getRejectedValue()));
               fieldErrors.put("fieldName", fieldError.getField());
               fieldErrors.put("message", fieldError.getDefaultMessage());
               error.add(fieldErrors);
           }
       } else {
           error = null;
       }
        Data data = Data.builder()
                .code(controllerException.getErrorCode().getCode())
                .message(controllerException.getErrorCode().getDescription())
                .errorDetails(error)
                .build();
        return new ResponseEntity<>(responseBuilder.getIndividualResponse(data),
                HttpStatus.OK);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(
            ServiceException serviceException) {
        Data data = Data.builder()
                .code(serviceException.getErrorCode().getCode())
                .message(serviceException.getErrorCode().getDescription())
                .message(serviceException.getMessage())
                .build();

        return new ResponseEntity<>(responseBuilder.getIndividualResponse(data), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException userNotFoundException) {

        Data data = Data.builder()
                .code(userNotFoundException.getCode())
                .message(userNotFoundException.getDescription())
                .build();
        return new ResponseEntity<>(responseBuilder.getIndividualResponse(data),
                HttpStatus.OK);
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public ResponseEntity<ErrorResponse> handleSqlException(SQLSyntaxErrorException exception) {
        Data data = Data.builder()
                .code(ErrorCode.SERVICE_UNAVAILABLE.getCode())
                .message(ErrorCode.SERVICE_UNAVAILABLE.getDescription())
                .errorDetails(exception.getCause().getMessage())
                .build();
        return new ResponseEntity<>(responseBuilder.getIndividualResponse(data),
                HttpStatus.OK);
    }


}
