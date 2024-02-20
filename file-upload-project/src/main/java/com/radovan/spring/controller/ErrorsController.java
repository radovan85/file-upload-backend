package com.radovan.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import com.radovan.spring.exceptions.DataNotValidatedException;
import com.radovan.spring.exceptions.FileUploadException;
import com.radovan.spring.exceptions.InstanceUndefinedException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ErrorsController {

	@ExceptionHandler(InstanceUndefinedException.class)
	public ResponseEntity<String> handleInstanceUndefinedException() {
		return ResponseEntity.internalServerError().body("The instance has been undefined!");
	}

	@ExceptionHandler(FileUploadException.class)
	public ResponseEntity<String> handleFileUploadException() {
		return ResponseEntity.internalServerError().body("Problems with file");
	}

	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<String> handleMultipartException(HttpServletRequest request, Exception e) {
		return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(DataNotValidatedException.class)
	public ResponseEntity<String> handleDataNotValidatedException(){
		return ResponseEntity.internalServerError().body("The data is not validated!");
	}
}
