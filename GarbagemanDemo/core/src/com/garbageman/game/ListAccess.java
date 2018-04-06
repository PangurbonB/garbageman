package com.garbageman.game;

import com.badlogic.gdx.graphics.Color;
import com.garbageman.game.cooked.Burrito;
import com.garbageman.game.cooked.Cake;
import com.garbageman.game.cooked.Hotdog;
import com.garbageman.game.cooked.Pizza;
import com.garbageman.game.cooked.Sandwich;
import com.garbageman.game.cooked.Soup;
import com.garbageman.game.cooked.Sushi;
import com.garbageman.game.customers.Justin;
import com.garbageman.game.garbage.AllTheBadHalloweenCandy;
import com.garbageman.game.garbage.AppleCore;
import com.garbageman.game.garbage.BagOfFlour;
import com.garbageman.game.garbage.BagOfSugar;
import com.garbageman.game.garbage.BananaPeel;
import com.garbageman.game.garbage.Bean;
import com.garbageman.game.garbage.Bread;
import com.garbageman.game.garbage.CrowWithOddEyeInfection;
import com.garbageman.game.garbage.DayOldDonut;
import com.garbageman.game.garbage.DeadRat;
import com.garbageman.game.garbage.DirtyKitchenSponge;
import com.garbageman.game.garbage.GarbageBag;
import com.garbageman.game.garbage.GarbageJuice;
import com.garbageman.game.garbage.GhostPepper;
import com.garbageman.game.garbage.HandfulOfAnts;
import com.garbageman.game.garbage.HomelessBeardShavings;
import com.garbageman.game.garbage.IceCream;
import com.garbageman.game.garbage.Ketchup;
import com.garbageman.game.garbage.Leaf;
import com.garbageman.game.garbage.Lettuce;
import com.garbageman.game.garbage.McdFries;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.McdSoda;
import com.garbageman.game.garbage.Milkshake;
import com.garbageman.game.garbage.MotorOil;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.MysteryMeat;
import com.garbageman.game.garbage.OldNewspaper;
import com.garbageman.game.garbage.PitaBread;
import com.garbageman.game.garbage.Pork;
import com.garbageman.game.garbage.RabbitFoot;
import com.garbageman.game.garbage.RazorBladeApple;
import com.garbageman.game.garbage.Salad;
import com.garbageman.game.garbage.Seaweed;
import com.garbageman.game.garbage.Smarties;
import com.garbageman.game.garbage.SquirrelTail;
import com.garbageman.game.garbage.Strawberry;
import com.garbageman.game.garbage.SugarCube;
import com.garbageman.game.garbage.SzechuanSauce;
import com.garbageman.game.garbage.ToiletPaper;
import com.garbageman.game.garbage.TornUpPamphlet;
import com.garbageman.game.garbage.Tortilla;
import com.garbageman.game.garbage.Trash;
import com.garbageman.game.garbage.TyroneBible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by bzonick5979 on 4/2/2018.
 */

public final class ListAccess {

    private static Trash objTemp = new McdHamburger();

    public static void updateMaps(){
        colorMap.put("Common", Garbageman.COMMON);
        colorMap.put("Uncommon", Garbageman.UNCOMMON);
        colorMap.put("Rare", Garbageman.RARE);
        colorMap.put("Very Rare", Garbageman.VERYRARE);
        colorMap.put("Legendary", Garbageman.LEGENDARY);
        colorMap.put("Bought", Garbageman.BOUGHT);
        colorMap.put("???", Garbageman.BEYOND_COMPREHENSION);

        //nast bar:
        colorMap.put("Red", Garbageman.RED);
        colorMap.put("Orange", Garbageman.ORANGE);
        colorMap.put("Yellow", Garbageman.YELLOW);
        colorMap.put("Green", Garbageman.GREEN);

        //type filler
        //"Veggies", "Meats", "Wraps", "Fillers", "Sweeteners", "Sauces"
        typeMap.put("Veggies", objTemp.VEGGIE);
        typeMap.put("Meats", objTemp.MEAT);
        typeMap.put("Wraps", objTemp.WRAP);
        typeMap.put("Fillers", objTemp.FILLER);
        typeMap.put("Sweeteners", objTemp.SWEETENER);
        typeMap.put("Sauces", objTemp.SAUCE);
    }

    public static ArrayList<Class> garbageItems = new ArrayList<Class>(Arrays.asList(
            AppleCore.class,
            BagOfSugar.class,
            BananaPeel.class,
            Bean.class,
            Bread.class,
            CrowWithOddEyeInfection.class,
            DirtyKitchenSponge.class,
            HandfulOfAnts.class,
            HomelessBeardShavings.class,
            Ketchup.class,
            Leaf.class,
            Lettuce.class,
            McdFries.class,
            McdHamburger.class,
            MysteryEyeball.class,
            OldNewspaper.class,
            Pork.class,
            RabbitFoot.class,
            Salad.class,
            Smarties.class,
            Strawberry.class,
            ToiletPaper.class,
            DeadRat.class,
            DayOldDonut.class,
            BagOfFlour.class,
            GarbageBag.class,
            GarbageJuice.class,
            GhostPepper.class,
            IceCream.class,
            McdSoda.class,
            Milkshake.class,
            MotorOil.class,
            MysteryMeat.class,
            PitaBread.class,
            RazorBladeApple.class,
            Seaweed.class,
            SquirrelTail.class,
            SugarCube.class,
            SzechuanSauce.class,
            TornUpPamphlet.class,
            AllTheBadHalloweenCandy.class,
            Tortilla.class,
            TyroneBible.class
    ));

    /*public static ArrayList<Class> safeGarbageItems = new ArrayList<Class>(Arrays.asList(
            AppleCore.class,
            BagOfSugar.class,
            BananaPeel.class,
            Bean.class,
            Bread.class,
            CrowWithOddEyeInfection.class,
            DirtyKitchenSponge.class,
            HandfulOfAnts.class,
            //HomelessBeardShavings.class,
            Ketchup.class,
            Leaf.class,
            Lettuce.class,
            McdFries.class,
            McdHamburger.class,
            //MysteryEyeball.class,
            OldNewspaper.class,
            Pork.class,
            RabbitFoot.class,
            Salad.class,
            Smarties.class,
            Strawberry.class,
            ToiletPaper.class,
            //DeadRat.class,
            GarbageBag.class
    ));*/

    public static ArrayList<Class> safeModeExclusions = new ArrayList<Class>(Arrays.asList(
            HomelessBeardShavings.class,
            MysteryEyeball.class,
            DeadRat.class,
            RazorBladeApple.class,
            TornUpPamphlet.class
    ));

    public static ArrayList<Class> customers = new ArrayList<Class>(Arrays.asList(
            Justin.class
    ));

    public static ArrayList<Class> foodItems = new ArrayList<Class>(Arrays.asList(
            Burrito.class,
            Cake.class,
            Hotdog.class,
            Pizza.class,
            Sandwich.class,
            Soup.class,
            Sushi.class
    ));

    public static java.util.Map<String, Color> colorMap = Collections.synchronizedMap(new HashMap());
    public static java.util.Map<String, Integer> typeMap = Collections.synchronizedMap(new HashMap());

}
