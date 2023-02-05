package de.ollie.filestore.ws.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import de.ollie.filestore.ws.core.service.DirectoryProvider;
import de.ollie.filestore.ws.core.service.FileService.FileData;
import de.ollie.filestore.ws.core.service.InputStreamReader;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

	private static final String CONTENT = "Test Content";
	private static final String FILE_NAME = "file.name";

	@Mock
	private DirectoryProvider directoryProvider;

	private InputStream inputStream = null;
	private InputStreamReader inputStreamReader = new InputStreamReaderImpl();

	private FileServiceImpl unitUnderTest;

	@BeforeEach
	void setUp() throws Exception {
		inputStream = new ByteArrayInputStream(CONTENT.getBytes("ISO-8859-1"));
		unitUnderTest = new FileServiceImpl(directoryProvider, inputStreamReader);
	}

	@Nested
	class TestsOfMethods_getFiles {

		@Test
		void returnsAListWithTheInformationOfFilesStoredInTheConfiguredDirectory(@TempDir Path tempDir)
				throws Exception {
			// Prepare
			when(directoryProvider.getUploadDirectory()).thenReturn(new File(tempDir.toString()));
			Files.writeString(Path.of(tempDir.toString() + "/" + FILE_NAME), CONTENT, StandardOpenOption.CREATE);
			// Run
			List<FileData> returned = unitUnderTest.getFiles();
			// Check
			assertFalse(returned.isEmpty());
		}

	}

	@Nested
	class TestsOfMethods_storeStreamContentInFile_String_InputStream {

		@Test
		void throwsANullValue_passingFileNameAsNull() {
			assertThrows(NullPointerException.class, () -> unitUnderTest.storeStreamContentInFile(null, inputStream));
		}

		@Test
		void throwsANullValue_passingInputStreamAsNull() {
			assertThrows(NullPointerException.class, () -> unitUnderTest.storeStreamContentInFile(FILE_NAME, null));
		}

		@Test
		@Disabled
		void createsAFileWithPassedNameInTheConfiguredDirectory_passAFileNameAndInputStream(@TempDir Path tempDir)
				throws Exception {
			when(directoryProvider.getUploadDirectory()).thenReturn(new File(tempDir.toString()));
			unitUnderTest.storeStreamContentInFile(FILE_NAME, inputStream);
			assertTrue(new File(tempDir.toString() + "/" + FILE_NAME).exists());
		}

		@Test
		@Disabled
		void createsAFileWithCorrectContentInTheConfiguredDirectory_passAFileNameAndInputStream(@TempDir Path tempDir)
				throws Exception {
			// Prepare
			when(directoryProvider.getUploadDirectory()).thenReturn(new File(tempDir.toString()));
			// Run
			unitUnderTest.storeStreamContentInFile(FILE_NAME, inputStream);
			// Check
			String written = Files.readString(Path.of(tempDir.toString() + "/" + FILE_NAME));
			assertEquals(CONTENT, written);
		}

	}

}