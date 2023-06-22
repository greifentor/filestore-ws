package de.ollie.filestore.ws.core.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.ollie.filestore.ws.core.service.DirectoryProvider;
import de.ollie.filestore.ws.core.service.FileService;
import de.ollie.filestore.ws.core.service.InputStreamReader;
import de.ollie.filestore.ws.core.service.InputStreamReader.ReadBytesReturn;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

	static final Logger LOG = LogManager.getLogger(FileServiceImpl.class);

	private final DirectoryProvider directoryProvider;
	private final InputStreamReader inputStreamReader;

	@Override
	public List<FileData> getFiles() throws IOException {
		return List
				.of(directoryProvider.getUploadDirectory().listFiles())
				.stream()
				.map(file -> new FileData().setFileName(file.getName()).setSizeInBytes(file.length()))
				.collect(Collectors.toList());
	}

	@Override
	public void storeStreamContentInFile(String fileName, InputStream inputStream) throws IOException {
		LOG.info("storing file: " + fileName);
		String completeFileName = directoryProvider.getUploadDirectory().toString() + "/" + fileName;
		LOG.info("writing to: " + completeFileName);
		FileOutputStream outputStream = new FileOutputStream(completeFileName);
		int bufferSize = directoryProvider.getUploadMaxFileSize();
		try {
			ReadBytesReturn readBytesReturn = null;
			int offset = 0;
			do {
				LOG.info("reading bytes " + offset + " to " + bufferSize + " of file: " + fileName);
				readBytesReturn = inputStreamReader.readBytes(inputStream, offset, bufferSize);
				LOG.info("read " + readBytesReturn.getCountOfReadBytes() + " of file: " + fileName);
				if (readBytesReturn.getCountOfReadBytes() > -1) {
					outputStream.write(readBytesReturn.getReadBytes(), offset, readBytesReturn.getCountOfReadBytes());
					outputStream.flush();
					LOG.info("wrote bytes " + offset + " to " + bufferSize + " of file: " + fileName);
					offset += readBytesReturn.getCountOfReadBytes();
				}
			} while (readBytesReturn.getCountOfReadBytes() == bufferSize);
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			outputStream.close();
			inputStream.close();
		}
		LOG.info("file stored: " + fileName);
	}

	@Override
	public void removeFile(String fileName) {
		new File(directoryProvider.getUploadDirectory() + "/" + fileName).delete();
	}

}
