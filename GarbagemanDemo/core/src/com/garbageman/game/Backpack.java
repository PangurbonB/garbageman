package com.garbageman.game;

import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by bzonick5979 on 9/27/2017.
 */

public class Backpack extends Actor{

    private int vertSlots = 5;
    private int horizSlots = 4;
    public int totalSlots = vertSlots * horizSlots;

    public ArrayList<Trash> contents = new ArrayList<Trash>();
    protected ArrayList<Trash> backupContents = new ArrayList<Trash>();
    public int width = 300;
    public int height = 400;

    Backpack(){
        this.setWidth(width);
        this.setHeight(height);
    }

    public boolean finalizeCooking(ArrayList<Trash>toCook, Trash thingToCook, ArrayList<Trash>currentInv){//true means it worked, false means there was a problem and it reverted
        boolean didItWork = false;

        return didItWork;
    }

    public Trash getRandom(){
        return this.contents.get(new Random().nextInt(this.contents.size() + 1));
    }

    public boolean add(Trash item){
        boolean success = false;
        if (this.contents.size() < this.totalSlots){
            contents.add(item);
            backupContents.add(item);
            success = true;
            //System.out.println("Worked: "+item.img);
        }
        else{
            System.out.println(this.contents.size()+"     "+this.totalSlots);
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
            backupContents.remove(index);
            success = true;
        }
        return success;
    }

    public boolean remove(String itemName){
        int index = getIndex(itemName);
        if (index != 0){
            remove(index);
            return true;
        }
        else{
            return false;
        }

    }

}

