package com.garbageman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.screens.MainMenuScreen;

/**
 * Created by dpearson6225 on 11/17/2017.
 */

public class UI {
    Stage stage;
    Garbageman game;
    String screenName;
    private Label repText;
    private Label moneyText;
    private ShapeRenderer shape = new ShapeRenderer();

    public UI(Stage stage, Garbageman game, String screenName){
        this.stage = stage;
        this.game = game;
        this.screenName = screenName;
    }

    private boolean checkCurrentScreen(){
        return game.currentScreen.equals(screenName);
    }

    private BitmapFont makeFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont press2P = generator.generateFont(parameter);
        generator.dispose();
        return press2P;
    }

    private void makeRect(int posX, int posY, int width, int height, Color bb){
        shape.setAutoShapeType(true);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(posX, posY, width, height, bb, bb, bb, bb);
        shape.end();
    }

    private void updateRep(int len, double rep){
        //rep = 50;//for now, out of 100
        int num = (int)(len*rep);
        Color color = Color.valueOf("#00ff11");
        double repcal = rep*100;
        if (repcal >= 75){
            color = Color.valueOf("#00ff11");
        }
        else if (repcal < 75 && repcal >= 50){
            color = Color.valueOf("#d7f442");
        }
        else if (repcal < 50 && repcal >= 25){
            color = Color.valueOf("#f4b841");
        }
        else if (repcal < 25){
            color = Color.valueOf("#f45541");
        }
        makeRect(((game.window_width-len)/2), game.window_height-75, num, 50, color);
    }

    public void makeUI(){
        Label.LabelStyle repStyle = new Label.LabelStyle();
        repStyle.font = makeFont(25);
        repText = new Label("Reputation", repStyle);
        repText.setBounds(250, game.window_height-75, 250, 75);
        repText.setColor(Color.BLACK);
        stage.addActor(repText);

        moneyText = new Label("MUNY", repStyle);
        moneyText.setBounds(200, game.window_height-75, 150, 50);
        moneyText.setColor(Color.BLACK);
        stage.addActor(moneyText);
    }

    public void update(){
        makeRect(0, game.window_height-100, game.window_width, 100, Color.valueOf("#939598"));
        int len = 500;
        makeRect((game.window_width-len)/2, game.window_height-75, len, 50, Color.LIGHT_GRAY);
        updateRep(len, (double)game.reputation/100);
        if (repText != null){
            repText.setText("Reputation: " + game.reputation + "/100");
            repText.setBounds((game.window_width-len)/2, game.window_height-75, len, 50);
            repText.setAlignment(Align.center);
        }
        if (moneyText != null){
            moneyText.setText("$"+game.money);
            moneyText.setAlignment(Align.center);
            moneyText.setColor(Color.BLACK);
        }
    }
}
