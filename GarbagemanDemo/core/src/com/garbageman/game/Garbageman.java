package com.garbageman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.IntFloatMap;
import com.garbageman.game.game.world.GetInput;
import com.garbageman.game.screens.GameScreen;
import com.garbageman.game.screens.MainMenuScreen;

public class Garbageman extends Game {
	public SpriteBatch batch;
	public static final int window_height = 720;
	public static final int window_width = 1280;
	public static final String title = "Garbageman";
	public String currentScreen = "MainMenuScreen";
	public AssetManager manager = new AssetManager();
	public int money = 50;
	public int reputation = 50;
	public final int repMax = 100;

	public void print(final String msg){
		System.out.println(msg);
	}

	@Override
	public void create () {
        //this.create();
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));

		//Gdx.input.setInputProcessor(new GetInput());
	}

	@Override
	public void render () {
       // this.render();
		super.render();
		//System.out.println(this.currentScreen);
	}
	
	/*@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	*/


}
