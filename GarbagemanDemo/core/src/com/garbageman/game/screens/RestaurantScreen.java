package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.cooked.Pizza;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dpearson6225 on 3/2/2018.
 */

public class RestaurantScreen implements Screen {

    Music music;

    private Garbageman game;
    public static String screenName = "RestaurantScreen";
    private Stage stage = new Stage();
    private Image background;
    private Customer currentCustomer = null, test2 = null, frontCustomer = null;
    private TextButton viewOrders;
    private Actor orderFrame;

    public static float nextToCounter = 650, floorHeight = 25;

    private int getRandInterval(){
        return 10/*new Random().nextInt(0)+100*/;
    }

    private int currentInterval = 0, maxInterval = getRandInterval();
    private boolean dontGo = false, showOrders = false;

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
        coverTheseWithInv.add(c);
        c.walkToPoint(c.getWhatPosXShouldBe(), floorHeight);
        return c;
    }

    @Override
    public void show() {

        this.music = game.music;

        music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/clashy.wav"));

        game.currentScreen = this.screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        Gdx.input.setInputProcessor(stage);

        background = new Image(Assets.findTexture("restBack"));
        background.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(background);
        background.toBack();

        TextButton.TextButtonStyle orderStyle = new TextButton.TextButtonStyle();
        orderStyle.font = game.makeFont(20);
        orderStyle.fontColor = Color.BLACK;
        viewOrders = new TextButton("View Orders", orderStyle);
        float height = 50, width = 150;
        viewOrders.setBounds(stage.getWidth()-(float)(width*1.5), stage.getHeight()-(height*3), width, height);
        viewOrders.setVisible(true);
        stage.addActor(viewOrders);
        coverTheseWithInv.add(viewOrders);
        viewOrders.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                orderFrame.setVisible(true);
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        float frameHeight = 350, frameWidth = 275;
        orderFrame = game.ui.makeRect((int)(stage.getWidth()-viewOrders.getWidth()*2), (int)(viewOrders.getY()-frameHeight), (int)frameWidth, (int)frameHeight, Color.BLUE, false);
        stage.addActor(orderFrame);
        //coverTheseWithInv.add(orderFrame);

        if (game.currentCustomers.size()> 0){
            for (Customer c: game.currentCustomers) {
                stage.addActor(c);
                coverTheseWithInv.add(c);
                c.setPosition(c.posX, c.posY);
                c.say();//leave it blank for no msg
            }
            frontCustomer = Customer.getFirstCustomer();
        }

        /*test2 = Customer.randomCustomer(stage);
        coverTheseWithInv.add(test2);*/
    }

    @Override
    public void render(float delta) {
        for (Actor a : coverTheseWithInv) {
            if (a != orderFrame) {
                a.setVisible(!game.ui.showInv);
            }
        }


        game.ui.update();
        stage.draw();
        Customer.updateAllCurrentCustomers();
        if (Gdx.input.isKeyPressed(Input.Keys.Y )){
            System.out.println("front customer: "+(frontCustomer == null));
            if (frontCustomer != null){
                if (!frontCustomer.hasOrder()) {
                    CookedFood food = new Pizza();
                    food.nast = new Random().nextInt(100) + 1;
                    frontCustomer.order = food;
                    frontCustomer.giveCookedFood(food);
                }
            }
        }

        if (frontCustomer != null){
            frontCustomer.say("I want food");
        }

        //System.out.println("SHOW ORDER: "+showOrders);
        if (showOrders && !game.ui.showInv){
            orderFrame.setVisible(true);
        }
        else if (!showOrders){
            orderFrame.setVisible(false);
        }

        if (!dontGo) {
            System.out.println("current interval: " + currentInterval + ", max: " + maxInterval);
            currentInterval++;
            if (currentInterval >= maxInterval) {
                dontGo = true;
                System.out.println("TIME FOR CUSTOMER!!!");
                currentInterval = 0;
                maxInterval = getRandInterval();
                currentCustomer = makeCustomerToCounter();
                frontCustomer = Customer.getFirstCustomer();
            }
        }
        else if (dontGo){
            if (currentCustomer != null){
                if (!currentCustomer.isMoving()){
                    //currentCustomer.say("I want some food");
                }
            }
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
        music.pause();
    }

    @Override
    public void dispose() {

    }
}
