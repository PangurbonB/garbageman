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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.cooked.CookedFood;
import com.garbageman.game.customers.Customer;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.screens.CookingScreen;
import com.garbageman.game.screens.FakeInvScreen;
import com.garbageman.game.screens.MainMenuScreen;
import com.garbageman.game.screens.RestaurantScreen;
import com.garbageman.game.screens.ShopScreen;
import com.garbageman.game.screens.Trashcan;

import java.util.ArrayList;

/**
 * Created by dpearson6225 on 11/17/2017.
 */

public class UI {
    //variables:

    public Stage stage;
    Garbageman game;
    Backpack backpack;
    String screenName;
    private Label repText;
    private Label moneyText;
    public TextButton menuButton;
    private TextButton invButton;
    private ShapeRenderer shape;
    public boolean showInv = false;
    private ArrayList<Actor> inv = new ArrayList<Actor>();
    private ArrayList<Actor> infoFrame = new ArrayList<Actor>();
    private Actor currentDown;
    private boolean showInfo = false;
    private int infoX, infoY;
    //private ArrayList<String> curInfoList = new ArrayList<String>();
    //private Label infoName;
    private Label rotten;
    private Label desc;
    public String trashcanScreenName = Trashcan.screenName;
    public int topBarHeight = 100;
    public ArrayList<Image> jumpToTop = new ArrayList<Image>();
    public int currentType = -20;

    //inv stuff
    private Actor repBar, background, barBackground, invBackground, invInfo;
    private int len = 500;
    private ArrayList<Trash> curInv = new ArrayList<Trash>();
    private Label noContent;
    private ArrayList<Label>infoLabels = new ArrayList<Label>();
    private Image invImgBack;
    private Actor rotBarBack, rotBarBar;
    private float rotBarY = 10;//by default; gets reset to pos of rotText
    private Trash infoItem;
    public static int squareSize = 256/2;
    private TextButton addToCook;
    private int infoItemIndex = -20;
    public boolean giveOrder = false;

    private Actor BLUE_SQUARE;

    private TextButton viewOrders;
    private Actor orderFrame;
    private ArrayList<Actor> orders = new ArrayList<Actor>();
    public boolean showOrders = false;
    public ArrayList<Actor> coverTheseWithInv = new ArrayList<Actor>();
    private String orderText = "Give This Order";


    /*
    organization:

    variables and unused methods


    private methods

    public methods
     */


    public UI(){
        //this is unused now
    }

    //private methods:

    private boolean checkCurrentScreen(){
        return game.currentScreen.equals(screenName);
    }//check if current screen is the same in game class

    public Actor makeRect(int posX, int posY, int width, int height, Color bb, boolean vis){//makes a new shape, replacing using ShapeRenderers
        Image item = new Image(Assets.findTexture("whiteBlank"));
        item.setBounds(posX, posY, width, height);
        item.setColor(bb);
        stage.addActor(item);
        item.setVisible(vis);
        return item;//*/
    }

