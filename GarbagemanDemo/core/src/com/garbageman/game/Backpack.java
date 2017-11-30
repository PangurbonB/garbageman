package com.garbageman.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.garbage.Trash;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Backpack extends Actor{

    public int vertSlots = 5;
    public int horizSlots = 4;
    public int totalSlots = vertSlots * horizSlots;

    public Image[] contents = new Image[vertSlots*horizSlots];

    public final int width = 400;
    public final int height = 400;



    public Backpack(){
        for (int i = 0; i < contents.length; i++) {
            contents[i] = null;
        }
    }

}
