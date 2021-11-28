package com.jahbz.wood.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jahbz.wood.core.Main;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Wood";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = screenSize.width;
		config.height = screenSize.height;
		config.addIcon("sprite_sheet.png", Files.FileType.Internal);
		//config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
