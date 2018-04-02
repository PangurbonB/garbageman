package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.Garbageman;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.customers.Justin;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

/**
 * Created by dpearson6225 on 3/2/2018.
 */

public class RestaurantMain implements Screen {
    Garbageman game;
    String screenName = "RestaurantMain";
    Stage stage = new Stage();
    Image background;
    Customer test = null, test2 = null;

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


        /*test = Customer.randomCustomer();
        stage.addActor(test);
        stage.addActor(test.overheadName);
        test.setVisible(true);
        */
        test2 = Customer.randomCustomer(stage);
        /*test2.fileName = "Brett";
        test2.setImg(0, 0);
        test2.customerName = "Brett";
        test2.overheadName.setText("Brett");
        stage.addActor(test2);
        stage.addActor(test2.overheadName);
        test2.setVisible(true); //*/
    }

    @Override
    public void render(float delta) {
        game.ui.update();
        stage.draw();
        Customer.updateAllCurrentCustomers();
        if (Gdx.input.isKeyPressed(Input.Keys.Y)){
            test2.walkToPoint(500, 0);
            System.out.println("moving: ");
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)){
            game.setScreen(new FakeInvScreen(game));
        }
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
