package com.garbageman.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.garbageman.game.Garbageman;
import com.garbageman.game.garbage.AppleCore;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
import com.garbageman.game.garbage.McdFries;
import com.garbageman.game.garbage.Pork;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.world.GestureHandler;
import com.garbageman.game.world.InputHandler;
import com.sun.org.apache.xpath.internal.operations.Lt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Trashcan implements Screen {



    Garbageman game;
    SpriteBatch batch;
    Skin skin;

    int incVal = 0;

    final McdFries fries = new McdFries();
    Table table;
    String str = fries.baseImgName + fries.img + fries.fileType;
    Image img1 = new Image();
    Image img2 = new Image();
    Image img3 = new Image();
    boolean consoleOpen = false;
    Skin txtSkin = new Skin(Gdx.files.internal("uiskin.json"));
    TextField text = new TextField("", txtSkin);

    Label Ltext;
    Label.LabelStyle textStyle;

    //table.add(img);
    Camera camera;

    boolean wasTouched = false;
    int consoleIndex = 0;

    Map<String, Float> velMap = Collections.synchronizedMap(new HashMap());
    Map<String, Float> oldLocMap = Collections.synchronizedMap(new HashMap());
    ArrayList<Image> imgs = new ArrayList();
    ArrayList<String> consoleLog = new ArrayList<String>();

    BitmapFont font = new BitmapFont();

    boolean addNums = false;

    int countFrame = 0;
    int x,y = 0;

    float friction = .1f;





    Stage stage = new Stage();

    //private Viewport viewport;

    public Trashcan (Garbageman game){

        InputProcessor inputProcessorOne = new InputHandler();
        InputProcessor inputProcessorTwo = new GestureDetector(new GestureHandler());
        //InputProcessor inputProcessorthree = new K
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessorOne);
        inputMultiplexer.addProcessor(inputProcessorTwo);
        Gdx.input.setInputProcessor(inputMultiplexer);


        this.game = game;
        this.stage = stage;
        this.batch = batch;
        this.font = font;

        consoleLog.add("");



        //velMap.put("y"+img.getName(), 0f);
        //velMap.put("x"+img.getName(), 0f);

        fries.setSize(32, 32);

    }


    public Image makeGarbage(String name){
        Image img;
        Skin skin1 = new Skin();
        skin1.add("mcdFries", new Texture(name));
        img = new Image(skin1, "mcdFries");
        return img;
    }

    public void makeSoftGarbage(String name){
        try {
            Image img;
            Trash trash = new Trash();
            Skin skin1 = new Skin();
            skin1.add("mcdFries", new Texture(trash.baseImgName + name + trash.fileType));
            imgs.add(new Image(skin1, "mcdFries"));
            imgs.get(imgs.size() - 1).setName(Integer.toString(imgs.size()));
            velMap.put(imgs.get(imgs.size() - 1).getName() + "x", 0f);
            velMap.put(imgs.get(imgs.size() - 1).getName() + "y", 0f);
            imgs.get(imgs.size() - 1).toBack();
        }
        catch (GdxRuntimeException e){
            System.out.println("Invalid item spawn");
        }
    }

    public void add(String[] cmds){
        try {
            makeSoftGarbage(cmds[1]);
        }
        catch (GdxRuntimeException e){
            System.out.println("Missing item name for spawn");
        }
        if(cmds.length == 4){
            try{
                int tx = Integer.parseInt(cmds[2]);
                int ty = Integer.parseInt(cmds[3]);
                imgs.get(imgs.size()-1).setX(tx);
                imgs.get(imgs.size()-1).setY(ty);
            }
            catch (GdxRuntimeException e){

            }
            catch (IndexOutOfBoundsException e){

            }
        }
    }

    public void addNumbers(){
        addNums = true;
    }

    public void interpretConsole(TextField text){
        String cText = consoleLog.get(consoleLog.size()-1);
        String[] cmds = cText.split(" ");
        if (cmds[0].equals("add")){
            add(cmds);
        }
        else if (cmds[0].equals("remove")){
            try{
                imgs.get(Integer.parseInt(cmds[1])-1).remove();
            }
            catch (GdxRuntimeException e){

            }
            catch (IndexOutOfBoundsException e){

            }
        }
        else if (cmds[0].equals("addNumbers")){
            addNumbers();
        }
        else if (cmds[0].equals("setSize")){
            try{
                imgs.get(Integer.parseInt(cmds[1])-1).setSize(Integer.parseInt(cmds[2]), Integer.parseInt(cmds[3]));
            }
            catch (GdxRuntimeException e){

            }
            catch (IndexOutOfBoundsException e){

            }
        }
        else if (cmds[0].equals("resetSize")){
            for(Image i : imgs){
                i.setSize(64, 64);
            }
        }
        else if (cmds[0].equals("setSizeAll")){
            try {
                for (Image i : imgs) {
                    i.setSize(Integer.parseInt(cmds[1]), Integer.parseInt(cmds[2]));
                }
            }
            catch (GdxRuntimeException e){

            }
            catch (IndexOutOfBoundsException e){

            }
        }
        else if (cmds[0].equals("move")){
            try{
                imgs.get(Integer.parseInt(cmds[1])-1).setX(Integer.parseInt(cmds[2]));
                imgs.get(Integer.parseInt(cmds[1])-1).setY(Integer.parseInt(cmds[3]));
            }
            catch (GdxRuntimeException e){

            }
            catch (IndexOutOfBoundsException e){

            }
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        text.toFront();
        imgs.add(makeGarbage(str));
        imgs.add(makeGarbage(str));
        imgs.add(makeGarbage(str));
        Pork pork = new Pork();
        AppleCore app = new AppleCore();
        CrowWithOddEyeInfection bag = new CrowWithOddEyeInfection();
        imgs.add(makeGarbage(pork.baseImgName + pork.img + pork.fileType));
        imgs.add(makeGarbage(app.baseImgName + app.img + app.fileType));
        imgs.add(makeGarbage(bag.baseImgName + bag.img + bag.fileType));

        for (int i = 0; i < imgs.size(); i++) {
            imgs.get(i).setName(Integer.toString(i));
        }

        for(int i=0; i<100; i++){
            for(int k=0; k<imgs.size(); k++) {
                oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY());
                oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX());
            }
        }

        for (int k = 0; k < imgs.size(); k++) {
            velMap.put(imgs.get(k).getName() + "x", 0f);
            velMap.put(imgs.get(k).getName() + "y", 0f);
        }

        for(int i=0; i<imgs.size(); i++){
            imgs.get(i).setSize(64, 64);
        }

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        text.toFront();
        fries.setX(x);
        fries.setY(y);
        fries.setSize(32, 32);

        if(addNums) {
            for (Image i : imgs) {
                textStyle = new Label.LabelStyle();
                textStyle.font = font;
                Ltext = new Label(i.getName(), textStyle);
                Ltext.setBounds(0, .2f, stage.getWidth(), 2);
                Ltext.setFontScale(1f, 1f);
                Ltext.setX(i.getX());
                Ltext.setY(i.getY());
            }
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.Q ) && !consoleOpen)
            Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.E) && !consoleOpen) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && consoleOpen){
            consoleIndex -= 1;
            if (consoleIndex < 0)
                consoleIndex = 0;
            text.setText(consoleLog.get(consoleIndex));
            text.setCursorPosition(text.getText().length());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) && consoleOpen){
            consoleIndex += 1;
            if (consoleIndex >= consoleLog.size())
                consoleIndex = consoleLog.size()-1;
            text.setText(consoleLog.get(consoleIndex));
            text.setCursorPosition(text.getText().length());
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.GRAVE)) {
            consoleIndex = consoleLog.size()-1;
            if(!consoleOpen) {
                consoleOpen = true;
                text.setWidth(stage.getWidth());
                stage.addActor(text);
                text.setTextFieldListener(new TextField.TextFieldListener() {
                    @Override
                    public void keyTyped(TextField textField, char c) {
                        if (c == '\n' || c == '\r') {
                            consoleLog.add(textField.getText());
                            textField.setText("");
                            consoleIndex = consoleLog.size();
                            textField.setCursorPosition(textField.getText().length());
                            interpretConsole(textField);
                        }

                    }
                });
            }
            else{
                consoleOpen = false;
                text.setText("");
                text.remove();
            }
        }

        //stage.addActor(fries);



        //System.out.println("X:"+img.getX());
        //System.out.println("Y:"+img.getY());
        for(int i=0; i<imgs.size(); i++) {
            stage.addActor(imgs.get(i));
            final int k = i;
            //System.out.println(velMap.get(imgs.get(i).getName() + "x"));

            imgs.get(k).setX(imgs.get(k).getX()+velMap.get(imgs.get(k).getName() + "x"));
            imgs.get(k).setY(imgs.get(k).getY()+velMap.get(imgs.get(k).getName() + "y"));


            float tx = velMap.get(imgs.get(k).getName()+ "x");
            float ty = velMap.get(imgs.get(k).getName()+ "y");
            float tm = (float) Math.sqrt((tx*tx)+(ty*ty));

            float fric = .9f;

            if(velMap.get(imgs.get(k).getName()+ "x") >= 0)
                velMap.put(imgs.get(k).getName() + "x", tx * fric/*((tm-.5f)/tm)*/);
            if(velMap.get(imgs.get(k).getName()+ "x") <= 0)
                velMap.put(imgs.get(k).getName() + "x", tx * fric/*((tm+.5f)/tm)*/);
            if(velMap.get(imgs.get(k).getName()+ "y") >= 0)
                velMap.put(imgs.get(k).getName() + "y", ty * fric/*((tm-.5f)/tm)*/);
            if(velMap.get(imgs.get(k).getName()+ "y") <= 0)
                velMap.put(imgs.get(k).getName() + "y", ty * fric/*((tm+.5f)/tm)*/);

            imgs.get(i).addListener(new ClickListener() {
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    if (wasTouched) {
                        System.out.println("X:");
                        System.out.println(oldLocMap.get("x"+"null"+"0"));
                        System.out.println(oldLocMap.get("x"+"null"+"9"));
                        System.out.println("Y:");
                        System.out.println(oldLocMap.get("y"+"null"+"0"));
                        System.out.println(oldLocMap.get("y"+"null"+"9"));

                        boolean gotX = false;
                        boolean gotY = false;
                        float xVel = 0f;
                        float yVel = 0f;
                        for (int i = 9; i > 0; i--) {

                            if(oldLocMap.get("y" + "null" + "0") - oldLocMap.get("y" + "null" + Integer.toString(i)) != 0f){
                                yVel = oldLocMap.get("y" + "null" + "0") - oldLocMap.get("y" + "null" + Integer.toString(i));
                                gotY = true;
                            }
                            if(oldLocMap.get("x" + "null" + "0") - oldLocMap.get("x" + "null" + Integer.toString(i)) != 0f){
                                xVel = oldLocMap.get("x" + "null" + "0") - oldLocMap.get("x" + "null" + Integer.toString(i));
                                gotX = true;
                            }

                        }
                        if(!gotX) xVel = 0f;
                        if(!gotY) yVel = 0f;



                        System.out.println("VELOCITIES:");
                        System.out.println("xVel:"+xVel);
                        System.out.println("yVel:"+yVel);

                        velMap.put(imgs.get(k).getName() + "x", xVel);
                        velMap.put(imgs.get(k).getName() + "y", yVel);

                        for(int i=0; i<10; i++){
                            oldLocMap.put("y" + "null" + Integer.toString(i), imgs.get(k).getY());
                            oldLocMap.put("x" + "null" + Integer.toString(i), imgs.get(k).getX());
                        }
                    }

                    wasTouched = false;

                }


                public void touchDragged(InputEvent event, float x, float y, int pointer) {

                    wasTouched = true;

                    final float oldX = imgs.get(k).getX();
                    final float oldY = imgs.get(k).getY();

                    String sX = Float.toString(oldX);
                    String sY = Float.toString(oldY);
                    if(countFrame == 9) {
                        for (int i = 9; i >= 0; i--) {
                            oldLocMap.put("y" + "null" + Integer.toString(i + 1), oldLocMap.get("y" + "null" + Integer.toString(i)));
                            oldLocMap.put("x" + "null" + Integer.toString(i + 1), oldLocMap.get("x" + "null" + Integer.toString(i)));
                        }
                        countFrame = 0;
                    }
                    oldLocMap.put("x"+"null"+"0", imgs.get(k).getX());
                    oldLocMap.put("y"+"null"+"0", imgs.get(k).getY());

                    countFrame ++;
                    imgs.get(k).moveBy(x - imgs.get(k).getWidth() / 2, y - imgs.get(k).getHeight() / 2);

                }
            });
        }

        stage.draw();



        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //viewport.setScreenWidth(width);
        //viewport.setScreenHeight(height);
        //stage.setViewport(viewport);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
