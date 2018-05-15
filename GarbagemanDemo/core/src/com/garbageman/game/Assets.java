package com.garbageman.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.garbage.BagOfSugar;
import com.garbageman.game.garbage.Bean;
import com.garbageman.game.garbage.Bread;
import com.garbageman.game.garbage.Ketchup;
import com.garbageman.game.garbage.Pork;
import com.garbageman.game.garbage.Salad;
import com.garbageman.game.garbage.Trash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by bzonick5979 on 4/25/2018.
 */

public class Assets {

    Garbageman game;

    private static Map<String, Texture> textures = Collections.synchronizedMap(new HashMap());
    public static ArrayList<BitmapFont> bitmapFonts = new ArrayList<BitmapFont>();
    public static ArrayList<Skin> skins = new ArrayList<Skin>();

    public Assets(Garbageman game){
        this.game = game;
        initTextures();
    }

    private void initTextures(){

        initGarbage();
        initCustomers();
        initFood();
        initScreens();
        initButtons();

        //Initialization for generic clutter/junk items. Did not see a point to making a method for this.
        newTexture("genericGarbage", "assets/Garbage/genericGarbage.png");
    }

    protected static void newTexture(String storageName, String tex){
        Texture texture = new Texture(tex);
        textures.put(storageName, texture);
    }

    public static Texture findTexture(String storageName){
        return textures.get(storageName);
    }

    public static BitmapFont newBitmapFont(int size){
        return null;
    }

    public static Skin newSkin(String name, Texture texToAdd){
        Skin skin = new Skin();
        skin.add(name, texToAdd);
        skins.add(skin);
        return skin;
    }

    public static Skin newSkin(){
        Skin skin = new Skin();
        skins.add(skin);
        return skin;
    }

    public static Skin newSkin(FileHandle fh){
        Skin skin = new Skin(fh);
        skins.add(skin);
        return skin;
    }

    public static void dispose(){
        for (int i = (textures.size()-1); i >=0; i--) {
            textures.remove(textures.get(i));
            System.out.println(textures.size());
        }
        for (BitmapFont i : bitmapFonts){
            i.dispose();
        }
        for (Skin i : skins){
            i.dispose();
        }
    }



    //Initialization statements for Buttons, Customers, Cooked Food items, Trash items, and backgrounds



    private void initButtons(){
        newTexture("whiteBlank", "assets/Buttons/whiteBlank.png");
        newTexture("playButtonMenu", "assets/playButton.png");
        newTexture("playButtonActiveMenu", "assets/playButtonActive.png");
        newTexture("exitButtonMenu", "assets/exitButton.png");
    }

    private void initCustomers(){
        for (Class i : Garbageman.customers) {
            try {
                Customer item = (Customer) Class.forName(i.getName()).newInstance();
                newTexture(item.customerName, item.fileLocation+ item.fileName + item.fileType);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFood(){
        for (Class i : Garbageman.foodItems) {
            try {
                CookedFood item = (CookedFood) Class.forName(i.getName()).newInstance();
                newTexture(item.name, "assets/Food/" + item.name.toLowerCase() +  item.fileType);
                newTexture(item.name+"Ghost", "assets/Food/"+item.name.toLowerCase()+"Ghost"+item.fileType);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initGarbage(){
        for (Class i : Garbageman.garbageItems) {
            try {
                Trash item = (Trash) Class.forName(i.getName()).newInstance();
                newTexture(item.name, item.getSpawnName());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Trash item = new BagOfSugar();
        newTexture(item.name+"Ghost", item.baseImgName + item.img + "Ghost" + item.fileType);
        item = new Bean();
        newTexture(item.name+"Ghost", item.baseImgName + item.img + "Ghost" + item.fileType);
        item = new Bread();
        newTexture(item.name+"Ghost", item.baseImgName + item.img + "Ghost" + item.fileType);
        item = new Ketchup();
        newTexture(item.name+"Ghost", item.baseImgName + item.img + "Ghost" + item.fileType);
        item = new Pork();
        newTexture(item.name+"Ghost", item.baseImgName + item.img + "Ghost" + item.fileType);
        item = new Salad();
        newTexture(item.name+"Ghost", item.baseImgName + item.img + "Ghost" + item.fileType);
        newTexture("CLEAR", item.baseImgName + "CLEAR" + item.fileType);
    }

    private void initScreens(){
        newTexture("background", "assets/Screens/dumpster1.png");
        newTexture("backpackRestaurant", "assets/Screens/TrashBackpackRestaurantCrop.png");
        newTexture("cookingScreen", "assets/Screens/craftingScreen2.png");
        newTexture("smallInv", "assets/Screens/smallInv.png");
        newTexture("inventory", "assets/Screens/Inventory.png");
        newTexture("shopScreenCrop", "assets/Screens/shopScreenCrop.png");
        newTexture("restBack", "assets/Screens/Restaurant.png");
    }

}
