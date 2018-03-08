package com.garbageman.game.customers;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
    public static int DOESNT_CARE = LOCAL_MIN;//0 to 20, will actually just eat anything (Justin oh no <3)




    //file info
    public String fileType = ".png";
    public String fileLocation = "assets/Customers/";
    public String fileName = "error";

    //randomly generated customer info
    public int picky = 1;

    public void choosePicky(){
        Random rand = new Random();
        picky = rand.nextInt(LOCAL_MAX-LOCAL_MIN)+LOCAL_MIN;
    }

    public void setImg(){
        SpriteSheetDivider sp = new SpriteSheetDivider();

        //this.setDrawable(this);
    }

   public Customer randomCustomer(){
       Random rand = new Random();
       Class getCust = Garbageman.customers[rand.nextInt(Garbageman.customers.length)+0];
       
       return null;
   }


}
