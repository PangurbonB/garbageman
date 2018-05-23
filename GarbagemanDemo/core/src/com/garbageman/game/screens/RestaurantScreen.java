package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;
import com.garbageman.game.GarbagemanInTheFlesh;
import com.garbageman.game.PassTrash;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.cooked.Pizza;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.customers.Dana;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dpearson6225 on 3/2/2018.
 */

public class RestaurantScreen implements Screen {

    private Garbageman game;
    public static String screenName = "RestaurantScreen";
    private Stage stage = new Stage();
    private Image background;
    private Customer currentCustomer = null, test2 = null;
    public Customer frontCustomer = null;
    private TextButton viewOrders;
    private Actor orderFrame;

    public static float nextToCounter = 650, floorHeight = 25;

    private int getRandInterval(){
        return 10/*new Random().nextInt(0)+100*/;
    }

    private int currentInterval = 0, maxInterval = getRandInterval();
    private boolean dontGo = false, showOrders = false;

    private ArrayList<Actor> coverTheseWithInv = new ArrayList<Actor>();

    private Actor giveOrderFrame;
    private TextButton give, cancel;
    private ArrayList<Actor> giveOrderList = new ArrayList<Actor>();



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

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = Garbageman.makeFont(15);
        GarbagemanInTheFlesh cc = new GarbagemanInTheFlesh();
        cc.setImg();
        cc.setPosition(1000, RestaurantScreen.floorHeight-5);
        cc.setSize(350, 350);
        cc.overheadName = new Label(cc.customerName, style);
        cc.overheadName.setAlignment(Align.center);
        cc.overheadName.setWrap(true);
        cc.setOverheadPos();
        stage.addActor(cc);
        stage.addActor(cc.overheadName);

        game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/swingy.wav"));
        game.music.play();
        game.music.setLooping(true);

        game.currentScreen = this.screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        Gdx.input.setInputProcessor(stage);

        background = new Image(Assets.findTexture("restBack"));
        background.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(background);
        background.toBack();

        giveOrderFrame = game.ui.makeRect(0, 0, 200, 200, Color.DARK_GRAY, false);
        giveOrderList.add(giveOrderFrame);
        stage.addActor(giveOrderFrame);
        giveOrderFrame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (giveOrderFrame.isVisible()){
                    giveOrderFrame.setVisible(false);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        TextButton.TextButtonStyle giveStyle = new TextButton.TextButtonStyle();
        giveStyle.font = Garbageman.makeFont(20);
        giveStyle.fontColor = Color.WHITE;
        give = new TextButton("Give Order", giveStyle);
        give.setSize(giveOrderFrame.getWidth(), giveOrderFrame.getHeight()/2);
        give.setVisible(false);
        stage.addActor(give);
        giveOrderList.add(give);
        give.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.ui.giveOrder = true;
                game.ui.showInv = true;
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        cancel = new TextButton("Cancel", giveStyle);
        cancel.setSize(giveOrderFrame.getWidth(), giveOrderFrame.getHeight()/2);
        cancel.setVisible(false);
        stage.addActor(cancel);
        giveOrderList.add(cancel);

        cancel.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                for (Actor a: giveOrderList) {
                    a.setVisible(false);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });


        //coverTheseWithInv.add(orderFrame);

        if (game.currentCustomers.size()> 0){
            for (Customer c: game.currentCustomers) {
                stage.addActor(c);
                c.clearListeners();
                coverTheseWithInv.add(c);
                c.setPosition(c.posX, c.posY);
                c.say();//leave it blank for no msg
            }
            frontCustomer = Customer.getFirstCustomer();
        }

        /*test2 = Customer.randomCustomer(stage);
        coverTheseWithInv.add(test2);*/
    }


    int waitTime = 0, waitMax = 120;
    @Override
    public void render(float delta) {

        if (game.resetRestaurant){
            if (waitTime >= waitMax) {
                game.resetRestaurant = false;
                game.setScreen(new RestaurantScreen(game));
            }
            else{
                waitTime++;
            }
        }
        else if (game.doReset){
            game.doReset = false;
            for(int x = Customer.front; x < Customer.listOfCustomers.size(); x++){
                Customer c = Customer.listOfCustomers.get(x);
                if (c != null){
                    c.walkToPoint(c.getX()+(c.getWidth()/2), RestaurantScreen.floorHeight);
                }
            }
        }

        for (Actor a : coverTheseWithInv) {
            if (a != orderFrame) {
                a.setVisible(!game.ui.showInv);
            }
        }

        if (frontCustomer != null){
            //System.out.println("NOT NULL");
            //System.out.println("FIRST "+Customer.listOfCustomers.get(0));
            if (Customer.listOfCustomers.get(0)== frontCustomer){
                //System.out.println("FIRST CUSTOMER");
                if (frontCustomer.finalFood != null){
                    //System.out.println("HAS ORDER");
                    frontCustomer.walkToPoint(1500, RestaurantScreen.floorHeight);
                    //remove front customer now
                }
            }
        }

        if (game.ui.showInv){
            for (Actor a: giveOrderList){
                a.setVisible(false);
            }
        }
        for (Actor a: giveOrderList){
            if (a.isVisible()){
                a.toFront();
            }
        }


        game.ui.update();
        stage.draw();
        Customer.updateAllCurrentCustomers(game);
        if (Gdx.input.isKeyPressed(Input.Keys.Y )){
            System.out.println("front customer: "+(frontCustomer == null));
            if (frontCustomer != null){
                if (!frontCustomer.hasOrder()) {
                    CookedFood food = new Pizza();
                    food.nast = new Random().nextInt(100) + 1;
                    frontCustomer.order = food;
                    frontCustomer.giveCookedFood(food, game);
                }
            }
        }

        if (frontCustomer != null){
            //System.out.println("FRONT LISTENER: "+frontCustomer.getListeners().size);
            if (frontCustomer.getListeners().size == 0){
                //System.out.println("GIVEN FRONT LISTENER");
                frontCustomer.addListener(new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        if (frontCustomer.order == null) {
                            frontCustomer.makeOrder();
                            frontCustomer.say("I want " + frontCustomer.order.name);
                            //frontCustomer.removeListener(this);
                        }
                        else if (frontCustomer.order != null){
                            for (Actor a: giveOrderList) {
                                a.setVisible(true);
                            }
                            giveOrderFrame.setPosition(frontCustomer.getX()+(frontCustomer.getWidth()/2), frontCustomer.getY()+(frontCustomer.getHeight()/2));
                            cancel.setPosition(giveOrderFrame.getX(), giveOrderFrame.getY());
                            give.setPosition(giveOrderFrame.getX(), cancel.getY()+(giveOrderFrame.getHeight()/2));
                        }
                        return super.touchDown(event, x, y, pointer, button);
                    }
                });
            }
        }

        if (Customer.listOfCustomers.size()> 0){
            for (Customer c: Customer.listOfCustomers) {
                if (c.order != null){
                    //System.out.println(c.customerName+" Order: "+c.order.name);
                }
            }
        }

        if (!dontGo) {
            //System.out.println("current interval: " + currentInterval + ", max: " + maxInterval);
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
        game.music.pause();
    }

    @Override
    public void dispose() {

    }
}
