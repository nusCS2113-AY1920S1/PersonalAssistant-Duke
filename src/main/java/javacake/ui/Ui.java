package javacake.ui;

import javacake.exceptions.DukeException;
import javacake.quiz.QuestionList;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    private static String cake = "cake";
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
     * @param progress progress of user
     * @return String containing first part of welcome message for GUI
     */
    public static String showWelcomeMsgPhaseB(boolean isFirstTime, String userName, int progress) {
        StringBuilder strA = new StringBuilder();
        if (isFirstTime) {
            strA.append("\nWelcome to JavaCake, ").append(userName).append("! ");
            strA.append("Now let's help you get started with Java! :3\n");
            strA.append(helpMessage());
        } else {
            strA.append("Hello ").append(userName).append("!\n");
            strA.append(getQuizResults(progress));
            strA.append("\nWhat do you want to do today?\n");
            strA.append(helpMessage());
        }
        return strA.toString();
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
        return "\nType 'list' to view main topics\n" + "Type 'exit' to rage quit\n";
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

    /**
     * Method to get text from file.
     * @param reader BufferedReader to read in text from file
     * @throws DukeException Error thrown when unable to close reader
     */
    public static String getTextFile(BufferedReader reader) throws DukeException {
        String lineBuffer;
        String output = "";
        try {
            while ((lineBuffer = reader.readLine()) != null) {
                output += lineBuffer;
                output += "\n";
            }
            reader.close();
        } catch (IOException e) {
            throw new DukeException("File not found!");
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

        System.out.println("Type \"back\" to go back to the table of contents.");
    }

    /**
     * Method to get quiz score.
     * @param progress the user's overall quiz score
     * @return String with quiz score message
     */
    public static String getQuizResults(int progress) {
        StringBuilder str = new StringBuilder();
        str.append("Here's your quiz progress so far :D\n");
        for (int i = 0; i < 4 * QuestionList.MAX_QUESTIONS; ++i) {
            if (i < progress) {
                str.append("#");
            } else {
                str.append("-");
            }
        }
        progress = progress * 100 / (4 * QuestionList.MAX_QUESTIONS);
        if (progress == 99) {
            progress = 100;
        }
        str.append(" ").append(progress).append("%\n");
        return  str.toString();
    }
}
