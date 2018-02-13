package com.garbageman.game.cooked;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Sushi extends CookedFood{

    {
        name = "Sushi";
        desc = "The raw fish is probably ok because it is most likely not fish";
        reqTypes = new int[] {t.MEAT, t.VEGGIE, t.FILLER};
        optionalTypes = new int[] {t.FILLER, t.MEAT, t.VEGGIE, t.NONE, t.NONE};
    }

}
