package UserInterfaces;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    Scanner scanner = new Scanner(System.in);
    private final String CLEAR_SCREEN = (char)27 + "[2J";
    public Ui() {}

    public void showWelcome() throws IOException {
        String s = getAsciiArt("./src/main/resources/asciiArt/welcome.txt");
        System.out.println(s);
    }

    public String getCommand() {
        System.out.println("Listening for command: ");
        String s = scanner.nextLine();
        return s;
    }
    public void clearScreen() {
        System.out.println(CLEAR_SCREEN);
    }

    public String getSaveFile() {
        return null;
    }
    private String getAsciiArt(String filepath) throws IOException {
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
    public String showAsciiArt(String filepath) throws IOException {
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
