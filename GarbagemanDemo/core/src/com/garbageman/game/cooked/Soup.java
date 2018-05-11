package com.garbageman.game.cooked;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Soup extends CookedFood{

    {
        name = "Soup";
        img = name;
        desc = "Just ignore the floating debris and it's alright";
        reqTypes = new int[] {t.VEGGIE, t.SAUCE, t.FILLER};
        optionalTypes = new int[] {t.MEAT, t.VEGGIE, t.VEGGIE, t.SAUCE, t.NONE};
    }

}
