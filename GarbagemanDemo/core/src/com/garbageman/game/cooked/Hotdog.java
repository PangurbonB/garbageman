package com.garbageman.game.cooked;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Hotdog extends CookedFood{

    {
        name = "Hotdog";
        desc = "You're pretty sure that there's no actual dog in this. Right?";
        reqTypes = new int[] {t.MEAT, t.WRAP, t.SAUCE};
        optionalTypes = new int[] {t.MEAT, t.SAUCE, t.NONE, t.NONE, t.NONE};
    }

}
