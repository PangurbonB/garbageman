package com.garbageman.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Backpack extends Actor{

    public int vertSlots = 5;
    public int horizSlots = 4;
    public int totalSlots = vertSlots * horizSlots;

    public ArrayList<Image> contents = new ArrayList<Image>();
    public int width = 400;
    public int height = 400;

    public Backpack(){
        this.setWidth(width);
        this.setHeight(height);
    }

}

