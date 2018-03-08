package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.Garbageman;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.customers.Justin;

/**
 * Created by dpearson6225 on 3/2/2018.
 */

public class RestaurantMain implements Screen {
    Garbageman game;
    String screenName = "RestaurantMain";
    Stage stage = new Stage();
    Image background;

    public RestaurantMain(Garbageman game){
        this.game = game;
    }

    private void makeCustomer(){

    }

    @Override
    public void show() {
        game.currentScreen = this.screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        Gdx.input.setInputProcessor(stage);

        background = (Image)game.ui.makeRect(0, 0, (int)stage.getWidth(), (int)stage.getHeight()-game.ui.topbarHeight, Color.WHITE, true);
        stage.addActor(background);
        background.toBack();


        Customer test = Customer.randomCustomer();
        stage.addActor(test);
        test.setVisible(true);
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
