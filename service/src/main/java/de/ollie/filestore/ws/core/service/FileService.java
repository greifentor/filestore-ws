package de.ollie.filestore.ws.core.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

public interface FileService {

	@Accessors(chain = true)
	@Data
	public static class FileData {
		private String fileName;
		private long sizeInBytes;
	}

	List<FileData> getFiles() throws IOException;

	void storeStreamContentInFile(String fileName, InputStream inputStream) throws IOException;

}
