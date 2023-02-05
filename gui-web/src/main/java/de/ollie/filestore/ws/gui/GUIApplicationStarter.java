package de.ollie.filestore.ws.gui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("de.ollie.filestore.ws")
public class GUIApplicationStarter extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GUIApplicationStarter.class, args);
	}

}