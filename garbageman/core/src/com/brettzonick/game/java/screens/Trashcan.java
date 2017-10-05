package com.brettzonick.game.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brettzonick.game.java.Garbageman;
import com.brettzonick.game.java.garbage.McdFries;
import com.brettzonick.game.java.garbage.Trash;
import com.brettzonick.game.java.world.InputHandler;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Trashcan implements Screen {

    Garbageman game;

    Stage stage = new Stage();



    private Viewport viewport;

    public Trashcan (Garbageman game){
        this.game = game;
    }

    final McdFries fries = new McdFries();

    @Override
    public void show() {



        fries.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                boolean touchdown=true;


            }

            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
                boolean touchdown=false;
                fries.img = "error";

                return true;
            }

            public void mouseMoved(InputEvent event, float x, float y, int pointer, int button){

            }

        });

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(new InputHandler());


        game.batch.begin();

        //game.batch.draw();


        game.batch.end();
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.setScreenWidth(width);
        viewport.setScreenHeight(height);
        stage.setViewport(viewport);

        fries.setSize(0.2f*stage.getWidth(), 0.2f*stage.getWidth() * fries.getHeight()/fries.getWidth());
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
