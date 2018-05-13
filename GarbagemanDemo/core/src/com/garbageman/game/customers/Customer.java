package com.garbageman.game.customers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
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
    public static int DOESNT_CARE = LOCAL_MIN+1;//0 to 20, will actually just eat anything (Duy oh no <3)




    //file info
    public String fileType = ".png";
    public String fileLocation = "assets/Customers/";
    public String fileName = "error";

    //list of all currently running customers:
    public static ArrayList<Customer> listOfCustomers = new ArrayList<Customer>();

    //randomly generated customer info
    public int picky = 1;
    protected static int spriteSize = 350;
    public String customerName = "NAME";
    public Label overheadName;
    protected SpriteSheetDivider SpriteDivide = new SpriteSheetDivider();
    protected float DEFAULT_POS = 0;
    protected static final float INCREMENT = 1, REPS = 5;
    public float posX = DEFAULT_POS, posY = DEFAULT_POS;

    int currentCycleStage = 0;
    int totalCycleStages = 60;
    int secondImgThresh = totalCycleStages/4;
    int thirdImgThresh = totalCycleStages/2;
    int finalImgThresh = secondImgThresh*3;

    public void choosePicky(){
        Random rand = new Random();
        //System.out.println("RAND PARMS: "+(LOCAL_MAX-LOCAL_MIN)+";   "+LOCAL_MIN);
        this.picky = rand.nextInt(LOCAL_MAX-LOCAL_MIN)+LOCAL_MIN;
    }

    public void setImg(){
        this.setDrawable(SpriteDivide.divideCustomer(this, this.fileName, 0, 0));
    }

    public void setImg(int indexX, int indexY){
        this.setDrawable(SpriteDivide.divideCustomer(this, this.fileName, indexX, indexY));
    }

    protected void setOverheadPos(){
        if (!this.overheadName.equals(null)){
            this.overheadName.setSize(this.getWidth(), this.getHeight()/4);
            this.overheadName.setPosition(this.getX(), this.getY()+this.getHeight());
            this.overheadName.setVisible(this.isVisible());
            this.overheadName.setColor(Color.BLACK);
        }
    }

    public static void updateAllCurrentCustomers(){
        for (Customer cust : Customer.listOfCustomers) {
            cust.updateOnRender();
        }
    }

    public static boolean findCustomerInList(Customer cc){
        boolean found = false;
        for (Customer check:listOfCustomers) {
            if (check.equals(cc)){
                found = true;
                break;
            }
        }
        return found;
    }

    public static void addCustomerToList(Customer cc){
        if (findCustomerInList(cc)== false){
            listOfCustomers.add(cc);
        }
        else
            System.out.println(cc.customerName+" could not be added, it is already in the list");
    }

   public static Customer randomCustomer(Stage stage){
       Random rand = new Random();
       Customer cc = null;
       try {
           cc = (Customer) Class.forName(Garbageman.customers.get(rand.nextInt(Garbageman.customers.size())).getName()).newInstance();
       } catch (InstantiationException e) {
           e.printStackTrace();
       } catch (IllegalAccessException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
        if (cc != null){
            cc.choosePicky();
            cc.setImg();
            cc.setSize(spriteSize, spriteSize);
            Customer.addCustomerToList(cc);
            Label.LabelStyle style = new Label.LabelStyle();
            style.font = Garbageman.makeFont(15);
            cc.overheadName = new Label(cc.customerName, style);
            cc.overheadName.setAlignment(Align.center);
            cc.overheadName.setWrap(true);
            cc.setOverheadPos();
            stage.addActor(cc);
            stage.addActor(cc.overheadName);

        }
        else if (cc != null){
            System.out.println("Unable to make customer");
        }
       return cc;
   }

   public void walkToPoint(float xPos, float yPos){
       this.posX = xPos;
       this.posY = yPos;
   }

   protected void updateOnRender(){
       for (int i = 0; i <= REPS; i++) {
           if (this.getX() != this.posX) {
               if (this.posX > this.getX()) {
                   this.setX(this.getX() + INCREMENT);
               } else if (this.posX < this.getX()) {
                   this.setX(this.getX() - INCREMENT);
               }
           }
           if (this.getY() != this.posY) {
               if (this.posY > this.getY()) {
                   this.setY(this.getY() + INCREMENT);
               } else if (this.posY < this.getY()) {
                   this.setY(this.getY() - INCREMENT);
               }
           }
           this.setOverheadPos();
       }
       this.currentCycleStage += 1;
       if(this.currentCycleStage > 15 && this.currentCycleStage <= 30){
           this.setImg(1,0);
       }
       else if(this.currentCycleStage > 30 && this.currentCycleStage <= 45){
           this.setImg(2,0);
       }
       else if(this.currentCycleStage > 45 && this.currentCycleStage <= 60){
           this.setImg(3,0);
       }
       if (this.currentCycleStage > 60){
           this.currentCycleStage = 0;
           this.setImg(0,0);
       }
   }

}
