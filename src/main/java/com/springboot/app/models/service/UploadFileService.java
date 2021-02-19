package com.springboot.app.models.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {

	String copy(MultipartFile file) throws IOException;
	boolean delete(String filename);
}
