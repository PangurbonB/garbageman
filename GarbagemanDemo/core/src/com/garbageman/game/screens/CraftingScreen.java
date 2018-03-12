package com.garbageman.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    public static boolean allSelected = false;
    int[][] craftingLocs = new int[8][2];
    int centerX = 641;
    int centerY = 359;
    boolean changed = false;
    SpriteSheetDivider sp = new SpriteSheetDivider();

    CookedFood input = new CookedFood();

    Trash bird = new CrowWithOddEyeInfection();

    Garbageman game;

    Class[] foodItems;

    public static String screenName = "Crafting";
    public static int place = 0;
    Skin sk = new Skin();
    Skin s = new Skin();




    public CraftingScreen(Garbageman game){
        this.game = game;
        foodItems = game.foodItems;
        for (int i = 0; i < foodItems.length; i++) {
            try {
                CookedFood test = (CookedFood) Class.forName(foodItems[i].getName()).newInstance();
                s.add(test.name.toLowerCase(), "assets/Food/"+test.name.toLowerCase()+".png");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void show() {
        game.currentScreen = this.screenName;
        try {
            input = (CookedFood) Class.forName(foodItems[0].getName()).newInstance();
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


        game.ui.init(this.game, this.stage, this.screenName);
        game.ui.makeUI();

        input.setSize(96, 96);
        input.setX(stage.getWidth()/2 - input.getWidth() + 40);
        input.setY(stage.getHeight()/2 - input.getHeight() + 35);
        input.setVisible(true);
        place = 0;
        input.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                input.setDrawable(sk, "name");
                input.name = "increment";
                return super.touchDown(event, x, y, pointer, button);
            }
        });



        sk.add("name", "assets/food/"+input.name+".png");
        input.setDrawable(sk, "name");
        stage.addActor(input);

        background.toBack();
        background.setVisible(true);
        background.setHeight(stage.getHeight());
        background.setWidth(stage.getWidth());
        stage.addActor(background);

        craftingLocs[0] = new int[]{377, 223};
        craftingLocs[1] = new int[]{907, 223};
        craftingLocs[2] = new int[]{1083, 350};
        craftingLocs[3] = new int[]{1020, 540};
        craftingLocs[4] = new int[]{823, 603};
        craftingLocs[5] = new int[]{457, 603};
        craftingLocs[6] = new int[]{260, 540};
        craftingLocs[7] = new int[]{197, 350};

        drawNewRecipe(input);



        input.toFront();


    }

    /*public CookedFood cook(CookedFood input, Trash[] trashes){
        try{

        }
        catch (){

        }


        return input;
    }*/

    public void drawNewRecipe(final CookedFood f){
        final ArrayList<Trash> trashes = new ArrayList<Trash>();
        Random rand = new Random();
        for (int i = 0; i < craftingLocs.length; i++) {
            final Trashcan tr = new Trashcan(game);
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
                    System.out.println("fugg");

                    game.ui.showInv = !game.ui.showInv;
                    trashes.get(k).setSelectedInInv(true);

                    allSelected = allSelected(trashes);
                    System.out.println("ALL SELECTED:    "+allSelected);
                    trashes.get(k).setImg();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            trashes.get(i).setSelectedInInv(false);
            if (i!=7) {
                trashes.get(i).setX(craftingLocs[i + 1][0] - 65);
                trashes.get(i).setY(stage.getHeight() - craftingLocs[i + 1][1] - 65);
                if (i>=input.reqTypes.length) {
                    game.ui.makeRect((int) craftingLocs[i + 1][0] - 50, (int) stage.getHeight() - craftingLocs[i + 1][1] - 50, 100, 100, Color.valueOf("#85c2ce"), true); //Light Blue rects
                }
                else {
                    game.ui.makeRect((int) craftingLocs[i + 1][0] - 50, (int) stage.getHeight() - craftingLocs[i + 1][1] - 50, 100, 100, Color.valueOf("#61c398"), true); //Light Green rects
                }
                trashes.get(i).toFront();
            }
            else{

                game.ui.makeRect((int) craftingLocs[0][0] - 50, (int) stage.getHeight() - craftingLocs[0][1] - 50, 100, 100, Color.valueOf("#85c2ce"), true); //Always a blue, always optional
                trashes.get(i).setX(craftingLocs[0][0] - 65);
                trashes.get(i).setY(stage.getHeight() - craftingLocs[0][1] - 65);
                trashes.get(i).toFront();
            }
        }
    }

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

        list[8] = f;
        return list;
    }

    @Override
    public void render(float delta) {

        if (changed){
            drawNewRecipe(input);
            changed ^= true;
        }


        if (input.name.equals("increment")){
            changed = true;
            place++;
            int loc = place;
            System.out.println("gotthere");

            if (loc >= foodItems.length) {
                place = 0;
            }
            loc = place;
            for (int i = 0; i < foodItems.length; i++) {
                System.out.println(foodItems[i].getName());
            }

            try {
                input.setVisible(false);
                input = (CookedFood) Class.forName(foodItems[loc].getName()).newInstance();
                System.out.println(foodItems[loc].getName());
                System.out.println(input.name);
                input.setSize(96, 96);
                input.setX(stage.getWidth()/2 - input.getWidth() + 40);
                input.setY(stage.getHeight()/2 - input.getHeight() + 35);
                input.setVisible(true);
                TextureRegion t = new TextureRegion(new Texture("assets/food/"+input.name+"Ghost.png"));
                TextureRegionDrawable tt = new TextureRegionDrawable();
                t.setRegion(0,0,32,32);
                tt.setRegion(t);
                input.setDrawable(tt);
                stage.addActor(input);
                input.toFront();

                input.addListener(new InputListener(){
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        input.setDrawable(sk, "name");
                        input.name = "increment";
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (allSelected){
            TextureRegion t = new TextureRegion(new Texture("assets/food/"+input.name+".png"));
            TextureRegionDrawable tt = new TextureRegionDrawable();
            t.setRegion(0,0,32,32);
            tt.setRegion(t);
            input.setDrawable(tt);
        }

        Gdx.input.setInputProcessor(stage);

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
            System.out.println("INPUT PROCESS: "+Gdx.input.getInputProcessor().equals(game.ui.stage));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            game.setScreen(new MainMenuScreen(this.game));
        }

       Gdx.input.setInputProcessor(stage);

        game.ui.update();
        stage.draw();
    }

    private boolean allSelected(ArrayList ar){
        System.out.println("Size:   "+ar.size());
        for (int i = 0; i < ar.size()-1; i++) {
            System.out.println("STUFF:   " + (((Trash) ar.get(i)).getSelectedInInv()));
            if (!(((Trash) ar.get(i)).getSelectedInInv())){
                return false;
            }
        }
        return true;
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