    private void updateRep(int len, double rep){//update the reputation bar
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

    private void createLabels(){//makes the info labels for clicking an item, like nastiness, name, and description
        if (infoLabels.size()== 0) {
            Label.LabelStyle smallerStyle = new Label.LabelStyle();
            smallerStyle.font = game.makeFont(15);
            if (infoLabels.size() ==0) {
                for (int x = 0; x <= 4; x++) {
                    //System.out.println("THIS: " + x);
                    Label infoName = new Label("", smallerStyle);
                    infoName.setAlignment(Align.center);
                    infoName.setBounds(0, (game.window_height-160) - (x*50), 250, 75);
                    infoName.setVisible(false);
                    stage.addActor(infoName);
                    //System.out.println("POSITION "+x+": "+infoName.getX()+", "+infoName.getY());
                    if (x == 1) {
                        //System.out.println("SET ROT BAR Y: "+infoName.getY());
                        rotBarY = infoName.getY();
                    }
                    //inv.add(infoName);
                    infoFrame.add(infoName);
                    infoLabels.add(infoName);
                    infoName.setVisible(false);
                    //System.out.println("info label " + x + " made "+ infoLabels.size());
                }
            } //else
            //System.out.println("NOT 4:::: " + curInfoList.size());
        }
    }

    private void setInvVis(boolean val, boolean getInfoFrame){//set the items in the inventory gui to show or not show; can also be used to set visibility of infoFrame
        //System.out.println("SET THE INV TO BE VIS: "+val+ ", info FRAME: "+getInfoFrame);

        for (Actor item:inv) {
            item.setVisible(val);
        }
        invInfo.setVisible(showInfo);
        if (showInfo){
            for (Actor item: infoFrame) {
                item.toFront();
            }
        }
        if (getInfoFrame){
            for (Actor item:infoFrame) {
                item.setVisible(val);
            }
        }
        /*if (getInfoFrame){
            for (Actor item:infoFrame) {
                item.setVisible(val);
            }
        }//*/
        /*ArrayList<Actor> check = null;
        if (!getInfoFrame){
            check = inv;
            //invInfo.setVisible(false);
        }
        else if (getInfoFrame == true){
            check = infoFrame;
        }
        if (showInfo){
            //invInfo.setVisible(true);
        }
        else if (!showInfo){
            invInfo.setVisible(false);
        }
        if (check != null) {
            for (int i = 0; i < check.size(); i++) {
                Actor b = check.get(i);
                b.setVisible(val);
            }
        }*/
    }

    private void closeInvInfo(){//closes the infoFrame and sets its contents to be invisible
        /*if (infoLabels.size()>= 4) {
            showInfo = !showInfo;
            for (int x = 0; x < infoLabels.size(); x++) {
                infoLabels.get(x).setVisible(false);
            }
            infoLabels.clear();
            rotBarBack.setVisible(false);
            rotBarBar.setVisible(false);
        }*/
    }


    //public methods:

    public void init(final Garbageman game, final Stage stage, String screenName){//init UI/sync to global, call this when opening a screen
        this.stage = stage;
        this.game = game;
        this.screenName = screenName;
        shape = new ShapeRenderer();
        Gdx.input.setInputProcessor(this.stage);

        Color barBackgroundGrey = Color.valueOf("#ffd280");
        this.background = makeRect(0, game.window_height- topBarHeight, game.window_width, 100, barBackgroundGrey, true);
        this.barBackground =  makeRect((game.window_width-len)/2, game.window_height-75, len, 50, Color.LIGHT_GRAY, true);
        this.repBar =  makeRect(((game.window_width-len)/2), game.window_height-75, 0, 50, Color.valueOf("#00ff11"), true);
        this.invBackground =  makeRect(0, 0, game.window_width, game.window_height-75, barBackgroundGrey, false);
        this.invInfo =  makeRect(0, 0, 250, game.window_height-75 , Color.BLUE, false);
        inv.add(this.invBackground);
        //inv.add(this.invInfo);
        infoFrame.add(invInfo);

        BLUE_SQUARE = makeRect(50, 50, 100, 100, Color.BLUE, false);

        TextButton.TextButtonStyle orderStyle = new TextButton.TextButtonStyle();
        orderStyle.font = game.makeFont(20);
        orderStyle.fontColor = Color.BLACK;
        viewOrders = new TextButton("View Orders", orderStyle);
        float height = 50, width = 150;
        viewOrders.setBounds(stage.getWidth()-(float)(width*1.5), stage.getHeight()-(height*3), width, height);
        viewOrders.setVisible(true);
        stage.addActor(viewOrders);
        coverTheseWithInv.add(viewOrders);
        viewOrders.addListener(new InputListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                showOrders = !showOrders;
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        float frameHeight = 350, frameWidth = 275;
        orderFrame = game.ui.makeRect((int)(stage.getWidth()-viewOrders.getWidth()*2), (int)(viewOrders.getY()-frameHeight), (int)frameWidth, (int)frameHeight, Color.GRAY, false);
        stage.addActor(orderFrame);
        coverTheseWithInv.add(orderFrame);

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

        addToCook = new TextButton("Cook This", invbbs);
        addToCook.setSize((invInfo.getWidth()/8)*6, 50);
        addToCook.setPosition((invInfo.getWidth()/8)*1, (stage.getHeight()/10)*3);
        addToCook.setVisible(false);
        addToCook.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (game.currentScreen.equals(FakeInvScreen.screenName)) {
                    //PassTrash.currentTrashCooking = infoItem;
                    //add item!
                    game.passTrash.addTrash(infoItemIndex);
                    infoItem = null;
                    infoItemIndex = -2;
                    game.setScreen(new CookingScreen(game, null));
                    showInfo = false;
                    infoItem = null;
                    currentDown = null;
                    //game.getScreen().dispose();
                    //stage.dispose();
                }
                else if (game.currentScreen.equals(RestaurantScreen.screenName)){
                    //System.out.println(infoItem.name);
                    PassTrash.orderToGive = (CookedFood) infoItem;
                    infoItem = null;
                    infoItemIndex = -2;
                    showInfo = false;
                    currentDown = null;
                    showOrders = false;
                    showInv = false;
                    addToCook.setVisible(false);
                    Customer c = ((RestaurantScreen)(game.getScreen())).frontCustomer;
                    int rep = c.giveCookedFood(PassTrash.orderToGive, game);
                    game.giveReputation(rep);
                    Customer.removeFromFrontOfLine();
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        stage.addActor(addToCook);

        invImgBack = new Image(Assets.findTexture("backpackRestaurant"));
        stage.addActor(invImgBack);
        invImgBack.setSize(stage.getWidth(), stage.getHeight());
        invImgBack.setVisible(false);
        inv.add(invImgBack);
        invImgBack.setZIndex(4);

        this.rotBarBack = makeRect((int)(invInfo.getWidth()*.1), (int)rotBarY, (int)(invInfo.getWidth()*.8), (int)invInfo.getHeight()/16, barBackgroundGrey, false);
        stage.addActor(this.rotBarBack);
        infoFrame.add(rotBarBack);
        //inv.add(rotBarBack);
        this.rotBarBar = makeRect((int)(invInfo.getWidth()*.1), (int)rotBarY, (int)(rotBarBack.getWidth()*.8), (int)invInfo.getHeight()/16, Color.valueOf("#f49542"), false);
        stage.addActor(rotBarBar);
        infoFrame.add(rotBarBar);
        //inv.add(rotBarBar);

        //this.rotBarBack.setVisible(false);
        //this.rotBarBar.setVisible(false);


        createLabels();
        upInv();

        if (game.currentScreen.equals(Trashcan.screenName) || game.currentScreen.equals(CookingScreen.screenName))
            showInv = false;

    }

    public void upInv(){//update the inventory display when an item is changed
        showInfo = false;
        infoItem = null;
        int size = 128;
        int startX = 260;
        int yPos = game.window_height-290;
        int yPlus = size+15;
        int xPos = startX;
        int xPlus = size+30;
        int tot = 0;
        inv.add(noContent);
        //System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB"+game.backpack.contents.size());
        //System.out.println("infoLabels: "+infoLabels.size());
        //System.out.println("make labels: "+(infoLabels.size()==0));
        if (infoLabels.size()== 0){
            createLabels();
        }
        for (int u = 0; u < game.backpack.contents.size(); u++)
            //System.out.println(u+": "+game.backpack.contents.get(u).name);
            try {
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
                                boolean canAdd = game.passTrash.findTrash(item);
                                if (!canAdd) {
                                    System.out.println("interval " + tot + ";;;; " + item.name);

                                    ImageButton.ImageButtonStyle ibStyle = new ImageButton.ImageButtonStyle();
                                    ibStyle.imageUp = item.getDrawable();

                                    ibStyle.imageUp.setMinHeight(size);
                                    ibStyle.imageUp.setMinWidth(size);
                                    final ImageButton localB = new ImageButton(ibStyle);
                                    localB.setBounds(xPos + 25, yPos, size, size);
                                    localB.setSize(squareSize, squareSize);

                                    //System.out.println("INFO: "+item.name+", "+item.rarity);


                                    final int finalTot = tot;
                                    localB.addListener(new InputListener() {
                                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                            if (currentDown == localB) {
                                                currentDown = null;
                                                infoItem = null;
                                                showInfo = false;

                                            } else if (currentDown == null || currentDown != localB) {
                                                showInfo = true;
                                                infoX = (int) localB.getX();
                                                infoY = (int) localB.getY();
                                                currentDown = localB;

                                                infoItem = item;
                                                infoItemIndex = finalTot;
                                            }

                                            return true;
                                        }

                                        ;
                                    });
                                    inv.add(localB);
                                    localB.setVisible(false);
                                    stage.addActor(localB);
                                    if (!canAdd)
                                        xPos = xPos + xPlus;
                                    else
                                        xPos = xPos - xPlus;
                                    //System.out.println(x);

                                    //System.out.println(x + " "+(x <= 6));

                                }
                                tot++;

                                canAdd = false;
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
                j.printStackTrace();
            }
    }

    public void makeUI(){//call this to make the UI in a new screen after initing it
        Label.LabelStyle repStyle = new Label.LabelStyle();
        repStyle.font = game.makeFont(25);
        repText = new Label("Reputation", repStyle);
        repText.setBounds(250, game.window_height-75, 250, 75);
        repText.setColor(Color.BLACK);
        stage.addActor(repText);

        moneyText = new Label("MUNY", repStyle);
        moneyText.setBounds(200, game.window_height-75, 150, 50);
        moneyText.setColor(Color.BLACK);
        stage.addActor(moneyText);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = game.makeFont(35);
        menuButton = new TextButton("Menu", buttonStyle);
        menuButton.setBounds(0, game.window_height-75, 175, 50);
        menuButton.getLabel().setColor(Color.BLACK);
        menuButton.getLabel().setAlignment(Align.center);
        menuButton.addListener(new InputListener(){
            public boolean mouseMoved(InputEvent event, float x, float y) {
                return super.mouseMoved(event, x, y);
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("CHECK ME: "+ checkCurrentScreen());
                if (checkCurrentScreen()){
                    System.out.println("Disposing: "+game.getScreen());
                    game.getScreen().dispose();
                    game.setScreen(new MainMenuScreen(game));
                }
                return true;
            }
        });
        stage.addActor(menuButton);

        if (!game.currentScreen.equals("Trashcan")){
            TextButton.TextButtonStyle invBStyle = new TextButton.TextButtonStyle();
            invBStyle.font = game.makeFont(25);
            invButton = new TextButton("Inventory", invBStyle);
            int invWidth = 250;
            invButton.setBounds(game.window_width-invWidth, game.window_height-75, invWidth, 50);
            invButton.getLabel().setAlignment(Align.center);
            invButton.getLabel().setColor(Color.BLACK);
            invButton.addListener(new InputListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    //System.out.println("CLIEKED + " + checkCurrentScreen());
                    if(game.currentScreen.equals(CookingScreen.screenName)){
                        game.setScreen(new RestaurantScreen(game));
                        return true;
                    }

                    if (screenName.equals(FakeInvScreen.screenName)){
                        game.setScreen(new CookingScreen(game, null));
                        showInv = false;

                        showInfo = false;
                        infoItem = null;
                    }
                    if (!screenName.equals(trashcanScreenName)) {
                        showInv = !showInv;
                        if (!showInv) {
                            //showInfo = false;
                        }
                    }
                    else if (screenName.equals(trashcanScreenName)){
                        //BRETT THIS IS WHERE YOU CAN DO INV STUFF IN THE DUMPSTER SCREEN
                    }
                    giveOrder = false;
                    addToCook.setVisible(false);
                    return true;
                }
            });
            //System.out.println("MADE LISTNER FOR INVBUTTON");
            stage.addActor(invButton);
            //invButton.setVisible(false); ///LAST CHANGE!!!
        }

        //this is the top section buttons for later:
        BitmapFont topFont = game.makeFont(20);
        TextButton.TextButtonStyle topStyle = new TextButton.TextButtonStyle();
        topStyle.font = topFont;
        int topSize = (int) Math.ceil(game.window_width/game.sections.length);
        //System.out.println("len: "+topSize+",     num: "+(topSize*game.sections.length)+",    width: "+game.window_width);
        for (int i = 0; i < game.sections.length; i++){
            TextButton button = new TextButton(game.sections[i], topStyle);
            //button.setBounds();
            button.setVisible(false);
            //inv.add(button);//THESE ARE THE WEIRD THINGS IN LOWER LEFT CORNER!!!!!! GKPOW$JOPWJOHGWNOHG{
            stage.addActor(button);
        }



        upInv();
    }

