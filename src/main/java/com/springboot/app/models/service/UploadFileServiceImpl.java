package com.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements UploadFileService {

	private final Logger log = org.slf4j.LoggerFactory.getLogger(getClass());

	private final static String UPLOAD_FOLDER = "uploads";


	@Override
	public String copy(MultipartFile file) throws IOException {
		
		String uniqueFilename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
		Path filePath = getPath(uniqueFilename);

		Files.copy(file.getInputStream(), filePath);

		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path path = getPath(filename);
		File file = path.toFile();
		
		if (file.exists() && file.canWrite()) {
			if (file.delete()) {
				log.info(filename + " deleted successfully.");
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}
}
