package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;
import com.garbageman.game.game.world.GestureHandler;
import com.garbageman.game.game.world.GetInput;


/**
 * Created by dpearson6225 on 9/25/2017.
 */

public class MainMenuScreen implements Screen {
    Garbageman game;
    AssetManager manager;

    Texture playButtonActive;
    Texture playButtonInactive;

    private Stage stage = new Stage();
    private Skin skin;
    //private Table table;
    //private BitmapFont font;
   // private TextButton playButton;
    private TextButton.TextButtonStyle bbstyle;
    private TextButton playButton, exitButton;
    private final int pbHeight = 250;
    private final int pbWidth = 350;
    private String screenName = "MainMenuScreen";
    private BitmapFont FONT;
    private Label lab;
    private TextButton toTest, openShop;
    private Image backing;

    private int wait = 0;
    private boolean played = false;

    private boolean checkCurrentScreen(){
        return game.currentScreen.equals(screenName);
    }

    public MainMenuScreen(Garbageman game){


        InputProcessor inputProcessorOne = new GetInput();
        InputProcessor inputProcessorTwo = new GestureDetector(new GestureHandler());
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);
        this.game = game;
        this.manager = game.manager;
        playButtonActive = Assets.findTexture("playButtonMenu");
        playButtonInactive = Assets.findTexture("playButtonMenuActive");
    }

    public void setLabColor(){
        System.out.println("hello");
    };

    @Override
    public void show() {
        if(Garbageman.startup) {
            game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/soundEffects/Startup.wav"));
            game.music.play();
            Garbageman.startup ^= true;
        }



        game.currentScreen = screenName;
        Gdx.input.setInputProcessor(stage);

        BitmapFont font12 = game.makeFont(35);

        TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
        bs.font = font12;
        bs.fontColor = Color.BLACK;
        toTest = new TextButton("Test Restaurant", bs);
        float num = 150;
        toTest.setBounds(num, num, num, num);
        toTest.setColor(Color.BLACK);
        toTest.setSize(250, 75);
        toTest.setPosition(0, 500);
        toTest.setVisible(true);
        toTest.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //toTest.getLabel().setText("ME LLAMO:TYRONE");
                game.setScreen(new RestaurantScreen(game));
                //game.getScreen().dispose();
                return true;
            }
        });
        stage.addActor(toTest);

        openShop = new TextButton("Shop", bs);
        openShop.setBounds(0, 300, 250, 75);
        openShop.setColor(Color.BLACK);
        openShop.setVisible(true);
        stage.addActor(openShop);
        openShop.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShopScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });





        /*Skin playButtonSkin = Assets.newSkin();
        playButtonSkin.add("play", Assets.findTexture("playButtonMenu"));
        playButtonSkin.add("exit", Assets.findTexture("exitButtonMenu"));
       // playButtonSkin.add("playDown", new Texture("assets/playButtonActive.png"));

        ImageButton.ImageButtonStyle playImgStyle = new ImageButton.ImageButtonStyle();
        playImgStyle.imageUp = playButtonSkin.getDrawable("play");
        ImageButton.ImageButtonStyle exitImgStyle = new ImageButton.ImageButtonStyle();
        exitImgStyle.imageUp = playButtonSkin.getDrawable("exit");
        //playImgStyle.imageDown = playButtonSkin.getDrawable("playDown");*/

        TextButton.TextButtonStyle mainBS = new TextButton.TextButtonStyle();
        mainBS.font = game.makeFont(70);
        mainBS.fontColor = Color.BLACK;
        playButton = new TextButton("Play", mainBS);
        playButton.setSize(pbWidth, pbHeight);
        playButton.setPosition((game.window_width-pbWidth)/2, (game.window_height-pbHeight)/2);
        playButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println(game.getScreen() + "   " + this.getClass());
                if (checkCurrentScreen()){
                    game.setScreen(new Trashcan(game));
                    //game.getScreen().dispose();
                }
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("UP");
            }
        });
        playButton.setVisible(true);

        exitButton = new TextButton("Exit", mainBS);
        exitButton.setSize(pbWidth, pbHeight);
        exitButton.setPosition((game.window_width-pbWidth)/2, ((game.window_height-pbHeight)/2)-pbHeight);
        exitButton.addListener(new InputListener(){
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
               if (checkCurrentScreen()){
                   Gdx.app.exit();
               }
               return true;
           }
        });
        exitButton.setVisible(true);

        stage.addActor(playButton);
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {

        if(wait >= 300 && !played){
            played = true;
            game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/themey.wav"));
            game.music.play();
            game.music.setLooping(true);
        }
        else{
            wait++;
        }


        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //stage.addActor(TEST);
        if (backing != null)
            stage.addActor(backing);
        playButton.toFront();
        exitButton.toFront();
        //stage.addActor(lab);
        game.batch.begin();

        if (manager.update() && manager.isLoaded("size10.ttf")) {
            BitmapFont ff = manager.get("size10.ttf", BitmapFont.class);
            ff.draw(game.batch, "THIS IS TEXT", 20, 20);
            ff.setColor(Color.BLACK);

            //System.out.println("WEKTPK}PW#ETK}PKW}PYT::: " + manager.isLoaded("size10.ttf"));
        }
        //manager.get("font1.fnt", BitmapFont.class).draw(game.batch, "TEST", 50, 50);

       /* TEST.setPosition(150, 150);
        TEST.addListener(new ClickListener(){
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                TEST.moveBy(x-TEST.getWidth()/2, y- TEST.getHeight()/2);
            }
        });*/
      //  game.batch.draw(playButtonActive, (Garbageman.window_width-350)/2, (Garbageman.window_height-150)/2);


        //System.out.println(Gdx.input.getX());



        if (Gdx.input.isKeyPressed(Input.Keys.Q)){
            Gdx.app.exit();
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.F)){
            game.setScreen(new GameScreen(game));
            //game.getScreen().dispose();
        }

        stage.draw();
        game.batch.end();//leave this at the end
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
