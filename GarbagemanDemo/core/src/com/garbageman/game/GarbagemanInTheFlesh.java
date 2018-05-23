package com.garbageman.game;

import com.garbageman.game.customers.Customer;

/**
 * Created by bzonick5979 on 5/23/2018.
 */

public class GarbagemanInTheFlesh extends Customer{

    {
        fileName = "garbageManMirror2";
        customerName = "You";
        LOCAL_MIN = LOCAL_MIN;
        LOCAL_MAX = LOCAL_MAX;
    }

    @Override
    public void setImg(){
        SpriteSheetDivider sp = new SpriteSheetDivider();
        this.setDrawable(sp.divideScreen("garbageManMirror2", 0));
    }
}
