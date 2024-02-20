package com.radovan.spring.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.FileDto;
import com.radovan.spring.entity.FileEntity;
import com.radovan.spring.exceptions.FileUploadException;
import com.radovan.spring.exceptions.InstanceUndefinedException;
import com.radovan.spring.repository.FileRepository;
import com.radovan.spring.service.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public FileDto addFile(MultipartFile file) {
		// TODO Auto-generated method stub

		String fileLocation = "C:\\Users\\Administrator\\upload-workspace\\file-upload-project\\src\\main\\resources\\static\\upload\\";
		Path locationPath = Paths.get(fileLocation);
		String imageUUID;

		if (!Files.exists(locationPath)) {
			Error error = new Error("Invalid file location!");
			throw new FileUploadException(error);
		}

		imageUUID = file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(fileLocation, imageUUID);

		if (file != null && !file.isEmpty()) {
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Error error = new Error("Invalid file location!");
				throw new FileUploadException(error);
			}
			System.out.println("IMage Save at:" + fileNameAndPath.toString());

		}

		FileDto fileDto = new FileDto();
		FileDto returnValue = null;
		fileDto.setName(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
		fileDto.setContentType(file.getContentType());
		fileDto.setSize(file.getSize());

		try {
			fileDto.setData(file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Error error = new Error("File upoload issue");
			throw new FileUploadException(error);
		}

		FileEntity fileEntity = tempConverter.fileDtoToEntity(fileDto);
		FileEntity storedFile = fileRepository.save(fileEntity);
		returnValue = tempConverter.fileEntityToDto(storedFile);

		return returnValue;
	}

	@Override
	public FileDto getFileById(Integer fileId) {
		// TODO Auto-generated method stub
		FileDto returnValue = null;
		Optional<FileEntity> fileOptional = fileRepository.findById(fileId);
		if (fileOptional.isPresent()) {
			returnValue = tempConverter.fileEntityToDto(fileOptional.get());
		} else {
			Error error = new Error("The file has not been found!");
			throw new InstanceUndefinedException(error);
		}

		return returnValue;
	}

	@Override
	public List<FileDto> listAll() {
		// TODO Auto-generated method stub
		List<FileDto> returnValue = new ArrayList<FileDto>();
		List<FileEntity> allFiles = fileRepository.findAll();
		if (!allFiles.isEmpty()) {
			allFiles.forEach((fileEntity) -> {
				FileDto fileDto = tempConverter.fileEntityToDto(fileEntity);
				returnValue.add(fileDto);
			});
		}

		return returnValue;
	}

	@Override
	public void deleteFile(Integer fileId) throws Throwable {
		// TODO Auto-generated method stub
		FileDto file = getFileById(fileId);
		String fileLocation = "C:\\Users\\Administrator\\upload-workspace\\file-upload-project\\src\\main\\resources\\static\\upload\\";

		Path path = Paths.get(fileLocation + file.getName());

		if (Files.exists(path)) {
			Files.delete(path);
		} else {
			Error error = new Error("Invalid file path!");
			throw new InstanceUndefinedException(error);
		}

		fileRepository.deleteById(file.getFileId());
		fileRepository.flush();
	}

}
