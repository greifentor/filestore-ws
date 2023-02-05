package de.ollie.filestore.ws.core.service;

import java.io.File;

public interface DirectoryProvider {

	int getUploadMaxFileSize();

	File getUploadDirectory();

}
