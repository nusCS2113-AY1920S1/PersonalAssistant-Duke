package Farmio;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.TaskList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Farmer {

    private final String JSON_KEY_MONEY = "money";
    private final String JSON_KEY_LEVEL = "level";
    private final String JSON_KEY_DAY = "day";
    private final String JSON_KEY_LOCATION = "location";
    private final String JSON_KEY_FARM_WHEAT = "farm_wheat";
    private final String JSON_KEY_FARM_CHICKEN = "farm_chicken";
    private final String JSON_KEY_FARM_COW = "farm_cow";
    private final String JSON_KEY_TASK_LIST = "task_list";
    private final String JSON_KEY_TASK_CURRENT = "task_current";
    private final String JSON_KEY_TASK_STATUS_FAIL = "task_status_fail";

    private int money;
    private double level;
    private int day;
    private String location;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected TaskList tasks;
    private int currentTask;
    private boolean hasfailedCurrentTask;
    private ArrayList<Double> levelList = new ArrayList<Double>(Arrays.asList(1.1,1.2,1.3,1.4,1.5,1.6,2.1,2.2));

    public Farmer() {
        this.money = 95;
        this.level = 1.1;
        this.day = 1;
        this.location = "WheatFarm";
        this.wheatFarm = new WheatFarm();
        this.chickenFarm = new ChickenFarm();
        this.cowFarm = new CowFarm();
        this.tasks = new TaskList();
        this.currentTask = -1;
        this.hasfailedCurrentTask = false;
    }

    public Farmer(JSONObject jsonObject) throws FarmioException {
        this.level = (Double) jsonObject.get(JSON_KEY_LEVEL);
        this.money = (int) (long) jsonObject.get(JSON_KEY_MONEY);
        this.day = (int) (long) jsonObject.get(JSON_KEY_DAY);
        this.location = (String) jsonObject.get(JSON_KEY_LOCATION);
        this.wheatFarm = new WheatFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_WHEAT));
        this.chickenFarm = new ChickenFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_CHICKEN));
        this.cowFarm = new CowFarm((JSONObject) jsonObject.get(JSON_KEY_FARM_COW));
        this.tasks = new TaskList((JSONArray) jsonObject.get(JSON_KEY_TASK_LIST));
        this.currentTask = (int) (long) jsonObject.get(JSON_KEY_TASK_CURRENT);
        this.hasfailedCurrentTask = (Boolean) jsonObject.get(JSON_KEY_TASK_STATUS_FAIL);
    }

    public Farmer(double level, int money, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, TaskList tasks) {
        this.level = level;
        this.money = money;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.tasks = tasks;
    }

    public int getMoney() {
        return money;
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
        if(level >= 1.3) {
            assets.put("Wheat", wheatFarm.getWheat());
            assets.put("Grain", wheatFarm.getGrain());
        }
        if (level >= 1.2) {
            assets.put("Seeds", wheatFarm.getSeeds());
        }
        if (level >= 1.1) {
            assets.put("Gold", money);
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

    public boolean isHasfailedCurrentTask() {
        if (hasfailedCurrentTask) {
            currentTask = -1;
            return true;
        }
        return false;
    }

    public void setTaskFailed() {
        hasfailedCurrentTask = true;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void spendMoney(int cost) {
        money -= cost;
    }

    public void earnMoney(int profit) {
        money += profit;
    }

    public int getCurrentTask() {
        return this.currentTask;
    }

    public double nextLevel(){
        if (level < levelList.get(levelList.size() - 1)) {
            level = levelList.get(levelList.indexOf(level) + 1);
            return level;
        }
        return 0;
    }

    public void startDay(Farmio farmio) throws FarmioException, FarmioFatalException {
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

    public void nextDay() throws FarmioException, FarmioFatalException {
        wheatFarm.growSeedlings();
        day += 1;
    }


    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put(JSON_KEY_LEVEL, level);
        obj.put(JSON_KEY_MONEY, money);
        obj.put(JSON_KEY_DAY, day);
        obj.put(JSON_KEY_LOCATION, location);
        obj.put(JSON_KEY_FARM_WHEAT, wheatFarm.toJSON());
        obj.put(JSON_KEY_FARM_CHICKEN, chickenFarm.toJSON());
        obj.put(JSON_KEY_FARM_COW, cowFarm.toJSON());
        obj.put(JSON_KEY_TASK_LIST, tasks.toJSON());
        obj.put(JSON_KEY_TASK_CURRENT, currentTask);
        obj.put(JSON_KEY_TASK_STATUS_FAIL, hasfailedCurrentTask);
        return obj;
    }
}