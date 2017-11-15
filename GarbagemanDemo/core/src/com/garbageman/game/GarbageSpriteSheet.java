package com.garbageman.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Random;

/**
 * Created by bzonick5979 on 11/15/2017.
 */

public class GarbageSpriteSheet {
    public static final int WIDTH = 4;

    public static final int TRASH_TOTAL = 15;

    private static int xy(int x, int y){
        return x + WIDTH*y;
    }

    public static Image randomPiece(){
        Random rand = new Random();
        int x = rand.nextInt(TRASH_TOTAL%4);
        int y = rand.nextInt(4);
        while (true) {
            if (xy(x, y) > TRASH_TOTAL-1) {
                x = rand.nextInt(TRASH_TOTAL%4);
                y = rand.nextInt(4);
            }
            else{
                break;
            }
        }

        Skin skin = new Skin();
        TextureRegion temp = new TextureRegion(new Texture("assets/Garbage/genericGarbage.png"), x*WIDTH, y*WIDTH, x*WIDTH+32, x*WIDTH+32);
        Texture tex = temp.getTexture();
        skin.add("temp", tex);
        Image img = new Image(skin, "temp");
        return img;
    }

}
