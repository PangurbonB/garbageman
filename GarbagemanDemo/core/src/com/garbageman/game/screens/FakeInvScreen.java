package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.garbageman.game.Garbageman;
import com.garbageman.game.garbage.Trash;

/**
 * Created by dpearson6225 on 3/22/2018.
 */

public class FakeInvScreen implements Screen {
    Garbageman game;
    Trash tr;
    Stage stage = new Stage();
    public static String screenName = "fakeInv";

    public FakeInvScreen(Garbageman game, Trash tr){
        this.game = game;
        this.tr = tr;
    }

    @Override
    public void show() {
        game.ui.currentType = this.tr.type;
        game.currentScreen = screenName;
        Gdx.input.setInputProcessor(this.stage);
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        game.ui.showInv = true;
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
