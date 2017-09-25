package com.brettzonick.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brettzonick.game.screens.GameScreen;
import com.brettzonick.game.screens.MainMenuScreen;
import com.brettzonick.game.world.InputHandler;

public class Garbageman extends Game {

	//WELMERT

	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new GameScreen(this));


	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.E))
			this.setScreen(new MainMenuScreen(this));
		super.render();
	}
}
