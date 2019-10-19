package FrontEnd;

import Exceptions.FarmioFatalException;
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
    private final String CLEAR_SCREEN = "\033c" + "\033[2J";

    public Ui(Storage storage) {
        this.scanner = new Scanner(System.in);
        this.storage = storage;
    }

    public void show(String message) {
        System.out.println(message);
    }

    public void showExit(){
        typeWriter("Bye-Bye");
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

    private String blankSpace(int n) {
        String output = "";
        for (int i = 0; i < n; i ++) {
            output += " ";
        }
        return output;
    }

    public ArrayList<String> loadStage(String path, int frame, int width, int height) {
        String filepath = "./src/main/resources/asciiArt/" + path + "/frame" + frame + ".txt";
        ArrayList<String> output = new ArrayList<>();
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = bufferreader.readLine()) != null) {
                if (line.length() < width) {
                    line = line + blankSpace(width - line.length());
                } else if (line.length() > width) {
                    line = blankSpace(width);
                }
                output.add("|"+ AsciiColours.BACKGROUND_WHITE + AsciiColours.BLACK + line + AsciiColours.SANE +"|");
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (output.size() < height) {
            for (int i = output.size(); i < height; i ++) {
                output.add("|"+ AsciiColours.BACKGROUND_WHITE + AsciiColours.BLACK + blankSpace(width) + AsciiColours.SANE +"|");
            }
        }
        return output;
    }

    public void typeWriter(String text) { //use terminal to see full effects, in console only seem to beline by line..
        int i;
        System.out.print(">>> ");
        try{
            Thread.sleep(150);//0.5s pause between characters
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        for(i = 0; i < text.length(); i++) {
            System.out.printf("%c", text.charAt(i));
            try{
                Thread.sleep(60);//0.5s pause between characters
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }
}
