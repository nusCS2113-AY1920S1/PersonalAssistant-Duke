import Commands.Command;

public class Farmio {
    private Storage storage;
    private Farmer farmer;
    private Ui ui;
    private Parser parser;

    public Farmio() {
        Ui ui = new Ui();
        parser = new Parser();
        Storage storage = new Storage();
        ui.showWelcome();
        String filepath;
        this.farmer = storage.load(ui.getSaveFile());
    }

    public void run() {
        boolean isExit = false;
        while(!isExit) {
       //introduce the problem, and show the tutorial, and show the conditions and the possible tasks and gets the user input
       String fullCommand = loadLevel(farmer);
       Command c = parser.parse(fullCommand);
       //create the new task, and add to the tasklist or do whatever
        c.execute();
        //Farmer goes through the day
        farmer.startDay();
        Storage.save(farmer);
        checkObjectives(farmer);
        isExit = c.getIsExit();
        }
    }

    public static void main(String[] args) {    //TODO - configure both OS
        new Farmio().run();
    }

    private static String loadLevel(Farmer farmer) {

        return "dummystring";
    }

    private static void checkObjectives(Farmer farmer) {

    }
}