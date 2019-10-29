package farmio;

import exceptions.FarmioException;
import exceptions.FarmioFatalException;
import places.ChickenFarm;
import places.CowFarm;
import places.WheatFarm;
import usercode.tasks.TaskList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Farmer {

    private final String JSON_KEY_GOLD = "gold";
    private final String JSON_KEY_LEVEL = "level";
    private final String JSON_KEY_DAY = "day";
    private final String JSON_KEY_LOCATION = "location";
    private final String JSON_KEY_FARM_WHEAT = "farm_wheat";
    private final String JSON_KEY_FARM_CHICKEN = "farm_chicken";
    private final String JSON_KEY_FARM_COW = "farm_cow";
    private final String JSON_KEY_TASK_LIST = "task_list";
    private final String JSON_KEY_TASK_CURRENT = "task_current";
    private final String JSON_KEY_TASK_STATUS_FAIL = "task_status_fail";
    private final String JSON_KEY_NAME = "name";

    private int gold;
    private double level;
    private int day;
    private String name;
    private String location;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected TaskList tasks;
    private int currentTask;
    private boolean hasfailedCurrentTask;
    private ArrayList<Double> levelList = new ArrayList<Double>(Arrays.asList(1.1,1.2,1.3,1.4,1.5,1.6,2.1,2.2));

    public Farmer() {
        this.gold = 10;
        this.level = 1.1; // temp relaced
        this.day = 1;
        this.location = "WheatFarm";
        this.wheatFarm = new WheatFarm();
        this.chickenFarm = new ChickenFarm();
        this.cowFarm = new CowFarm();
        this.tasks = new TaskList();
        this.currentTask = -1;
        this.hasfailedCurrentTask = false;
        this.name = "name";
    }

    public Farmer(JSONObject jsonObject) throws FarmioException {
        try {
            this.level = (Double) jsonObject.get(JSON_KEY_LEVEL);
            this.gold = (int) (long) jsonObject.get(JSON_KEY_GOLD);
            this.day = (int) (long) jsonObject.get(JSON_KEY_DAY);
            this.location = (String) jsonObject.get(JSON_KEY_LOCATION);
            this.wheatFarm = new WheatFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_WHEAT));
            this.chickenFarm = new ChickenFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_CHICKEN));
            this.cowFarm = new CowFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_COW));
            this.tasks = new TaskList((JSONArray) jsonObject.get(JSON_KEY_TASK_LIST));
            this.currentTask = (int) (long) jsonObject.get(JSON_KEY_TASK_CURRENT);
            this.hasfailedCurrentTask = (Boolean) jsonObject.get(JSON_KEY_TASK_STATUS_FAIL);
            this.name = (String) jsonObject.get(JSON_KEY_NAME);
        } catch (Exception e) {
            throw new FarmioException("Game save corrupted!");
        }
    }

    public Farmer(double level, int gold, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, TaskList tasks, String name) {
        this.level = level;
        this.gold = gold;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.tasks = tasks;
        this.name = name;
    }

    public void inputName(String username) {
        name = username;
    }

    public String getName() {
        return name;
    }

    public int getGold() {
        return gold;
    }

    public boolean hasGold() {
        return gold > 0;
    }

    public double getLevel() {
        return level;
    }

    public int getDay() {
        return day;
    }

    public String getLocation() {
        return location;
    }

    public void changeLocation(String newLocation) {
        location = newLocation;
    }

    public Map<String, Integer> getAssets() {
        Map<String, Integer> assets = new HashMap<>();

        if (level >= 3) {
        }
        if (level >= 2) {
        }
        if (level >= 1.4) {
            assets.put("Wheat", wheatFarm.getWheat());
            assets.put("Grain", wheatFarm.getGrain());
        }
        if(level >= 1.3) {
            assets.put("Seedlings", wheatFarm.getSeedlings());
        }
        if (level >= 1.2) {
            assets.put("Seeds", wheatFarm.getSeeds());
        }
        if (level >= 1.1) {
            assets.put("Gold", gold);
        }
        return assets;
    }

    public WheatFarm getWheatFarm() { return  wheatFarm; }

    public ChickenFarm getChickenFarm() {
        return chickenFarm;
    }

    public CowFarm getCowFarm() {
        return cowFarm;
    }

    public TaskList getTasks() {
        return tasks;
    }

    /**
     * Checks if curent task has failed and resets current task
     * @return true if current task has failed and false otherwise
     */
    public boolean isHasfailedCurrentTask() {
        if (hasfailedCurrentTask) {
            currentTask = -1;
            return true;
        }
        return false;
    }

    /**
     * Reverts task list execution failure
     */
    public void resetTaskFailed() {hasfailedCurrentTask = false;}

    /**
     * Sets task list execution as failed
     */
    public void setTaskFailed() {
        hasfailedCurrentTask = true;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void spendGold(int cost) {
        gold -= cost;
    }

    public void earnGold(int profit) {
        gold += profit;
    }

    /**
     * Gets the index of the current task in execution
     * @return the index of the current task
     */
    public int getCurrentTask() {
        return this.currentTask;
    }

    /**
     * Increases the level
     * @return the next level number. 0 if current level is not registered or game has ended
     */
    public double nextLevel(){
        if (level < levelList.get(levelList.size() - 1)) {
            level = levelList.get(levelList.indexOf(level) + 1);
            return level;
        }
        return 0;
    }

    /**
     * Takes care of TaskList execution and handles task failure
     * @param farmio The game where the day should be started
     * @throws FarmioFatalException if file from action's simulation cannot be found
     */
    public void startDay(Farmio farmio) throws FarmioFatalException {
        try {
            for (int i = 0; i < tasks.size(); i++) {
                this.currentTask = i;
                tasks.get(i).execute(farmio);
            }
        } catch (FarmioException e) {
            farmio.setStage(Farmio.Stage.LEVEL_FAILED);
        } finally {
            this.currentTask = -1;
        }
    }

    public void nextDay() {
        wheatFarm.growSeedlings();
        day += 1;
    }

    public JSONObject toJson(){
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEY_LEVEL, level);
        obj.put(JSON_KEY_GOLD, gold);
        obj.put(JSON_KEY_DAY, day);
        obj.put(JSON_KEY_LOCATION, location);
        obj.put(JSON_KEY_FARM_WHEAT, wheatFarm.toJson());
        obj.put(JSON_KEY_FARM_CHICKEN, chickenFarm.toJson());
        obj.put(JSON_KEY_FARM_COW, cowFarm.toJson());
        obj.put(JSON_KEY_TASK_LIST, tasks.toJson());
        obj.put(JSON_KEY_TASK_CURRENT, currentTask);
        obj.put(JSON_KEY_TASK_STATUS_FAIL, hasfailedCurrentTask);
        obj.put(JSON_KEY_NAME, name);
        return obj;
    }

    public JSONObject updateJSON(JSONObject object){
        object.replace(JSON_KEY_TASK_LIST, tasks.toJson());
        return object;
    }
}