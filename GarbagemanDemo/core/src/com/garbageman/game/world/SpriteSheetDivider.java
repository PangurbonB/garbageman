package com.garbageman.game.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.garbageman.game.garbage.Trash;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpriteSheetDivider {

    /*
        InfoList contents:
        0: Cell Width  === Keep Float
        1: Cell Height === Keep Float
        2: Cells Wide  >>> To int
        3: Total Cells >>> To int
     */

    public static final Map<String, Float[]> infoMap = Collections.synchronizedMap(new HashMap());

    public static final int WIDTH = 4;

    public static final int TRASH_TOTAL = 15;

    public SpriteSheetDivider(){
        Float[] temp;
        infoMap.put("Trashcans", new Float[]{384f, 216f, 1f, 1f});    ///
        infoMap.put("TitleScreen", new Float[]{384f, 216f, 1f, 1f}); ///
        infoMap.put("Inventory", new Float[]{384f, 216f, 4f, 6f});   ///
        infoMap.put("SmallInv", new Float[]{96f, 216f, 4f, 5f});    ///
    }

    private int xy(int x, int y){
        return x + WIDTH*y;
    }

    public TextureRegionDrawable divideItem(String itemName, int place){
        Float[] currInfo = infoMap.get(itemName);

        int x = place % Math.round(currInfo[2]) +1;

        int y = (int)((place - place%x)/currInfo[2]);
        x--;

        TextureRegion temp = new TextureRegion();
        temp.setTexture(new Texture("assets/Screens/"+itemName+".png"));
        temp.setRegion(Math.round((currInfo[0])*x), Math.round((currInfo[1])*y), Math.round((currInfo[0])), Math.round((currInfo[1])));
        System.out.println((currInfo[0])*x+"+"+(currInfo[1])*y+"+"+(currInfo[0])+"+"+(currInfo[1]));
        TextureRegionDrawable temp1 = new TextureRegionDrawable();
        temp1.setRegion(temp);
        return temp1;
    }

    public Trash randomPiece(){
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
        TextureRegionDrawable temp1 = new TextureRegionDrawable();
        temp1.setRegion(temp);
        Trash img = new Trash();
        img.setDrawable(temp1);
        return img;
    }
}
