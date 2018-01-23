package com.garbageman.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.screens.MainMenuScreen;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 11/17/2017.
 */

public class UI {
    Stage stage;
    Garbageman game;
    Backpack backpack;
    String screenName;
    private Label repText;
    private Label moneyText;
    private TextButton menuButton;
    private TextButton invButton;
    private ShapeRenderer shape;
    private boolean showInv = false;
    private ArrayList<Actor> inv = new ArrayList<Actor>();
    private ArrayList<Actor> infoFrame = new ArrayList<Actor>();
    private Actor currentDown;
    private boolean showInfo = false;
    private int infoX, infoY;
    private ArrayList<String> curInfoList = new ArrayList<String>();
    //private Label infoName;
    private Label rotten;
    private Label desc;
    private String trashcanScreenName = "Trashcan";
    public int topbarHeight = 100;
    public ArrayList<Image> jumpToTop = new ArrayList<Image>();

    //inv stuff
    private Actor repBar, background, barBackground, invBackground, invInfo;
    private int len = 500;
    private ArrayList<Trash> curInv = new ArrayList<Trash>();
    private Label noContent;
    private ArrayList<Label>infoLabels = new ArrayList<Label>();
    private Image invImgBack;
    private Actor rotBarBack, rotBarBar;


    public UI(){

    }

    private boolean checkCurrentScreen(){
        return game.currentScreen.equals(screenName);
    }

    private BitmapFont makeFont(int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/PressStart2P.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        BitmapFont press2P = generator.generateFont(parameter);
        generator.dispose();
        return press2P;
    }

    private Actor makeRect(int posX, int posY, int width, int height, Color bb, boolean vis){
        /*shape.setAutoShapeType(true);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.rect(posX, posY, width, height, bb, bb, bb, bb);
        shape.end();//*/
        Image item = new Image(new Texture("assets/Buttons/whiteBlank.png"));
        item.setBounds(posX, posY, width, height);
        item.setColor(bb);
        stage.addActor(item);
        item.setVisible(vis);
        return item;//*/
    }

    private void updateRep(int len, double rep){
        //rep = 50;//for now, out of 100
        int num = (int)(len*rep);
        Color color = Color.valueOf("#00ff11");
        double repcal = rep*100;
        if (repcal >= 75){
            color = Color.valueOf("#00ff11");
        }
        else if (repcal < 75 && repcal >= 50){
            color = Color.valueOf("#d7f442");
        }
        else if (repcal < 50 && repcal >= 25){
            color = Color.valueOf("#f4b841");
        }
        else if (repcal < 25){
            color = Color.valueOf("#f45541");
        }
       //SIZE BAR HERE
        this.repBar.setWidth(num);
        this.repBar.setColor(color);
    }

    private Texture getTextureFromTrash(Trash item){
        return new Texture("assets/Garbage/"+item.img+".png");
    }

    private void createLabels(){
        if (infoLabels.size()== 0) {
            Label.LabelStyle smallerStyle = new Label.LabelStyle();
            smallerStyle.font = game.makeFont(15);
            if (curInfoList.size() >= 4) {
                for (int x = 0; x <= curInfoList.size(); x++) {
                    System.out.println("THIS: " + x);
                    Label infoName = new Label("", smallerStyle);
                    infoName.setAlignment(Align.center);
                    infoName.setBounds(0, (game.window_height-160) - (x*50), 250, 75);
                    infoName.setVisible(false);
                    stage.addActor(infoName);
                    //inv.add(infoName);
                    //infoFrame.add(infoName);
                    infoLabels.add(infoName);
                    System.out.println("info label " + x + " made");
                }
            } //else
                //System.out.println("NOT 4:::: " + curInfoList.size());
        }
    }

    public void init(Garbageman game, Stage stage, String screenName){
        this.stage = stage;
        this.game = game;
        this.screenName = screenName;
        shape = new ShapeRenderer();

        Color barBackgroundGrey = Color.valueOf("#ffd280");
        this.background = makeRect(0, game.window_height-topbarHeight, game.window_width, 100, barBackgroundGrey, true);
        this.barBackground =  makeRect((game.window_width-len)/2, game.window_height-75, len, 50, Color.LIGHT_GRAY, true);
        this.repBar =  makeRect(((game.window_width-len)/2), game.window_height-75, 0, 50, Color.valueOf("#00ff11"), true);
        this.invBackground =  makeRect(0, 0, game.window_width, game.window_height-75, barBackgroundGrey, false);
        this.invInfo =  makeRect(0, 0, 250, game.window_height-75 , Color.BLUE, false);
        inv.add(this.invBackground);
        inv.add(this.invInfo);

        BitmapFont labFont = game.makeFont(25);
        TextButton.TextButtonStyle invbbs = new TextButton.TextButtonStyle();
        invbbs.font = labFont;
        Label.LabelStyle invlbs = new Label.LabelStyle();
        invlbs.font = labFont;
        invlbs.fontColor = Color.BLACK;
        this.noContent = new Label("Your Inventory is Empty", invlbs);
        noContent.setAlignment(Align.center);
        noContent.setBounds((game.window_width-200)/2, game.window_height-250, 200, 50);
        noContent.setVisible(false);
        stage.addActor(noContent);

        invImgBack = new Image(new Texture("assets/Screens/TrashBackpackRestaurantCrop.png"));
        stage.addActor(invImgBack);
        invImgBack.setSize(stage.getWidth(), stage.getHeight());
        invImgBack.setVisible(false);
        inv.add(invImgBack);
        invImgBack.setZIndex(4);

        this.rotBarBack = makeRect(0, 0, (int)(double)(invInfo.getWidth()*.8), (int)invInfo.getHeight()/8, barBackgroundGrey, true);
        stage.addActor(this.rotBarBack);

        createLabels();
        upInv();

        if (game.currentScreen.equals("Trashcan"))
            showInv = false;

    }

