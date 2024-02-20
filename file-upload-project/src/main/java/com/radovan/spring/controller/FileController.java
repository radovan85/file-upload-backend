package com.radovan.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.radovan.spring.dto.FileDto;
import com.radovan.spring.service.FileService;
import com.radovan.spring.utils.FileValidator;

@RestController
@RequestMapping(value = "/api/files")
@CrossOrigin(value = "*")
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private FileValidator fileValidator;

	@PostMapping(value = "/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file) throws Throwable {

		fileValidator.validateFile(file);

		fileService.addFile(file);
		return ResponseEntity.ok().body("The file has been stored!");
	}

	@GetMapping(value = "/fileDetails/{fileId}")
	public ResponseEntity<byte[]> getFileDetails(@PathVariable("fileId") Integer fileId) {

		FileDto file = fileService.getFileById(fileId);
		return ResponseEntity.ok().body(file.getData());
	}

	@GetMapping(value = "/getFile/{fileId}")
	public ResponseEntity<FileDto> getFile(@PathVariable("fileId") Integer fileId) {

		FileDto file = fileService.getFileById(fileId);
		return ResponseEntity.ok().body(file);
	}

	@GetMapping(value = "/allFiles")
	public ResponseEntity<List<FileDto>> listAllFiles() {

		List<FileDto> allFiles = fileService.listAll();
		return ResponseEntity.ok().body(allFiles);
	}

	@DeleteMapping(value = "/deleteFile/{fileId}")
	public ResponseEntity<String> deleteFile(@PathVariable("fileId") Integer fileId) throws Throwable {
		fileService.deleteFile(fileId);
		return ResponseEntity.ok().body("The file with id " + fileId + " has been permanently deleted!");
	}
}
