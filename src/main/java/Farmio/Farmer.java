package Farmio;

import Exceptions.FarmioException;
import Exceptions.FarmioFatalException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.TaskList;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.util.ArrayList;
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

    public Farmer() {
        this.money = 10;
        this.level = 1.1;
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
        if(level >= 1.2) {
            assets.put("Seeds", wheatFarm.getSeeds());
            assets.put("Wheat", wheatFarm.getGreenWheat());
            assets.put("Grain", wheatFarm.getRipeWheat());
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
            hasfailedCurrentTask = false;
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

    public void nextLevel(){
        ++this.level;
    }

    public void startDay(Farmio farmio) throws FarmioException, FarmioFatalException {
        for (int i = 0; i < tasks.size(); i++) {
            this.currentTask = i;
            tasks.get(i).execute(farmio);
        }
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("level", level);
        obj.put("money", money);
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        //obj.put("task_list", tasks.toJSON());
        return obj;
    }
}
