package com.garbageman.game.garbage;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Trash extends Actor{

    //Ingredient types
    public final int UNUSABLE = 0;
    public final int MEAT = 1;
    public final int VEGGIE = 2;
    public final int WRAP = 3;
    public final int FILLER = 4;
    public final int SWEETENER = 5;
    public final int SAUCE = 6;

    //Rarity values
    public final int COMMON = 0;
    public final int COM = 0;
    public final int UNCOMMON = 1;
    public final int UNCOM = 1;
    public final int UNC = 1;
    public final int RARE = 2;
    public final int VRARE = 3;
    public final int VERYRARE = 3;
    public final int LEGENDARY = 4;
    public final int BOUGHT = 5;



    public String name = "Bug";
    public String baseImgName = "assets/Garbage/";
    public String fileType = ".png";
    public String img = "error";
    public String desc = "Item is missing description";
    public int x,y = 0;
    public int width,height = 32;
    public boolean inBag = false;
    public int type = UNUSABLE;
    public int rarity = COMMON;
    //Nastiness is on a scale of 1-100
    //1 is fresh, 100 is foul
    public int nast = 1;

    public boolean selected = false;

}
