package com.garbageman.game.cooked;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Pizza extends CookedFood{

    {
        name = "Pizza";
        desc = "Can you truly put anything on pizza?";
        reqTypes = new int[] {t.WRAP, t.FILLER, t.SAUCE};
        optionalTypes = new int[] {t.FILLER, t.MEAT, t.VEGGIE, t.ANYTHING, t.NONE};
    }

}
