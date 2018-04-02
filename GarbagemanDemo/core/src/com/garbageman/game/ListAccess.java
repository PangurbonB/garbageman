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
import com.garbageman.game.garbage.HandfulOfAnts;
import com.garbageman.game.garbage.HomelessBeardShavings;
import com.garbageman.game.garbage.Ketchup;
import com.garbageman.game.garbage.Leaf;
import com.garbageman.game.garbage.Lettuce;
import com.garbageman.game.garbage.McdFries;
import com.garbageman.game.garbage.McdHamburger;
import com.garbageman.game.garbage.MysteryEyeball;
import com.garbageman.game.garbage.OldNewspaper;
import com.garbageman.game.garbage.Pork;
import com.garbageman.game.garbage.RabbitFoot;
import com.garbageman.game.garbage.Salad;
import com.garbageman.game.garbage.Smarties;
import com.garbageman.game.garbage.Strawberry;
import com.garbageman.game.garbage.ToiletPaper;
import com.garbageman.game.garbage.Trash;

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

    public static Class[] garbageItems = {
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
    };

    public static Class[] safeGarbageItems = {
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
    };

    public static Class[] customers = {
            Justin.class,
    };

    public static Class[] foodItems = {
            Burrito.class,
            Cake.class,
            Hotdog.class,
            Pizza.class,
            Sandwich.class,
            Soup.class,
            Sushi.class,
    };

    public static java.util.Map<String, Color> colorMap = Collections.synchronizedMap(new HashMap());
    public static java.util.Map<String, Integer> typeMap = Collections.synchronizedMap(new HashMap());

}
