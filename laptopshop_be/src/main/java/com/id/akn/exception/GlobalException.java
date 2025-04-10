package com.id.akn.exception;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;




@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(), req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LaptopException.class)
    public ResponseEntity<ErrorDetails> LaptopExceptionHandler(LaptopException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDetails> handleIOException(IOException ex, WebRequest req) {
        ErrorDetails err = new ErrorDetails(
                ex.getMessage(),
                req.getDescription(false),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CartItemException.class)
    public ResponseEntity<ErrorDetails> CartItemExceptionHandler(CartItemException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OsVersionException.class)
    public ResponseEntity<ErrorDetails> OsVersionExceptionHandler(OsVersionException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BrandException.class)
    public ResponseEntity<ErrorDetails> BrandExceptionHandler(BrandException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CpuException.class)
    public ResponseEntity<ErrorDetails> CpuExceptionHandler(CpuException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderException.class)
    public ResponseEntity<ErrorDetails> OrderExceptionHandler(OrderException ue, WebRequest req){
        ErrorDetails err= new ErrorDetails(ue.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(err,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        // Lấy danh sách các lỗi và xây dựng thông báo chi tiết
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.append(error.getField())
                    .append(": ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        });

        // Tạo đối tượng ErrorDetails
        ErrorDetails errorDetails = new ErrorDetails(
                errors.toString(),
                "Validation failed",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException() {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Endpoint not found");
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e, WebRequest req){
        ErrorDetails error=new ErrorDetails(e.getMessage(),req.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.ACCEPTED);
    }
}

