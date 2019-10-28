package Farmio;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

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
    public String modelAnswer;

    private boolean detailedFeedbackProvided = true;

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
        modelAnswer = (String) object.get("modelAnswer");
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
        //todo -Level-dependant objective checker
        String output = "";
        int seeds = farmer.wheatFarm.getSeeds();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();

        if(seeds != endSeeds){
            int balancedWheatSeed = endSeeds - seeds;
            output += " Seeds left :"  + balancedWheatSeed;
        }
        else {
            output += " Seeds Completed";
        }
        if(wheat != endWheat){
            int balancedWheatGreen = endWheat - wheat;
            output += " | Wheat left :"  + balancedWheatGreen;
        }
        else {
            output += " | Wheat Completed";
        }
        if(grain != endGrain){
            int balancedWheatRipe = endGrain - grain;
            output += " | Grain left :" + balancedWheatRipe;
        } else {
            output += " | Grain Completed";
        }

        return output;
    }

    //need to rename this lol
    public List<String> convertStringToList(String modelAnswer){
        //todo build a much more comprehensive parser for level
        String[] taskItems = modelAnswer.split("|");
        List<String> modelTaskList =new ArrayList<String>(Arrays.asList(taskItems));
        return  modelTaskList;
    }

    public String levelParser(String userActions, String modelAns){
        //separate user list into arraylist , separate model ans into subsections
        List<String> modelTaskList = convertStringToList(modelAnswer);
        //compare the two given




       return "model ans";
    }

    public String getPermutationFeedback(Farmio farmio, double levelNumber){
        //todo convert to some sort of metric for future iterations
        //farmio.getFarmer().tasks.toStringArray();
        //include some sort of level parser
        //levelParser(userAction, modelAns);
        return "getPermutation Feedback";
}

    //only applicable if level fails
    public String getDetailedFeedback( Farmio farmio){
        double levelNumber = farmio.getFarmer().getLevel();
        String output = "";
        output += " The objective of this level was to " + objective;
        output += "\nUnfortunately you were unable to complete within the allocated time of " + deadline + " days";

        //Iterate through task list
        output += "\nYour actions ";
        output += farmio.getFarmer().tasks.toString();

        output += getPermutationFeedback(farmio, levelNumber);

        return output;
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
                //add enter and day end
                feedback += "detailed feedback : -- \n";
                feedback += checkIncompleteObjectives(farmer);
                feedback += "\n Press [ENTER] to continue the game or [RESET] to restart the level";
            }
            return feedback;
        }

        else if (currentLevelState == objectiveResult.FAILED){
            String feedback = "Oh no! The objectives were not met by the deadline! Level failed ! \n";
            if(detailedFeedbackProvided){
               feedback +=  getDetailedFeedback(farmio); // only applicable for failed levels
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