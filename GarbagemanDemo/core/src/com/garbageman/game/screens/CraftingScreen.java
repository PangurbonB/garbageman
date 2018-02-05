package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class CraftingScreen implements Screen{

    Image background = new Image(new Texture("assets/Screens/craftingScreen.png"));
    Stage stage = new Stage();
    int[][] craftingLocsX;
    int centerX = 641;
    int centerY = 359;

    @Override
    public void show() {
        background.toBack();
        background.setVisible(true);
        background.setHeight(stage.getHeight());
        background.setWidth(stage.getWidth());
        stage.addActor(background);

        craftingLocsX[0] = new int[]{373, 224};
        craftingLocsX[1] = new int[]{904, 220};
        craftingLocsX[2] = new int[]{1081, 348};
        craftingLocsX[3] = new int[]{1019, 538};
        craftingLocsX[4] = new int[]{818, 604};
        craftingLocsX[5] = new int[]{454, 609};
        craftingLocsX[6] = new int[]{257, 541};
        craftingLocsX[7] = new int[]{374, 216};


    }

    @Override
    public void render(float delta) {

        if(Gdx.input.isKeyJustPressed(Input.Keys.M))
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());

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
