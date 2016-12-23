package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MyBochek;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = MyBochek.WIDTH;
		config.height = MyBochek.HEIGHT;
		config.title = MyBochek.TITLE;
		new LwjglApplication(new MyBochek(), config);
	}
}
