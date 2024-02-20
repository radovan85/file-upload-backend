package com.radovan.spring.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.radovan.spring.dto.FileDto;
import com.radovan.spring.entity.FileEntity;

@Component
public class TempConverter {

	@Autowired
	private ModelMapper mapper;

	public FileDto fileEntityToDto(FileEntity fileEntity) {
		FileDto returnValue = mapper.map(fileEntity, FileDto.class);
		return returnValue;
	}

	public FileEntity fileDtoToEntity(FileDto fileDto) {
		FileEntity returnValue = mapper.map(fileDto, FileEntity.class);
		return returnValue;
	}
}
