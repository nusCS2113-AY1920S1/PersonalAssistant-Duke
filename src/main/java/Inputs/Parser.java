package Inputs;

import COMPal.Duke;

import java.util.Scanner;

public class Parser {
    Duke d;
    Scanner sc;


    public Parser(Duke d){
        this.d=d;
        sc = new Scanner(System.in);
    }

    /**
     * @Function
     * @param cmd
     * No return value
     * This function handles the main CLI parsing. Just pass in the cmd string and it will work its magic.
     * It uses regex to understand the command entered.
     * @UsedIn: COMPal.Duke.handleUserInput()
     * todo:Replace all console output with GUI output
     */
    public void processCommands(String cmd){

            if(cmd.equals("bye")){
                d.exitDuke();
            }
            else if(cmd.equals("list")){
                d.ui.listTasks();
            }
            else if(cmd.matches("done ([0-9]+)")){
                d.tasklist.taskDone(cmd);

            }
            else if(cmd.matches("delete ([0-9]+)")){
                d.tasklist.deleteTask(cmd);
            }
            else if (cmd.matches("(todo|event|deadline) .+")){
                d.tasklist.addTask(cmd);
            }
            else if (cmd.matches("find (.*)")){
                d.tasklist.findTask(cmd);
            }
            else if (cmd.matches("(todo|event|deadline)")){
                try {
                    throw new Duke.DukeException("☹ OOPS!!! The description of a "+cmd+" cannot be empty.");
                } catch (Duke.DukeException e) {
                    d.ui.printg(e.toString());
                }
            }
            else{
                try {
                    throw new Duke.DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
                } catch (Duke.DukeException e) {
                    d.ui.printg(e.toString());
                }
            }

    }



}
