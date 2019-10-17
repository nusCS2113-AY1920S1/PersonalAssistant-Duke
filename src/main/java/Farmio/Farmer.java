package Farmio;

import FarmioExceptions.FarmioException;
import FrontEnd.Ui;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.TaskList;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class Farmer {
    private int money;
    private int level;
    private int day;
    private String location;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected TaskList tasks;

    public Farmer() {
        this.money = 100;
        this.level = 1;
        this.day = 1;
        this.location = "WheatFarm";
        this.wheatFarm = new WheatFarm(); //TODO: create wheatFarm subclass
        this.chickenFarm = new ChickenFarm(); //TODO: create chickenFarm subclass
        this.cowFarm = new CowFarm(); //TODO: create cowFarm subclass
        this.tasks = new TaskList();
    }

    public Farmer(JSONObject jsonObject) throws FarmioException {
        this.level = (Integer) jsonObject.get("level");
        this.money = (Integer) jsonObject.get("money");
        this.wheatFarm = new WheatFarm((JSONObject) jsonObject.get("farm_wheat"));
        this.chickenFarm = new ChickenFarm((JSONObject) jsonObject.get("farm_chicken"));
        this.cowFarm = new CowFarm((JSONObject) jsonObject.get("farm_cow"));
        //this.tasks = new TaskList((JSONArray) jsonObject.get("task_list"));
    }

    public Farmer(int level, int money, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, TaskList tasks) {
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

    public int getLevel() { return level;}

    public int getDay() {return day;}

    public String getLocation() {return location;}

    public void changeLocation(String newLocation) {
        location = newLocation;
    }

    public ArrayList<Pair<String, Integer>> getAssets() {
        ArrayList<Pair<String, Integer>> assets = new ArrayList<Pair<String, Integer>>();

        if(level == 1)
        {
            assets.add(new Pair<>("Seeds", wheatFarm.getSeeds()));
            assets.add(new Pair<>("Wheat", wheatFarm.getRipeWheat()));
            level++;
        }
        else if(level == 2)
        {
            assets.add(new Pair<>("Seeds", wheatFarm.getSeeds()));
            assets.add(new Pair<>("Wheat", wheatFarm.getRipeWheat()));
            assets.add(new Pair<>("Chicken",0));
            assets.add(new Pair<>("Eggs",0));
            level++;
        }
        else if(level == 3)
        {
            assets.add(new Pair<>("Seeds", wheatFarm.getSeeds()));
            assets.add(new Pair<>("Wheat", wheatFarm.getRipeWheat()));
            assets.add(new Pair<>("Chicken",0));
            assets.add(new Pair<>("Eggs",0));
            assets.add(new Pair<>("Milk",0));
        }
        return assets;
    }


    public WheatFarm getWheatFarm() { return  wheatFarm; }

    public ChickenFarm getChickenFarm() { return chickenFarm; }

    public CowFarm getCowFarm() { return cowFarm; }

    public TaskList getTasks() { return tasks; }

    public void setMoney(int money){
        this.money = money;
    }

    public void changeMoney(int change) { money -= change; }

    public void nextLevel(){
        ++this.level;
    }

    public void startDay(Farmio farmio) throws FarmioException {
        for (int i = 0; i < tasks.size(); i++) {
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
