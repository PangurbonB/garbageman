package com.garbageman.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;
import com.garbageman.game.ListAccess;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 4/23/2018.
 */

public class ShopScreen implements Screen {
    private Stage stage = new Stage();
    private Garbageman game;
    public static String screenName = "ShopScreen";
    private Image background;
    private ArrayList<Actor> sendToBack = new ArrayList<Actor>();

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


        int yInBetween = 155;
        int ySize = 133;
        int height = 350;
        /*Actor rect = game.ui.makeRect(0, height, yInBetween, 50, Color.BLUE, true);
        rect.setVisible(true);
        sendToBack.add(rect);
        stage.addActor(rect);
        Actor rect2 = game.ui.makeRect(yInBetween, height, ySize, 50, Color.GOLD, true);
        rect2.setVisible(true);
        sendToBack.add(rect2);
        stage.addActor(rect2);//*/
        int itemNum = 0;

        for (int x = 1; x <= 3; x++){
            for (int y = 1; y <= 2; y++){
                //Actor rect = game.ui.makeRect()
                int setHeight = height;
                if (y == 1){
                    setHeight = (height/2)-40;//ok this is so dumb and I'm gonna regret it later
                    //this really should be done by percentages...
                }
                if (setHeight != 0){
                    int xPos = x*(yInBetween);
                    if (x == 2){
                        xPos = x*(yInBetween+ySize);
                    }
                    else if (x == 3){//this is so dumb
                        xPos = (x*(yInBetween+ySize))+ySize-15;
                    }
                    Trash localItem = ListAccess.shopMap.get(itemNum); // = game.ui.makeRect(xPos, setHeight, ySize, ySize, Color.GREEN, true);
                    stage.addActor(localItem);
                    localItem.setVisible(true);
                    itemNum++;
                }
            }
        }
        background.setSize(stage.getWidth(), stage.getHeight());
        background.setPosition(0, 0);
        sendToBack.add(background);
        stage.addActor(background);
    }

    private void sendAllToBack(){
        for (Actor a: sendToBack){
            a.toBack();
        }
    }

    @Override
    public void render(float delta) {
        game.ui.update();
        sendAllToBack();
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
