public class Farmer {
    protected int money;
    protected int level;
    protected Farm wheatFarm;
    protected Farm chickenFarm;
    protected Farm cowFarm;
    protected TaskList tasks;

    public Farmer() {
        this.money = 100;
        this.level = 0;
        this.wheatFarm = new Farm(); //TODO: create wheatFarm subclass
        this.chickenFarm = new Farm(); //TODO: create chickenFarm subclass
        this.cowFarm = new Farm(); //TODO: create cowFarm subclass
        this.tasks = new TaskList();
    }

    public Farmer(int level, int money, Farm wheatFarm, Farm chickenFarm, Farm cowFarm, TaskList tasks) {
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
