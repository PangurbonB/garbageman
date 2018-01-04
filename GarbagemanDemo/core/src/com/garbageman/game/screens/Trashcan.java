package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.garbageman.game.Backpack;
import com.garbageman.game.GarbageSpriteSheet;
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Trashcan implements Screen {
    //All of my initializations:
        //Basic Stuffs
            Garbageman game;
            SpriteBatch batch;
            Stage stage = new Stage();
            private UI ui;
            BitmapFont font = new BitmapFont();

        //Backpack Stuffs
            Backpack backpack = new Backpack();
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
            private String screenName = "Trashcan";

        //Interaction Stuffs
            boolean wasTouched = false;
            int countFrame = 0;
            float fric = .9f;

        //Array/Arraylist/Map Stuffs
            Map<String, Float> velMap = Collections.synchronizedMap(new HashMap());
            Map<String, Float> oldLocMap = Collections.synchronizedMap(new HashMap());
            Map<String, int[]> bglocs = Collections.synchronizedMap(new HashMap());
            ArrayList<Trash> imgs = new ArrayList();
            ArrayList<String> consoleLog = new ArrayList<String>();
            ArrayList<Actor> nums = new ArrayList<Actor>();

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

            Class[] garbageItems = {
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


    public Trashcan(Garbageman game) {
        int[] xys = {200, 270, 930, 610};
        bglocs.put("dumpster1", xys);

        int[] currLocs = bglocs.get(currBg);
        Random rand = new Random();
        System.out.println(currLocs[3]);
        for (int i = 0; i < 30; i++) {
            System.out.println(stage.getHeight());
            int currX = (rand.nextInt(currLocs[2] - currLocs[0]) + currLocs[0]);
            int currY = (int) stage.getHeight() - (rand.nextInt(currLocs[3] - currLocs[1]) + currLocs[1]);
            System.out.println("currX: " + currX);
            System.out.println("currY: " + currY + "\n");
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
        this.stage = stage;
        this.batch = batch;
        this.font = font;
        this.background = background;
        consoleLog.add("");
    }

    //For generating random pieces of garbage inside of a defined area
    public Float[] generateLocation(HashMap bglocs) {
        Float[] currlocs = (Float[]) bglocs.get(currBg);
        float stx = currlocs[0];
        float sty = currlocs[1];
        float fx = currlocs[2];
        float fy = currlocs[3];

        Random rand = null;
        int nx = rand.nextInt(((int) fx - (int) stx) + 1) + (int) stx;
        int ny = rand.nextInt(((int) fy - (int) sty) + 1) + (int) sty;

        Float[] ncoords = {(float) nx, (float) ny};
        return ncoords;
    }

    //Makes a new piece of garbage, but does most of the work for you.
    public void makeSoftGarbage(String string) {
        try {
            for (int i = 0; i < garbageItems.length; i++) {
                if(garbageItems[i].getSimpleName().toLowerCase().equals(string.toLowerCase())){
                    System.out.println(garbageItems[i].getSimpleName());

                    Trash object = (Trash) Class.forName(garbageItems[i].getName()).newInstance();
                    makeSoftGarbage(object);
                }
                else{
                    //System.out.println(garbageItems[i].getSimpleName()+ " "+ string);
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
            Skin skin1 = new Skin();
            skin1.add(trash.name, new Texture(trash.baseImgName + trash.img + trash.fileType));
            trash.setDrawable(skin1, trash.name);
            stage.addActor(trash);
            imgs.add(trash);
            imgs.get(imgs.size() - 1).setName(Integer.toString(imgs.size() - 1));
            velMap.put(imgs.get(imgs.size() - 1).getName() + "x", 0f);
            velMap.put(imgs.get(imgs.size() - 1).getName() + "y", 0f);
            imgs.get(imgs.size() - 1).toBack();
            imgs.get(imgs.size() - 1).setSize(size, size);
            imgs.get(imgs.size() - 1).setX(x);
            imgs.get(imgs.size() - 1).setY(y);
            addNumber(imgs.get(imgs.size() - 1));
        } catch (GdxRuntimeException e) {
            e.printStackTrace();
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

            } catch (IndexOutOfBoundsException e) {

            }
        }
        if (cmds.length == 3){
            for (int i = 0; i < Integer.parseInt(cmds[2])-1; i++) {
                //makeSoftGarbage(cmds[1]);
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

    //Adds a number to a specified item. The number represents that item's name.
    public void addNumber(Image img) {
        textStyle = new Label.LabelStyle();
        textStyle.font = font;
        Ltext = new Label(img.getName(), textStyle);
        Ltext.setBounds(0, .2f, stage.getWidth(), 2);
        Ltext.setFontScale(1f, 1f);
        Ltext.setX(img.getX());
        Ltext.setY(img.getY());
        stage.addActor(Ltext);
        nums.add(Ltext);
    }

    //Makes numbers invisible or visible
    public void makeNumsVisible(boolean vis) {
        for (Actor i : nums) {
            i.setVisible(vis);
        }
    }

    //Takes the console's most recent entry and tries to interpret it into a command. Lots of try/catches and if's. Could be made into a switch/case.
    public void interpretConsole() {
        String cText = consoleLog.get(consoleLog.size() - 1);
        String[] cmds = cText.split(" ");
        if (cmds[0].equals("add")) {
            add(cmds);
        } else if (cmds[0].equals("remove")) {
            try {
                imgs.get(Integer.parseInt(cmds[1]) - 1).remove();
                nums.get(Integer.parseInt(cmds[1]) - 1).remove();

            } catch (GdxRuntimeException e) {

            } catch (IndexOutOfBoundsException e) {

            } catch (NumberFormatException e) {
                if (cmds[1].equals("all")) {
                    imgs.clear();
                    nums.clear();
                }
            }
        } else if (cmds[0].equals("addNumbers")) {
            for (int i = 0; i < imgs.size(); i++) {
                makeNumsVisible(true);
            }
        } else if (cmds[0].equals("removeNumbers")) {
            for (int i = 0; i < imgs.size(); i++) {
                makeNumsVisible(false);
            }
        } else if (cmds[0].equals("cursorLoc")) {
            System.out.println(Gdx.input.getX() + "," + Gdx.input.getY());
        } else if (cmds[0].equals("setSize")) {
            try {
                imgs.get(Integer.parseInt(cmds[1])).setSize(Integer.parseInt(cmds[2]), Integer.parseInt(cmds[3]));
            } catch (GdxRuntimeException e) {

            } catch (IndexOutOfBoundsException e) {

            } catch (NumberFormatException e) {

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

            } catch (IndexOutOfBoundsException e) {

            } catch (NumberFormatException e) {

            }
        } else if (cmds[0].equals("move")) {
            try {
                imgs.get(Integer.parseInt(cmds[1]) - 1).setX(Integer.parseInt(cmds[2]));
                imgs.get(Integer.parseInt(cmds[1]) - 1).setY(Integer.parseInt(cmds[3]));
            } catch (GdxRuntimeException e) {

            } catch (IndexOutOfBoundsException e) {

            } catch (NumberFormatException e) {

            }
        } else if (cmds[0].equals("setVel")) {
            try {
                velMap.put(imgs.get(Integer.parseInt(cmds[1]) - 1).getName() + "x", Float.parseFloat(cmds[2]));
                velMap.put(imgs.get(Integer.parseInt(cmds[1]) - 1).getName() + "y", Float.parseFloat(cmds[3]));
            } catch (GdxRuntimeException e) {

            } catch (IndexOutOfBoundsException e) {

            } catch (NumberFormatException e) {

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

            } catch (IndexOutOfBoundsException e) {

            } catch (NumberFormatException e) {

            }
        } else if (cmds[0].equals("help")) {
            for (int i = 0; i < helpList.length; i++) {
                System.out.println(helpList[i]);
            }
        }
    }

    //The method that initially draws things.
    @Override
    public void show() {
        Skin skin = new Skin();
        skin.add("hi", new Texture("assets/Buttons/PLAY.png"));



        backpackImg.setDrawable(skin, "hi");
        backpackImg.setSize(backpack.getWidth(), stage.getHeight());
        backpackImg.setX(stage.getWidth() - backpackImg.getWidth());
        stage.addActor(backpackImg);
        backpackImg.toFront();
        backpackImg.setVisible(false);
        stage.addActor(background);
        background.setWidth(stage.getWidth());
        background.setHeight(stage.getHeight()-100);



        game.currentScreen = this.screenName;
        ui = new UI(stage, game, this.screenName);
        ui.makeUI();
        Gdx.input.setInputProcessor(stage);
        text.toFront();

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


    }

    @Override
    public void render(float delta) {
        background.toBack();
        if (Gdx.input.getX() >= stage.getWidth() - backpackImg.getWidth() - backpackOpenProc && wasTouched) {
            backpackImg.setVisible(true);
        } else {
            backpackImg.setVisible(false);
        }
        game.batch.begin();
        text.toFront();
        for (int i = 0; i < nums.size(); i++) {
            nums.get(i).setX(imgs.get(i).getX() + .5f * imgs.get(i).getWidth());
            nums.get(i).setY(imgs.get(i).getY() + .5f * imgs.get(i).getHeight());
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.Q) && !consoleOpen)
            Gdx.app.exit();

        if (Gdx.input.isKeyPressed(Input.Keys.E) && !consoleOpen) {
            Gdx.app.exit();
        }

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
                            System.out.println(consoleLog.get(consoleLog.size() - 1));
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
            stage.addActor(imgs.get(i));
            final int k = i;
            //System.out.println(velMap.get(imgs.get(i).getName() + "x"));

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
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                    if (imgs.get(k).getX() - (imgs.get(k).getWidth() / 2) >= stage.getWidth() - backpackImg.getWidth() && wasTouched) {
                        imgs.get(k).setVisible(false);
                        System.out.println(imgs.get(k).getDrawable().toString());


                        if (backpack.contents.size() < backpack.totalSlots && !imgs.get(k).getDrawable().toString().equals("TextureRegionDrawable"))
                            backpack.contents.add(imgs.get(k));
                        System.out.println("Size: " + backpack.contents.size());
                        for (int i = 0; i < backpack.contents.size(); i++) {

                            System.out.println("Name " + i + ": " + backpack.contents.get(i).getName());
                        }

                    } else if (wasTouched) {
                        System.out.println("X:");
                        System.out.println(oldLocMap.get("x" + "null" + "0"));
                        System.out.println(oldLocMap.get("x" + "null" + "9"));
                        System.out.println("Y:");
                        System.out.println(oldLocMap.get("y" + "null" + "0"));
                        System.out.println(oldLocMap.get("y" + "null" + "9"));

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


                        System.out.println("VELOCITIES:");
                        System.out.println("xVel:" + xVel);
                        System.out.println("yVel:" + yVel);


                        velMap.put(imgs.get(k).getName() + "x", xVel);
                        velMap.put(imgs.get(k).getName() + "y", yVel);

                        for (int i = 0; i < 10; i++) {
                            oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY());
                            oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX());
                        }
                    }

                    wasTouched = false;

                }


                public void touchDragged(InputEvent event, float x, float y, int pointer) {
                    wasTouched = true;
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
                    imgs.get(k).moveBy(x - imgs.get(k).getWidth() / 2, y - imgs.get(k).getHeight() / 2);

                }
            });
        }
        ui.update();

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
