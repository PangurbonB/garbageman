package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.garbageman.game.Garbageman;
import com.garbageman.game.UI;
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
    //private TextureAtlas atlas;
    private Skin skin;
    //private Table table;
    //private BitmapFont font;
   // private TextButton playButton;
    private TextButton.TextButtonStyle bbstyle;
    private ImageButton playButton, exitButton;
    private final int pbHeight = 250;
    private final int pbWidth = 350;
    private String screenName = "MainMenuScreen";
    private BitmapFont FONT;
    private Label lab;
    private TextButton toTest;
    private Image backing;

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
        playButtonActive = new Texture("assets/playButton.png");
        playButtonInactive = new Texture("assets/playButtonActive.png");
    }

    public void setLabColor(){
        System.out.println("hello");
    };

    @Override
    public void show() {
        game.currentScreen = screenName;
        Gdx.input.setInputProcessor(stage);

        /*backing = new Image(new Texture("assets/Screens/mainTitle_single.png"));
        backing.setBounds(0, 0, game.window_width, game.window_height); //*/

        /*FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter size1Params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        size1Params.fontFileName = "assets/PressStart2P.ttf";
        size1Params.fontParameters.size = 10;
        manager.load("size10.ttf", BitmapFont.class, size1Params);
        System.out.println("LOADED " + manager.isLoaded("size10.ttf"));
        //manager.load("font1.fnt", BitmapFont.class);

        /*FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter loadFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        loadFont.fontFileName = "PressStart2P.ttf";
        loadFont.fontParameters.size = 15;
        manager.load("PressStart2P.ttf", BitmapFont.class, loadFont);

        BitmapFont FF = manager.get("PressStart2P.ttf");

        -----------11/1 ^^^

       /* font = new BitmapFont(Gdx.files.internal("assets/font1.fnt"));
        skin = new Skin();

        stage = new Stage(new ScreenViewport());
        playButton = new TextButton("PLAY", skin);*/


       // skin = new Skin();
       // skin.add("test", new Texture("assets/playButton.png"));
       //bbstyle = skin.get("playButton", TextButton.TextButtonStyle.class);
      // playButton = new TextButton("Play", skin)
       /* skin = new Skin();
        skin.add("test", new Texture("assets/playButton.png"));
        TEST = new Image(skin, "test");*/

        /* manager.load("assets/PressStart2P.ttf", BitmapFont.class);
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter loadFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        loadFont.fontFileName = "PressStart2P.ttf";
        loadFont.fontParameters.size = 15;
        manager.load("PressStart2P.ttf", BitmapFont.class, loadFont);
        BitmapFont mainFont = manager.get("PressStart2P.ttf", BitmapFont.class);

        skin = new Skin();
        //skin.add("test", new Texture("assets/playButton.png"));
       // bbstyle = skin.get("playButton", TextButton.TextButtonStyle.class);
        TextButton testButton = new TextButton("Helo ther", bbstyle); // */

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 35;
        BitmapFont font12 = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        /*BitmapFont font = new BitmapFont();
        Label.LabelStyle sty = new Label.LabelStyle();
        sty.font = font12;
        Label lab = new Label("Hello Dana!", sty);
        lab.setBounds(25, 25, 25, 25);
        //lab.setFontScale(4);
        lab.setColor(Color.BLACK);
        lab.setPosition(50, 650);
        stage.addActor(lab);*/

        TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
        bs.font = font12;
        bs.fontColor = Color.BLACK;
        toTest = new TextButton("TEST UI", bs);
        float num = 150;
        toTest.setBounds(num, num, num, num);
        toTest.setColor(Color.BLACK);
        toTest.setSize(250, 75);
        toTest.setPosition(0, 500);
        toTest.setVisible(true);
        toTest.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //toTest.getLabel().setText("ME LLAMO:TYRONE");
                game.setScreen(new GameScreen(game));
                return true;
            }
        });
        stage.addActor(toTest);




        Skin playButtonSkin = new Skin();
        playButtonSkin.add("play", new Texture("assets/playButton.png"));
        playButtonSkin.add("exit", new Texture("assets/exitButton.png"));
       // playButtonSkin.add("playDown", new Texture("assets/playButtonActive.png"));

        ImageButton.ImageButtonStyle playImgStyle = new ImageButton.ImageButtonStyle();
        playImgStyle.imageUp = playButtonSkin.getDrawable("play");
        ImageButton.ImageButtonStyle exitImgStyle = new ImageButton.ImageButtonStyle();
        exitImgStyle.imageUp = playButtonSkin.getDrawable("exit");
        //playImgStyle.imageDown = playButtonSkin.getDrawable("playDown");

        playButton = new ImageButton(playImgStyle);
        playButton.setSize(pbWidth, pbHeight);
        playButton.setPosition((game.window_width-pbWidth)/2, (game.window_height-pbHeight)/2);
        playButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                //System.out.println(game.getScreen() + "   " + this.getClass());
                if (checkCurrentScreen()){
                    game.setScreen(new Trashcan(game));
                }
                return true;
            }
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("UP");
            }
        });
        playButton.setVisible(true);

        exitButton = new ImageButton(exitImgStyle);
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


        System.out.println("called");
    }

    @Override
    public void render(float delta) {



        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);



        //stage.addActor(TEST);
        if (backing != null)
            stage.addActor(backing);
        stage.addActor(playButton);
        stage.addActor(exitButton);
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

    }

    @Override
    public void dispose() {

    }
}
