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
import com.brettzonick.game.java.garbage.McdFries;
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

    final McdFries fries = new McdFries();
    Table table;
    String str = fries.baseImgName + fries.img + fries.fileType;
    Image img = new Image();
    //table.add(img);
    Camera camera;

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

        fries.setSize(32, 32);

    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        skin = new Skin();
        skin.add("mcdFries", new Texture(str));
        img = new Image(skin, "mcdFries");

        oldLocMap.put("y"+img.getName(), img.getY());
        oldLocMap.put("x"+img.getName(), img.getX());

        velMap.put("y"+img.getName(), img.getY());
        velMap.put("x"+img.getName(), img.getX());

        imgs.add(img);

    }

    @Override
    public void render(float delta) {

        final float oldX = x;
        final float oldY = y;

        fries.setX(x);
        fries.setY(y);
        fries.setSize(32, 32);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            Gdx.app.exit();

        stage.addActor(img);

        //stage.addActor(fries);

        game.batch.begin();

        img.addListener(new ClickListener(){
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                img.moveBy(x - img.getWidth() / 2, y - img.getHeight() / 2);
                float yVel = y-oldLocMap.get("y"+img.getName());
                float xVel = x-oldLocMap.get("x"+img.getName());
                oldLocMap.put("y"+img.getName(), img.getY());
                oldLocMap.put("x"+img.getName(), img.getX());
                velMap.put("y"+img.getName(), yVel);
                velMap.put("x"+img.getName(), xVel);
            }

            public void touchUp(InputEvent event, float x, float y, int pointer) {


                System.out.println("test");
                System.out.println(x-oldLocMap.get("x"+img.getName()));


            }
        });

        if(!velMap.get("x"+img.getName()).equals(0f)){
            System.out.println(velMap.get("y"+img.getName()));
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
