package com.garbageman.game;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.garbage.Trash;

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

    public static void save(Backpack backpack){

        for (Trash i : backpack.contents) {
            ArrayList<String> trashInfo = new ArrayList<String>();
            trashInfo.add(String.valueOf(i.isCookedFood));
            trashInfo.add(i.getName());
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



        try {
            FileWriter file = new FileWriter("Save1.json");
            file.write(backpackContents.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ArrayList<String>> load(){
        try {
            JSONArray obj = (JSONArray) parser.parse(new FileReader("Save1.json"));
            System.out.println(obj);
            ArrayList<String> e = (ArrayList) obj.get(0);
            ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>>();
            for (Object i : obj) {
                ret.add((ArrayList<String>) i);
            }
            return ret;

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}