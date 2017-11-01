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
        for(int i=0; i<100; i++){
            for(int k=0; k<imgs.size(); k++) {
                oldLocMap.put("y" + imgs.get(k).getName()+ Integer.toString(i), imgs.get(k).getY());
                oldLocMap.put("x" + imgs.get(k).getName() + Integer.toString(i), imgs.get(k).getX());
            }
        }


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

        skin = new Skin();
        skin.add("mcdFries", new Texture(str));
        img1 = new Image(skin, "mcdFries");

        skin = new Skin();
        skin.add("mcdFries", new Texture(str));
        img2 = new Image(skin, "mcdFries");

        skin = new Skin();
        skin.add("mcdFries", new Texture(str));
        img3 = new Image(skin, "mcdFries");



        imgs.add(makeGarbage(str));
        imgs.add(makeGarbage(str));
        imgs.add(makeGarbage(str));
        Pork pork = new Pork();
        AppleCore app = new AppleCore();
        CrowWithOddEyeInfection bag = new CrowWithOddEyeInfection();
        imgs.add(makeGarbage(pork.baseImgName + pork.img + pork.fileType));
        imgs.add(makeGarbage(app.baseImgName + app.img + app.fileType));
        imgs.add(makeGarbage(bag.baseImgName + bag.img + bag.fileType));

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

            imgs.get(i).addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (wasTouched) {
                        System.out.println("X:");
                        System.out.println(oldLocMap.get("x"+imgs.get(k).getName()+"0"));
                        System.out.println(oldLocMap.get("x"+imgs.get(k).getName()+"99"));
                        System.out.println("Y:");
                        System.out.println(oldLocMap.get("y"+imgs.get(k).getName()+"0"));
                        System.out.println(oldLocMap.get("y"+imgs.get(k).getName()+"99"));

                        float yVel = oldLocMap.get("y"+imgs.get(k).getName()+"0") - oldLocMap.get("y"+imgs.get(k).getName()+"99");
                        float xVel = oldLocMap.get("x"+imgs.get(k).getName()+"0") - oldLocMap.get("x"+imgs.get(k).getName()+"99");

                        System.out.println("VELOCITIES:");
                        System.out.println("xVel:"+xVel);
                        System.out.println("yVel:"+yVel);

                        velMap.put(imgs.get(k).getName() + "x", xVel);
                        velMap.put(imgs.get(k).getName() + "y", yVel);

                        for(int i=0; i<100; i++){
                            oldLocMap.put("y" + imgs.get(k).getName()+ Integer.toString(i), imgs.get(k).getY());
                            oldLocMap.put("x" + imgs.get(k).getName() + Integer.toString(i), imgs.get(k).getX());
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
                    for(int i=99; i>=0; i--){
                        oldLocMap.put("y"+imgs.get(k).getName()+Integer.toString(i+1), oldLocMap.get("y"+imgs.get(k).getName()+Integer.toString(i)));
                        oldLocMap.put("x"+imgs.get(k).getName()+Integer.toString(i+1), oldLocMap.get("x"+imgs.get(k).getName()+Integer.toString(i)));
                    }
                    oldLocMap.put("x"+imgs.get(k).getName()+"0", imgs.get(k).getX());
                    oldLocMap.put("y"+imgs.get(k).getName()+"0", imgs.get(k).getY());

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
