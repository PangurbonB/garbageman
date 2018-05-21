package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.garbageman.game.Assets;
import com.garbageman.game.Garbageman;

/**
 * Created by Pangur on 5/20/2018.
 */

public class CreditsScreen implements Screen{


    private TextField credits;
    private TextButton menu;
    Stage stage = new Stage();
    Garbageman game;
    Image background = new Image(Assets.findTexture("whiteBlank"));

    public static String screenName = "CreditsScreen";


    public CreditsScreen(Garbageman game){
        this.game = game;
    }

    private String[] lines = new String[] {"Credits:", "","","", "Arte", "","Silas Kidd-Myers","", "","", "Music","","Silas Kidd-Myers","","","","Coding","", "Dana Pearson","","Brett Zonick"};


    @Override
    public void show() {

        game.music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/clashy.wav"));
        game.music.play();
        game.music.setLooping(true);

        game.currentScreen = screenName;
        game.ui.init(game, stage, screenName);

        background.setBounds(0, 0, stage.getWidth(), stage.getHeight());
        background.setVisible(true);
        stage.addActor(background);

        BitmapFont font12 = game.makeFont(35);
        TextField.TextFieldStyle ts = new TextField.TextFieldStyle();
        ts.font = font12;
        ts.fontColor = Color.BLACK;
        for (int i = 0; i < lines.length; i++) {
            credits = new TextField(lines[i], ts);
            credits.setBounds((stage.getWidth()/2) - 35*lines[i].split("").length/2, 650 - 30*i, 35*lines[i].split("").length, 75);
            credits.setColor(Color.BLACK);
            credits.setVisible(true);
            stage.addActor(credits);
        }

        TextButton.TextButtonStyle bs = new TextButton.TextButtonStyle();
        bs.font = font12;
        bs.fontColor = Color.BLACK;

        menu = new TextButton("Menu", bs);
        menu.setBounds(0, 300, 250, 75);
        menu.setColor(Color.BLACK);
        menu.setVisible(true);
        stage.addActor(menu);
        menu.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenuScreen(game));
                return super.touchDown(event, x, y, pointer, button);
            }
        });

    }

    @Override
    public void render(float delta) {
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
        game.music.pause();
    }

    @Override
    public void dispose() {

    }
}
