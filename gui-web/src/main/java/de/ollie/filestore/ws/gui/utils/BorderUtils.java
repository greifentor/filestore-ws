package de.ollie.filestore.ws.gui.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.dom.Element;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BorderUtils {
	
	public void addShadowBorder(Component component) {
		Element element = component.getElement();
		element.getStyle().set("-moz-border-radius", "4px");
		element.getStyle().set("-webkit-border-radius", "4px");
		element.getStyle().set("border-radius", "4px");
		element.getStyle().set("border", "1px solid gray");
		element
				.getStyle()
				.set(
						"box-shadow",
						"10px 10px 20px #e4e4e4, -10px 10px 20px #e4e4e4, -10px -10px 20px #e4e4e4, 10px -10px 20px #e4e4e4");
	}

}
