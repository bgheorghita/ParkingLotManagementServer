package com.basware.ParkingLotManagementServer.controllers.taxes;

import com.basware.ParkingLotManagementServer.exceptions.ResourceNotFoundException;
import com.basware.ParkingLotManagementServer.exceptions.ServiceNotAvailable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class APIExceptionHandlerController {
    public final static String RESOURCE_NOT_FOUND_MSG = "Resource not found. ";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handlerNotFoundException(Exception e, WebRequest request){
        return new ResponseEntity<Object>(RESOURCE_NOT_FOUND_MSG + e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceNotAvailable.class)
    public ResponseEntity<Object> handlerServiceNotAvailableException(Exception e, WebRequest request){
        return new ResponseEntity<Object>(e.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
