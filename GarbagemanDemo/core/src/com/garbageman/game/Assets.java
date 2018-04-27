package com.garbageman.game;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.garbage.Trash;

import org.lwjgl.opencl.CL;

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
        initializeGarbage();
    }

    private static void initializeGarbage(){
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


        newTexture("background", "assets/Screens/dumpster1.png");
    }


    public static void newTexture(String storageName, String tex){
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

}
