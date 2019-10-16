package Farmio;

import FarmioExceptions.FarmioException;
import FrontEnd.Ui;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import UserCode.Tasks.TaskList;
import org.json.simple.JSONObject;

public class Farmer {
    private int money;
    private int level;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected TaskList tasks;
    public Ui ui;

    public Farmer() {
        this.money = 100;
        this.level = 1;
        this.wheatFarm = new WheatFarm(); //TODO: create wheatFarm subclass
        this.chickenFarm = new ChickenFarm(); //TODO: create chickenFarm subclass
        this.cowFarm = new CowFarm(); //TODO: create cowFarm subclass
        this.tasks = new TaskList();
    }

    public Farmer(Ui ui, JSONObject jsonObject) throws FarmioException {
        this.ui = ui;
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

    public void setMoney(int money){
        this.money = money;
    }

    public void nextLevel(){
        ++this.level;
    }

    public void startDay() {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).execute(ui);
        }
    }

    public JSONObject toJSON(){
        JSONObject obj = new JSONObject();
        obj.put("level", level);
        obj.put("money", money);
        obj.put("farm_wheat", wheatFarm.toJSON());
        obj.put("farm_chicken", chickenFarm.toJSON());
        obj.put("farm_cow", cowFarm.toJSON());
        obj.put("task_list", tasks.toJSON());
        return obj;
    }
}
