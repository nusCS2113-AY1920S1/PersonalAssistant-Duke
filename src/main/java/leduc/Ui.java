package leduc;

import leduc.command.SetWelcomeCommand;
import leduc.exception.DukeException;
import leduc.exception.FileException;
import leduc.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
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
        File file = SetWelcomeCommand.openFile(filepath);

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
}
