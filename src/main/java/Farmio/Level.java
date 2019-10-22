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
    int location;

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
//        location = Math.toIntExact((Long) object.get("location"));
    }

    public ArrayList<String> getNarratives(){
        return narratives;
    }

    public String getPath(){
        return filePath;
    }

    public enum objectiveResult {
        NOT_DONE,
        DONE,
        FAILED
    }

    public objectiveResult checkAnswer(Farmio farmio){
        //if (...)
        return objectiveResult.NOT_DONE;
    }

    public Map<String, Integer> getGoals() {
        Map<String, Integer> goals = new HashMap< String,Integer>();
        goals.put("Gold", endMoney);
        goals.put("Seeds", endWheatSeed);
        goals.put("Wheat", endWheatGreen);
        goals.put("Grain", endWheatRipe);
        goals.put("Chicken", endChicken);
        goals.put("Egg", endChickenEggs);
        goals.put("Cow", endCow);
        goals.put("Milk", endCowMilk);
        return goals;
    }
}