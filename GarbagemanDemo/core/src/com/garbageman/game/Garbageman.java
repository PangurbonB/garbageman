package com.garbageman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.garbageman.game.garbage.Leaf;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.screens.MainMenuScreen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class Garbageman extends Game {
	public SpriteBatch batch;
	public static final int window_height = 720;
	public static final int window_width = 1280;
	public static final String title = "Garbageman";
	public String currentScreen = "MainMenuScreen";
	public AssetManager manager = new AssetManager();
	public static boolean canResize = false;
	public int money = 50;
	public int reputation = 50;
	public final int repMax = 100;
	public String[] sections = {"Veggies", "Meats", "Wraps", "Fillers", "Sweeteners", "Sauces"};
	public String[] sectionsForMainInv = {"Veggies", "Meats", "Wraps", "Fillers", "Sweeteners", "Sauces", "Complete Meals", "Restaurant Items"};

	public Backpack backpack = new Backpack();
	public UI ui = new UI();


	//colors for rarity:
	public Color COMMON = Color.WHITE;
	public Color UNCOMMON = Color.valueOf("#08f900");
	public Color RARE = Color.valueOf("#0004f9");
	public Color VERYRARE = Color.valueOf("#8d00f9");
	public Color LEGENDARY = Color.valueOf("#00f9f0");
	public Color BEYOND_COMPREHENSION = Color.valueOf("#f9009d");
	public Color BOUGHT = Color.BLACK;

	public java.util.Map<String, Color> colorMap = Collections.synchronizedMap(new HashMap());


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
		/*for (int i = 0; i <= 6; i++) {
			Trash let = new MysteryEyeball();
			let.setNast(new Random().nextInt(100));
			this.backpack.contents.add(let);
			//eyeball, banana, crow
			if (i == 4){
				McdHamburger burger = new McdHamburger();
				burger.setNast(10);
				this.backpack.contents.add(burger);
			}
		}//*/
		//Gdx.input.setInputProcessor(new GetInput());

		colorMap.put("Common", COMMON);
		colorMap.put("Uncommon", UNCOMMON);
		colorMap.put("Rare", RARE);
		colorMap.put("Very Rare", VERYRARE);
		colorMap.put("Legendary", LEGENDARY);
		colorMap.put("Bought", BOUGHT);
		colorMap.put("???", BEYOND_COMPREHENSION);
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
