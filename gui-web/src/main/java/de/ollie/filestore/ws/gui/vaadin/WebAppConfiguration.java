package de.ollie.filestore.ws.gui.vaadin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * A class for the web app configuration;
 */
@Configuration
@Getter
public class WebAppConfiguration {

	@Value("${app.version}")
	private String appVersion;

}
