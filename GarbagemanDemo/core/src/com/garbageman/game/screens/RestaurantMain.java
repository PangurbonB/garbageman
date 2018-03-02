package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.garbageman.game.Garbageman;

/**
 * Created by dpearson6225 on 3/2/2018.
 */

public class RestaurantMain implements Screen {
    Garbageman game;
    String screenName = "RestaurantMain";
    Stage stage = new Stage();

    public RestaurantMain(Garbageman game){
        this.game = game;
    }

    @Override
    public void show() {
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        game.ui.update();
        stage.draw();
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
