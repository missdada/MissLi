package com.example.demo.service;

import org.springframework.web.multipart.MultipartFile;

public interface BatchImportService {
	public String batchImport(String fileName, MultipartFile mfile, String userName);

}
