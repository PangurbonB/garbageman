package com.garbageman.game.cooked;

import com.garbageman.game.garbage.Trash;

/**
 * Created by bzonick5979 on 2/5/2018.
 */

public class Burrito extends CookedFood{

    {
        name = "Burrito";
        img = name;
        desc = "Nobody knows what's in these anyway, right?";
        reqTypes = new int[]{t.WRAP, t.MEAT, t.VEGGIE};
        optionalTypes = new int[]{t.SAUCE, t.MEAT, t.VEGGIE, t.NONE,t.NONE};

    }
}
