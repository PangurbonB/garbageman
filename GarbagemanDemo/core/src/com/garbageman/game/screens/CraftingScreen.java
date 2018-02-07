package com.garbageman.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.garbageman.game.Garbageman;
import com.garbageman.game.SpriteSheetDivider;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
import com.garbageman.game.garbage.Leaf;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.OldNewspaper;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class CraftingScreen implements Screen{

    Image background = new Image(new Texture("assets/Screens/craftingScreen.png"));
    Stage stage = new Stage();
    int[][] craftingLocs = new int[8][2];
    int centerX = 641;
    int centerY = 359;

    Garbageman game;

    public CraftingScreen(Garbageman game){
        this.game = game;
    }

    @Override
    public void show() {

        background.toBack();
        background.setVisible(true);
        background.setHeight(stage.getHeight());
        background.setWidth(stage.getWidth());
        stage.addActor(background);

        craftingLocs[0] = new int[]{377, 222};
        craftingLocs[1] = new int[]{904, 221};
        craftingLocs[2] = new int[]{1081, 348};
        craftingLocs[3] = new int[]{1014, 544};
        craftingLocs[4] = new int[]{819, 596};
        craftingLocs[5] = new int[]{451, 599};
        craftingLocs[6] = new int[]{252, 534};
        craftingLocs[7] = new int[]{193, 349};

        ArrayList<Trash> trashes = new ArrayList<Trash>();

        for (int i = 0; i < craftingLocs.length; i++) {
            Trashcan tr = new Trashcan(game);


            Random rand = new Random();
            int x = rand.nextInt(tr.garbageItems.length-1);
            Trash crow = tr.makeRandGarbage(x);

            crow.setName(Integer.toString(i));
            trashes.add(crow);
            stage.addActor(trashes.get(i));
            trashes.get(i).setImg();
            trashes.get(i).setVisible(true);
            trashes.get(i).setSize(96, 96);
            trashes.get(i).toFront();
            trashes.get(i).setX(craftingLocs[i][0] - 50);
            trashes.get(i).setY(stage.getHeight() - craftingLocs[i][1] - 65);
        }


    }

    public CookedFood cook(CookedFood input, Trash[] trashes){
        try{

        }
        catch (){

        }


        return input;
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
