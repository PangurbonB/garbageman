package com.garbageman.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.customers.Duy;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
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
        infoMap.put("trashcans", new Float[]{384f, 216f, 1f, 1f});    ///
        infoMap.put("titleScreen", new Float[]{384f, 216f, 1f, 1f}); ///
        infoMap.put("inventory", new Float[]{384f, 216f, 4f, 6f});   ///
        infoMap.put("smallInv", new Float[]{96f, 216f, 4f, 5f});    ///

    }

    private int xy(int x, int y){
        return x + WIDTH*y;
    }

    public TextureRegionDrawable divideScreen(String itemName, int place){
        System.out.println(itemName);
        Float[] currInfo = infoMap.get(itemName);
        //System.out.println(currInfo.length);
        int x = place % Math.round(currInfo[2]) +1;

        int y = (int)((place - place%x)/currInfo[2]);
        x--;

        TextureRegion temp = new TextureRegion();
        temp.setTexture(Assets.findTexture(itemName));
        temp.setRegion(Math.round((currInfo[0])*x), Math.round((currInfo[1])*y), Math.round((currInfo[0])), Math.round((currInfo[1])));
        //System.out.println((currInfo[0])*x+"+"+(currInfo[1])*y+"+"+(currInfo[0])+"+"+(currInfo[1]));
        TextureRegionDrawable temp1 = new TextureRegionDrawable();
        temp1.setRegion(temp);
        return temp1;
    }

    public TextureRegionDrawable divideGarbage(Trash item, String name) {
        TextureRegion temp = new TextureRegion();
        CrowWithOddEyeInfection crow = new CrowWithOddEyeInfection();
        try {
            temp.setTexture(Assets.findTexture(item.name));
        }
        catch (GdxRuntimeException e){
            e.printStackTrace();
            CrowWithOddEyeInfection c = new CrowWithOddEyeInfection();
            temp.setTexture(Assets.findTexture(crow.name));
        }
        if (temp.getTexture().getTextureData().getWidth() == 96) {
            if (item.nast >= item.HMTHRESH) {
                temp.setRegion(64, 0, 32, 32);
            } else if (item.nast >= item.MLTHRESH) {
                temp.setRegion(32, 0, 32, 32);
            } else {
                temp.setRegion(0, 0, 32, 32);
            }
            TextureRegionDrawable temp1 = new TextureRegionDrawable();
            temp1.setRegion(temp);
            return temp1;
        }
        else{
            TextureRegionDrawable temp1 = new TextureRegionDrawable();
            temp1.setRegion(temp);
            return temp1;
        }
    }

    public TextureRegionDrawable divideCustomer(Customer customer, String name, int indexX, int indexY) {
        TextureRegion temp = new TextureRegion();
        try {
            temp.setTexture(Assets.findTexture(customer.customerName));
        }
        catch (GdxRuntimeException e){
            e.printStackTrace();
            Duy j = new Duy();
            temp.setTexture(Assets.findTexture(j.customerName));
        }
        try {
            if (!(indexX * 32 >= temp.getTexture().getTextureData().getWidth()) && !(indexY*32 >= temp.getTexture().getTextureData().getHeight())) {
                temp.setRegion(indexX * 128, indexY * 128, 128, 128);
                TextureRegionDrawable temp1 = new TextureRegionDrawable();
                temp1.setRegion(temp);
                return temp1;
            }
            else{
                temp.setRegion(0,0,32,32);
                TextureRegionDrawable temp1 = new TextureRegionDrawable();
                temp1.setRegion(temp);
                return temp1;
            }
        }
        catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            temp.setRegion(0,0,32,32);
            TextureRegionDrawable temp1 = new TextureRegionDrawable();
            temp1.setRegion(temp);
            return temp1;
        }
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

        TextureRegion temp = new TextureRegion();
        temp.setTexture(Assets.findTexture("genericGarbage"));
        temp.setRegion(x*32, y*32, (32), (32));
        TextureRegionDrawable temp1 = new TextureRegionDrawable();
        temp1.setRegion(temp);
        Trash img = new Trash();
        img.setDrawable(temp1);
        return img;
    }
}