    public void update(){//call this in the render method of your screen to update the UI info on render
        Color barBackgroundGrey = Color.valueOf("#939598");

        //System.out.println("ROT BAR VIS:"+rotBarBack.isVisible());
        //System.out.println("CHECK SCREEN: "+checkCurrentScreen());

        if (game.currentScreen.equals(RestaurantScreen.screenName) || game.currentScreen.equals(CookingScreen.screenName)){
            viewOrders.setVisible(true);
            viewOrders.toFront();
            if (viewOrders.getStage() != stage){
                stage.addActor(viewOrders);
            }
            //System.out.println("SET VISIBLE! "+viewOrders.isVisible()+" : "+ viewOrders.getX()+", "+viewOrders.getY());
            for (Actor a : coverTheseWithInv) {
                //if (a != orderFrame) {
                    a.setVisible(!game.ui.showInv);
                //}
            }


            if (!game.ui.showInv){
                orderFrame.setVisible(showOrders);
            }
            else if (game.ui.showInv){
                orderFrame.setVisible(false);
            }
            if (showOrders){
                viewOrders.setText("Close");

                ArrayList<Customer> getOrders = new ArrayList<Customer>();
                for (Customer c: Customer.listOfCustomers) {
                    if (c.order != null){
                        getOrders.add(c);
                    }
                }
                if (getOrders.size() != orders.size()){
                    orders.clear();
                    Label.LabelStyle sty = new Label.LabelStyle();
                    sty.font = game.makeFont(15);
                    sty.fontColor = Color.WHITE;
                    float yPlus = 50;
                    float y = orderFrame.getY()+(orderFrame.getHeight())-yPlus;
                    System.out.println("y: "+yPlus);
                    for (Customer c: getOrders) {
                        final Customer z = c;
                        //System.out.println("order: "+c.customerName+": "+c.order.name);
                        Label lab = new Label(c.customerName+": "+c.order.name, sty);
                        lab.setSize(orderFrame.getWidth(), 50);
                        lab.setPosition(orderFrame.getX()+10, y);
                        lab.setWrap(true);
                        y = y - yPlus;
                        lab.setVisible(false);
                        if (lab.getListeners().size == 0){
                            lab.addListener(new InputListener(){
                                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                    if (game.getScreen() instanceof RestaurantScreen || game.getScreen() instanceof CookingScreen) {

                                        game.setScreen(new CookingScreen(game, z.order));

                                    }
                                    return super.touchDown(event, x, y, pointer, button);
                                }
                            });
                        }
                        stage.addActor(lab);
                        orders.add(lab);
                        coverTheseWithInv.add(lab);
                    }
                }
                else {
                    for (Actor a: orders) {
                        a.setVisible(orderFrame.isVisible());
                    }
                }
            }
            else{
                viewOrders.setText("View Orders");
            }
            for (Actor a: orders) {
                a.setVisible(orderFrame.isVisible());
                a.toFront();

                if (a.getStage() != stage){
                    stage.addActor(a);
                }
            }
        }
        else{
            //showInv = false;
            viewOrders.setVisible(false);
            orderFrame.setVisible(false);

        }
        for (Actor a: orders){
            a.toFront();
        }//*/

