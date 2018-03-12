package com.garbageman.game.customers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.garbageman.game.Garbageman;
import com.garbageman.game.SpriteSheetDivider;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by dpearson6225 on 3/6/2018.
 */

public class Customer extends Image {

    //customer pickiness: 100 is high, 1 is low, the more picky a customer is, the more they care about their food and the more their review matters
    protected static int LOCAL_MIN = 0;
    protected static int LOCAL_MAX = 100;
    public static int REALLY_PICKY = 85;//85 to 100
    public static int KINDOF_PICKY = 50;//50 to 85
    public static int NOT_PICKY = 20;//20 to 50
    public static int DOESNT_CARE = LOCAL_MIN+1;//0 to 20, will actually just eat anything (Justin oh no <3)




    //file info
    public String fileType = ".png";
    public String fileLocation = "assets/Customers/";
    public String fileName = "error";

    //randomly generated customer info
    public int picky = 1;
    protected static int spriteSize = 128;
    public String customerName = "NAME";
    public Label overheadName;

    public void choosePicky(){
        Random rand = new Random();
        System.out.println("RAND PARMS: "+(LOCAL_MAX-LOCAL_MIN)+";   "+LOCAL_MIN);
        this.picky = rand.nextInt(LOCAL_MAX-LOCAL_MIN)+LOCAL_MIN;
    }

    public void setImg(){
        SpriteSheetDivider sp = new SpriteSheetDivider();
        this.setDrawable(sp.divideCustomer(this, this.fileName, 0, 0));
    }

    public void setImg(int indexX, int indexY){
        SpriteSheetDivider sp = new SpriteSheetDivider();
        this.setDrawable(sp.divideCustomer(this, this.fileName, indexX, indexY));
    }

    public void setOverheadPos(){
        if (!this.overheadName.equals(null)){
            this.overheadName.setSize(this.getWidth(), this.getHeight()/4);
            this.overheadName.setPosition(0, this.getHeight());
            this.overheadName.setVisible(true);
            this.overheadName.setColor(Color.BLACK);
        }
    }

   public static Customer randomCustomer(){
       Random rand = new Random();
       Customer cc = null;
       try {
           cc = (Customer) Class.forName(Garbageman.customers[rand.nextInt(Garbageman.customers.length)+0].getName()).newInstance();
       } catch (InstantiationException e) {
           e.printStackTrace();
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
        if (!cc.equals(null)){
            cc.choosePicky();
            cc.setImg();
            cc.setSize(spriteSize, spriteSize);
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = Garbageman.makeFont(15);
            cc.overheadName = new Label(cc.customerName, style);
            cc.overheadName.setAlignment(Align.center);
            cc.overheadName.setWrap(true);
            cc.setOverheadPos();
        }
        else if (cc.equals(null)){
            System.out.println("Unable to make customer");
        }
       return cc;
   }

   public void walkToPoint(float xPos, float yPos){
       this.setPosition(xPos, yPos);
       this.setOverheadPos();
   }

}
