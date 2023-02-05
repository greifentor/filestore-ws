package de.ollie.filestore.ws.core.service;

import java.io.IOException;
import java.io.InputStream;

import lombok.Data;
import lombok.experimental.Accessors;

public interface InputStreamReader {

	@Accessors(chain = true)
	@Data
	public static class ReadBytesReturn {
		private int countOfReadBytes;
		private byte[] readBytes;
	}

	ReadBytesReturn readBytes(InputStream inputStream, int len, int offset) throws IOException;

}
