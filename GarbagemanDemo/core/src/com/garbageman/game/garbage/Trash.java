package com.garbageman.game.garbage;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.PassTrash;
import com.garbageman.game.SpriteSheetDivider;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Trash extends Image{


    //Ingredient types
    public static final int UNUSABLE = 0;
    public static final int MEAT = 1;
    public static final int VEGGIE = 2;
    public static final int WRAP = 3;
    public static final int FILLER = 4;
    public static final int SWEETENER = 5;
    public static final int SAUCE = 6;
    public static final int ANYTHING = 7;
    public static final int NONE = -1;
    public static final int ALLFOODTYPES = PassTrash.allFoodTypes;

    //Rarity values
    public static final int COMMON = 0;
    public static final int COM = 0;
    public static final  int UNCOMMON = 1;
    public static final  int UNCOM = 1;
    public static final  int UNC = 1;
    public static final  int RARE = 2;
    public static final  int VRARE = 3;
    public static final  int VERYRARE = 3;
    public static final  int LEGENDARY = 4;
    public static final  int BOUGHT = 5;
    public static final  int BEYOND_COMPREHENSION = 6;

    public String name = "Bug";
    public String baseImgName = "assets/Garbage/";
    public String fileType = ".png";
    public String img = "error";
    public String desc = "Item is missing description";
    public String desc2 = "";
    public String desc3 = "";
    public int x,y = 0;
    public int width,height = 32;
    public int type = UNUSABLE;
    public int rarity = COMMON;
    //Nastiness is on a scale of 1-100
    //1 is fresh, 100 is foul
    public int nast = 1;
    public static final int MLTHRESH = 34;
    public static final int HMTHRESH = 67;
    public static final int MAXNAST = 100;
    public static final int MINNAST = 1;

    //Ingredient value multipliers
    public static final double COM_MULTIPLIER = 1;
    public static final double UNCOM_MULTIPLIER = 1.5;
    public static final double RARE_MULTIPLIER = 2;
    public static final double VRARE_MULTIPLIER = 2.5;
    public static final double LEGENDARY_MULTIPLIER = 3;
    public static final double BOUGHT_MULTIPLIER = 1.5;
    public static final double BEYOND_COMPREHENSION_MULTIPLIER = 5;


    public boolean selectedInInv = false;

    public boolean selected = false;

    public void setImg(){
        SpriteSheetDivider sp = new SpriteSheetDivider();
        this.setDrawable(sp.divideGarbage(this, this.img));
    }

    public void setImg(String name){
        SpriteSheetDivider sp = new SpriteSheetDivider();
        this.setDrawable(sp.divideGarbage(this, name));
    }

    public void setSelectedInInv(boolean sel){
        this.selectedInInv = sel;
    }

    public boolean getSelectedInInv(){
        return this.selectedInInv;
    }

    public void setNast(int newNast){
        nast = newNast;
        setImg();
    }

    public String getRarity(int in){
        String str = "";
        switch (in){
            case 1:
                str = "Uncommon";
                break;
            case 2:
                str = "Rare";
                break;
            case 3:
                str = "Very Rare";
                break;
            case 4:
                str = "Legendary";
                break;
            case 5:
                str = "Bought";
                break;
            case 6:
                str = "???";
                break;
            default:
                str = "Common";
                break;
        }
        return str;
    }

    public String getRarity(){
        String str = "";
        switch (rarity){
            case 1:
                str = "Uncommon";
                break;
            case 2:
                str = "Rare";
                break;
            case 3:
                str = "Very Rare";
                break;
            case 4:
                str = "Legendary";
                break;
            case 5:
                str = "Bought";
                break;
            case 6:
                str = "???";
                break;
            default:
                str = "Common";
                break;
        }
        return str;
    }

    public double getMultiplier(){
        double i = 0;
        switch (rarity){
            case 1:
                i = UNCOM_MULTIPLIER;
                break;
            case 2:
                i = RARE_MULTIPLIER;
                break;
            case 3:
                i = VRARE_MULTIPLIER;
                break;
            case 4:
                i = LEGENDARY_MULTIPLIER;
                break;
            case 5:
                i = BOUGHT_MULTIPLIER;
                break;
            case 6:
                i = BEYOND_COMPREHENSION_MULTIPLIER;
                break;
            default:
                i = COM_MULTIPLIER;
                break;
        }
        return i;
    }

}
