package com.garbageman.game.cooked;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Sandwich extends CookedFood{

    {
        name = "Sandwich";
        img = name;
        desc  = "Can it be a sandwich if it doesn't use bread?";
        reqTypes = new int[] {t.MEAT, t.WRAP, t.FILLER};
        optionalTypes = new int[] {t.SAUCE, t.VEGGIE, t.NONE, t.NONE, t.NONE};
    }

}
