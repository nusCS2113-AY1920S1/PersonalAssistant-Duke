package UserInterfaces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    Scanner scanner = new Scanner(System.in);
    private final String CLEAR_SCREEN = (char)27 + "[2J";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void show(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        show("Error: " + message);
    }

    public void showWarning(String message) {
        show("Warning: " + message);
    }
    public void clearScreen() {
        System.out.println(CLEAR_SCREEN);
    }

    public void showInfo(String message) {
        show("Info: " + message);
    }

    private String getAsciiArt(String filepath) {
        String output = "";
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = bufferreader.readLine()) != null) {
                output += line;
                output += "\n";
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return output;
    }
    public void showMenu(boolean hasSave) {
        show("Menu:");
        if(hasSave){
            show("\t\u2022 Load Save");
        }
        show("\t\u2022 New Game\n\t\u2022 Quit");
    }

    public String getInput() {
        show("\nInput: ");
        return scanner.nextLine();
    }

    public void getEnter() {
        try {
            System.in.read();
        } catch (IOException e) {
            showError("Something went wrong!");
        }
    }

    public String showAsciiArt(String filepath) {
        String output = getAsciiArt(filepath);
        System.out.println(output);
        return output;
    }
    public void showStatusbar() { //could put in farmer or some kind of info in parameters
        //details ie:money, wheat, chicken
        int money = 0;
        System.out.println("money = " + money);
        //Task list and current task if any
        String currentTask = "if akshay then anarayan";
        System.out.println("current task");
    }
}
