package javacake;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    private static String border = "____________________________________________________________";

    /**
     * Constructor for Ui.
     */
    public Ui() {

    }

    /**
     * The message that pops up when first starting the program.
     * @param isFirstTime true for first time users
     * @param userName The name of the user using JavaCake
     * @param progress The progress of the user in viewing the whole content
     * @return String containing username
     */
    public String showWelcome(boolean isFirstTime, String userName, int progress) {
        StringBuilder welcomePhaseA = new StringBuilder();
        welcomePhaseA.append("Hello! Welcome to JavaCake!\n");
        welcomePhaseA.append("~making Java a Piece of Cake\n").append(border);

        System.out.println(welcomePhaseA.toString());

        if (isFirstTime) {
            welcomePhaseA = new StringBuilder();
            welcomePhaseA.append("I see this is your first time here! ");
            welcomePhaseA.append("What name would you like to be called?\n").append(border);

            System.out.println(welcomePhaseA.toString());

            userName = readCommand();
        }

        StringBuilder welcomePhaseB = new StringBuilder();
        if (isFirstTime) {
            welcomePhaseB.append(border);
            welcomePhaseB.append("\nWelcome to JavaCake, ").append(userName).append("! ");
            welcomePhaseB.append("Now let's help you get started with Java! :3\n");
            welcomePhaseB.append(border);
        } else {
            welcomePhaseB.append(border);
            welcomePhaseB.append("\nHello ").append(userName).append("! ");
            welcomePhaseB.append("Here's your quiz progress so far :D\n");
            progress *= 6;
            for (int i = 0; i < 18; ++i) {
                if (i < progress) {
                    welcomePhaseB.append("#");
                } else {
                    welcomePhaseB.append("-");
                }
            }
            progress = progress * 33 / 6;
            if (progress == 99) {
                progress = 100;
            }
            welcomePhaseB.append(" ").append(progress).append("%\n");
            welcomePhaseB.append("What do you want to do today?\n").append(border);
        }
        System.out.println(welcomePhaseB.toString());

        return userName;
    }

    /**
     * Prints a new border to separate messages by Ui.
     */
    public void showLine() {
        System.out.println(border);
    }

    /**
     * Method to read command inputted by user.
     * @return String containing input by user
     */
    public String readCommand() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    /**
     * Shows error when trying to load the save file.
     */
    public void showLoadingError() {
        System.out.println("No saved files detected.");
    }

    /**
     * Displays the error message on the Ui.
     * @param e String containing the error message
     */
    public void showError(String e) {
        System.out.println(e);
    }

    /**
     * Displays the message on the Ui.
     * @param m String containing the message
     */
    public void showMessage(String m) {
        System.out.println(m);
    }

    /**
     * Method to display text from file.
     * @param reader BufferedReader to read in text from file
     * @throws DukeException Error thrown when unable to close reader
     */
    public void displayTextFile(BufferedReader reader) throws DukeException {
        String lineBuffer;
        try {
            while ((lineBuffer = reader.readLine()) != null) {
                System.out.println(lineBuffer);
            }
            reader.close();
        } catch (IOException e) {
            throw new DukeException("File not found!");
        }
    }
}
