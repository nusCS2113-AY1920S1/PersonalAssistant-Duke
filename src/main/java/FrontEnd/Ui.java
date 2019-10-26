package FrontEnd;

import Exceptions.FarmioFatalException;
import Farmio.Farmio;
import Farmio.Storage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
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
        show(AsciiColours.RED + "Warning: " + message + AsciiColours.SANE);
    }

    public void clearScreen() {
        System.out.println(CLEAR_SCREEN);
    }

    public void showInfo(String message) {
        show(AsciiColours.CYAN + "Info: " + AsciiColours.SANE + message );
    }

    public String getInput() {
        show("\nInput: ");
        return scanner.nextLine().replace("[","").replace("]","");
    }
    public void sleep(int delay) {
        try{
            Thread.sleep(delay);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
            clearScreen();
            showWarning("Simulator refersh interrupted! Interface may not display correctly.");
        }
    }
    public void typeWriter(String text) { //ill clean this shit up soon.
        final char LEVEL_BEGIN_PLACEHOLDER = '~';
        final char EARLY_ESCAPE_PLACEHOLDER = '/';
        boolean isNewline = false;
        int lineLength = 0;
        System.out.print(">>> ");
        sleep(150);
        for(int i = 0; i < text.length(); i++) {
            lineLength ++;
            if (lineLength > GameConsole.FULL_CONSOLE_WIDTH - 10 && text.charAt(i) == ' ') {
                System.out.print("\n   ");
                lineLength = 0;
            } else if (text.charAt(i) == '\n') {
                isNewline = true;
                lineLength = 0;
            } else if (text.charAt(i) == LEVEL_BEGIN_PLACEHOLDER) {
                System.out.println("\n"+ " ".repeat(44) + AsciiColours.GREEN + AsciiColours.UNDERLINE + "[LEVEL BEGIN]" + AsciiColours.SANE+ "\n");
                return;
            } else if (text.charAt(i) == EARLY_ESCAPE_PLACEHOLDER) {
                System.out.println();
                return;
            }
            System.out.printf("%c", text.charAt(i));
            if (isNewline) {
                System.out.print("    ");
                isNewline = false;
            }
            sleep(10);
        }
        show("\n\n" + " ".repeat(74) + "Press [ENTER] to continue..");
    }
}
