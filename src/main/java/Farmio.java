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
            //introduce the problem, and show the tutorial, and show the conditions and the possible tasks
            loadLevel(farmer);
            //gets and processes all the user inputs, such as creating new tasks, adding them to the list, etc until player does command run
            isExit = playerInput(farmer, ui, parser);
            //Farmer goes through the day
            farmer.startDay();
            Storage.save(farmer);
            checkObjectives(farmer);
        }
    }

    public static void main(String[] args) {    //TODO - configure both OS
        new Farmio().run();
    }

    private static void loadLevel(Farmer farmer) {

    }

    private static boolean playerInput(Farmer farmer, Ui ui, Parser parser) {
        //returns when command = start day
        boolean isStart = false;
        boolean isExit = false;
        while(!isStart){
            String fullCommand = ui.getInput();
            Command c = parser.parse(fullCommand);
            isStart = c.getIsStart();
            isExit = c.getIsExit();
        }
        return isExit;
    }

    private static void checkObjectives(Farmer farmer) {

    }
}