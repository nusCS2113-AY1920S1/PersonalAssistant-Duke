import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    Scanner scanner;

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

    public void showInfo(String message) {
        show("Info: " + message);
    }

    public void showMenu(boolean hasSave) {
        show("Menu:");
        if(hasSave){
            show("\t\u2022 Load Save\n");
        }
        show("\t\u2022 New Game\n\t\u2022 Quit");
    }

    public String getInput() {
        show("Input: ");
        return scanner.nextLine();
    }
}
