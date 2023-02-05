package de.ollie.filestore.ws.core.service.impl;

import java.io.File;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import de.ollie.filestore.ws.core.service.DirectoryProvider;

@Named
@Configuration
public class DirectoryProviderImpl implements DirectoryProvider {

	@Value("${upload.max.file.size:104857600}")
	private long uploadMaxFileSize;
	@Value("${upload.directory.name}")
	private String uploadDirectoryName;

	@Override
	public int getUploadMaxFileSize() {
		return (int) uploadMaxFileSize;
	}

	@Override
	public File getUploadDirectory() {
		return new File(completePath(uploadDirectoryName));
	}

	private String completePath(String s) {
		s = s.replace("\\", "/");
		return !s.endsWith("/") ? s + "/" : s;
	}

}
