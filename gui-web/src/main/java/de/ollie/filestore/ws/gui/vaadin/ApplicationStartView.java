package de.ollie.filestore.ws.gui.vaadin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import de.ollie.filestore.ws.core.model.localization.LocalizationSO;
import de.ollie.filestore.ws.core.service.DirectoryProvider;
import de.ollie.filestore.ws.core.service.FileService;
import de.ollie.filestore.ws.core.service.FileService.FileData;
import de.ollie.filestore.ws.core.service.localization.ResourceManager;
import de.ollie.filestore.ws.gui.utils.BorderUtils;
import lombok.RequiredArgsConstructor;

/**
 * A start view for the application.
 */
@Route(ApplicationStartView.URL)
@PreserveOnRefresh
@VaadinSessionScope
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
@CssImport(value = "./styles/vaadin-text-area-styles.css", themeFor = "vaadin-area-field")
@RequiredArgsConstructor
public class ApplicationStartView extends VerticalLayout {

	public static final LocalizationSO LOCALIZATION = LocalizationSO.DE;
	public static final Logger LOG = LogManager.getLogger(ApplicationStartView.class);
	public static final String URL = "filestorews";

	private final DirectoryProvider directoryProvider;
	private final FileService fileService;
	private final ResourceManager resourceManager;
	private final WebAppConfiguration configuration;

	private Grid<FileData> grid = null;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		LOG.info("attached");
		removeAll();
		addHeaderComponent();
		addUploadComponent();
		addDirectoryComponent();
	}

	private void addHeaderComponent() {
		VerticalLayout layout = new VerticalLayout();
		layout.setWidthFull();
		BorderUtils.addShadowBorder(layout);
		Label label =
				new Label(
						resourceManager
								.getLocalizedString("app.title", LocalizationSO.DE)
								.replace("{}", configuration.getAppVersion()));
		label.getStyle().set("font-weight", "bold");
		layout.add(label);
		add(layout);
	}

	private void addUploadComponent() {
		MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setMaxFileSize(directoryProvider.getUploadMaxFileSize());
		upload.addSucceededListener(event -> {
			LOG.info("upload started.");
			String fileName = event.getFileName();
			InputStream inputStream = buffer.getInputStream(fileName);
			LOG.info("upload complete!");
			try {
				fileService.storeStreamContentInFile(fileName, inputStream);
			} catch (IOException ioe) {
				LOG.error("while uploading file: " + fileName, ioe);
			}
			updateView();
		});
		upload.setWidthFull();
		BorderUtils.addShadowBorder(upload);
		add(upload);
	}

	private void updateView() {
		LOG.info("update view");
		List<FileData> fileData = List.of();
		try {
			fileData = fileService.getFiles();
		} catch (IOException ioe) {
			LOG.error("while reading files", ioe);
		}
		grid.setItems(fileData);
	}

	private void addDirectoryComponent() {
		grid = new Grid<>(20);
		grid
				.addColumn(fileData -> fileData.getFileName())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ApplicationStartView.grid.header.fileName.label",
										LocalizationSO.DE))
				.setSortable(true);
		grid
				.addColumn(fileData -> fileData.getSizeInBytes())
				.setHeader(
						resourceManager
								.getLocalizedString(
										"ApplicationStartView.grid.header.fileSize.label",
										LocalizationSO.DE))
				.setSortable(true);
		BorderUtils.addShadowBorder(grid);
		add(grid);
		updateView();
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		LOG.info("detached");
	}

}