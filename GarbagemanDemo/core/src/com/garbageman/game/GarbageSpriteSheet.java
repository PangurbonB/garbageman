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

        int x = rand.nextInt(TRASH_TOTAL%WIDTH);
        int y = rand.nextInt(TRASH_TOTAL/WIDTH);
        while (true) {
            if (xy(x, y) > TRASH_TOTAL-1) {
                x = rand.nextInt(TRASH_TOTAL%WIDTH);
                y = rand.nextInt(TRASH_TOTAL/WIDTH);
            }
            else{
                break;
            }
        }

        Skin skin = new Skin();
        TextureRegion temp = new TextureRegion();
        temp.setTexture(new Texture("assets/Garbage/genericGarbage.png"));
        temp.setRegion(x*32, y*32, (32), (32));
        Image img = new Image(temp);
        return img;
    }

}