    public void upInv(){
        int size = 128;
        int startX = 200;
        int yPos = game.window_height-350;
        int yPlus = size+15;
        int xPos = startX;
        int xPlus = size+30;
        int tot = 0;
        //inv.add(noContent);
        //System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB"+game.backpack.contents.size());
        for (int u = 0; u < game.backpack.contents.size(); u++)
            //System.out.println(u+": "+game.backpack.contents.get(u).name);
        try {
            while (game.backpack.contents.size()== 0)
                System.out.println("waiting...");

            if (game.backpack.contents.size() > 0 && !noContent.equals(null)) {
                //System.out.println("contents: "+ game.backpack.contents.size());
                noContent.setVisible(false);
                // System.out.println("set vis false: "+noContent.isVisible());
                for (int y = 0; y <= 3; y++) {
                    //System.out.println("looped");
                    for (int x = 0; x < 5; x++) {
                        //System.out.println("tot: "+tot+"  "+"size: "+game.backpack.contents.size());
                        if (tot < game.backpack.contents.size()) {
                            //System.out.println("here we are");
                            final Trash item = game.backpack.contents.get(tot);
                            //System.out.println("interval " + tot + ";;;; " + item.name);
                            Drawable img = item.getDrawable();

                            ImageButton.ImageButtonStyle ibStyle = new ImageButton.ImageButtonStyle();
                            ibStyle.imageUp = item.getDrawable();

                            ibStyle.imageUp.setMinHeight(size);
                            ibStyle.imageUp.setMinWidth(size);
                            final ImageButton localB = new ImageButton(ibStyle);
                            localB.setBounds(xPos + 25, yPos, size, size);
                            localB.setSize(256, 256);

                            System.out.println("INFO: "+item.name+", "+item.rarity);


                            localB.addListener(new InputListener() {
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    //create info
                                    //System.out.println("CLIEDK");
                                    //new InfoFrame(game, stage, (int)localB.getX(), (int)localB.getY());
                                    //makeRect((int)localB.getX(), (int)localB.getY(), 100, 100, Color.BLUE);
                                    System.out.println("current down: "+currentDown);
                                    if (currentDown == null || currentDown != localB) {
                                        showInfo = true;
                                        infoX = (int) localB.getX();
                                        infoY = (int) localB.getY();
                                        currentDown = localB;
                                        curInfoList.clear();
                                        curInfoList.add(0, "Item: " + item.name);
                                        curInfoList.add(1, "Rot:"+Integer.toString(item.nast));//rottenness
                                        curInfoList.add(2, item.getRarity(item.rarity));
                                        curInfoList.add(3, item.desc);
                                       // System.out.println("Current rarity: "+item.getRarity(item.rarity));
                                        //System.out.println("SIZE:" + curInfoList.size());

                                    } else if (currentDown != null) {
                                        System.out.println("eq? "+ (currentDown == localB));
                                        if (currentDown == localB) {
                                            currentDown = null;
                                            showInfo = false;
                                            closeInvInfo();
                                        }
                                    }
                                    return true;
                                }

                                ;
                            });
                            inv.add(localB);
                            localB.setVisible(false);
                            stage.addActor(localB);
                            xPos = xPos + xPlus;
                            //System.out.println(x);
                            tot++;
                            //System.out.println(x + " "+(x <= 6));
                        }
                    }
                    yPos = yPos - yPlus;
                    xPos = startX;
                }
                curInv = game.backpack.contents;
                //System.out.println("DONE");
            } else if (game.backpack.contents.size() == 0) {
                System.out.println("THERE IS NO INV STUFF");
                noContent.setVisible(true);
                //game.backpack.add(new McdHamburger());
            }
        }
        catch (java.lang.NullPointerException j){
            //lol this always errors System.out.println("figgle");
        }
    }

    public void makeUI(){
        Label.LabelStyle repStyle = new Label.LabelStyle();
        repStyle.font = makeFont(25);
        repText = new Label("Reputation", repStyle);
        repText.setBounds(250, game.window_height-75, 250, 75);
        repText.setColor(Color.BLACK);
        stage.addActor(repText);

        moneyText = new Label("MUNY", repStyle);
        moneyText.setBounds(200, game.window_height-75, 150, 50);
        moneyText.setColor(Color.BLACK);
        stage.addActor(moneyText);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = makeFont(35);
        menuButton = new TextButton("Menu", buttonStyle);
        menuButton.setBounds(0, game.window_height-75, 175, 50);
        menuButton.getLabel().setColor(Color.BLACK);
        menuButton.getLabel().setAlignment(Align.center);
        menuButton.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (checkCurrentScreen()){
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
        stage.addActor(menuButton);

        if (!game.currentScreen.equals("Trashcan")){
            TextButton.TextButtonStyle invBStyle = new TextButton.TextButtonStyle();
            invBStyle.font = makeFont(25);
            invButton = new TextButton("Inventory", invBStyle);
            int invWidth = 250;
            invButton.setBounds(game.window_width-invWidth, game.window_height-75, invWidth, 50);
            invButton.getLabel().setAlignment(Align.center);
            invButton.getLabel().setColor(Color.BLACK);
            invButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    //System.out.println("CLIEKED + " + checkCurrentScreen());
                    if (!screenName.equals(trashcanScreenName)) {
                        showInv = !showInv;
                    }
                    else if (screenName.equals(trashcanScreenName)){
                        //BRETT THIS IS WHERE YOU CAN DO INV STUFF IN THE DUMPSTER SCREEN
                    }
                    return true;
                }
            });
            //System.out.println("MADE LISTNER FOR INVBUTTON");
            stage.addActor(invButton);
        }

        //this is the top section buttons for later:
        BitmapFont topFont = game.makeFont(20);
        TextButton.TextButtonStyle topStyle = new TextButton.TextButtonStyle();
        topStyle.font = topFont;
        int topSize = (int) Math.ceil(game.window_width/game.sections.length);
        System.out.println("len: "+topSize+",     num: "+(topSize*game.sections.length)+",    width: "+game.window_width);
        for (int i = 0; i < game.sections.length; i++){
            TextButton button = new TextButton(game.sections[i], topStyle);
            //button.setBounds();
            button.setVisible(false);
            inv.add(button);
            stage.addActor(button);
        }



        upInv();
    }

    private void setInvVis(boolean val, boolean getInfoFrame){
        ArrayList<Actor> check = null;
        if (getInfoFrame == false){
            check = inv;
            invInfo.setVisible(false);
        }
        else if (getInfoFrame == true){
            check = infoFrame;
        }
        if (showInfo){
           invInfo.setVisible(true);
        }
        else if (!showInfo){
            invInfo.setVisible(false);
        }
        if (check != null) {
            for (int i = 0; i < check.size(); i++) {
                Actor b = check.get(i);
                b.setVisible(val);
            }
        }
    }

    private void closeInvInfo(){
        if (curInfoList.size()>= 4) {
            showInfo = !showInfo;
            for (int x = 0; x < infoLabels.size(); x++) {
                infoLabels.get(x).setVisible(false);
            }
            infoLabels.clear();
        }
    }

    public void update(){
        Color barBackgroundGrey = Color.valueOf("#939598");

        updateRep(len, (double)game.reputation/100);
        if (repText != null){
            repText.setText("Reputation: " + game.reputation + "/100");
            repText.setBounds((game.window_width-len)/2, game.window_height-75, len, 50);
            repText.setAlignment(Align.center);
        }
        if (moneyText != null){
            moneyText.setText("$"+game.money);
            moneyText.setAlignment(Align.center);
            moneyText.setColor(Color.BLACK);
        }

        if (showInv == true){
            if (infoLabels.size()== 0){
                createLabels();
            }
            if (!game.backpack.contents.equals(curInv))
                upInv();
            setInvVis(true, false);
            invButton.getLabel().setText("Close");
            //System.out.println("curInfoList: "+curInfoList.size());
            if (showInfo && curInfoList.size()== 4){
                setInvVis(true, true);

                //System.out.println("INFO_LABELS: "+infoLabels.size());
                if (infoLabels.size()> 0) {
                    for (int x = 0; x < curInfoList.size(); x++) {
                        //System.out.println("ITEM: "+x+" ;; SIZE: "+infoLabels.size());
                        infoLabels.get(x).setText(curInfoList.get(x));
                        if (x == 2) {
                            System.out.println(infoLabels.get(x).getText());
                            System.out.println(game.colorMap.get("Rare"));
                            infoLabels.get(x).setColor(game.colorMap.get(infoLabels.get(x).getText().toString()));
                        }
                        infoLabels.get(x).setVisible(true);
                        //System.out.println("set text to '"+curInfoList.get(x)+"'");
                    }
                }
            }
            else if (showInfo && curInfoList.size()== 4 && !showInv){
                closeInvInfo();
            }
            else{
                setInvVis(false, true);
                closeInvInfo();
            }
        }
        else if (showInv == false){
            setInvVis(false, false);
            closeInvInfo();
            try{
                invButton.getLabel().setText("Inventory");
            }
            catch(java.lang.NullPointerException e){
                //e.printStackTrace();
            }
        }
    }
}
