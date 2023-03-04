package com.example.lab23a;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.example.lab23a.model.FileBuilder;

/**
 * Loads image files from file system
 */
public class IconLoader {
	/**
	 *
	 * @return icon of the Termverse application
	 */
	public static Image load() {
		return new Image(FileBuilder.getIconDestination());
	}

	/**
	 *
	 * @return icon with exclamation point for pop-up windows
	 */
	public static Image loadAttentionIcon() {
		return new Image(FileBuilder.getAttentionIconDestination());
	}

	/**
	 * Loads attention icon and puts in the stage
	 * @param addTo - the stage, to which attention icon will be added
	 */
	public static void addAttentionIcon(Stage addTo) {
		addTo.getIcons().add(loadAttentionIcon());
	}
}
