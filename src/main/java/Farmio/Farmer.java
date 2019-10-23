package Farmio;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.Task;
import UserCode.Tasks.TaskList;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Farmer {
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
    private ArrayList<Double> levelList = new ArrayList<Double>( Arrays.asList(1.1,1.2,1.3,1.4,1.5,1.6,2.1,2.2) );

    public Farmer() {
        this.money = 10;
        this.level = 1.6;
        this.day = 1;
        this.location = "WheatFarm";
        this.wheatFarm = new WheatFarm(); //TODO: create wheatFarm subclass
        this.chickenFarm = new ChickenFarm(); //TODO: create chickenFarm subclass
        this.cowFarm = new CowFarm(); //TODO: create cowFarm subclass
        this.tasks = new TaskList();
        this.currentTask = -1;
        this.hasfailedCurrentTask = false;
    }

    public Farmer(JSONObject jsonObject) {
        this.level = (Integer) jsonObject.get("level");
        this.money = (Integer) jsonObject.get("money");
        this.wheatFarm = new WheatFarm((JSONObject) jsonObject.get("farm_wheat"));
        this.chickenFarm = new ChickenFarm((JSONObject) jsonObject.get("farm_chicken"));
        this.cowFarm = new CowFarm((JSONObject) jsonObject.get("farm_cow"));
        //this.tasks = new TaskList((JSONArray) jsonObject.get("task_list"));
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

    public double getLevel() { return level;}

    public int getDay() {return day;}

    public String getLocation() {return location;}

    public void changeLocation(String newLocation) {
        location = newLocation;
    }

    public Map<String, Integer> getAssets() {
        Map<String, Integer> assets = new HashMap<>();

        if (level >= 3) {
        }
        if(level >= 2) {

        }
        if(level >= 1.3) {
            assets.put("Wheat", wheatFarm.getRipeWheat());
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

    public ChickenFarm getChickenFarm() { return chickenFarm; }

    public CowFarm getCowFarm() { return cowFarm; }

    public TaskList getTasks() { return tasks; }

    public boolean isHasfailedCurrentTask() {
        if (hasfailedCurrentTask) {
            currentTask = -1;
            return true;
        }
        return false;
    }
    public void setTaskFailed() {hasfailedCurrentTask = true;}

    public void setMoney(int money){
        this.money = money;
    }

    public void spendMoney(int cost){
        money -= cost;
    }

    public void earnMoney(int profit){
        money += profit;
    }

    public int getCurrentTask() {return this.currentTask;}

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

    public void nextDay(Farmio farmio) throws FarmioException, FarmioFatalException {
        wheatFarm.growSeedlings();
    }


    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("level", level);
        obj.put("money", money);
        obj.put("day", day);
        obj.put("location", location);
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        obj.put("task_list", tasks.toJSON());
        obj.put("task_current", currentTask);
        obj.put("task_failed_status", hasfailedCurrentTask);
        return obj;
    }
}