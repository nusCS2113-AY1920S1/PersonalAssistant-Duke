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
    private final String CLEAR_SCREEN = "\033c" + "\033[2J";

    public Ui() {
        this.scanner = new Scanner(System.in);
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

    public String getInput() {
        show("\nInput: ");
        return scanner.nextLine();
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
