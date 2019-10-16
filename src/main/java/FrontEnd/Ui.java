package FrontEnd;

import Farmio.Farmio;
import Farmio.Storage;

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

    public void showWelcome(){
        try{
            show(storage.getAsciiArt("welcome"));
        } catch (IOException e) {
            showWarning("'welcome' ascii art missing!");
        }
        show("Press ENTER to continue.");
    }

    public void showExit(){
        show("Bye-Bye");
    }

    public void showNarrative(ArrayList<String> narratives, String directory, Farmio farmio) {
        for(int i = 0; i < narratives.size(); ++i){
            clearScreen();
            show(GameConsole.content(loadStage(directory, i), farmio));
//            typeWriter(narratives.get(i));
            show(narratives.get(i));
            if(i != narratives.size() - 1) {
                show("Press ENTER to continue.");
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
    private String blankSpace(int n) {
        String output = "";
        for (int i = 0; i < n; i ++) {
            output += " ";
        }
        return output;
    }

    public ArrayList<String> loadStage(String path, int frame) {
        String filepath = "./src/main/resources/asciiArt/" + path + "/frame" + frame + ".txt";
        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = bufferreader.readLine()) != null) {
                if (line.length() < 55) {
                    int padding_left = (55 - line.length()) / 2;
                    int padding_right = 55 - line.length() - padding_left;

                    line = blankSpace(padding_left) + line + blankSpace(padding_right);
                } else if (line.length() > 55) {
                    line = blankSpace(55);
                }
                output.add("|"+ AsciiColours.BACKGROUND_WHITE + AsciiColours.BLACK + line + AsciiColours.SANE +"|");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (output.size() < 18) {
            for (int i = output.size(); i < 18; i ++) {
                output.add("|"+ AsciiColours.BACKGROUND_WHITE + AsciiColours.BLACK + blankSpace(55) + AsciiColours.SANE +"|");
            }
        }
        return output;
    }
    public void typeWriter(String text) { //use terminal to see full effects, in console only seem to beline by line..
        int i;
        System.out.print(">>> ");
        try{
            Thread.sleep(1500);//0.5s pause between characters
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        for(i = 0; i < text.length(); i++) {
            System.out.printf("%c", text.charAt(i));
            try{
                Thread.sleep(120);//0.5s pause between characters
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }
}
