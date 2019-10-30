package farmio;

import javafx.util.Pair;
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
    public ArrayList<String> successfulFeedback;

    private boolean detailedFeedbackProvided = false;

    private ObjectiveResult levelState;

    /**
     *  Level Constructor .
     * @param object something
     * @param name something
     */
    public Level(JSONObject object, String name) {
        JSONArray array = (JSONArray) object.get("narratives");
        narratives = new ArrayList<>();
        for (Object i : array) {
            String line = (String) i;
            line = line.replace("+", name);
            narratives.add(line);
        }

        JSONArray feedbackarray = (JSONArray) object.get("feedback");
        successfulFeedback = new ArrayList<>();
        for (Object i : feedbackarray) {
            successfulFeedback.add((String) i);
        }

        filePath = (String) object.get("file_path");
        endGold = Math.toIntExact((Long) object.get("gold"));
        endSeeds = Math.toIntExact((Long) object.get("seeds"));
        endSeedlings = Math.toIntExact((Long) object.get("seedlings"));
        endWheat = Math.toIntExact((Long) object.get("wheat"));
        endGrain = Math.toIntExact((Long) object.get("grain"));
        deadline = Math.toIntExact((Long) object.get("deadline"));
        objective = (String) object.get("objective");
        hint = ((String) object.get("hint")).replace("+", name);
        modelAnswer = (String) object.get("modelAnswer");

    }

    /**
     * Get the narrative of the level .
     * @return the list of narrative
     */
    public ArrayList<String> getNarratives() {
        return narratives;
    }

    /** .
     * Get hint for completing the level
     * @return the hint
     */
    public String getHint() {
        return hint;
    }

    /**
     * Get path for simulation of the level's narrative.
     * @return the file path
     */
    public String getPath() {
        return filePath;
    }

    public enum ObjectiveResult {
        NOT_DONE,
        DONE,
        FAILED,
        INVALID
    }

    private boolean checkDeadlineExceeded(int currentDay) {
        return deadline < currentDay;
    }

    private boolean allDone(Farmer farmer) {
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

    /**
     * Summary .
     * @param farmio something
     * @return
     */
    public ObjectiveResult checkAnswer(Farmio farmio) {
        if (farmio.getFarmer().isHasfailedCurrentTask()) {
            farmio.getFarmer().resetTaskFailed();
            return ObjectiveResult.INVALID;
        }
        Farmer farmer = farmio.getFarmer();
        ObjectiveResult levelState;
        int day = farmer.getDay();
        if (checkDeadlineExceeded(day)) {
            levelState = ObjectiveResult.FAILED;
        } else {
            if (allDone(farmer)) {
                levelState = ObjectiveResult.DONE;
            } else if (checkDeadlineExceeded(day + 1)) {
                levelState = ObjectiveResult.FAILED;
            } else {
                levelState = ObjectiveResult.NOT_DONE;
            }
        }
        return levelState;
    }

    //need to convert into a listString format

    /**
     * Summary.
     * @param farmer something
     * @return
     */
    private String checkIncompleteObjectives(Farmer farmer) {
        //todo -Level-dependant objective checker
        String output = "";
        int seeds = farmer.wheatFarm.getSeeds();
        int wheat = farmer.wheatFarm.getWheat();
        int grain = farmer.wheatFarm.getGrain();

        if (seeds != endSeeds) {
            int balancedWheatSeed = endSeeds - seeds;
            output += " Seeds left :"  + balancedWheatSeed;
        } else {
            output += " Seeds Completed";
        }

        if (wheat != endWheat) {
            int balancedWheatGreen = endWheat - wheat;
            output += " | Wheat left :"  + balancedWheatGreen;
        } else {
            output += " | Wheat Completed";
        }

        if (grain != endGrain) {
            int balancedWheatRipe = endGrain - grain;
            output += " | Grain left :" + balancedWheatRipe;
        } else {
            output += " | Grain Completed";
        }

        return output;
    }

    /**
     * summary.
     * @param modelAnswer something
     * @return
     */
    public List<String> convertStringToList(String modelAnswer) {

        //todo build a much more comprehensive parser for level
        String[] taskItems = modelAnswer.split("|");
        List<String> modelTaskList = new ArrayList<String>(Arrays.asList(taskItems));
        return  modelTaskList;
    }

    //todo- logical error correction
    /**
     * Summary.
     * @param taskList something
     * @return
     */
    public List<String> convertTaskListFormat(List<String> taskList) {

        List<String> splitTaskList = new ArrayList<String>();
        for (String taskListItems: taskList) {
            String removedNumbering = taskListItems.substring(taskListItems.indexOf(".") + 1);
            removedNumbering.trim();// removed the numbering

            //separate based on actions - todo check how its divided
            //String[] splitString = taskListItems.split("\\s+");
            String[] splitString = taskListItems.split("(?<!\\G\\w)\\s"); //splits on every second space
            //should be after every 3 spacesh

            splitTaskList.add(splitString[0]);
            if (splitString[1] != null && !splitString[1].isEmpty()) {
                splitTaskList.add(splitString[1]);
            }

        }
        return splitTaskList;
    }

    /* //todo - possible refactor/ creation of levelParsed Class
    //need to refactor to a levelParser class
    public String levelParser(List<String> userTaskList, String modelAnswer){
        //separate user list into arraylist , separate model ans into subsections
        List<String> modelTaskList = convertStringToList(modelAnswer);
        List<String> modifieduserTaskList = convertTaskListFormat(userTaskList);
        //compare the two given
        //i need to print out what it looks like on the
       return "model ans";
    }
    */

    //todo complete getPermutation Feedback Implementation

    /**
     * Something.
     * @param farmio something
     * @param levelNumber something
     * @return
     */
    public String getPermutationFeedback(Farmio farmio,double levelNumber) {
        //todo convert to some sort of metric for future iterations
        List<String> userTaskList = farmio.getFarmer().tasks.toStringArray();
        List<String> modelTaskList = convertStringToList(modelAnswer);
        List<String> modifieduserTaskList = convertTaskListFormat(userTaskList);

        if (levelNumber == 1.4) {
            for (int i = 0; i < userTaskList.size(); i++) {
                //action checker
                String[] userTaskString = userTaskList.get(i).split("\\s+");
                String[] modelTaskString = modelTaskList.get(i).split("\\s+");
                /*
                String[] userTask = userTaskString.split("\\s+");
                String[] modelTaskString = modelTaskString.split("\\s+");
                */
            }
        }

        //return  levelParser(userTaskList, modelAnswer);
        return "getPermutation Feedback";
    }

    //only applicable if level fails
    //todo convert detailed feedback to List<String>

    /**
     * Summary.
     * @param farmio something
     * @return
     */
    public String getDetailedFeedback(Farmio farmio) {
        double levelNumber = farmio.getFarmer().getLevel(); // unsure if this is needed rn
        String output = "";
        output += " The objective of this level was to " + objective;
        output += "\nUnfortunately you were unable to complete within the allocated time of " + deadline + " days";

        //Iterate through task list
        output += "\nYour actions ";
        output += farmio.getFarmer().tasks.toString();

        //todo complete perm
        //output += getPermutationFeedback(farmio, levelNumber);

        return output;
    }

    /**
     * Summary.
     * @return something
     */
    public List<String> getSuccessfulFeedback() {
        List<String> output = new ArrayList<String>();
        //String output = "";
        for (String x: successfulFeedback) {
            output.add(x);
        }
        return output;
    }

    /**
     * Summary.
     * @param farmio something
     * @return
     */

    public List<String> getFeedback(Farmio farmio) {
        Farmer farmer = farmio.getFarmer();
        ObjectiveResult currentLevelState = farmio.getLevel().getLevelState();

        List<String> output = new ArrayList<String>();
        if (currentLevelState == ObjectiveResult.DONE) {
            output.addAll(getSuccessfulFeedback());
            output.add("well done you have completed the level - all tasks has been completed succesfully");
            return output;
        } else if (currentLevelState == ObjectiveResult.NOT_DONE) { //day completed but tasks not achieved succesfult
            String feedback = "tasks have yet to be completed";
            output.add(feedback);
            if (detailedFeedbackProvided) {
                //add enter and day end
                feedback += "detailed feedback : -- \n";
                feedback += checkIncompleteObjectives(farmer);
            }
            output.add("Press [ENTER] to continue the game or [RESET] to restart the level");
            return output;
        } else if (currentLevelState == ObjectiveResult.FAILED) {
            String feedback = "Oh no! The objectives were not met by the deadline! Level failed ! \n";
            output.add(feedback);
            if (detailedFeedbackProvided) { //todo -redo this code
                // feedback +=  getDetailedFeedback(farmio); // only applicable for failed levels
            }
            return output;
        } else if (currentLevelState == ObjectiveResult.INVALID) {
            output.add("Oh no! There has been an error during code execution!");
            return  output;
        }
        return output;
    }

    public ObjectiveResult getLevelState() {
        return levelState;
    }

    /**
     * Get the list of goals to be completed.
     * @return the list of goals
     */
    public Map<String,Integer> getGoals() {
        Map<String,Integer> goals = new HashMap<String,Integer>();
        goals.put("Gold", endGold);
        goals.put("Seeds", endSeeds);
        goals.put("Seedlings", endSeedlings);
        goals.put("Wheat", endWheat);
        goals.put("Grain", endGrain);
        return goals;
    }

    /**
     * Get the main objective of the level.
     * @return the objective of the level
     */
    public String getObjective() {
        return objective;
    }

}