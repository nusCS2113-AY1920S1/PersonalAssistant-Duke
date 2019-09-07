package ControlPanel;

import java.io.IOException;
import java.util.Scanner;

public class Ui {

    private Scanner scanner;

    public Ui (){
        scanner = new Scanner(System. in);
    }

    public void showWelcome(){
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println("____________________________________________________________\n" +
                "     Hello! I'm Duke\n" +
                "     What can I do for you?\n" +
                "____________________________________________________________\n");
    }

    public String readCommand(){
        return scanner.nextLine();
    }

    public boolean inputStatus(){
        return scanner.hasNextLine();
    }



    public void showLine(){
        System.out.println("____________________________________________________________\n");
    }

    public void showLoadingError(){
        System.out.println("This is not a valid input from the file!!!");
    }

    public void showError(String message){
        System.out.println(message);
    }


}
