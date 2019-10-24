package Farmio;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {
    ArrayList<String> narratives;
    String filePath;
    int endSeeds;
    int endWheat;
    int endGrain;
    int endGold;
    int deadline;

    public Level(JSONObject object, Farmer farmer) {
        JSONArray array = (JSONArray) object.get("narratives");
        narratives = new ArrayList<>();
        for (Object i : array) {
            narratives.add((String) i);
        }
        filePath = (String) object.get("file_path");
        endGold = Math.toIntExact((Long) object.get("gold"));
        endSeeds = Math.toIntExact((Long) object.get("seeds"));
        endWheat = Math.toIntExact((Long) object.get("wheat"));
        endGrain = Math.toIntExact((Long) object.get("grain"));
        deadline = Math.toIntExact((Long) object.get("deadline"));
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

    private boolean checkDeadlineExceeded(int currentDay){
        return deadline < currentDay;
    }

    private boolean allDone(Farmer farmer){
        if (farmer.getLevel() == 1.1) {
            return farmer.getLocation().equals("Market");
        }
        int seeds = farmer.wheatFarm.getSeeds();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();
        int gold = farmer.getMoney();
        return (seeds >= endSeeds) && (wheat >= endWheat) && (grain >= endGrain) && (gold >= endGold);
    }

    public objectiveResult checkAnswer(Farmio farmio){
        if (farmio.getFarmer().isHasfailedCurrentTask()) {
            farmio.getFarmer().resetTaskFailed();
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
            } else if (checkDeadlineExceeded(day + 1)){
                currentLevelState = objectiveResult.FAILED;
            }
            else{
                currentLevelState = objectiveResult.NOT_DONE;
            }
        }
        //getFeedback(farmer, currentLevelState);
        return currentLevelState;
    }

    private String checkIncompleteObjectives(Farmer farmer){
       //compare the differences
       //check the level types
        String output = "";
        int seeds = farmer.wheatFarm.getSeeds();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();

        if(seeds != endSeeds){
            int balancedWheatSeed = endSeeds - seeds;
            output += "\nSeeds left :"  + balancedWheatSeed;
        }
        else {
            output += "\nSeeds Completed";
        }
        if(wheat != endWheat){
            int balancedWheatGreen = endWheat - wheat;
            output += "\n Wheat left :"  + balancedWheatGreen;
        }
        else {
            output += "\nWheat Completed";
        }
        if(grain != endGrain){
            int balancedWheatRipe = endGrain - grain;
            output += "\nGrain left :" + balancedWheatRipe;
        }
        else {
            output += "\nGrain Completed";
        }

        return output;
    }


    private String getFeedback(Farmer farmer, objectiveResult currentLevelState){
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
        goals.put("Gold", endGold);
        goals.put("Seeds", endSeeds);
        goals.put("Wheat", endWheat);
        goals.put("Grain", endGrain);
        return goals;
    }
}