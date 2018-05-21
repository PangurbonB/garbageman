package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;
import com.garbageman.game.PassTrash;
import com.garbageman.game.SpriteSheetDivider;
import com.garbageman.game.cooked.Burrito;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.garbage.BagOfSugar;
import com.garbageman.game.garbage.Bean;
import com.garbageman.game.garbage.Bread;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
import com.garbageman.game.garbage.Ketchup;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.Pork;
import com.garbageman.game.garbage.Salad;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class CookingScreen implements Screen{

    Image background = new Image(Assets.findTexture("cookingScreen"));
    Stage stage = new Stage();
    public static boolean allSelected = false;
    int[][] craftingLocs = new int[8][2];
    int centerX = 641;
    int centerY = 359;
    boolean changed = false;
    boolean fakeinvScreen = false;
    SpriteSheetDivider sp = new SpriteSheetDivider();

    final ArrayList<Trash> trashes = new ArrayList<Trash>();

    CookedFood item;

    CookedFood input = new CookedFood();

    Garbageman game;

    ArrayList<Class> foodItems;

    public static String screenName = "Crafting";
    private TextButton fakeMenu, fakeInventory;
    private TextButton cookButton;
    private Actor cookBackground;
    Skin sk = Assets.newSkin();
    Skin s = Assets.newSkin();


    TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
    private TextButton shopButton, scavengeButton;
    BitmapFont font12 = game.makeFont(35);


    public CookingScreen(Garbageman game, CookedFood item){

        this.item = item;
        this.game = game;
        this.foodItems = Garbageman.foodItems;

        foodItems = game.foodItems;
        for (int i = 0; i < foodItems.size(); i++) {
            try {
                CookedFood test = (CookedFood) Class.forName(foodItems.get(i).getName()).newInstance();
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

        //MUSIC STUFF
        /*--------------------------------------------------------------------------------------------*/
        int randomMusic = new Random().nextInt(2) + 1;
        if (!game.music.isPlaying()) {
            if (randomMusic == 1) {
                game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/chordy.wav"));
            }
            else{
                game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/menuey.wav"));
            }
            game.music.play();
            game.music.setLooping(true);

        }
        /*--------------------------------------------------------------------------------------------*/

        bs.font = font12;
        bs.fontColor = Color.BLACK;
        scavengeButton = new TextButton("Scavenge", bs);
        scavengeButton.setBounds(35, 575, 250, 50);
        scavengeButton.setVisible(true);
        scavengeButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Trashcan(game));
                return true;
            }
        });

        shopButton = new TextButton("Shop", bs);
        shopButton.setBounds(-33, 500, 250, 50);
        shopButton.setVisible(true);
        shopButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShopScreen(game));
                return true;
            }
        });

        stage.addActor(shopButton);
        stage.addActor(scavengeButton);



        if (item != null){
            try {
                for (int i = 0; i < foodItems.size(); i++) {
                    if (foodItems.get(i).equals(item.getClass())){
                        PassTrash.place = i;
                        System.out.println(i);
                    }
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }



        game.currentScreen = this.screenName;

        //inventory buttons:
        TextButton.TextButtonStyle topStyle = new TextButton.TextButtonStyle();
        topStyle.font = game.makeFont(25);
        fakeMenu = new TextButton("  ", topStyle);
        stage.addActor(fakeMenu);
        fakeMenu.setVisible(true);
        fakeMenu.setPosition(game.ui.menuButton.getX(), game.ui.menuButton.getY());
        fakeMenu.setSize(game.ui.menuButton.getWidth(), game.ui.menuButton.getHeight());
        fakeMenu.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.passTrash.dumpTrash();
                game.setScreen(new MainMenuScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        //System.out.println("POSITION:: "+fakeMenu.getX());



        TextButton.TextButtonStyle cookStyle = new TextButton.TextButtonStyle();
        cookStyle.font = Garbageman.makeFont(20);
        cookStyle.fontColor = Color.BLACK;
        cookButton = new TextButton("Cook " +input.name, cookStyle);

        cookButton.getLabel().setWrap(true);
        cookButton.setSize(200, 75);
        cookButton.setPosition((stage.getWidth()-cookButton.getWidth())/2, 50);
        cookBackground = game.ui.makeRect((int)cookButton.getX(), (int)cookButton.getY(), (int)cookButton.getWidth(), (int)cookButton.getHeight(), Color.LIME, false);
        cookBackground.toFront();
        cookButton.toFront();
        stage.addActor(cookBackground);
        stage.addActor(cookButton);
        cookButton.setVisible(false);
        cookButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.backpack.add(game.passTrash.cookFood(input, trashes));
                drawNewRecipe(input);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        try {
            //System.out.println(PassTrash.place + "LLLLLLLLLLLLLLLL");
            input = (CookedFood) Class.forName(foodItems.get(PassTrash.place).getName()).newInstance();
        } catch (InstantiationException e) {
            input = new Burrito();
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            input = new Burrito();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            input = new Burrito();
            e.printStackTrace();
        }

        sk.add("name", Assets.findTexture(input.name));
        sk.add("ghost", Assets.findTexture(input.name+"Ghost"));


        game.ui.init(this.game, this.stage, this.screenName);
        game.ui.makeUI();

        stage.addActor(input);
        input.setSize(96, 96);
        input.setX(stage.getWidth()/2 - input.getWidth() + 40);
        input.setY(stage.getHeight()/2 - input.getHeight() + 35);
        input.setVisible(true);
        input.setImg();
        TextureRegion t = new TextureRegion(Assets.findTexture(input.name+"Ghost"));
        TextureRegionDrawable tt = new TextureRegionDrawable();
        t.setRegion(0,0,32,32);
        tt.setRegion(t);
        input.setDrawable(tt);
        input.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                input.setImg();
                input.increment = true;
                game.passTrash.dumpTrash();
                return super.touchDown(event, x, y, pointer, button);
        }
        });


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
        changed = true;



        input.toFront();

        shopButton.toFront();
        scavengeButton.toFront();


    }

    /*public CookedFood cook(CookedFood input, Trash[] trashes){
        try{

        }
        catch (){

        }


        return input;
    }*/

    public void drawNewRecipe(final CookedFood f){
        trashes.clear();

        Random rand = new Random();

        for (int i = 0; i < craftingLocs.length; i++) {
            final Trashcan tr = new Trashcan(game);
            int x = rand.nextInt(game.garbageItems.size());
            Actor[] list = makeGhosts(f);
            Trash crow;
            try{
                crow = PassTrash.currentCooking[i];
                crow.setName(Integer.toString(i));
                crow.selected = true;
            }
            catch (NullPointerException e){
                //System.out.println("Nully Boy");
                crow = (Trash) list[i];
            }
            crow.setName(Integer.toString(i));

            trashes.add(crow);
            stage.addActor(trashes.get(i));
            if(trashes.get(i).type != Trash.NONE) {
                trashes.get(i).setVisible(true);
            }
            else {
                trashes.get(i).setVisible(false);
            }
            trashes.get(i).setSize(96, 96);
            trashes.get(i).toFront();
            final int k = i;

            if (i!=7) {
                trashes.get(i).setX(craftingLocs[i + 1][0] - 65);
                trashes.get(i).setY(stage.getHeight() - craftingLocs[i + 1][1] - 65);
                if (i>=f.reqTypes.length) {
                    game.ui.makeRect(craftingLocs[i + 1][0] - 50, (int) stage.getHeight() - craftingLocs[i + 1][1] - 50, 100, 100, Color.valueOf("#85c2ce"), true); //Light Blue rects
                }
                else {
                    game.ui.makeRect(craftingLocs[i + 1][0] - 50, (int) stage.getHeight() - craftingLocs[i + 1][1] - 50, 100, 100, Color.valueOf("#61c398"), true); //Light Green rects
                }
                trashes.get(i).toFront();
            }
            else{

                game.ui.makeRect((int) craftingLocs[0][0] - 50, (int) stage.getHeight() - craftingLocs[0][1] - 50, 100, 100, Color.valueOf("#85c2ce"), true); //Always a blue, always optional
                trashes.get(i).setX(craftingLocs[0][0] - 65);
                trashes.get(i).setY(stage.getHeight() - craftingLocs[0][1] - 65);
                trashes.get(i).toFront();
            }
            //System.out.println(trashes.get(i).selected);
        }
        //out.println("TSIZE:  "+trashes.size());
        for (int i = 0; i < trashes.size(); i++) {
            allSelected = allSelected(trashes);
            final int k = i;
            trashes.get(i).addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    game.passTrash.currentTypeToAdd = trashes.get(k).type;
                    game.passTrash.selectedIndex = k;
                    fakeinvScreen = true;
                    game.setScreen(new FakeInvScreen(game, trashes.get(k)));
                    trashes.get(k).setSelectedInInv(true);

                    trashes.get(k).setImg();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
            trashes.get(i).setSelectedInInv(false);
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
                    break;
                case Trash.VEGGIE:
                    newT = new Salad();
                    newT.setImg("SaladGhost");
                    break;
                case Trash.WRAP:
                    newT = new Bread();
                    newT.setImg("BreadGhost");
                    break;
                case Trash.FILLER:
                    newT = new Bean();
                    newT.setImg(new Bean().name + "Ghost");
                    break;
                case Trash.SWEETENER:
                    newT = new BagOfSugar();
                    newT.setImg(new BagOfSugar().name + "Ghost");
                    break;
                case Trash.SAUCE:
                    newT = new Ketchup();
                    newT.setImg("KetchupGhost");
                    break;
                case Trash.ANYTHING:
                    newT = new CrowWithOddEyeInfection();
                    break;

            }
            newT.setVisible(true);
            //newT.setColor(Color.LIGHT_GRAY);
            list[i] = newT;
        }
        for (int i = f.reqTypes.length; i < f.reqTypes.length+f.optionalTypes.length; i++) {
            Trash newT;
            switch (f.optionalTypes[i-f.reqTypes.length]){
                default:
                    newT = new MysteryEyeball();
                    newT.setImg("CLEAR");
                    newT.setVisible(false);
                    newT.setStateNone();
                    newT.toBack();
                    break;
                case Trash.MEAT:
                    newT = new Pork();
                    newT.setImg("PorkGhost");
                    newT.setVisible(true);
                    break;
                case Trash.VEGGIE:
                    newT = new Salad();
                    newT.setImg("SaladGhost");
                    newT.setVisible(true);
                    break;
                case Trash.WRAP:
                    newT = new Bread();
                    newT.setImg("BreadGhost");
                    newT.setVisible(true);
                    break;
                case Trash.FILLER:
                    newT = new Bean();
                    newT.setImg(new Bean().name + "Ghost");
                    newT.setVisible(true);
                    break;
                case Trash.SWEETENER:
                    newT = new BagOfSugar();
                    newT.setImg(new BagOfSugar().name + "Ghost");
                    newT.setVisible(true);
                    break;
                case Trash.SAUCE:
                    newT = new Ketchup();
                    newT.setImg("KetchupGhost");
                    newT.setVisible(true);
                    break;
                case Trash.ANYTHING:
                    newT = new CrowWithOddEyeInfection();
                    newT.setImg();
                    newT.setVisible(true);
                    break;
            }
            newT.setIsGhost(true);
            list[i] = newT;
        }

        list[8] = f;
        return list;
    }

    @Override
    public void render(float delta) {

        fakeMenu.toFront();
        fakeMenu.setVisible(true);

        for (Actor a: game.ui.coverTheseWithInv) {
            a.toFront();
        }

        if (changed){
            drawNewRecipe(input);
            changed ^= true;
        }


        if (input.increment == true){
            input.increment = false;
            allSelected = false;
            changed = true;
            PassTrash.place++;
            int loc = PassTrash.place;

            if (loc >= foodItems.size()) {
                PassTrash.place = 0;
            }
            loc = PassTrash.place;
            for (int i = 0; i < foodItems.size(); i++) {
                //System.out.println(foodItems.get(i).getName());
            }

            try {
                input.setVisible(false);
                input = (CookedFood) Class.forName(foodItems.get(loc).getName()).newInstance();
                input.setSize(96, 96);
                input.setX(stage.getWidth()/2 - input.getWidth() + 40);
                input.setY(stage.getHeight()/2 - input.getHeight() + 35);
                input.setVisible(true);
                TextureRegion t = new TextureRegion(Assets.findTexture(input.name+"Ghost"));
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
                        input.increment = true;
                        game.passTrash.dumpTrash();
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
            cookButton.setText("Cook " + input.name);
            TextureRegion t = new TextureRegion(Assets.findTexture(input.name));
            TextureRegionDrawable tt = new TextureRegionDrawable();
            t.setRegion(0,0,32,32);
            tt.setRegion(t);
            input.setDrawable(tt);

            cookBackground.setVisible(true);
            cookButton.setVisible(true);
        }
        else {
            cookBackground.setVisible(false);
            cookButton.setVisible(false);
        }

        Gdx.input.setInputProcessor(stage);

        if(Gdx.input.isKeyJustPressed(Input.Keys.M)) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
            System.out.println("INPUT PROCESS: "+Gdx.input.getInputProcessor().equals(game.ui.stage));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.F)){
            game.setScreen(new MainMenuScreen(this.game));
            this.dispose();
        }

       Gdx.input.setInputProcessor(stage);

        cookBackground.toFront();
        cookButton.toFront();



        game.ui.update();
        stage.draw();
    }

    private boolean allSelected(ArrayList ar){
        for (int i = 0; i < 3; i++) {
            if (!(((Trash) ar.get(i)).selected)){
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
    public void hide(){
        if (!fakeinvScreen) {
            game.music.pause();
        }
    }

    @Override
    public void dispose() {

    }
}