        updateRep(len, (double)game.reputation/100);
        if (repText != null){
            repText.setText("Reputation: " + game.reputation + "/100");
            repText.setBounds((game.window_width-len)/2, game.window_height-75, len, 50);
            repText.setAlignment(Align.center);
        }
        if (moneyText != null){
            String setMoney = ""+game.money;
            if (game.money % 1 == 0){
                setMoney = ""+((int)game.money);
            }
            moneyText.setText("$"+setMoney);
            moneyText.setAlignment(Align.center);
            moneyText.setColor(Color.BLACK);
        }

        if (showInv == true && !game.currentScreen.equals(CookingScreen.screenName)){
            if (!game.currentScreen.equals(CookingScreen.screenName)) {
                if (infoLabels.size() == 0 && showInfo) {
                    //createLabels();
                    //System.out.println("LABELS MADE!!!!");
                    rotBarBack.setVisible(true);
                    rotBarBar.setVisible(true);
                } else if (infoLabels.size() > 0 && showInfo) {
                    for (int z = 0; z < infoLabels.size(); z++) {
                        infoLabels.get(z).setVisible(true);
                        stage.addActor(infoLabels.get(z));
                    }
                } else if (!showInfo) {
                    if (infoLabels.size() > 0) {
                        //System.out.println("take awy them thangs0");
                        for (int z = 0; z < infoLabels.size(); z++) {
                            infoLabels.get(z).setVisible(false);
                        }
                        //infoLabels.clear();
                        rotBarBack.setVisible(false);
                        rotBarBar.setVisible(false);
                    }
                }
            }
            if (!game.backpack.contents.equals(curInv))
                upInv();
            setInvVis(true, false);
            if (game.currentScreen.equals(CookingScreen.screenName))
                invButton.setVisible(false);
            else
                invButton.setVisible(true);
            invButton.getLabel().setText("Close");
            if (game.currentScreen.equals(FakeInvScreen.screenName))
                invButton.getLabel().setText("Cancel");
            //System.out.println("curInfoList: "+curInfoList.size());
            if (showInfo && infoItem != null){
                setInvVis(true, true);//HERE

                //System.out.println("INFO_LABELS: "+infoLabels.size());
                if (infoLabels.size()> 0) {
                    invInfo.setVisible(true);
                    invInfo.toFront();
                    rotBarBar.setVisible(true);
                    rotBarBack.setVisible(true);
                    rotBarBack.toFront();
                    rotBarBar.toFront();
                    addToCook.toFront();
                    for (int x = 0; x < infoLabels.size(); x++) {
                        //System.out.println("ITEM: "+x+" ;; SIZE: "+infoLabels.size());
                        Label local = infoLabels.get(x);
                        local.toFront();
                        local.setWrap(true);
                        //System.out.println("BAS EIMG "+infoItem.baseImgName);
                        if (x == 0){
                            local.setText("Item: "+infoItem.name);
                        }
                        else if (x == 2) {
                            //System.out.println(local.getText());
                            if (!(infoItem instanceof CookedFood)) {
                                local.setText(infoItem.getRarity(infoItem.rarity));
                                local.setColor(game.colorMap.get(infoLabels.get(x).getText().toString()));
                            }
                            else{
                                local.setText("Sell Price: $"+((CookedFood) infoItem).sellPrice);
                                local.setColor(Color.WHITE);
                            }
                        }
                        else if (x == 3){
                            //System.out.println("LENY: "+local.getText().length);
                            String desc;
                            if (infoItem.nast >= infoItem.HMTHRESH && infoItem.desc3 != "") {
                                desc = infoItem.desc3;
                            } else if (infoItem.nast >= infoItem.MLTHRESH && infoItem.desc2 != "") {
                                desc = infoItem.desc2;
                            } else {
                                desc = infoItem.desc;
                            }
                            local.setText(desc);
                            if (local.getText().length > 30) {
                                local.setY(local.getHeight()*(float)4.75);
                            }

                        }
                        else if (x == 1) {
                            float numy = rotBarY-(float)(rotBarY*-.04);
                            rotBarBack.setY(numy);
                            rotBarBar.setY(numy);
                            local.setSize(rotBarBack.getWidth(), rotBarBack.getHeight());
                            local.setPosition(rotBarBack.getX(), rotBarBack.getY());
                            local.setText("Nastiness: "+infoItem.nast);
                            int nast = infoItem.nast;
                            local.setWrap(true);

                            //backpack.contents.get(c)
                            if (nast <= infoItem.MLTHRESH){
                                rotBarBar.setColor(game.colorMap.get("Green"));
                            }
                            else if (nast > infoItem.MLTHRESH && nast <= infoItem.HMTHRESH){
                                rotBarBar.setColor(game.colorMap.get("Yellow"));
                            }
                            else if (nast > infoItem.HMTHRESH){
                                rotBarBar.setColor(game.colorMap.get("Red"));
                            }
                            float width = ((rotBarBack.getWidth()*(((float)nast/100 ))));
                            //System.out.println("FLOAT ME BB: "+width);
                            rotBarBar.setSize(width, rotBarBar.getHeight());

                            rotBarBar.toFront();
                            local.toFront();
                            //rotBarBar.;
                            //System.out.println("x == 3, "+rotBarY+", "+rotBarBack.getY());
                        }
                        //local.setText("!!! "+curInfoList.get(x));
                        local.setVisible(true);
                        //System.out.println(local.getText());
                        //System.out.println(x+":: set text to '"+curInfoList.get(x)+"'");

                    }

                    //System.out.println("current type: "+infoItem.type+" : "+PassTrash.currentTypeToAdd);
                    if (game.currentScreen.equals(FakeInvScreen.screenName)&& infoItem.type == game.passTrash.currentTypeToAdd){
                        addToCook.setVisible(true);
                        //System.out.println("they can cook this!");
                    }
                    else if (game.currentScreen.equals(RestaurantScreen.screenName)&& giveOrder && infoItem instanceof CookedFood){
                        addToCook.setText(orderText);
                        addToCook.setVisible(true);
                    }
                    else{
                        addToCook.setVisible(false);
                        //System.out.println("they can't cook this!");
                    }
                    if (game.currentScreen.equals(FakeInvScreen.screenName)&& game.passTrash.currentTypeToAdd == game.passTrash.ALLFOODTYPES) {
                        //Brett add the question mark catch here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //uh I may have already added it right here
                        addToCook.setVisible(true);
                    }
                    if (addToCook.getText() == "Cook This"){
                        addToCook.getLabel().setWrap(false);
                    }
                    else{
                        addToCook.getLabel().setWrap(true);
                    }

                }
            }
            else if (showInfo && !showInv){
                closeInvInfo();
                invInfo.setVisible(false);
            }
            else{
                //setInvVis(false, false);//HERE
                invInfo.setVisible(false);
                closeInvInfo();
            }

            noContent.setVisible(false);
            if (game.backpack.contents.size()== 0 && showInv){
                noContent.setVisible(true);
                //System.out.println("SET NO CONTENT BIS");
            }
            else if (game.backpack.contents.size()> 0){
                noContent.setVisible(false);
                //System.out.println("IT'S not vis");
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
        if (!showInv){
            invInfo.setVisible(false);
            setInvVis(false, true);
        }
        /*if (rotBarBar.isVisible() && showInfo){
            rotBarBar.toFront();
            System.out.println("TO FRONT!  "+rotBarBar.isVisible()+ "  "+ rotBarBar.getHeight()+ ", "+rotBarBar.getWidth());
        }*/
        if (viewOrders.isVisible()){
            viewOrders.toFront();
            if (viewOrders.getStage() != stage){
                stage.addActor(viewOrders);
            }
        }


        if (game.currentScreen.equals(CookingScreen.screenName)){
            invButton.setText("Restaurant");
        }
        if (invButton != null)
            invButton.toFront();

    }
}