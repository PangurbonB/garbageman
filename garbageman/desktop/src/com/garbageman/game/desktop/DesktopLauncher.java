package com.garbageman.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.garbageman.game.Garbageman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Garbageman.title;
		config.foregroundFPS = 60;
		config.resizable = false;
		config.height = Garbageman.window_height;
		config.width = Garbageman.window_width;
		config.forceExit = false;
		new LwjglApplication(new Garbageman(), config);
	}
}
