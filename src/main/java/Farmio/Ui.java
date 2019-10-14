package Farmio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    private Storage storage;
    private final String CLEAR_SCREEN = (char)27 + "[2J";

    public Ui(Storage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
    }

    public void show(String message) {
        System.out.println(message);
    }

    void showWelcome(){
        try{
            show(storage.getAsciiArt("welcome"));
        } catch (IOException e) {
            showWarning("'welcome' ascii art missing!");
        }
        show("Press ENTER to continue.");
    }

    void showExit(){
        show("Bye-Bye");
    }

    public void showNarrative(ArrayList<String> narratives) {
        for(int i = 0; i < narratives.size(); ++i){
            show(narratives.get(i));
            if(i != narratives.size() - 1) {
                getInput();
            }
        }
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
        StringBuilder output = new StringBuilder();
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = bufferreader.readLine()) != null) {
                output.append(line);
                output.append("\n");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return output.toString();
    }

    public String getInput() {
        show("\nInput: ");
        return scanner.nextLine();
    }

    public void showAsciiArt(String fileName) {
        String output = getAsciiArt(fileName);
        System.out.println(output);
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
