package com.garbageman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.garbageman.game.cooked.Burrito;
import com.garbageman.game.cooked.Cake;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.cooked.Hotdog;
import com.garbageman.game.cooked.Pizza;
import com.garbageman.game.cooked.Sandwich;
import com.garbageman.game.cooked.Soup;
import com.garbageman.game.cooked.Sushi;
import com.garbageman.game.garbage.AppleCore;
import com.garbageman.game.garbage.BagOfSugar;
import com.garbageman.game.garbage.BananaPeel;
import com.garbageman.game.garbage.Bean;
import com.garbageman.game.garbage.Bread;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
import com.garbageman.game.garbage.DirtyKitchenSponge;
import com.garbageman.game.garbage.Feces;
import com.garbageman.game.garbage.HandfulOfAnts;
import com.garbageman.game.garbage.HomelessBeardShavings;
import com.garbageman.game.garbage.Ketchup;
import com.garbageman.game.garbage.Leaf;
import com.garbageman.game.garbage.Lettuce;
import com.garbageman.game.garbage.McdFries;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.OldNewspaper;
import com.garbageman.game.garbage.Pork;
import com.garbageman.game.garbage.RabbitFoot;
import com.garbageman.game.garbage.Salad;
import com.garbageman.game.garbage.Smarties;
import com.garbageman.game.garbage.Strawberry;
import com.garbageman.game.garbage.ToiletPaper;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.garbage.Vomit;
import com.garbageman.game.screens.MainMenuScreen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

	//nast bar colors:
	public Color RED = Color.valueOf("#ff0000");
	public Color ORANGE = Color.valueOf("#ff6a00");
	public Color YELLOW = Color.valueOf("#ffe100");
	public Color GREEN = Color.valueOf("#59ff00");

	public java.util.Map<String, Color> colorMap = Collections.synchronizedMap(new HashMap());

	public java.util.Map<String, Integer> typeMap = Collections.synchronizedMap(new HashMap());
	public Trash objTemp = new McdHamburger();

	public Class[] garbageItems = {
			AppleCore.class,
			BagOfSugar.class,
			BananaPeel.class,
			Bean.class,
			Bread.class,
			CrowWithOddEyeInfection.class,
			DirtyKitchenSponge.class,
			Feces.class,
			HandfulOfAnts.class,
			HomelessBeardShavings.class,
			Ketchup.class,
			Leaf.class,
			Lettuce.class,
			McdFries.class,
			McdHamburger.class,
			MysteryEyeball.class,
			OldNewspaper.class,
			Pork.class,
			RabbitFoot.class,
			Salad.class,
			Smarties.class,
			Strawberry.class,
			ToiletPaper.class,
			Vomit.class
	};

	public Class[] foodItems = {
			Burrito.class,
			Cake.class,
			Hotdog.class,
			Pizza.class,
			Sandwich.class,
			Soup.class,
			Sushi.class,
	};

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
				Trash burger = new Smarties();
				burger.setNast(100);
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

		//nast bar:
		colorMap.put("Red", this.RED);
		colorMap.put("Orange", this.ORANGE);
		colorMap.put("Yellow", this.YELLOW);
		colorMap.put("Green", this.GREEN);

		//type filler
		//"Veggies", "Meats", "Wraps", "Fillers", "Sweeteners", "Sauces"
		typeMap.put("Veggies", objTemp.VEGGIE);
		typeMap.put("Meats", objTemp.MEAT);
		typeMap.put("Wraps", objTemp.WRAP);
		System.out.println("FILLER: "+objTemp.FILLER);
		typeMap.put("Fillers", objTemp.FILLER);
		System.out.println("NEW FILLER: "+typeMap.get("Fillers"));
		typeMap.put("Sweeteners", objTemp.SWEETENER);
		typeMap.put("Sauces", objTemp.SAUCE);
		//typeMap.put("Completed Meals", new CookedFood().)//make this show cooked food later -Dana
	}

	@Override
	public void render () {
       // this.render();
		if (Gdx.input.isKeyPressed(Input.Keys.Q)){
			Gdx.app.exit();
		}
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
