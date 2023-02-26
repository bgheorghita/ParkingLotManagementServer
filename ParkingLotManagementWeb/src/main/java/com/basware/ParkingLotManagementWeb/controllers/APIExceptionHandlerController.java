package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandlerController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotAvailable.class)
    public ResponseEntity<Object> handlerServiceNotAvailableException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<Object> handlerInvalidInputException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SaveException.class)
    public ResponseEntity<Object> handlerSaveException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketException.class)
    public ResponseEntity<Object> handlerTicketException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(VehicleAlreadyParkedException.class)
    public ResponseEntity<Object> handlerVehicleAlreadyParkedException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TooManyRequestsException.class)
    public ResponseEntity<Object> handlerTooManyRequestsException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<Object> handlerUserAlreadyRegisteredException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(Exception e, WebRequest request){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
    }
}
