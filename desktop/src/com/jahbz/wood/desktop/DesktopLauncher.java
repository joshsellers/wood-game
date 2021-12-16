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
		config.width = screenSize.width / 2;
		config.height = screenSize.height / 2;

		Main.WIDTH = config.width / (int) Main.VIEWPORT_SCALE;
		Main.HEIGHT = config.height / (int) Main.VIEWPORT_SCALE;

		config.y -= 2;

		new LwjglApplication(new Main(), config);
	}
}
