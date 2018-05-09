package com.garbageman.game;
import com.garbageman.game.garbage.Trash;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;


/**
 * Created by bzonick5979 on 5/3/2018.
 */

public class Save {

    static JSONObject inventory = new JSONObject();
    static JSONObject backpackObject = new JSONObject();

    public static void save(Backpack backpack){
        backpackObject.put("backpack", backpack);
        inventory.put("backpack", backpack.contents);


        try {
            FileWriter file = new FileWriter("Save1.json");
            file.write(inventory.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Backpack load(){
        return (Backpack) backpackObject.get("backpack");
    }

}
