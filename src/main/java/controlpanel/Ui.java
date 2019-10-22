package controlpanel;

import java.util.Scanner;

public class Ui {

    private Scanner scanner;
    private static String outputString = "";
    private static String graphContainerString = "";

    //@@ cctt1014
    public Ui() {
        scanner = new Scanner(System. in);
        outputString = "";
        graphContainerString = "";
    }

    public String showLine() {
        return ("____________________________________________________________\n");
    }

    public void showLoadingError() {
        System.out.println("This is not a valid input from the file!!!");
    }

    public String showError(String message) {
        return ("ERROR: " + message);
    }

    public void appendToOutput(String msg) {
        outputString += msg;
    }

    public String getOutputString() {
        return outputString;
    }

    public void clearOutputString() {
        outputString = "";
    }

    //@@ chengweixuan
    public String getGraphContainerString() {
        return graphContainerString;
    }

    public void clearGraphContainerString() {
        graphContainerString = "";
    }

    public void appendToGraphContainer(String msg) {
        graphContainerString += msg;
    }
}
