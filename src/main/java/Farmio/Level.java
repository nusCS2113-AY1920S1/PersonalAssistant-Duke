package Farmio;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Level {
    private ArrayList<String> narratives;
    private String filePath;
    private String objective;
    private String hint;
    private int endSeeds;
    private int endSeedlings;
    private int endWheat;
    private int endGrain;
    private int endGold;
    private int deadline;

    private boolean detailedFeedbackProvided = false;

    private objectiveResult levelState;

    public Level(JSONObject object) {
        JSONArray array = (JSONArray) object.get("narratives");
        narratives = new ArrayList<>();
        for (Object i : array) {
            narratives.add((String) i);
        }
        filePath = (String) object.get("file_path");
        endGold = Math.toIntExact((Long) object.get("gold"));
        endSeeds = Math.toIntExact((Long) object.get("seeds"));
        endSeedlings = Math.toIntExact((Long) object.get("seedlings"));
        endWheat = Math.toIntExact((Long) object.get("wheat"));
        endGrain = Math.toIntExact((Long) object.get("grain"));
        deadline = Math.toIntExact((Long) object.get("deadline"));
        objective = (String) object.get("objective");
        hint = (String) object.get("hint");
    }

    /**
     * Get the narrative of the level
     * @return the list of narrative
     */
    public ArrayList<String> getNarratives(){
        return narratives;
    }

    /**
     * Get hint for completing the level
     * @return the hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Get path for simulation of the level's narrative
     * @return the file path
     */
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
        int seedlings = farmer.wheatFarm.getSeedlings();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();
        int gold = farmer.getGold();
        return (seeds >= endSeeds) && (wheat >= endWheat) && (grain >= endGrain) && (seedlings >= endSeedlings)
                && (gold >= endGold);
    }

    public objectiveResult checkAnswer(Farmio farmio){
        if (farmio.getFarmer().isHasfailedCurrentTask()) {
            farmio.getFarmer().resetTaskFailed();
            return objectiveResult.INVALID;
        }
        Farmer farmer = farmio.getFarmer();
        int day = farmer.getDay();
        if(checkDeadlineExceeded(day)){
            levelState = objectiveResult.FAILED;
        }
        else {
            if (allDone(farmer)) {
                levelState = objectiveResult.DONE;
            } else if (checkDeadlineExceeded(day + 1)){
                levelState = objectiveResult.FAILED;
            }
            else{
                levelState = objectiveResult.NOT_DONE;
            }
        }
        return levelState;
    }

    private String checkIncompleteObjectives(Farmer farmer){
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

    public String getDetailedFeedback( Farmio farmio){
        //states what went wrong with the level
        return "";
    }




    public String getFeedback(Farmio farmio){
        Farmer farmer = farmio.getFarmer();
        objectiveResult currentLevelState = farmio.getLevel().getLevelState();
        if(currentLevelState == objectiveResult.DONE){
            return "well done you have completed the level - all tasks has been completed succesfully";
        }

        else if(currentLevelState == objectiveResult.NOT_DONE){ //day completed but tasks not achieved succesfult
            String feedback = "tasks have yet to be completed";
            if(detailedFeedbackProvided){
                feedback += "detailed feedback : -- \n";
                feedback += checkIncompleteObjectives(farmer);
            }
            return feedback;
        }

        else if (currentLevelState == objectiveResult.FAILED){
            String feedback = "Oh no! The objectives were not met by the deadline! Level failed";
            if(detailedFeedbackProvided){
               feedback += "detailed feedback";
            }
            return feedback;
        }
        else if (currentLevelState == objectiveResult.INVALID){
            return "Oh no! There has been an error during code execution!";
        }
        return "";
    }

    public objectiveResult getLevelState(){
        return levelState;
    }

    /**
     * Get the list of goals to be completed
     * @return the list of goals
     */
    public Map<String, Integer> getGoals() {
        Map<String, Integer> goals = new HashMap< String,Integer>();
        goals.put("Gold", endGold);
        goals.put("Seeds", endSeeds);
        goals.put("Seedlings", endSeedlings);
        goals.put("Wheat", endWheat);
        goals.put("Grain", endGrain);
        return goals;
    }

    /**
     * Get the main objective of the level
     * @return the objective of the level
     */
    public String getObjective() {
        return objective;
    }
}