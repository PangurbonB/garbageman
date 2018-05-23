package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
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
import com.garbageman.game.ListAccess;
import com.garbageman.game.UI;
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
    private float buttonSizeX = 170, buttonSizeY = 50;
    private ArrayList<Actor> coverTheseWithInv = new ArrayList<Actor>();

    public ShopScreen (Garbageman game){
        this.game = game;
    }

    private ArrayList<Actor> makeButton(String text, Color backgroundColor, float posX, float posY){
        TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
        bs.font = game.makeFont(15);
        bs.fontColor = Color.BLACK;
        TextButton button = new TextButton(text, bs);
        button.setBounds(posX, posY, buttonSizeX, buttonSizeY);
        button.getLabel().setWrap(true);
        button.setVisible(true);
        Actor frame = game.ui.makeRect((int)button.getX(), (int)button.getY(), (int)button.getWidth(), (int)button.getHeight(), backgroundColor, true);

        stage.addActor(button);
        stage.addActor(frame);

        ArrayList<Actor> list = new ArrayList<Actor>();
        list.add(0, button);
        list.add(1, frame);
        coverTheseWithInv.add(button);
        coverTheseWithInv.add(frame);
        return list;
    }

    private int getOwned(Trash item){
        int num = 0;
        for (Trash t:game.backpack.contents) {
            if (t.type == item.type && t.name == item.name){
                num++;
            }
        }
        return num;
    }

    @Override
    public void show() {

        game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/roundy.wav"));
        game.music.play();
        game.music.setLooping(true);

        game.currentScreen = screenName;
        game.ui.init(game, stage, screenName);
        game.ui.makeUI();
        background = new Image(Assets.findTexture("shopScreenCrop"));
        background.setSize(stage.getWidth(), stage.getHeight()-game.ui.topBarHeight);


        int yInBetween = 155;
        int ySize = 133;
        int height = 350;
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
                    final Trash localItem = ListAccess.shopMap.get(itemNum); // = game.ui.makeRect(xPos, setHeight, ySize, ySize, Color.GREEN, true);
                    localItem.setImg();
                    stage.addActor(localItem);
                    localItem.setVisible(true);
                    localItem.setSize(UI.squareSize, UI.squareSize);
                    localItem.setPosition(xPos-10, setHeight-15);
                    coverTheseWithInv.add(localItem);
                    float bPosY = localItem.getY();
                    if (y == 2){
                        bPosY = localItem.getY()+140;
                    }
                    int owned = getOwned(localItem);
                    final ArrayList<Actor> buttons = makeButton("Buy: $"+localItem.baseSellPrice+"0"+" (Owned:"+owned+")", Color.GREEN, localItem.getX()-10, bPosY);
                    final TextButton button = (TextButton)buttons.get(0);
                    button.addListener(new InputListener(){
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button1) {
                            if (game.money >= localItem.baseSellPrice && game.backpack.contents.size()< game.backpack.totalSlots) {
                                game.money = game.money - localItem.baseSellPrice;
                                try {
                                    Trash item = (Trash) Class.forName(localItem.getClass().getName()).newInstance();
                                    game.backpack.add(item);
                                    int newOwned = getOwned(item);
                                    button.setText("Buy: $" + item.baseSellPrice+"0" + " (Owned:" + newOwned + ")");
                                    item.setImg();
                                    item.setSize(128, 128);
                                    item.setNast( 1);
                                    item.setVisible(true);
                                    game.ui.upInv();
                                    game.ui.update();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }

                            }
                            return super.touchDown(event, x, y, pointer, button1);
                        }
                    });

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

        if (game.ui.showInv){
            for (Actor a:coverTheseWithInv) {
                a.setVisible(false);
            }
        }
        else{
            for (Actor a:coverTheseWithInv) {
                a.setVisible(true);
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
