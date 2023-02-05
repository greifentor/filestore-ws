package de.ollie.filestore.ws.core.service.impl;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Named;

import de.ollie.filestore.ws.core.service.InputStreamReader;

@Named
public class InputStreamReaderImpl implements InputStreamReader {

	@Override
	public ReadBytesReturn readBytes(InputStream inputStream, int offset, int len) throws IOException {
		byte[] bytes = new byte[len];
		int countOfReadBytes = inputStream.read(bytes, offset, len);
		return new ReadBytesReturn().setCountOfReadBytes(countOfReadBytes).setReadBytes(bytes);
	}

}
