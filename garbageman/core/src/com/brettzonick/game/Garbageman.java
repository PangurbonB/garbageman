package com.brettzonick.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brettzonick.game.world.InputHandler;

public class Garbageman extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	public boolean close = false;
	public static int x, y = 0;



	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("assets/tyrone.jpg");

		Gdx.input.setInputProcessor(new InputHandler());

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isKeyPressed(Input.Keys.Q))
			Gdx.app.exit();
		if (Gdx.input.isKeyPressed(Input.Keys.L))
			Garbageman.x += 3;
		if (Gdx.input.isKeyPressed(Input.Keys.J))
			Garbageman.x -= 3;
		if (Gdx.input.isKeyPressed(Input.Keys.I))
			Garbageman.y += 3;
		if (Gdx.input.isKeyPressed(Input.Keys.K))
			Garbageman.y -= 3;

		batch.begin();

		batch.draw(img, x, y);

		batch.end();

	}
}
