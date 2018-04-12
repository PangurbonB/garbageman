package com.garbageman.game;

import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 4/2/2018.
 */

public class PassTrash {
    public ArrayList<Trash> currentCooking = new ArrayList<Trash>();
    public Trash currentTrashCooking;
    public int currentTypeToAdd = -20;
    public static int allFoodTypes = 1200;
    Garbageman game;

    public PassTrash(Garbageman game){
        this.game = game;
    }

    public void addTrash(int index){
        if (game.backpack.contents.size()< index) {
            Trash item = game.backpack.contents.get(index);
            currentCooking.add(item);
            game.backpack.remove(index);
        }
    }

    public boolean findTrash(){
        return true;
    }

    public void removeTrash(int index){
        if (currentCooking.size()< index) {
            Trash item = currentCooking.get(index);
            game.backpack.add(item);
            currentCooking.remove(index);
        }
    }
}
