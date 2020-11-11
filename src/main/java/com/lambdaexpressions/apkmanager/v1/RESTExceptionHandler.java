package com.lambdaexpressions.apkmanager.v1;

import com.lambdaexpressions.apkmanager.exceptions.*;
import com.lambdaexpressions.apkmanager.v1.model.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by steccothal
 * on Tuesday 10 November 2020
 * at 9:42 PM
 */
@ControllerAdvice
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e, WebRequest webRequest) {
    return this.createExceptionResponse(e, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({WriteFileException.class, ReadFileException.class})
  public ResponseEntity<Object> handleIOException(ReadFileException e, WebRequest webRequest) {
    return this.createExceptionResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MissingDataException.class)
  public ResponseEntity<Object> handleMissingDataException(MissingDataException e, WebRequest webRequest) {
    return this.createExceptionResponse(e, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<Object> createExceptionResponse(PackageException e, HttpStatus status) {
    ErrorDTO errorDTO = ErrorDTO.builder().appName(e.getAppName()).version(e.getVersion()).errorMessage(e.getMessage()).build();
    return new ResponseEntity<Object>(errorDTO, new HttpHeaders(), status);
  }

}
