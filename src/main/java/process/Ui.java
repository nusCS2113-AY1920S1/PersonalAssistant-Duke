package process;

import java.util.Scanner;

import static java.lang.System.*;
/**
 * Represents the user interface
 */
public class Ui {
    Scanner input = new Scanner(System.in);
    private String line = " ____________________________________________________________";
    private String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    /**
     * Shows the welcome message
     */
    public void showWelcome() {
        System.out.println(line);
        System.out.print(logo);
        System.out.println(line);
        System.out.println("Hello! I'm Duke");
        System.out.println("What can I do for you?");
        System.out.println(line);
    }
    /**
     * Shows a line
     */
    public void showLine() {
        System.out.println(line);
    }
    /**
     * Shows and
     * @return whatever string is passed into the function
     * @param thingy to be printed
     */
    public String print_this(String thingy) {
        System.out.println(thingy);
        return thingy;
    }
    /**
     * Reads and
     * @return user input from keyboard
     */
    public String readCommand() {
        return input.nextLine();
    }
    /**
     * Shows the goodbye message
     */
    public void close() {
        System.out.println("Bye. Hope to see you again soon!");
    }
    /**
     * Shows and returns the error message
     * @param error_msg the error message to be formatted and printed
     * @return the formatted error message
     */
    public String showError(String error_msg) {
        String out_ = "";
        if(error_msg.equals("datetime")) {
            out_ =  "OOPS!!! Please enter the datetime in this format: dd/mm/yyyy HHMM";
        } else if(error_msg.equals("unknown")) {
            out_ =  ("OOPS!!! I'm sorry, but I don't know what that means :-(");
        } else if (error_msg.equals("arg1 error find")) {
            out_ =  "OOPS!!! Please enter a keyword or phrase for your search";
        } else if (error_msg.equals("arg1 error todo")) {
            out_ =  "OOPS!!! The description of a todo cannot be blank";
        } else if (error_msg.equals("arg1 error deadline")) {
            out_ =  "OOPS!!! The description of a deadline cannot be blank";
        } else if (error_msg.equals("arg1 error event")) {
            out_ =  "OOPS!!! The description of an event cannot be blank";
        } else if (error_msg.equals("index error")) {
            out_ =  "OOPS!!! Please enter a valid task index number";
        } else if (error_msg.equals("empty list")) {
            out_ =  "You have no tasks in your list";
        } else if (error_msg.equals("empty task")) {
            out_ ="OOPS!!! The description of a task cannot be empty.";
        }
        else out_ =  ("OOPS!!! " + error_msg);
        System.out.println(out_);
        return out_;
    }
    /**
     * Shows and returns the loading error
     * @return loading error
     */
    public String showLoadingError() {
        String out_ = "OOPS!!! I'm sorry, but your saved file cannot be found";
        System.out.println(out_);
        return out_;
    }
    /**
     * Shows and
     * @return the given taska as done
     * @param task to be displayed as done
     */
    public String showTaskDone(String task) {
//        String out_ = "Nice! I've marked this task as done:\n" + "[✓] " + task;
        String out_ = "Nice! I've marked this task as done:\n" + task;
        System.out.println(out_);
        return out_;
    }
    /**
     * Shows and
     * @return the given task as deleted
     * @param task to be shown as deleted
     * @param size of the task list
     */
    public String showTaskDelete(String task, int size) {
        String out_ = "Noted. I've removed this task:" + "\n" + "\t" + task + "\n" + "Now you have " + Integer.toString(size) + " tasks in the list.";
        System.out.println(out_);
        return out_;
    }
    /**
     * Shows and
     * @return the given task as added
     * @param task to be shown as added
     * @param size of the task list
     */
    public String showTaskAdded(String task, int size) {
        String out_ = "Got it. I've added this task:" + "\n" + "\t" + task  +"\n" + "Now you have " + Integer.toString(size) + " tasks in the list.";
        System.out.println(out_);
        return out_;
    }
    //☹
}
