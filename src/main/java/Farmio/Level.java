package Farmio;

import Places.Farm;
import UserCode.Conditions.BooleanCondition;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {
    ArrayList<String> narratives;
    String filePath;
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
        narratives = new ArrayList<>();
        for (Object i : array) {
            narratives.add((String) i);
        }
        filePath = (String) object.get("file_path");
        endMoney = Math.toIntExact((Long) object.get("money"));
        endWheatSeed = Math.toIntExact((Long) object.get("wheat_seed"));
        endWheatGreen = Math.toIntExact((Long) object.get("wheat_green"));
        endWheatRipe = Math.toIntExact((Long) object.get("wheat_ripe"));
        endChicken = 0;
        endChickenEggs = 0;
        endCow = 0;
        endCowMilk = 0;
    }

    public ArrayList<String> getNarratives(){
        return narratives;
    }

    public String getPath(){
        return filePath;
    }

    public boolean checkAnswer(Farmio farmio){
        farmio.getUi().show("Checking answers now");
        return true;
    }

    public Map<String, Integer> getGoals() {
        Map<String, Integer> dummy = new HashMap< String,Integer>();
        dummy.put("Gold", endMoney);
        dummy.put("Seeds", endWheatSeed);
        dummy.put("Wheat", endWheatGreen);
        dummy.put("Grain", endWheatRipe);
        dummy.put("Chicken", endChicken);
        dummy.put("Egg", endChickenEggs);
        dummy.put("Cow", endCow);
        dummy.put("Milk", endCowMilk);
        return dummy;
    }
}
