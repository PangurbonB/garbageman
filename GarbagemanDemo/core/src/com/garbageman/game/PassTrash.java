package com.garbageman.game;

import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 4/2/2018.
 */

public class PassTrash {
    public Trash[] currentCooking = new Trash[8];
    public Trash currentTrashCooking;
    public int currentTypeToAdd = -20;
    public final static int ALLFOODTYPES = 1200;
    public int selectedIndex = -1;
    Garbageman game;

    public PassTrash(Garbageman game){
        this.game = game;
    }

    public void addTrash(int indexBackpack){
        System.out.println("INDEX BACK " +indexBackpack);
        if (game.backpack.contents.size() > indexBackpack) {
            Trash item = game.backpack.contents.get(indexBackpack);
            currentCooking[selectedIndex] = item;
            game.backpack.remove(indexBackpack);
            System.out.println("!!!!!!!CHECK: "+indexBackpack+" ;; "+ item.name);
        }

        for (int i = 0; i < currentCooking.length; i++) {
            try {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + currentCooking[i].name);
            }
            catch (NullPointerException e){

            }
        }
    }

    public boolean findTrash(){
        return true;
    }

    public void removeTrash(int indexToAdd){
        if (currentCooking.length < indexToAdd) {
            Trash item = currentCooking[indexToAdd];
            game.backpack.add(item);
            currentCooking[indexToAdd] = null;
        }
    }
}
