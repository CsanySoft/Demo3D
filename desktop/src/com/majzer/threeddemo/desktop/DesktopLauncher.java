package com.majzer.threeddemo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.majzer.threeddemo.ModelDemo;
import com.majzer.threeddemo.ThreeDDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		ew LwjglApplication(new ThreeDDemo(), config);
		new LwjglApplication(new ModelDemo(), config);
	}
}
