package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.garbageman.game.Backpack;
import com.garbageman.game.Garbageman;
import com.garbageman.game.UI;
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
import com.garbageman.game.world.GestureHandler;
import com.garbageman.game.world.InputHandler;
import com.garbageman.game.SpriteSheetDivider;

import org.lwjgl.opengl.XRandR;

import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Trashcan implements Screen {
    //All of my initializations:
        //Basic Stuffs
            static Garbageman game;
            SpriteBatch batch;
            Stage stage = new Stage();
            private UI ui;
            public static String screenName = "Trashcan";//this is for the UI <3 Dana
            BitmapFont font = new BitmapFont();
            SpriteSheetDivider sp = new SpriteSheetDivider();

        //Backpack Stuffs
            Backpack backpack;
            //Distance from backpack to open it (in pixels)
            final int backpackOpenProc = 200;
            Image backpackImg = new Image();

        //Console Stuffs
            boolean consoleOpen = false;
            Label Ltext;
            Label.LabelStyle textStyle;
            int consoleIndex = 0;
            Skin txtSkin = new Skin(Gdx.files.internal("uiskin.json"));
            TextField text = new TextField("", txtSkin);

        //Background/Screen Stuffs
            String currBg = "dumpster1";
            Image background = new Image(new Texture("assets/Screens/dumpster1.png"));

        //Interaction Stuffs
            boolean wasTouched = false;
            boolean touchingBug = false;
            Trash currentObject = null;
            int countFrame = 0;
            float fric = .9f;
            float xDiff = 0;
            float yDiff = 0;



        //Array/Arraylist/Map Stuffs
            Map<String, Float> velMap = Collections.synchronizedMap(new HashMap());
            Map<String, Float> oldLocMap = Collections.synchronizedMap(new HashMap());
            Map<String, Float[]> bglocs = Collections.synchronizedMap(new HashMap());
            ArrayList<Trash> imgs = new ArrayList();
            ArrayList<String> consoleLog = new ArrayList<String>();

        //Filled Array Stuffs
            String[] helpList = {
                    "Commands (items in parentheses are optional)",
                    "",
                    "add [itemname] ([x] [y])               -- Adds a new item of trash to the game",
                    "remove [itemnumber]                    -- Removes an item of trash from the game. Item number can be found by using addNumbers",
                    "addNumbers                             -- Displays numbers ontop of items",
                    "setSize [itemnumber] [width] [height]  -- Sets the size of a specific item to a specific height/width",
                    "resetSize                              -- Resets the size of every item on screen to 160x160",
                    "setSizeAll [width] [height]            -- Sets the size of every item on screen to a specific width and height",
                    "move [itemnumber] [x] [y]              -- Moves a specified item to specified x and y values",
                    "setVel [itemnumber] [xVel] [yVel]      -- Sets the velocity of a specific item",
                    "removeVels                             -- Sets the velocity of every item in the game to be 0",
                    "setFric [fric]                         -- Sets the global friction coefficient. 1 is no friction, 0 is 100% friction, 2 is very funny",
                    "help                                   -- Displays this command"
            };

            


    public Trashcan(Garbageman game) {
        Float[] xys = {230f, 50f, 930f, 410f};
        bglocs.put(currBg, xys);

        Float[] currLocs = bglocs.get(currBg);
        Random rand = new Random();
        //System.out.println(currLocs[3]);
        for (int i = 0; i < 30; i++) {
            //System.out.println(stage.getHeight());
            int currX = (rand.nextInt(currLocs[2].intValue() - currLocs[0].intValue()) + currLocs[0].intValue());
            int currY = (int) stage.getHeight() - (rand.nextInt(currLocs[3].intValue() - currLocs[1].intValue()) + currLocs[1].intValue());
            //System.out.println("currX: " + currX);
            //System.out.println("currY: " + currY + "\n");
        }

        //Stuff to make this work with two inputprocessors
        InputProcessor inputProcessorOne = new InputHandler();
        InputProcessor inputProcessorTwo = new GestureDetector(new GestureHandler());
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //Making sure everything initializes properly
        this.game = game;
        this.backpack = game.backpack;
        consoleLog.add("");
    }

    //For generating random pieces of garbage inside of a defined area
    public Float[] generateLocation() {
        Float[] cls = bglocs.get(currBg);
        float stx = cls[0];
        float sty = cls[1];
        float fx = cls[2];
        float fy = cls[3];

        Random rand = new Random();
        int nx = rand.nextInt(((int) fx - (int) stx) + 1) + (int) stx;
        int ny = rand.nextInt(((int) fy - (int) sty) + 1) + (int) sty;

        return new Float[] {(float) nx, (float) ny};
    }

    public static Trash makeRandGarbage(int x){
        try {
            return (Trash) Class.forName(game.garbageItems.get(x).getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Makes a new piece of garbage, but does most of the work for you.
    public void makeSoftGarbage(String string) {
        try {
            for (Class garbageItem : game.garbageItems) {
                if (garbageItem.getSimpleName().toLowerCase().equals(string.toLowerCase())) {

                    Trash object = (Trash) Class.forName(garbageItem.getName()).newInstance();
                    makeSoftGarbage(object);
                }
            }
        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void makeSoftGarbage(Trash trash) {
        makeSoftGarbage(trash, 0, 0);
    }

    public void makeSoftGarbage(Trash trash, int x, int y) {
        makeSoftGarbage(trash, x, y, 160);
    }

    public void makeSoftGarbage(Trash trash, int x, int y, int size) {
        try {
            Random rand = new Random();


            Skin skin1 = new Skin();
            skin1.add(trash.name, new Texture(trash.baseImgName + trash.img + trash.fileType));
            trash.nast = rand.nextInt(100)+1;
            trash.setImg();
            stage.addActor(trash);
            imgs.add(trash);
            imgs.get(imgs.size() - 1).setName(Integer.toString(imgs.size() - 1));
            velMap.put(imgs.get(imgs.size() - 1).getName() + "x", 0f);
            velMap.put(imgs.get(imgs.size() - 1).getName() + "y", 0f);
            imgs.get(imgs.size() - 1).toBack();
            imgs.get(imgs.size() - 1).setSize(size, size);
            imgs.get(imgs.size() - 1).setX(x);
            imgs.get(imgs.size() - 1).setY(y);
        } catch (GdxRuntimeException e) {
            e.printStackTrace();
        }
    }


    public void spawnJunk(int amt){
        for (int i = 0; i < amt; i++) {
            Float[] plocs = generateLocation();
            SpriteSheetDivider sp = new SpriteSheetDivider();
            Trash junk = sp.randomPiece();
            junk.setX(plocs[0]);
            junk.setY(plocs[1]);
            stage.addActor(junk);
            junk.setVisible(true);
            junk.setSize(200, 200);
            junk.toFront();
            imgs.add(junk);
        }
    }

    public void spawnItem(int amt){
        for (int i = 0; i < amt; i++) {
            Float[] plocs = generateLocation();
            Random rand = new Random();
            int t = rand.nextInt(game.garbageItems.size());
            Trash item = makeRandGarbage(t);
            item.nast = rand.nextInt(100)+1;
            item.setImg();
            item.setX(plocs[0]);
            item.setY(plocs[1]);
            stage.addActor(item);
            item.setVisible(true);
            item.setSize(128, 128);
            item.toFront();
            imgs.add(item);

        }
    }

    //A command triggered thru the console. Adds a new piece of garbage to the world
    public void add(String[] cmds) {
        try {
            makeSoftGarbage(cmds[1]);
            System.out.println("gotthere");
        }
        catch (GdxRuntimeException e){
            System.out.println("Missing item name for spawn");
        }
        if (cmds.length == 4) {
            try {
                int tx = Integer.parseInt(cmds[2]);
                int ty = Integer.parseInt(cmds[3]);
                imgs.get(imgs.size() - 1).setX(tx);
                imgs.get(imgs.size() - 1).setY(ty);
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        if (cmds.length == 3){
            for (int i = 0; i < Integer.parseInt(cmds[2])-1; i++) {
                makeSoftGarbage(cmds[1]);
            }
        }
        if (cmds.length == 5) {
            int tx = Integer.parseInt(cmds[2]);
            int ty = Integer.parseInt(cmds[3]);
            imgs.get(imgs.size() - 1).setX(tx);
            imgs.get(imgs.size() - 1).setY(ty);
            for (int i = 0; i < Integer.parseInt(cmds[4])-1; i++) {
                //makeSoftGarbage(cmds[1]);
                tx = Integer.parseInt(cmds[2]);
                ty = Integer.parseInt(cmds[3]);
                imgs.get(imgs.size() - 1).setX(tx);
                imgs.get(imgs.size() - 1).setY(ty);
            }
        }
    }

    //Takes the console's most recent entry and tries to interpret it into a command. Lots of try/catches and if's. Could be made into a switch/case.
    private void interpretConsole() {
        String cText = consoleLog.get(consoleLog.size() - 1);
        String[] cmds = cText.split(" ");
        if (cmds[0].equals("add")) {
            add(cmds);
        } else if (cmds[0].equals("remove")) {
            try {
                imgs.get(Integer.parseInt(cmds[1]) - 1).remove();

            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                if (cmds[1].equals("all")) {
                    imgs.clear();
                }
            }
        }else if (cmds[0].equals("cursorLoc")) {
            System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
        } else if (cmds[0].equals("setSize")) {
            try {
                imgs.get(Integer.parseInt(cmds[1])).setSize(Integer.parseInt(cmds[2]), Integer.parseInt(cmds[3]));
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (cmds[0].equals("resetSize")) {
            for (Image i : imgs) {
                i.setSize(160, 160);
            }
        } else if (cmds[0].equals("setSizeAll")) {
            try {
                for (Image i : imgs) {
                    i.setSize(Integer.parseInt(cmds[1]), Integer.parseInt(cmds[2]));
                }
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (cmds[0].equals("move")) {
            try {
                imgs.get(Integer.parseInt(cmds[1]) - 1).setX(Integer.parseInt(cmds[2]));
                imgs.get(Integer.parseInt(cmds[1]) - 1).setY(Integer.parseInt(cmds[3]));
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (cmds[0].equals("setVel")) {
            try {
                velMap.put(imgs.get(Integer.parseInt(cmds[1]) - 1).getName() + "x", Float.parseFloat(cmds[2]));
                velMap.put(imgs.get(Integer.parseInt(cmds[1]) - 1).getName() + "y", Float.parseFloat(cmds[3]));
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (cmds[0].equals("removeVels")) {
            for (Image i : imgs) {
                velMap.put(i.getName() + "x", 0f);
                velMap.put(i.getName() + "y", 0f);
            }
        } else if (cmds[0].equals("setFric")) {
            try {
                fric = Float.parseFloat(cmds[1]);
            } catch (GdxRuntimeException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (cmds[0].equals("help")) {
            for (String aHelpList : helpList) {
                System.out.println(aHelpList);
            }
        }
    }

    //The method that initially draws things.
    @Override
    public void show() {
        Skin skin = new Skin();
        skin.add("hi", new Texture("assets/Buttons/PLAY.png"));





        spawnItem(20);
        spawnJunk(40);


        game.currentScreen = screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        Gdx.input.setInputProcessor(stage);
        text.toFront();

        SpriteSheetDivider sp = new SpriteSheetDivider();
        backpackImg.setDrawable(sp.divideItem("Inventory", 0));
        backpackImg.setSize(backpack.getWidth(), stage.getHeight()-game.ui.topbarHeight);
        backpackImg.setX(stage.getWidth() - backpackImg.getWidth());
        stage.addActor(backpackImg);
        backpackImg.toFront();
        backpackImg.setVisible(false);
        stage.addActor(background);
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getHeight()-game.ui.topbarHeight);

        for (int i = 0; i < imgs.size(); i++) {
            imgs.get(i).setName(Integer.toString(i));
        }

        for (int i = 0; i < 100; i++) {
            for (int k = 0; k < imgs.size(); k++) {
                oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY() + i);
                oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX() + i);
            }
        }

        for (int k = 0; k < imgs.size(); k++) {
            velMap.put(imgs.get(k).getName() + "x", 0f);
            velMap.put(imgs.get(k).getName() + "y", 0f);
        }

        for (int i = 0; i < imgs.size(); i++) {
            imgs.get(i).setSize(160, 160);
        }


        for (int i = 0; i < imgs.size(); i++) {
            System.out.println(imgs.get(i).img+" "+imgs.get(i).getName()+" "+imgs.get(i).name);
        }


    }

    @Override
    public void render(float delta) {


        for (int i = 0; i < imgs.size(); i++) {
            imgs.get(i).setName(Integer.toString(i));
        }


        background.toBack();
        if (Gdx.input.getX() >= stage.getWidth() - backpackImg.getWidth() - backpackOpenProc && wasTouched && !touchingBug) {
            backpackImg.setVisible(true);
            SpriteSheetDivider sp = new SpriteSheetDivider();
            try {
                backpackImg.setDrawable(sp.divideItem("SmallInv", currentObject.rarity));
            }
            catch (NullPointerException e){
                e.printStackTrace();
                backpackImg.setDrawable(sp.divideItem("SmallInv", 0));
            }


        } else {
            backpackImg.setVisible(false);
        }
        game.batch.begin();
        text.toFront();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.Q) && !consoleOpen || Gdx.input.isKeyPressed(Input.Keys.E) && !consoleOpen)
            Gdx.app.exit();

        if (Gdx.input.isKeyJustPressed(Input.Keys.P )) {

        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.O )) {
            game.setScreen(new CraftingScreen(game));
        }

        /*Console controls*/
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && consoleOpen) {
            consoleIndex -= 1;
            if (consoleIndex < 0)
                consoleIndex = 0;
            text.setText(consoleLog.get(consoleIndex));
            text.setCursorPosition(text.getText().length());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && consoleOpen) {
            consoleIndex += 1;
            if (consoleIndex >= consoleLog.size())
                consoleIndex = consoleLog.size() - 1;
            text.setText(consoleLog.get(consoleIndex));
            text.setCursorPosition(text.getText().length());
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            consoleIndex = consoleLog.size() - 1;
            if (!consoleOpen) {
                consoleOpen = true;
                text.setWidth(stage.getWidth());
                stage.addActor(text);
                text.setTextFieldListener(new TextField.TextFieldListener() {
                    @Override
                    public void keyTyped(TextField textField, char c) {
                        if (c == '\n' || c == '\r') {
                            consoleLog.add(textField.getText());
                            textField.setText("");
                            consoleIndex = consoleLog.size();
                            textField.setCursorPosition(textField.getText().length());
                            interpretConsole();
                        }

                    }
                });
            } else {
                consoleOpen = false;
                text.setText("");
                text.remove();
            }
        }

        for (int i = 0; i < imgs.size(); i++) {
            if(imgs.get(i) != null) {
                stage.addActor(imgs.get(i));
                final int k = i;

                imgs.get(k).setX(imgs.get(k).getX() + velMap.get(imgs.get(k).getName() + "x"));
                imgs.get(k).setY(imgs.get(k).getY() + velMap.get(imgs.get(k).getName() + "y"));


                float tx = velMap.get(imgs.get(k).getName() + "x");
                float ty = velMap.get(imgs.get(k).getName() + "y");
                float tm = (float) Math.sqrt((tx * tx) + (ty * ty));


                if (velMap.get(imgs.get(k).getName() + "x") >= 0)
                    velMap.put(imgs.get(k).getName() + "x", tx * fric/*((tm-.5f)/tm)*/);
                if (velMap.get(imgs.get(k).getName() + "x") <= 0)
                    velMap.put(imgs.get(k).getName() + "x", tx * fric/*((tm+.5f)/tm)*/);
                if (velMap.get(imgs.get(k).getName() + "y") >= 0)
                    velMap.put(imgs.get(k).getName() + "y", ty * fric/*((tm-.5f)/tm)*/);
                if (velMap.get(imgs.get(k).getName() + "y") <= 0)
                    velMap.put(imgs.get(k).getName() + "y", ty * fric/*((tm+.5f)/tm)*/);



                imgs.get(i).addListener(new ClickListener() {

                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){

                        xDiff = imgs.get(k).getX() - Gdx.input.getX();
                        yDiff = imgs.get(k).getY() - stage.getHeight() + Gdx.input.getY();
                        wasTouched = true;
                        //System.out.println("called TouchDown");

                       /* Texture referenceTexture = new Texture(imgs.get(k).baseImgName + imgs.get(k).img + imgs.get(k).fileType);

                        Sprite sprite = new Sprite();
                        sprite.setRegion(referenceTexture);
                        Rectangle spriteBounds = sprite.getBoundingRectangle();
                        Color color = new Color();
                        if (spriteBounds.contains(xDiff, yDiff)) {
                            Texture texture = sprite.getTexture();

                            int spriteLocalX = (int) (xDiff);
                            // we need to "invert" Y, because the screen coordinate origin is top-left
                            int spriteLocalY = (int) (yDiff);

                            System.out.println(xDiff + " " + yDiff);

                            int textureLocalX = sprite.getRegionX() + spriteLocalX;
                            int textureLocalY = sprite.getRegionY() + spriteLocalY;

                            if (!texture.getTextureData().isPrepared()) {
                                texture.getTextureData().prepare();
                            }
                            Pixmap pixmap = texture.getTextureData().consumePixmap();
                        }
*/
                        return true;
                    }

                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                        if ((imgs.get(k).getX() /*- (imgs.get(k).getWidth() / 2)*/ >= (stage.getWidth() - backpackImg.getWidth())) && wasTouched) {

                            if (!touchingBug) {
                                backpack.add(imgs.get(k));
                                imgs.get(k).setVisible(false);
                                imgs.remove(k);
                            }

                        } else if (wasTouched) {

                            boolean gotX = false;
                            boolean gotY = false;
                            float xVel = 0f;
                            float yVel = 0f;
                            for (int i = 9; i > 0; i--) {

                                if (oldLocMap.get("y" + "null" + "0") - oldLocMap.get("y" + "null" + Integer.toString(i)) != 0f) {
                                    yVel = oldLocMap.get("y" + "null" + "0") - oldLocMap.get("y" + "null" + Integer.toString(i));
                                    gotY = true;
                                }
                                if (oldLocMap.get("x" + "null" + "0") - oldLocMap.get("x" + "null" + Integer.toString(i)) != 0f) {
                                    xVel = oldLocMap.get("x" + "null" + "0") - oldLocMap.get("x" + "null" + Integer.toString(i));
                                    gotX = true;
                                }

                            }
                            if (!gotX) xVel = 0f;
                            if (!gotY) yVel = 0f;

                            velMap.put(imgs.get(k).getName() + "x", xVel);
                            velMap.put(imgs.get(k).getName() + "y", yVel);

                            for (int i = 0; i < 10; i++) {
                                oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY());
                                oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX());
                            }
                        }

                        wasTouched = false;
                        touchingBug = false;
                        currentObject = null;


                    }


                    public void touchDragged(InputEvent event, float x, float y, int pointer) {
                        if (imgs.get(k).name.toLowerCase().equals("bug")) {
                            touchingBug = true;
                        }
                        wasTouched = true;
                        currentObject = imgs.get(k);
                        imgs.get(k).toFront();

                        if (countFrame == 9) {
                            for (int i = 9; i >= 0; i--) {
                                oldLocMap.put("y" + "null" + Integer.toString(i + 1), oldLocMap.get("y" + "null" + Integer.toString(i)));
                                oldLocMap.put("x" + "null" + Integer.toString(i + 1), oldLocMap.get("x" + "null" + Integer.toString(i)));
                            }
                            countFrame = 0;
                        }
                        oldLocMap.put("x" + "null" + "0", imgs.get(k).getX());
                        oldLocMap.put("y" + "null" + "0", imgs.get(k).getY());

                        countFrame++;
                        imgs.get(k).setPosition(Gdx.input.getX() + xDiff, stage.getHeight()-(Gdx.input.getY()) + yDiff);
                    }

                });
            }
        }

        game.ui.update();
        if (this.screenName.equals(game.ui.trashcanScreenName)){
            game.ui.updateSlidescreen(this.stage, this.backpackImg, this.screenName);
        }

        stage.draw();
        
        game.batch.end();
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
