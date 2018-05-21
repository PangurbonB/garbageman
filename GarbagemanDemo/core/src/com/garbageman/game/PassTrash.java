package com.garbageman.game;

import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 4/2/2018.
 */

public class PassTrash {
    public static Trash[] currentCooking = new Trash[8];
    public Trash currentTrashCooking;
    public static int place = 0;
    public int currentTypeToAdd = -20;
    public final static int ALLFOODTYPES = 1200;
    public int selectedIndex = -1;
    public static CookedFood orderToGive = null;
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
            //System.out.println("!!!!!!!CHECK: "+indexBackpack+" ;; "+ item.name);
        }

        for (int i = 0; i < currentCooking.length; i++) {
            try {
                //System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + currentCooking[i].name);
            }
            catch (NullPointerException e){

            }
        }
    }

    public boolean findTrash(Trash item){
        boolean found = false;
        for (int x = 0; x < currentCooking.length; x++) {
            try {
                Trash check = currentCooking[x];
                if (check.equals(item)) {
                    found = true;
                    break;
                }
            }
            catch (NullPointerException e){

            }
        }
        return found;
    }

    public void dumpTrash(){
        for (int x = 0; x < currentCooking.length; x++){
            try{
                currentCooking[x].setVisible(false);
                currentCooking[x]= null;
            }
            catch (NullPointerException e){

            }
        }
    }

    public void removeTrash(int indexToAdd){
        if (currentCooking.length < indexToAdd) {
            Trash item = currentCooking[indexToAdd];
            game.backpack.add(item);
            currentCooking[indexToAdd] = null;
        }
    }

    public CookedFood cookFood(CookedFood emptyFood, ArrayList<Trash> trashes){
        System.out.println("IT GOT COOKED");
        //dumpTrash();

        int totalPrice = 0;
        int nastSum = 0;
        int totalVals = 0;

        emptyFood.containsCrowWithOddEyeInfection = false;

        for (Trash i : trashes) {
            if (!i.isGhost){
                i.setPrice();
                totalPrice += i.sellPrice;
                nastSum += i.nast;
                totalVals++;

                if (i instanceof CrowWithOddEyeInfection){
                    emptyFood.containsCrowWithOddEyeInfection = true;
                }

                for (int x = 0; x < game.backpack.contents.size(); x++){
                    Trash t = game.backpack.contents.get(x);
                    if (t.equals(i)){
                        game.backpack.contents.remove(x);
                    }
                }
            }
        }

        int nastAvg = nastSum/totalVals;

        System.out.println("NAST VALUE OF "+emptyFood.name+":  "+nastAvg);

        emptyFood.nast = nastAvg;
        emptyFood.sellValue = totalPrice;




        return emptyFood;
    }
}
