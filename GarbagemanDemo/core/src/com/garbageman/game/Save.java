package com.garbageman.game;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.screens.RestaurantScreen;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



/**
 * Created by bzonick5979 on 5/3/2018.
 */

public class Save {

    static JSONObject inventory = new JSONObject();
    static JSONArray backpackContents = new JSONArray();
    static JSONParser parser = new JSONParser();
    static JSONObject parsedObject = null;
    static private Garbageman game;

    public Save(Garbageman game){
        this.game = game;
        try {
            JSONObject parsedObject = (JSONObject) parser.parse(new FileReader("Save1.json"));
            this.parsedObject = parsedObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static void save(){
        JSONObject customerAttributes = new JSONObject();
        // picky: int
        // name: String
        // placeInLine: int
        // order: CookedFood

        for (Customer i : Customer.listOfCustomers){
            customerAttributes.put("Picky", i.picky);
            customerAttributes.put("Name", i.customerName);
            customerAttributes.put("PlaceInLine", Customer.listOfCustomers.indexOf(i));
            customerAttributes.put("Order", i.order);
        }

        for (Trash i : game.backpack.contents) {
            System.out.println("?????????????????????????????"+i.getName());
            ArrayList<String> trashInfo = new ArrayList<String>();
            trashInfo.add(String.valueOf(i.isCookedFood));
            trashInfo.add(i.img);
            trashInfo.add(String.valueOf(i.nast));
            trashInfo.add(String.valueOf(i.containsCrowWithOddEyeInfection));
            if (i instanceof CookedFood){
                trashInfo.add(String.valueOf(((CookedFood) i).sellValue));
            }
            else{
                trashInfo.add("0");
            }

            backpackContents.add(trashInfo);
        }



        JSONObject savingObject = new JSONObject();
        savingObject.put("Backpack", backpackContents);
        savingObject.put("Reputation", game.reputation);
        savingObject.put("Money", game.money);
        savingObject.put("Customers", customerAttributes);

        try {
            FileWriter file = new FileWriter("Save1.json");
            file.write(savingObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<String>> loadBackpack(){
        if (parsedObject != null) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!" + parsedObject);
            JSONArray items = (JSONArray) parsedObject.get("Backpack");
            ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
            if (parsedObject.size() != 0) {
                try{
                    for (Object i : items) {
                        ret.add((ArrayList<String>) i);
                    }
                } catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
            return ret;
        }
        else{
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!! PARSEDOBJECT IS NULL");
            return null;
        }
    }

    /*public static int loadRep(){
        if (parsedObject != null){

        }
    }*/

}