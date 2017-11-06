package com.brettzonick.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.brettzonick.game.java.Garbageman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "You wish your trash was as hot as ours";
		config.resizable = false;
		config.height = 720;
		config.width = 1280;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;

		new LwjglApplication(new Garbageman(), config);
	}
}
