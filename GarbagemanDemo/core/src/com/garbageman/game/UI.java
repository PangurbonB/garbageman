package com.garbageman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private ArrayList<Actor> infoFrame = new ArrayList<Actor>();
    private Actor currentDown;
    private boolean showInfo = false;
    private int infoX, infoY;
    private ArrayList<String> curInfoList = new ArrayList<String>();
    private Label infoName;
    private Label rotten;
    private Label desc;


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
        int size = 128;
        int startX = 200;
        int yPos = game.window_height-350;
        int yPlus = size+20;
        int xPos = startX;
        int xPlus = size+20;
        int tot = 1;
        for (int y = 0; y <= 3; y++){
            //System.out.println("looped");
            for (int x = 0; x < 5; x++){
                Skin lbs = new Skin();
                Texture img = new Texture("assets/Garbage/McdFries.png");
                lbs.add("default", img);

                ImageButton.ImageButtonStyle ibStyle = new ImageButton.ImageButtonStyle();
                ibStyle.imageUp = lbs.getDrawable("default");
                ibStyle.imageUp.setMinHeight(size);
                ibStyle.imageUp.setMinWidth(size);
                final ImageButton localB = new ImageButton(ibStyle);
                localB.setBounds(xPos+25, yPos, size, size);
                localB.setSize(256,256);
                localB.setSkin(lbs);
                localB.addListener(new InputListener(){
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        //create info
                        //System.out.println("CLIEDK");
                        //new InfoFrame(game, stage, (int)localB.getX(), (int)localB.getY());
                        //makeRect((int)localB.getX(), (int)localB.getY(), 100, 100, Color.BLUE);
                        if (currentDown == null || currentDown != localB) {
                            showInfo = true;
                            infoX = (int) localB.getX();
                            infoY = (int) localB.getY();
                            currentDown = localB;
                            curInfoList.clear();
                            curInfoList.add(0, "This name: "+x);
                            curInfoList.add(1, "15");//rottenness
                            curInfoList.add(2, "Uncommon");
                            curInfoList.add(3, "This is a test item. For testing, you just click the button and it puts in this description.");
                            System.out.println("SIZE:"+curInfoList.size());

                        }
                        else if (currentDown != null){
                            if (currentDown == localB){
                                currentDown = null;
                                showInfo = false;
                            }
                        }
                        return true;
                    };
                });
                inv.add(localB);
                localB.setVisible(false);
                stage.addActor(localB);
                xPos = xPos + xPlus;
                //System.out.println(x);
                tot++;
                //System.out.println(x + " "+(x <= 6));
            }
            yPos = yPos - yPlus;
            xPos = startX;
        }
        Label.LabelStyle smallerStyle = new Label.LabelStyle();
        smallerStyle.font = game.makeFont(15);
        infoName = new Label("Item Name", smallerStyle);
        infoName.setAlignment(Align.center);
        infoName.setBounds(0, game.window_height-200, 250, 75);
        infoName.setVisible(false);
        stage.addActor(infoName);
        infoFrame.add(infoName);
        //System.out.println("DONE");
    }

    private void setInvVis(boolean val, boolean getInfoFrame){
        ArrayList<Actor> check = null;
        if (getInfoFrame == false){
            check = inv;
        }
        else if (getInfoFrame == true){
            check = infoFrame;
        }
        if (check != null) {
            for (int i = 0; i < check.size(); i++) {
                Actor b = check.get(i);
                b.setVisible(val);
            }
        }
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
            setInvVis(true, false);
            invButton.getLabel().setText("Close");
            if (showInfo && curInfoList.size()== 4){
                setInvVis(true, true);
                makeRect(0, 0, 250, game.window_height-75 , Color.BLUE);
                infoName.setText(curInfoList.get(0));
            }
            else{
                setInvVis(false, true);
            }
        }
        else if (showInv == false){
            setInvVis(false, false);
            invButton.getLabel().setText("Inventory");
        }
    }
}
