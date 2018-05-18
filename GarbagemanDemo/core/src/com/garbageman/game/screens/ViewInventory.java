package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.garbageman.game.Save;
import com.garbageman.game.UI;

import sun.font.TextLabel;

/**
 * Created by dpearson6225 on 9/25/2017.
 */

public class ViewInventory implements Screen{
    //uh so i guess we dont need this anymore, it's been moved to the UI...
    Garbageman game;
    String screenName = "ViewInventory";
    Stage stage;
    UI ui;
    Label heading;

    private Music music;

    public ViewInventory(Garbageman game){
        this.game = game;
    }

    public boolean checkCurrentScreen(){
        return game.currentScreen.equals(screenName);
    }

    public void create(){

    }

    @Override
    public void show() {

        music = Gdx.audio.newMusic(Gdx.files.internal("assets/Sounds/Songs/menuey.wav"));
        music.play();
        music.setLooping(true);
        stage = new Stage();
        game.currentScreen = screenName;
        Gdx.input.setInputProcessor(stage);

        game.ui.init(game, stage, screenName);
        game.ui.makeUI();

        Label.LabelStyle headingStyle = new Label.LabelStyle();
        headingStyle.font = game.makeFont(35);
        heading = new Label("Heading", headingStyle);
        heading.setAlignment(Align.center);
        heading.setColor(Color.BLACK);
        heading.setBounds(250, 50, 200, 100);
        heading.setVisible(true);
        stage.addActor(heading);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.ui.update();
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
