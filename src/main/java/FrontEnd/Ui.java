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

    public void typeWriter(String text) { //ill clean this shit up soon.
        final char OBJECTIVE_PLACEHOLDER = ':';
        final char LEVEL_BEGIN_PLACEHOLDER = '~';
        final char EARLY_ESCAPE_PLACEHOLDER = '/';
        int i;
        int lineLength = 0;
        System.out.print(">>> ");
        try{
            Thread.sleep(150);//0.5s pause between characters
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        for(i = 0; i < text.length(); i++) {
            lineLength ++;
            if (lineLength > 95 && text.charAt(i) == ' ') {
                System.out.println();
                System.out.print("   ");
                lineLength = 0;
            } else if (text.charAt(i) == '\n') {
                System.out.print("   ");
                lineLength = 0;
            } else if (text.charAt(i) == OBJECTIVE_PLACEHOLDER) {
                show(AsciiColours.RED + AsciiColours.UNDERLINE + "[Objective]" + AsciiColours.SANE);
                System.out.print("   ");
                lineLength = 0;
            } else if (text.charAt(i) == LEVEL_BEGIN_PLACEHOLDER) {
                System.out.println("\n\n"+ " ".repeat(44) + AsciiColours.GREEN + AsciiColours.UNDERLINE + "[LEVEL BEGIN]" + AsciiColours.SANE+ "\n");
                return;
            } else if (text.charAt(i) == EARLY_ESCAPE_PLACEHOLDER) {
                return;
            }
            System.out.printf("%c", text.charAt(i));
            try{
                Thread.sleep(40);//0.5s pause between characters
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
        show("\n\n" + " ".repeat(77) + "Press ENTER to continue..");
    }
}
