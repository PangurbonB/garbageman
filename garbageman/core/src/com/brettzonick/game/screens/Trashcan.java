package com.brettzonick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.brettzonick.game.Garbageman;
import com.brettzonick.game.world.InputHandler;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Trashcan implements Screen {

    Garbageman game;

    public Trashcan (Garbageman game){
        this.game = game;
    }

    @Override
    public void show() {
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
