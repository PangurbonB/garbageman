package com.brettzonick.game.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brettzonick.game.java.Garbageman;
import com.brettzonick.game.java.garbage.AppleCore;
import com.brettzonick.game.java.garbage.BagOfSugar;
import com.brettzonick.game.java.garbage.CrowWithOddEyeInfection;
import com.brettzonick.game.java.garbage.McdFries;
import com.brettzonick.game.java.garbage.Pork;
import com.brettzonick.game.java.garbage.Trash;
import com.brettzonick.game.java.world.GestureHandler;
import com.brettzonick.game.java.world.InputHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Trashcan implements Screen {



    Garbageman game;
    BitmapFont font;
    SpriteBatch batch;
    Skin skin;

    int incVal = 0;

    final McdFries fries = new McdFries();
    Table table;
    String str = fries.baseImgName + fries.img + fries.fileType;
    Image img1 = new Image();
    Image img2 = new Image();
    Image img3 = new Image();
    //table.add(img);
    Camera camera;

    boolean wasTouched = false;

    Map<String, Float> velMap = Collections.synchronizedMap(new HashMap());
    Map<String, Float> oldLocMap = Collections.synchronizedMap(new HashMap());
    ArrayList<Image> imgs = new ArrayList();

    int countFrame = 0;
    int x,y = 0;

    float friction = .1f;





    Stage stage = new Stage();

    //private Viewport viewport;

    public Trashcan (Garbageman game){

        InputProcessor inputProcessorOne = new InputHandler();
        InputProcessor inputProcessorTwo = new GestureDetector(new GestureHandler());
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //

        this.game = game;
        this.stage = stage;
        this.batch = batch;
        this.font = font;



        //velMap.put("y"+img.getName(), 0f);
        //velMap.put("x"+img.getName(), 0f);

        fries.setSize(32, 32);

    }


    public Image makeGarbage(String name){
        Image img;
        Skin skin1 = new Skin();
        skin1.add("mcdFries", new Texture(name));
        img = new Image(skin1, "mcdFries");
        return img;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        imgs.add(makeGarbage(str));
        imgs.add(makeGarbage(str));
        imgs.add(makeGarbage(str));
        Pork pork = new Pork();
        AppleCore app = new AppleCore();
        CrowWithOddEyeInfection bag = new CrowWithOddEyeInfection();
        imgs.add(makeGarbage(pork.baseImgName + pork.img + pork.fileType));
        imgs.add(makeGarbage(app.baseImgName + app.img + app.fileType));
        imgs.add(makeGarbage(bag.baseImgName + bag.img + bag.fileType));

        for (int i = 0; i < imgs.size(); i++) {
            imgs.get(i).setName(Integer.toString(i));
        }

        for(int i=0; i<100; i++){
            for(int k=0; k<imgs.size(); k++) {
                oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY());
                oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX());
            }
        }

        for (int k = 0; k < imgs.size(); k++) {
            velMap.put(imgs.get(k).getName() + "x", 0f);
            velMap.put(imgs.get(k).getName() + "y", 0f);
        }

        for(int i=0; i<imgs.size(); i++){
            imgs.get(i).setSize(64, 64);
        }

    }

    @Override
    public void render(float delta) {

        fries.setX(x);
        fries.setY(y);
        fries.setSize(32, 32);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            Gdx.app.exit();
        }

        //stage.addActor(fries);

        game.batch.begin();

        //System.out.println("X:"+img.getX());
        //System.out.println("Y:"+img.getY());
        for(int i=0; i<imgs.size(); i++) {
            stage.addActor(imgs.get(i));
            final int k = i;
            //System.out.println(velMap.get(imgs.get(i).getName() + "x"));

            imgs.get(k).setX(imgs.get(k).getX()+velMap.get(imgs.get(k).getName() + "x"));
            imgs.get(k).setY(imgs.get(k).getY()+velMap.get(imgs.get(k).getName() + "y"));


            float tx = velMap.get(imgs.get(k).getName()+ "x");
            float ty = velMap.get(imgs.get(k).getName()+ "y");
            float tm = (float) Math.sqrt((tx*tx)+(ty*ty));

            float fric = .9f;

            if(velMap.get(imgs.get(k).getName()+ "x") >= 0)
                velMap.put(imgs.get(k).getName() + "x", tx * fric/*((tm-.5f)/tm)*/);
            if(velMap.get(imgs.get(k).getName()+ "x") <= 0)
                velMap.put(imgs.get(k).getName() + "x", tx * fric/*((tm+.5f)/tm)*/);
            if(velMap.get(imgs.get(k).getName()+ "y") >= 0)
                velMap.put(imgs.get(k).getName() + "y", ty * fric/*((tm-.5f)/tm)*/);
            if(velMap.get(imgs.get(k).getName()+ "y") <= 0)
                velMap.put(imgs.get(k).getName() + "y", ty * fric/*((tm+.5f)/tm)*/);

            imgs.get(i).addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (wasTouched) {
                        System.out.println("X:");
                        System.out.println(oldLocMap.get("x"+"null"+"0"));
                        System.out.println(oldLocMap.get("x"+"null"+"9"));
                        System.out.println("Y:");
                        System.out.println(oldLocMap.get("y"+"null"+"0"));
                        System.out.println(oldLocMap.get("y"+"null"+"9"));

                        boolean gotX = false;
                        boolean gotY = false;
                        float xVel = 0f;
                        float yVel = 0f;
                        for (int i = 9; i > 0; i--) {

                            if(oldLocMap.get("y" + "null" + "0") - oldLocMap.get("y" + "null" + Integer.toString(i)) != 0f){
                                yVel = oldLocMap.get("y" + "null" + "0") - oldLocMap.get("y" + "null" + Integer.toString(i));
                                gotY = true;
                            }
                            if(oldLocMap.get("x" + "null" + "0") - oldLocMap.get("x" + "null" + Integer.toString(i)) != 0f){
                                xVel = oldLocMap.get("x" + "null" + "0") - oldLocMap.get("x" + "null" + Integer.toString(i));
                                gotX = true;
                            }

                        }
                        if(!gotX) xVel = 0f;
                        if(!gotY) yVel = 0f;



                        System.out.println("VELOCITIES:");
                        System.out.println("xVel:"+xVel);
                        System.out.println("yVel:"+yVel);

                        velMap.put(imgs.get(k).getName() + "x", xVel);
                        velMap.put(imgs.get(k).getName() + "y", yVel);

                        for(int i=0; i<10; i++){
                            oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY());
                            oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX());
                        }
                    }

                    wasTouched = false;

                }


                public void touchDragged(InputEvent event, float x, float y, int pointer) {

                    wasTouched = true;

                    final float oldX = imgs.get(k).getX();
                    final float oldY = imgs.get(k).getY();

                    String sX = Float.toString(oldX);
                    String sY = Float.toString(oldY);
                    if(countFrame == 9) {
                        for (int i = 9; i >= 0; i--) {
                            oldLocMap.put("y" + "null" + Integer.toString(i + 1), oldLocMap.get("y" + "null" + Integer.toString(i)));
                            oldLocMap.put("x" + "null" + Integer.toString(i + 1), oldLocMap.get("x" + "null" + Integer.toString(i)));
                        }
                        countFrame = 0;
                    }
                    oldLocMap.put("x"+"null"+"0", imgs.get(k).getX());
                    oldLocMap.put("y"+"null"+"0", imgs.get(k).getY());

                    countFrame ++;
                    imgs.get(k).moveBy(x - imgs.get(k).getWidth() / 2, y - imgs.get(k).getHeight() / 2);

                }
            });
        }

        stage.draw();


        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //viewport.setScreenWidth(width);
        //viewport.setScreenHeight(height);
        //stage.setViewport(viewport);


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
