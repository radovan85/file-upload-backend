package com.radovan.spring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.radovan.spring.dto.FileDto;

public interface FileService {

	FileDto addFile(MultipartFile file);

	FileDto getFileById(Integer fileId);

	List<FileDto> listAll();
	
	void deleteFile(Integer fileId) throws Throwable;
}
