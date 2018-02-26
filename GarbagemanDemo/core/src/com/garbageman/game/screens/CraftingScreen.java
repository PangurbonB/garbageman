package com.garbageman.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.garbageman.game.Garbageman;
import com.garbageman.game.SpriteSheetDivider;
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

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class CraftingScreen implements Screen{

    Image background = new Image(new Texture("assets/Screens/craftingScreen2.png"));
    Stage stage = new Stage();
    int[][] craftingLocs = new int[8][2];
    int centerX = 641;
    int centerY = 359;
    SpriteSheetDivider sp = new SpriteSheetDivider();



    Trash bird = new CrowWithOddEyeInfection();

    Garbageman game;

    Class[] foodItems;

    String screenName = "Crafting";
    Skin sk = new Skin();
    CookedFood input;




    public CraftingScreen(Garbageman game){
        this.game = game;
        foodItems = game.foodItems;
    }

    @Override
    public void show() {

        CookedFood input;

        game.currentScreen = this.screenName;
        Random rand = new Random();
        try {
            input = (CookedFood) Class.forName(foodItems[rand.nextInt(foodItems.length)].getName()).newInstance();
        } catch (InstantiationException e) {
            input = new Hotdog();
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            input = new Hotdog();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            input = new Hotdog();
            e.printStackTrace();
        }
        sk.add("name", new Texture("assets/Food/" + input.name.toLowerCase() + ".png"));
        sk.add("ghost", new Texture("assets/Food/"+ input.name.toLowerCase()+ "Ghost.png"));


        game.ui.init(game, stage, screenName);
        game.ui.makeUI();

        input.setSize(96, 96);
        input.setX(stage.getWidth()/2 - input.getWidth() + 40);
        input.setY(stage.getHeight()/2 - input.getHeight() + 35);
        input.setVisible(true);





        input.setDrawable(sk, "name");
        stage.addActor(input);

        background.toBack();
        background.setVisible(true);
        background.setHeight(stage.getHeight());
        background.setWidth(stage.getWidth());
        stage.addActor(background);

        craftingLocs[0] = new int[]{377, 222};
        craftingLocs[1] = new int[]{904, 221};
        craftingLocs[2] = new int[]{1081, 348};
        craftingLocs[3] = new int[]{1014, 544};
        craftingLocs[4] = new int[]{819, 596};
        craftingLocs[5] = new int[]{451, 599};
        craftingLocs[6] = new int[]{252, 534};
        craftingLocs[7] = new int[]{193, 349};


        final ArrayList<Trash> trashes = new ArrayList<Trash>();

        for (int i = 0; i < craftingLocs.length; i++) {
            Trashcan tr = new Trashcan(game);
            int x = rand.nextInt(game.garbageItems.length-1);

            Actor[] list = makeGhosts(input);
            System.out.println(i);
            System.out.println(list.length);
            Trash crow = (Trash) list[i];


            crow.setName(Integer.toString(i));
            trashes.add(crow);
            stage.addActor(trashes.get(i));
            trashes.get(i).setVisible(true);
            trashes.get(i).setSize(96, 96);
            trashes.get(i).toFront();
            final int k = i;
            trashes.get(i).addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    trashes.get(k).setDrawable(sk, "name");
                    System.out.println("fugg");
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            if (i!=7) {
                trashes.get(i).setX(craftingLocs[i + 1][0] - 50);
                trashes.get(i).setY(stage.getHeight() - craftingLocs[i + 1][1] - 65);
            }
            else{
                trashes.get(i).setX(craftingLocs[0][0] - 50);
                trashes.get(i).setY(stage.getHeight() - craftingLocs[0][1] - 65);
            }
        }
        input.toFront();

    }

    /*public CookedFood cook(CookedFood input, Trash[] trashes){
        try{

        }
        catch (){

        }


        return input;
    }*/

    public Actor[] makeGhosts(final CookedFood f){

        Actor[] list = new Actor[9];

        for (int i = 0; i < (f.reqTypes.length); i++) {
            Trash newT;
            switch (f.reqTypes[i]){
                default:
                    newT = new MysteryEyeball();
                    break;
                case Trash.MEAT:
                    newT = new Pork();
                    newT.setImg("PorkGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.VEGGIE:
                    newT = new Salad();
                    newT.setImg("SaladGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.WRAP:
                    newT = new Bread();
                    newT.setImg("BreadGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.FILLER:
                    newT = new Bean();
                    newT.setImg("beanGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.SWEETENER:
                    newT = new BagOfSugar();
                    newT.setImg("bagOfSugarGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.SAUCE:
                    newT = new Ketchup();
                    newT.setImg("KetchupGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.ANYTHING:
                    newT = new CrowWithOddEyeInfection();
                    break;

            }
            //newT.setColor(Color.LIGHT_GRAY);
            list[i] = newT;
        }
        for (int i = f.reqTypes.length; i < f.reqTypes.length+f.optionalTypes.length; i++) {
            Trash newT;
            switch (f.optionalTypes[i-f.reqTypes.length]){
                default:
                    newT = new MysteryEyeball();
                    newT.setImg();
                    break;
                case Trash.MEAT:
                    newT = new Pork();
                    newT.setImg("PorkGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.VEGGIE:
                    newT = new Salad();
                    newT.setImg("SaladGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.WRAP:
                    newT = new Bread();
                    newT.setImg("BreadGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.FILLER:
                    newT = new Bean();
                    newT.setImg("beanGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.SWEETENER:
                    newT = new BagOfSugar();
                    newT.setImg("bagOfSugarGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.SAUCE:
                    newT = new Ketchup();
                    newT.setImg("KetchupGhost");
                    System.out.println(newT.name);
                    break;
                case Trash.ANYTHING:
                    newT = new CrowWithOddEyeInfection();
                    newT.setImg();
                    break;
            }
            //newT.setColor(Color.LIGHT_GRAY);
            list[i] = newT;
        }
        //f.setColor(Color.LIGHT_GRAY);
        System.out.println(f.name.toLowerCase());

        f.setDrawable(sk, "ghost");


        list[8] = f;
        return list;
    }

    @Override
    public void render(float delta) {


        if(Gdx.input.isKeyJustPressed(Input.Keys.M))
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());

        game.ui.update();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
