package com.jahbz.wood.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.jahbz.wood.core.Main;

import java.awt.*;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "wood";
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = screenSize.width;
		config.height = screenSize.height;
		Main.WIDTH = config.width;
		Main.HEIGHT = config.height;
		//config.addIcon("sprite_sheet.png", Files.FileType.Internal);
		config.y -= 2;
		//config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
