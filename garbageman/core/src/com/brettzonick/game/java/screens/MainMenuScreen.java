package com.brettzonick.game.java.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.brettzonick.game.java.Garbageman;
import com.brettzonick.game.java.world.InputHandler;

/**
 * Created by bzonick5979 on 9/25/2017.
 */

public class MainMenuScreen implements Screen {
    Texture img;
    public static int x, y = 0;
    Garbageman game;

    @Override
    public void show() {

    }

    public MainMenuScreen (Garbageman game){
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(new InputHandler());

        game.batch.begin();

        game.batch.draw(img, x, y);

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
