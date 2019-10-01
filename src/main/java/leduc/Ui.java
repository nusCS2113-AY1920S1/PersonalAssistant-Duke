package leduc;

import leduc.command.SetWelcomeCommand;
import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
/**
 *  Represents leduc.Ui which deals with the interactions with the user.
 */
public class Ui {
    private Scanner sc;
    private File file;
    private SetWelcomeCommand w;

    /**
     * Constructor of the leduc.Ui
     */
    public Ui(){
        this.sc = new Scanner(System.in);
    }

    /**
     * Returns the String representing the next line of command of the user.
     * @return the String representing the next line of command of the user.
     */
    public String readCommand(){
        return this.sc.nextLine();
    }

    /**
     * Returns a File object
     * @return a file object containing the welcome message
     */
    public static File openFile(String filepath) throws FileException {
        //open file, throw exception if the file doesnt exist.
        File file;
        file = new File(filepath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * Display the duke logo.
     */
    public void displayLogo(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
    }

    /**
     * Show welcome to the user.
     */
    public void showWelcome() throws FileException {

        //open the file
        String filepath = System.getProperty("user.dir")+ "/data/welcome.txt";
        File file = openFile(filepath);

        //create Scanner object to read file
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new FileException();
        }

        String welcomeMessage = "";
        //build welcome message
        while (sc2.hasNext()) {
            welcomeMessage = welcomeMessage + '\t' + sc2.nextLine() + '\n';
        }
        //display welcome message
        this.displayLogo();
        this.display(welcomeMessage);
    }


    /**
     * Bye message to the user.
     */
    public void showBye(){
        this.display("\t Bye. Hope to see you again soon!");
    }


    /**
     * Display the String in the parameter between two lines.
     * @param s String which will be printed.
     */
    public void display(String s){
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println(s);
        System.out.println("\t---------------------------------------------------------------------------------");
    }

    /**
     * Display the list of tasks.
     * @param tasks tasks list.
     */
    public void showList(TaskList tasks){
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println("\t Here are the tasks in your list:");
        for (int i = 0 ;i< tasks.size() ; i++ ){
            System.out.print(tasks.displayOneElementList(i));
        }
        System.out.println("\t---------------------------------------------------------------------------------");
    }

    /**
     * Display the error message
     * @param e the error that has been catch
     */
    public void showError(DukeException e){
        System.out.println(e.print());
    }

    /**
     * Display every command
     */
    public void showHelp(){
        System.out.println("\t---------------------------------------------------------------------------------");
        System.out.println("\t All command will be display as :");
        System.out.println("\t commandName [PARAMETERS] : description of the command");
        System.out.println("\t All parameters will be written in UPPER_CASE");
        System.out.println("\t Parameters are :");
        System.out.println("\t DESCRIPTION : the description of a task");
        System.out.println("\t DATE : the date of a task");
        System.out.println("\t INDEX : the index of the task (goes from 1 to ...)");
        System.out.println("\t KEYWORD : the keyword to find a task");
        System.out.println("\t Date format is DD/MM/YYYY HH:mm");
        System.out.println("\t All blank space should be respected");
        System.out.println("\t Here are the list of all command:");
        System.out.println("\t todo DESCRIPTION : create a todo task");
        System.out.println("\t deadline DESCRIPTION /by DATE : create a deadline task");
        System.out.println("\t event DESCRIPTION /at DATE - DATE : create an event task");
        System.out.println("\t list : show all the tasks");
        System.out.println("\t bye : exit the application");
        System.out.println("\t done INDEX : mark as done the task of index INDEX");
        System.out.println("\t delete INDEX : delete the task of index INDEX");
        System.out.println("\t find KEYWORD : find the task with a keyword");
        System.out.println("\t snooze INDEX : snooze a task of index INDEX");
        System.out.println("\t postpone INDEX /by DATE : postpone a deadline task");
        System.out.println("\t reschedule INDEX /at DATE - DATE : reschedule an event task");
        System.out.println("\t remind : remind the first three task");
        System.out.println("\t help : show the list of all command");
        System.out.println("\t---------------------------------------------------------------------------------");
    }
}
