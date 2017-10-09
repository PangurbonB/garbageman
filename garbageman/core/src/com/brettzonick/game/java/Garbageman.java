package com.brettzonick.game.java;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brettzonick.game.java.screens.GameScreen;
import com.brettzonick.game.java.screens.MainMenuScreen;
import com.brettzonick.game.java.screens.Trashcan;
import com.brettzonick.game.java.world.InputHandler;

public class Garbageman extends Game {

	//WELMERT

	public SpriteBatch batch;
	public static final int window_height = 720;
	public static final int window_width = 1280;


	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new Trashcan(this));
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.E))
			this.setScreen(new MainMenuScreen(this));
		super.render();
	}
}
