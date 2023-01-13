package com.basware.ParkingLotManagementWeb.controllers;

import com.basware.ParkingLotManagementWeb.exceptions.InvalidInput;
import com.basware.ParkingLotManagementWeb.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementWeb.exceptions.ServiceNotAvailable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandlerController {
    public final static String RESOURCE_NOT_FOUND_MSG = "Resource not found.";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<Object>(RESOURCE_NOT_FOUND_MSG + excMsg, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotAvailable.class)
    public ResponseEntity<Object> handlerServiceNotAvailableException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<Object>(excMsg, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<Object> handlerInvalidInputException(Exception e, WebRequest request){
        String excMsg = e.getMessage() == null ? "" : e.getMessage();
        return new ResponseEntity<Object>(excMsg, HttpStatus.BAD_REQUEST);
    }
}
