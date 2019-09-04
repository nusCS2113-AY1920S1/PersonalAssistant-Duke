import java.util.ArrayList;


public class Duke {
    public static void main(String[] args) {

        UI ui = new UI(); //initialize ui class that handles input from user

        ui.welcomeMessage(); //Output welcome message

        ArrayList<Task> myTasks = new ArrayList<>(); //Instantiate an array list of a dynamic size and class Task
        TaskList myList = new TaskList(myTasks); //Initialise tasklist
        Storage save = new Storage("save.txt"); //initialize the storage class

        save.readSave(myList); //initial reading of save file during startup

        Parser parse = new Parser();
        parse.setSave(save);
        parse.setTaskList(myList);

        UI.separator();

        String myString = ui.inputCommand(); //get raw input from user

        while (!myString.equals("bye")) {
            parse.parseInput(myString);
            myString = ui.inputCommand();
        }

        ui.byeMessage();

    }





}


