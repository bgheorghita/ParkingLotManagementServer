package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandlerController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotAvailable.class)
    public ResponseEntity<Object> handlerServiceNotAvailableException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<Object> handlerInvalidInputException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Object> handlerTicketNotFoundException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SaveException.class)
    public ResponseEntity<Object> handlerSaveException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TicketException.class)
    public ResponseEntity<Object> handlerTicketException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(VehicleAlreadyParkedException.class)
    public ResponseEntity<Object> handlerVehicleAlreadyParkedException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<>(excMsg, HttpStatus.CONFLICT);
    }
}
