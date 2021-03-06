package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.Garbageman;
import com.garbageman.game.UI;

import sun.font.TextLabel;

/**
 * Created by dpearson6225 on 9/25/2017.
 */

public class GameScreen implements Screen{

    Image img;
    public static float x, y;
    public static final int SPEED = 1000;
    Garbageman game;
    private final boolean resetOnOpen = false;
    //private TextButton menuButton;
    private final float mbHeight = 76;
    private final float mbWidth = 150;
    private Stage stage = new Stage();
    private String screenName = "GameScreen";
    private ShapeRenderer shape = new ShapeRenderer();
    private Label repText;
    private Label moneyText;
    UI ui;

    public GameScreen(Garbageman game){
        this.game = game;
    }

    public boolean checkCurrentScreen(){
        return game.currentScreen.equals(screenName);
    }

    public BitmapFont makeFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont press2P = generator.generateFont(parameter);
        generator.dispose();
        return press2P;
    }

    public TextButton newButton(String txt, String type, int fontSize, float posX, float posY, float sizeX, float sizeY){
        BitmapFont press2P = makeFont(fontSize);
        TextButton.TextButtonStyle tStyle = new TextButton.TextButtonStyle();
        tStyle.font = press2P;
        TextButton bu = new TextButton(txt, tStyle);
        bu.getLabel().setColor(Color.BLACK);
        bu.setSize(sizeX, sizeY);
        bu.setPosition(posX, posY);
        stage.addActor(bu);
        return bu;//just in case it's needed
    };

    @Override
    public void show() {
        game.currentScreen = screenName;
        Gdx.input.setInputProcessor(stage);

        ui = new UI(stage, game, this.screenName);
        ui.makeUI();
        //System.out.println("");
        if (img == null) {
            /*img = new Image(new Texture("assets/Garbage/Pork.png"));
            int nn = 256;
            img.setSize(nn, nn);
            stage.addActor(img);
            if (resetOnOpen) {
                x = 0;
                y = 0;
            }*/
        }

        /*Skin buttonSkins = new Skin();
        buttonSkins.add("menuButton", new Texture("assets/menuButton1.png"));
        ImageButton.ImageButtonStyle menuButtonStyle = new ImageButton.ImageButtonStyle();
        menuButtonStyle.imageUp = buttonSkins.getDrawable("menuButton");
        menuButton = new ImageButton(menuButtonStyle);
        menuButton.setPosition(0, game.window_height - mbHeight+2);
        //System.out.println(game.window_height - mbHeight);
        menuButton.setSize(mbWidth, mbHeight);
        menuButton.setVisible(true);
        menuButton.addListener(new InputListener(){
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               if (checkCurrentScreen()){
                   game.setScreen(new MainMenuScreen(game));
               }
              return true;
           }
        });*/

        /*menuButton = newButton("Menu", "button", 30, 0, game.window_height - mbHeight+2, 150, 50);
        menuButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (checkCurrentScreen())
                    game.setScreen(new MainMenuScreen(game));
                return true;
            }
        });*/
    }


    public static void move(float newx, float newy){
        x+= newx;
        y+= newy;
    }
    public void makeRect(int posX, int posY, int width, int height, Color bb){
        shape.setAutoShapeType(true);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(posX, posY, width, height, bb, bb, bb, bb);
        shape.end();
    }

    //this data will eventually be global and saved, for now it's here as a demo

    @Override
    public void render(float delta) {
        //System.out.println("" + Gdx.graphics.getDeltaTime());
        float plus = SPEED * Gdx.graphics.getDeltaTime();

        /*if (Gdx.input.isKeyPressed(Input.Keys.D)){
            move(plus, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            move(-plus, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            move(0, plus);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            move(0, -plus);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)){
            game.setScreen(new MainMenuScreen(game));
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            game.giveReputation(-1);
            game.giveMoney(-1);
            //System.out.println(game.reputation);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)){
            game.giveReputation(1);
            game.giveMoney(1);
            //System.out.println(game.reputation);
        }

        /*if (this.x > game.window_width){
            this.x = 0-img.getWidth();
        }
        else if (this.x < 0-img.getWidth()){
            this.x = game.window_width-img.getWidth()+img.getWidth();
        }
        if (this.y > game.window_height){
            this.y = 0-img.getHeight();
        }
        else if (this.y < 0-img.getHeight()){
            this.y = game.window_height-img.getHeight()+img.getHeight();
        }*/

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //stage.addActor(menuButton);
        //img.setPosition(x, y);



        game.batch.begin();
        //game.batch.draw(img, x, y);
        game.batch.end();
        ui.update();
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
