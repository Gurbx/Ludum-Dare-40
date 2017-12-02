package com.gurbx.ld40.utils;

import com.badlogic.gdx.Screen;
import com.gurbx.ld40.Application;

public abstract class GameScreen implements Screen {
	protected final Application app;
	
	public GameScreen(Application app) {
		this.app = app;
	}

}
