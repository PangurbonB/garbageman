package com.brettzonick.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.brettzonick.game.Garbageman;
import com.brettzonick.game.world.InputHandler;

/**
 * Created by bzonick5979 on 9/25/2017.
 */

public class GameScreen implements Screen{

    Texture img;
    public static int x, y = 0;
    public static final float SPEED = 1000;

    Garbageman game;

    @Override
    public void show() {

    }

    public GameScreen (Garbageman game){
        this.game = game;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Gdx.input.setInputProcessor(new InputHandler());

        float dt = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            Gdx.app.exit();
        /*
        if (Gdx.input.isKeyPressed(Input.Keys.L))
            x += SPEED*dt;
        if (Gdx.input.isKeyPressed(Input.Keys.J))
            x -= SPEED*dt;
        if (Gdx.input.isKeyPressed(Input.Keys.I))
            y += SPEED*dt;
        if (Gdx.input.isKeyPressed(Input.Keys.K))
            y -= SPEED*dt;
        */

        img = new Texture("assets/PLAY.png");

        game.batch.begin();

        /*
        if(x > (img.getWidth()) + 720) x = 0-(img.getWidth());
        if(x < 0-(img.getWidth())) x = (img.getWidth()) + 720;
        if(y > (img.getHeight()) + 1280) y = 0-(img.getHeight());
        if(y < 0-(img.getHeight())) y = (img.getHeight()) + 1280;
        */

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
