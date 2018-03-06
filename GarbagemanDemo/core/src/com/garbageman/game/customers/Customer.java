package com.garbageman.game.customers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 3/6/2018.
 */

public class Customer extends Image {

    //customer pickiness: 100 is high, 1 is low, the more picky a customer is, the more they care about their food and the more their review matters
    public int picky = 1;
    public static int PICKY_MAX = 100;
    public static int PICKY_MIN = 1;

    public String fileType = ".png";
    public String fileLocation = "assets/Garbage/";
    public String fileName = "error";
}
