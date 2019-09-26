import Places.ChickenFarm;
import Places.CowFarm;
import Places.WheatFarm;
import Task.TaskList;

public class Farmer {
    protected int money;
    protected int level;
    protected WheatFarm wheatFarm;
    protected ChickenFarm chickenFarm;
    protected CowFarm cowFarm;
    protected TaskList tasks;

    public Farmer() {
        this.money = 100;
        this.level = 0;
        this.wheatFarm = new WheatFarm(); //TODO: create wheatFarm subclass
        this.chickenFarm = new ChickenFarm(); //TODO: create chickenFarm subclass
        this.cowFarm = new CowFarm(); //TODO: create cowFarm subclass
        this.tasks = new TaskList();
    }

    public Farmer(int level, int money, WheatFarm wheatFarm, ChickenFarm chickenFarm, CowFarm cowFarm, TaskList tasks) {
        this.level = level;
        this.money = money;
        this.wheatFarm = wheatFarm;
        this.chickenFarm = chickenFarm;
        this.cowFarm = cowFarm;
        this.tasks = tasks;
    }

    public void startDay() {
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).execute();
        }
    }
}
