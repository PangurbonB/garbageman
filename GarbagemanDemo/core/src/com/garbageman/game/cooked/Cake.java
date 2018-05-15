package com.garbageman.game.cooked;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Cake extends CookedFood{

    {
        name = "Cake";
        img = name;
        desc = "The frosting is... hard";
        reqTypes = new int[] {t.FILLER, t.SWEETENER, t.SAUCE};
        optionalTypes = new int[] {t.SWEETENER, t.SWEETENER, t.FILLER, t.NONE, t.NONE};
    }

}
