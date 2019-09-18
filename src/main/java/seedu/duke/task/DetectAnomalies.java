package seedu.duke.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DetectAnomalies {

    private String filePath = "data/duke.txt";
    String command;

    public DetectAnomalies(String command) {
        this.command = command;
    }

    /**
     * Test if there is any clashes.
     * @return true or false.
     */
    public Boolean test() {
        Boolean check = false;
        try {
            Scanner dukeTxt = new Scanner(new File(this.filePath));
            while (dukeTxt.hasNextLine()) {
                // splits line input based on |
                String[] taskString = dukeTxt.nextLine().split("\\|");
                String[] commandString = command.split(" |/by",2);

                if (commandString.equals("deadline") || commandString.equals("event")) {
                    if (taskString[0].equals("D")) {
                        System.out.println(commandString[1]);
                        if (taskString[3].equals(commandString[1])) {
                            System.out.println("There is a clash in date and time with this task!");
                            check = true;
                        }
                    }
                    if (taskString[0].equals("E")) {
                        if (taskString[3].equals(commandString[1])) {
                            System.out.println("There is a clash in date and time with this event!");
                            check = true;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\t_____________________________________");
            System.out.println("\tNo list saved in database. Please create a list now.");
            System.out.println("\t_____________________________________\n\n");
        }
        return check;
    }
}

