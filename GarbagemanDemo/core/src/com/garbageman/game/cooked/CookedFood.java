package com.garbageman.game.cooked;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class CookedFood extends Image{

    protected Trash t = new Trash();
    public String name = "default";
    public ArrayList<Trash> ingredients = new ArrayList<Trash>();
    public int quality = 0;
    public String desc = "No description yet";
    public int[] reqTypes = {-1, -1, -1};
    public int[] optionalTypes = {-1, -1, -1, -1, -1};
}
