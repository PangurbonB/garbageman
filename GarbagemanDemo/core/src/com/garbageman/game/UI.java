package com.garbageman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.screens.GameScreen;
import com.garbageman.game.screens.MainMenuScreen;
import com.garbageman.game.screens.Trashcan;
import com.garbageman.game.screens.ViewInventory;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 11/17/2017.
 */

public class UI {
    Stage stage;
    Garbageman game;
    String screenName;
    private Label repText;
    private Label moneyText;
    private TextButton menuButton;
    private TextButton invButton;
    private ShapeRenderer shape = new ShapeRenderer();
    private boolean showInv = false;
    private ArrayList<Actor> inv = new ArrayList<Actor>();


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

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = makeFont(35);
        menuButton = new TextButton("Menu", buttonStyle);
        menuButton.setBounds(0, game.window_height-75, 175, 50);
        menuButton.getLabel().setColor(Color.BLACK);
        menuButton.getLabel().setAlignment(Align.center);
        menuButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (checkCurrentScreen()){
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
        stage.addActor(menuButton);

        TextButton.TextButtonStyle invBStyle = new TextButton.TextButtonStyle();
        invBStyle.font = makeFont(25);
        invButton = new TextButton("Inventory", invBStyle);
        int invWidth = 250;
        invButton.setBounds(game.window_width-invWidth, game.window_height-75, invWidth, 50);
        invButton.getLabel().setAlignment(Align.center);
        invButton.getLabel().setColor(Color.BLACK);
        invButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println("CLIEKED + " + checkCurrentScreen());
                showInv = !showInv;
                return true;
            }
        });
        //System.out.println("MADE LISTNER FOR INVBUTTON");
        stage.addActor(invButton);

        BitmapFont labFont = game.makeFont(25);
        TextButton.TextButtonStyle invbbs = new TextButton.TextButtonStyle();
        invbbs.font = labFont;
        int size = 150;
        int yPos = game.window_height-300;
        int yPlus = size+20;
        int xPos = 50;
        int xPlus = size+20;
        int tot = 1;
        for (int y = 0; y <= 3; y++){
            System.out.println("looped");
            for (int x = 0; x <= 6; x++){
                TextButton localB = new TextButton("ITEM: "+tot, invbbs);
                localB.setBounds(xPos, yPos, size, size);
                inv.add(localB);
                localB.setVisible(true);
                stage.addActor(localB);
                xPos = xPos + xPlus;
                System.out.println(x);
                tot++;
                System.out.println(x + " "+(x <= 6));
            }
            yPos = yPos - yPlus;
            xPos = 50;
        }
        System.out.println("DONE");
    }

    public void update(){
        Color barBackgroundGrey = Color.valueOf("#939598");
        makeRect(0, game.window_height-100, game.window_width, 100, barBackgroundGrey);
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

        if (showInv == true){
            makeRect(0, 0, game.window_width, game.window_height-75, barBackgroundGrey);

        }
        else if (showInv == false){

        }
    }
}
