package com.garbageman.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.screens.MainMenuScreen;
import com.garbageman.game.screens.Trashcan;

import java.util.ArrayList;
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
    private boolean SAFE_MODE = false;
	private boolean autoGenInvItems = true;
	private int numOfGenItems = 11;

	public PassTrash passTrash = new PassTrash(this);

	//colors for rarity:
	public static Color COMMON = Color.WHITE;
	public static Color UNCOMMON = Color.valueOf("#08f900");
	public static Color RARE = Color.valueOf("#00bef9");
	public static Color VERYRARE = Color.valueOf("#8d00f9");
	public static Color LEGENDARY = Color.valueOf("#00f9f0");
	public static Color BEYOND_COMPREHENSION = Color.valueOf("#f9009d");
	public static Color BOUGHT = Color.BLACK;

	//nast bar colors:
	public static Color RED = Color.valueOf("#ff0000");
	public static Color ORANGE = Color.valueOf("#ff6a00");
	public static Color YELLOW = Color.valueOf("#ffe100");
	public static Color GREEN = Color.valueOf("#59ff00");

	public static ArrayList<Class> garbageItems;
	public static ArrayList<Class> safeModeExclusions;
	public static ArrayList<Class> customers;
	public static ArrayList<Class> foodItems;

	public java.util.Map<String, Color> colorMap;

	public java.util.Map<String, Integer> typeMap;

	public void giveReputation(int amt){
		int plus = this.reputation+amt;
		if (plus >= 0 && plus <= this.repMax){
			this.reputation = plus;
		}
		else
			System.out.println("error: " + amt + " rep tried to give");
		System.out.println("new rep " + this.reputation);
	}

	public static BitmapFont makeFont(int size){
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
		this.garbageItems = ListAccess.garbageItems;
		this.safeModeExclusions = ListAccess.safeModeExclusions;
		this.customers = ListAccess.customers;
		this.foodItems = ListAccess.foodItems;

		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
		ArrayList<Class> deleteList = new ArrayList<Class>();
        if (SAFE_MODE){
			for (Class i : garbageItems) {
				for (Class j : safeModeExclusions) {
					if (i.equals(j)){
						deleteList.add(i);
					}
				}
			}
			for (Class i : deleteList) {
				garbageItems.remove(i);
			}
		}

		if (autoGenInvItems){
			//Trashcan trashcan = new Trashcan(this);
			for (int x = 0; x <= numOfGenItems; x++){
				int num = new Random().nextInt(this.garbageItems.size());
				Trash item = Trashcan.makeRandGarbage(num);
				item.setVisible(true);
				item.setSize(UI.squareSize, UI.squareSize);
				item.toFront();
				item.setImg();
				backpack.add(item);
				System.out.println("added an item "+item.name);
			}
		}
		this.setScreen(new MainMenuScreen(this));

        ListAccess.updateMaps();
        this.colorMap = ListAccess.colorMap;
        this.typeMap = ListAccess.typeMap;

		//typeMap.put("Completed Meals", new CookedFood().)//make this show cooked food later -Dana
	}

	@Override
	public void render () {
		if (Gdx.input.isKeyPressed(Input.Keys.Q)){
			Gdx.app.exit();
		}
		super.render();
	}
}
