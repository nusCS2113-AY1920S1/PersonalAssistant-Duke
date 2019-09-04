import java.util.ArrayList;


public class Duke {

    private TaskList myList;
    private Storage save;
    private UI ui;
    private Parser parse;

    //Method to initialize all important classes and data on startup
    public Duke(String filePath) {

        ui = new UI(); //initialize ui class that handles input from user


        ArrayList<Task> myTasks = new ArrayList<>(); //Instantiate an array list of a dynamic size and class Task
        myList = new TaskList(myTasks); //Initialise tasklist
        save = new Storage(filePath); //initialize the storage class
        parse = new Parser();
        parse.setSave(save);
        parse.setTaskList(myList);

    }

    //method to run the actual duke program
    public void run() {
        ui.welcomeMessage(); //Output welcome message
        save.readSave(myList); //initial reading of save file during startup

        UI.separator();

        String myString = ui.inputCommand(); //get raw input from user

        while (!myString.equals("bye")) {
            parse.parseInput(myString);
            myString = ui.inputCommand();
        }

        ui.byeMessage();
    }

    public static void main(String[] args) {

        new Duke("save.txt").run();

    }

}


