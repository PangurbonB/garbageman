package com.garbageman.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Backpack extends Actor{

    public int vertSlots = 5;
    public int horizSlots = 4;
    public int totalSlots = vertSlots * horizSlots;

    public ArrayList<Trash> contents = new ArrayList<Trash>();
    public int width = 400;
    public int height = 400;

    public Backpack(){
        this.setWidth(width);
        this.setHeight(height);
    }

    public boolean add(Trash item){
        boolean success = false;
        if (this.contents.size() < this.totalSlots){
            contents.add(item);
            success = true;

        }
        return success;
    }

    public int getIndex(String findObj){
        int index = 0;
        for (int i = 0; i < contents.size(); i++) {
            if (contents.get(i).name.equals(findObj)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public boolean remove(int index){
        boolean success = false;
        if (contents.size()<= index) {
            contents.remove(index);
            success = true;
        }
        return success;
    }

    public boolean remove(String itemName){
        boolean success = false;
        int index = getIndex(itemName);
        if (index != 0){
            remove(index);
        }
        return success;
    }

}

