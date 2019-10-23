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
        gold = Math.toIntExact((Long) object.get("money"));
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
        FAILED,
        INVALID
    }

    public boolean checkDeadlineExceeded(int currentDay){
        return deadline <= currentDay;
    }

    public boolean allDone(Farmer farmer){
        int gold = farmer.getMoney();

        //Wheat Farm
        int WheatSeed = farmer.wheatFarm.getSeeds();
        int WheatGreen = farmer.wheatFarm.getGreenWheat();
        int WheatRipe = farmer.wheatFarm.getRipeWheat();

        return (gold == endMoney )  && (WheatGreen == endWheatGreen)  && (WheatRipe == endWheatRipe) && (endWheatSeed == WheatSeed);
    }

    public objectiveResult checkAnswer(Farmio farmio){
        if (farmio.getFarmer().isHasfailedCurrentTask()) {
            return objectiveResult.INVALID;
        }
        int day = farmer.getDay();
        objectiveResult currentLevelState;
        if(checkDeadlineExceeded(day)){
            currentLevelState =  levelState.FAILED;
        }
        else {
            if (allDone(farmer)) {
                currentLevelState = levelState.ALLDONE;
            }
            else{
                currentLevelState = levelState.NOTDONE;
            }
        }
        return currentLevelState;
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