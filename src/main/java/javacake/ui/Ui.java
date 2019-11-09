package javacake.ui;

import javacake.Logic;
import javacake.commands.ListNoteCommand;
import javacake.commands.ScoreCommand;
import javacake.commands.ReminderCommand;
import javacake.exceptions.CakeException;
import javacake.quiz.Question;
import javacake.storage.StorageManager;

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
     * Method to obtain Welcome Message Part 1.
     * @param isFirstTime boolean to check if first time
     * @return String containing first part of welcome message for GUI
     */
    public static String showWelcomeMsgPhaseA(boolean isFirstTime) {
        StringBuilder strA = new StringBuilder();
        strA.append("\nWelcome to JavaCake! ");
        strA.append("where learning Java can be a Piece of Cake!\n");

        if (isFirstTime) {
            strA.append("\nI see this is your first time here! ");
            strA.append("What name would you like to be called?\n\n");
        }
        return strA.toString();
    }

    /**
     * Method to obtain Welcome Message Part 2.
     * @param isFirstTime boolean to check if first time
     * @param userName username
     * @param storageManager progress of user
     * @return String containing first part of welcome message for GUI
     */
    public static String showWelcomeMsgPhaseB(boolean isFirstTime, String userName, StorageManager storageManager)
            throws CakeException {
        StringBuilder strA = new StringBuilder();
        if (isFirstTime) {
            strA.append("\nWelcome to JavaCake, ").append(userName).append("! ");
            strA.append("Now let's help you get started with Java! :3\n");
            strA.append(helpMessage());
        } else {
            strA.append("Hello ").append(userName).append("!\n");
            strA.append(new ScoreCommand("score").execute(Logic.getInstance(), new Ui(), storageManager));
            strA.append("\nWhat do you want to do today?\n");
            strA.append(helpMessage());
        }
        return strA.toString();
    }

    public static String showDeadlineReminder(StorageManager storageManager) throws CakeException {
        return new ReminderCommand("remind").execute(Logic.getInstance(), new Ui(), storageManager);
    }

    public static String showNoteList(StorageManager storageManager) throws CakeException {
        return new ListNoteCommand("listnote").execute(Logic.getInstance(), new Ui(), storageManager);
    }

    /**
     * Prints a new border to separate messages by Ui.
     */
    public void showLine() {
        System.out.println(border);
    }

    /**
     * Prints help message to assist user.
     */
    public static String helpMessage() {
        return "\nType 'list' to view main topics.\n"
                + "Type 'overview' to view all content.\n"
                + "Type 'help' to view all commands available.\n"
                + "Type 'change' to change to a different color scheme.\n"
                + "Type 'exit' to rage quit.\n";
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
        System.out.print(m);
    }

    /**
     * Method to get text from file.
     * @param reader BufferedReader to read in text from file
     * @throws CakeException Error thrown when unable to close reader
     */
    public static String getTextFile(BufferedReader reader) throws CakeException {
        String lineBuffer;
        String output = "";
        try {
            while ((lineBuffer = reader.readLine()) != null) {
                output += lineBuffer;
                output += "\n";
            }
            reader.close();
        } catch (IOException e) {
            throw new CakeException("File not found!");
        }
        return output;
    }

    /**
     * Displays the quiz question.
     * @param question the question to be shown to the user.
     * @param index the current question the user is on.
     * @param maxQuestions the maximum number of questions in the quiz session.
     */
    public void displayQuiz(String question, int index, int maxQuestions) {
        System.out.println(index + "/" + maxQuestions);
        System.out.println(question);
    }

    /**
     * Displays the results of a quiz.
     * @param score the user's score in that quiz.
     * @param maxScore the maximum score possible in that quiz.
     */
    public void displayResults(int score, int maxScore) {
        System.out.println("This is your score:");
        System.out.println("    " + score + " / " + maxScore);

        if ((double)score / maxScore <= 0.5) {
            System.out.println("Aw, that's too bad! Try revising the topics and try again. Don't give up!");
        } else if ((double)score / maxScore != 1.0) {
            System.out.println("Almost there! Clarify some of your doubts and try again.");
        } else {
            System.out.println("Congrats! Full marks, you're amazing!");
        }

        System.out.println("Type \"review\" to review your answers.");
        System.out.println("Type \"back\" to go back to the table of contents.");
    }

    /**
     * Displays a question, along with the user's answer and the correct answer.
     * @param question the question to display. User's answer must not be null.
     * @param index the current question the user is on.
     * @param maxQuestions the maximum number of questions in the quiz session.
     */
    public void displayReview(Question question, int index, int maxQuestions) {
        System.out.println("Enter a number to go to that question. Type \"back\" to go back to table of contents.");
        displayQuiz(question.getQuestion(), index, maxQuestions);
        System.out.println("\n");
        System.out.println("Your answer: " + question.getUserAnswer());
        System.out.println("Correct answer: " + question.getAnswer());
    }
}