package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;
import com.garbageman.game.customers.Customer;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dpearson6225 on 3/2/2018.
 */

public class RestaurantScreen implements Screen {
    Garbageman game;
    String screenName = "RestaurantScreen";
    Stage stage = new Stage();
    Image background;
    Customer test = null, test2 = null;
    int currentInterval = 0, maxInterval = new Random().nextInt(40);

    private ArrayList<Actor> coverTheseWithInv = new ArrayList<Actor>();



    public RestaurantScreen(Garbageman game){
        this.game = game;
    }

    private Customer makeCustomerToCounter(){
        Customer c = Customer.randomCustomer(stage);
        //make order too?
        /*
        current idea:
        customer is generated, customer comes to counter, leaves if not interacted with in certain amount of time
        after customer leaves it waits a random amount of time and then generates another customer
         */
        return c;
    }

    @Override
    public void show() {
        game.currentScreen = this.screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        Gdx.input.setInputProcessor(stage);

        background = new Image(Assets.findTexture("restBack"));
        background.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(background);
        background.toBack();

        /*test2 = Customer.randomCustomer(stage);
        coverTheseWithInv.add(test2);*/
    }

    @Override
    public void render(float delta) {
        for (Actor a : coverTheseWithInv) {
            a.setVisible(!game.ui.showInv);
        }


        game.ui.update();
        stage.draw();
        Customer.updateAllCurrentCustomers();
        if (Gdx.input.isKeyPressed(Input.Keys.Y )&& test2 != null){
            test2.walkToPoint(500, 0);
            System.out.println("moving: ");
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
