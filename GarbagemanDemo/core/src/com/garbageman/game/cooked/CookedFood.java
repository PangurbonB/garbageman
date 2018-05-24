package com.garbageman.game.cooked;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.SpriteSheetDivider;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class CookedFood extends Trash{

    {
        name = "default";
        desc = "A Burrito";
        isCookedFood = true;
    }

    protected Trash t = new Trash();
    public ArrayList<Trash> ingredients = new ArrayList<Trash>();
    public int quality = 0;
    public int[] reqTypes = {-1, -1, -1};
    public int[] optionalTypes = {-1, -1, -1, -1, -1};

    public boolean increment = false;

    public final int baseSellPrice = 5;

    public double sellValue = baseSellPrice;

    public boolean containsCrowWithOddEyeInfection = false;

}
