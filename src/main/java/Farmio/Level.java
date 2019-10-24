package Farmio;

import Places.Farm;
import Places.WheatFarm;
import UserCode.Conditions.BooleanCondition;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {
    ArrayList<String> narratives;
    String filePath;
    int endWheatSeed;
    int endWheatGreen;
    int endWheatRipe;
    int endChicken;
    int endChickenEggs;
    int endCow;
    int endCowMilk;
    int location;
    int gold;
    int deadline;

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
        deadline = Math.toIntExact((Long) object.get("deadline"));


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

        //Wheat Farm
        int WheatSeed = farmer.wheatFarm.getSeeds();
        int WheatGreen = farmer.wheatFarm.getGreenWheat();
        int WheatRipe = farmer.wheatFarm.getWheat();
        return (WheatGreen == endWheatGreen)  && (WheatRipe == endWheatRipe) && (endWheatSeed == WheatSeed);


        //Cow Farm
    }

    public objectiveResult checkAnswer(Farmio farmio){
        if (farmio.getFarmer().isHasfailedCurrentTask()) {
            return objectiveResult.INVALID;
        }

        Farmer farmer = farmio.getFarmer();
        int day = farmer.getDay();
        objectiveResult currentLevelState;
        if(checkDeadlineExceeded(day)){
            currentLevelState =  objectiveResult.FAILED;
            getFeedback(farmer, currentLevelState);
        }
        else {
            if (allDone(farmer)) {
                currentLevelState = objectiveResult.DONE;
            }
            else{
                currentLevelState = objectiveResult.NOT_DONE;
            }
        }
        //getFeedback(farmer, currentLevelState);
        return currentLevelState;
    }

    public String checkIncompleteObjectives (Farmer farmer){
       //compare the differences
       //check the level types
        String output = "";
        int WheatSeed = farmer.wheatFarm.getSeeds();
        int WheatGreen = farmer.wheatFarm.getGreenWheat();
        int WheatRipe = farmer.wheatFarm.getWheat();

        if(WheatSeed != endWheatSeed){
            int balancedWheatSeed = endWheatSeed - WheatSeed;
            output += "\nWheatSeed left :"  + balancedWheatSeed;
        }
        else {
            output += "\nWheatSeed Completed";
        }
        if(WheatGreen != endWheatGreen){
            int balancedWheatGreen = endWheatGreen - WheatGreen;
            output += "\n WheatGreen left :"  + balancedWheatGreen;
        }
        else {
            output += "\nWheatGreen Completed";
        }
        if(WheatRipe != endWheatRipe){
            int balancedWheatRipe = endWheatRipe - WheatRipe;
            output += "\nWheatRipe left :" + balancedWheatRipe;
        }
        else {
            output += "\nWheatRipe Completed";
        }

        return output;
    }


    public String getFeedback( Farmer farmer, objectiveResult currentLevelState ){
       //get Feedback on whats not completed
        //need to complete.
        if(currentLevelState == objectiveResult.DONE){
            return "all tasks has been completed";
        }

        else if(currentLevelState == objectiveResult.NOT_DONE){
            return checkIncompleteObjectives(farmer);
        }

        else if (currentLevelState == objectiveResult.FAILED){
            return "level failed";
        }
        return "";
    }



    public Map<String, Integer> getGoals() {
        Map<String, Integer> goals = new HashMap< String,Integer>();
        goals.put("Gold", gold);
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