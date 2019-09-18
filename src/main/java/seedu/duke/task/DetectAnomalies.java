package seedu.duke.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DetectAnomalies {

    private String filePath = "data/duke.txt";
    String command;
    public DetectAnomalies(String command){this.command = command;}

    public Boolean test(){
        Boolean check = false;
        try {
            Scanner duke_txt = new Scanner(new File(this.filePath));
            while (duke_txt.hasNextLine()) {
                // splits line input based on |
                String task_string[] = duke_txt.nextLine().split("\\|");
                String command_string[] = command.split(" |/by",2);

                if(command_string.equals("deadline") || command_string.equals("event")){
                    if(task_string[0].equals("D")){
                        System.out.println(command_string[1]);
                        if(task_string[3].equals(command_string[1])) {
                            System.out.println("There is a clash in date and time with this task!");
                            check = true;
                        }
                    }
                    if(task_string[0].equals("E")){
                        if(task_string[3].equals(command_string[1])){
                            System.out.println("There is a clash in date and time with this event!");
                            check = true;
                        }
                    }
                }
            }
        }catch (FileNotFoundException e){
            System.out.println("\t_____________________________________");
            System.out.println("\tNo list saved in database. Please create a list now.");
            System.out.println("\t_____________________________________\n\n");
        }
        return check;
    }
}

