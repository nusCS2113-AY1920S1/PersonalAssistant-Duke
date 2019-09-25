import Commands.Command;

import java.io.IOException;

public class Farmio {
    private Storage storage;
    private Farmer farmer;
    private Ui ui;
    private Parser parser;

    public Farmio() throws IOException {
        this.ui = new Ui();
        this.parser = new Parser();
        this.storage = new Storage();
        ui.showWelcome();
        String filepath;
        this.farmer = new Farmer(); //load savefile if it exists
        //storage.load(ui.getSaveFile());
    }

    public void run() {
        boolean isExit = false;
        while(!isExit) {
            //introduce the problem, and show the tutorial, and show the conditions and the possible tasks and gets the user input
            loadLevel(farmer);
            //create the new task, and add to the tasklist or do whatever
            isExit = getUserActions(farmer, ui, parser);
            farmer.startDay();
            Storage.save(farmer);
            checkObjectives(farmer);
        }
    }

    public static void main(String[] args) throws IOException {    //TODO - configure both OS
        new Farmio().run();
    }

    public boolean getUserActions(Farmer farmer, Ui ui, Parser parser) {
        boolean isStart = false;
        boolean isExit = false;
        while (!isStart) {
            String fullCommand = ui.getCommand();
            Command c = parser.parse(fullCommand);
            c.execute();
            isStart = true;//c.getIsStart();
            isExit = true;//c.getIsExit();
        }
        return  isExit;
    }


    private static void loadLevel(Farmer farmer) {

    }

    private static void checkObjectives(Farmer farmer) {

    }
}