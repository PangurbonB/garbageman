package com.garbageman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.IntFloatMap;
import com.garbageman.game.game.world.GetInput;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.Trash;
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
	public String[] sections = {"Veggies", "Meats", "Wraps", "Fillers", "Sweeteners", "Sauces"};
	public String[] sectionsForMainInv = {"Veggies", "Meats", "Wraps", "Fillers", "Sweeteners", "Sauces", "Complete Meals", "Restaurant Items"};

	public Backpack backpack = new Backpack();
	public UI ui = new UI();


	//colors for rarity:
	public Color common = Color.WHITE;
	public Color uncommon = Color.valueOf("#08f900");
	public Color rare = Color.valueOf("#0004f9");
	public Color veryRare = Color.valueOf("#8d00f9");
	public Color legendary = Color.valueOf("#00f9f0");
	public Color questionMark = Color.valueOf("#f9009d");
	public Color purchased = Color.BLACK;


	public void print(final String msg){
		System.out.println(msg);
	}

	public void giveReputation(int amt){
		int plus = this.reputation+amt;
		if (plus >= 0 && plus <= this.repMax){
			this.reputation = plus;
		}
		else
			System.out.println("error: " + amt + " rep tried to give");
		System.out.println("new rep " + this.reputation);
	}

	public BitmapFont makeFont(int size){
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/PressStart2P.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = size;
		BitmapFont press2P = generator.generateFont(parameter);
		generator.dispose();
		return press2P;
	}

	public void giveMoney(int amt){
		//for now
		if (this.money + amt >= 0)
			this.money = this.money + amt;
		else
			System.out.println("Money can't be less than 0: " + (this.money+amt));

	}


	@Override
	public void create () {
        //this.create();
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		for (int i = 0; i <= 6; i++)
			this.backpack.contents.add(new MysteryEyeball());

		//Gdx.input.setInputProcessor(new GetInput());
	}

	@Override
	public void render () {
       // this.render();
		super.render();
		//System.out.println(this.currentScreen);
		//System.out.println(this.currentScreen);
	}
	
	/*@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
	*/


}
