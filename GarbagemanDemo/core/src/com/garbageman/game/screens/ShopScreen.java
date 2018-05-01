package com.garbageman.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;

/**
 * Created by dpearson6225 on 4/23/2018.
 */

public class ShopScreen implements Screen {
    private Stage stage = new Stage();
    private Garbageman game;
    public static String screenName = "ShopScreen";
    private Image background;

    public ShopScreen (Garbageman game){
        this.game = game;
    }

    @Override
    public void show() {
        game.currentScreen = screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        background = new Image(Assets.findTexture("shopScreenCrop"));
        background.setSize(stage.getWidth(), stage.getHeight()-game.ui.topBarHeight);
        background.setPosition(0, 0);
        background.toBack();
        stage.addActor(background);
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
