package Farmio;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Level {
    ArrayList<String> narratives;
    int startMoney;
    int startWheatSeed;
    int startWheatGreen;
    int startWheatRipe;
    int startChicken;
    int startChickenEggs;
    int startCow;
    int startCowMilk;
    int endMoney;
    int endWheatSeed;
    int endWheatGreen;
    int endWheatRipe;
    int endChicken;
    int endChickenEggs;
    int endCow;
    int endCowMilk;

    public Level(JSONObject object, Farmer farmer) {
        JSONArray array = (JSONArray) object.get("narratives");
        for (Object i : array) {
            narratives.add((String) i);
        }
        startMoney = farmer.money;
        startWheatSeed = farmer.wheatFarm.getSeeds();
        startWheatGreen = farmer.wheatFarm.getGreenWheat();
        startWheatRipe = farmer.wheatFarm.getRipeWheat();
//        startChicken;
//        startChickenEggs;
//        startCow;
//        startCowMilk;
//        endMoney;
//        endWheatSeed;
//        endWheatGreen;
//        endWheatRipe;
//        endChicken;
//        endChickenEggs;
//        endCow;
//        endCowMilk;
    }

    public ArrayList<String> getNarratives(){
        return narratives;
    }

    public String checkAnswer(){
        return "";
    }
}
