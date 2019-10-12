package javacake.commands;

import javacake.Duke;
import javacake.DukeException;
import javacake.ProgressStack;
import javacake.Profile;
import javacake.Ui;
import javacake.Storage;
import javacake.quiz.Question;
import javacake.topics.ListIndex1;
import javacake.topics.ListIndex2;
import javacake.topics.ListIndex3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GoToCommand extends Command {

    private String index;

    public GoToCommand(String inputCommand) {
        String[] buffer = inputCommand.split("\\s+");
        index = buffer[1];
    }

    /**
     * Execute jumping to given index.
     * @param progressStack TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @throws DukeException Error thrown when unable to close reader
     */
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        try {
            if (progressStack.checkProgress() == 1 && index.equals("1")) {
                progressStack.mainListToListIndex1();
                return new ListIndex1().printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("2")) {
                progressStack.mainListToListIndex2();
                return new ListIndex2().printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("3")) {
                progressStack.mainListToListIndex3();
                return new ListIndex3().printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("4")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.ALL).execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    return "!@#_QUIZ_4";
                }
            } else if (progressStack.checkProgress() == 2 && index.equals("1.1")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/javabasics/Print.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("1.2")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/javabasics/Read.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("1.3")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/javabasics/ClassesandObjects.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("1.4")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.BASIC).execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    return "!@#_QUIZ_1";
                }
            } else if (progressStack.checkProgress() == 2 && index.equals("2.1")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/Abstraction.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.2")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/Encapsulation.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.3")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/Inheritance.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.4")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/content/oop/Polymorphism.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.5")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.OOP).execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    return "!@#_QUIZ_2";
                }
            } else if (progressStack.checkProgress() == 2 && index.equals("3.1")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Enumerations/Enumerations.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("3.2")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Varargs/Varargs.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("3.3")) {
                progressStack.listIndexToSubList();
                return getTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Exceptions/Exceptions.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("3.4")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.EXTENSIONS)
                            .execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    return "!@#_QUIZ_3";
                }
            } else {
                throw new DukeException("Please enter a valid index!");
            }
        } catch (IOException e) {
            throw new DukeException("File does not exists");
        }
    }

    /**
     * Method to get text from file.
     * @param reader BufferedReader to read in text from file
     * @throws DukeException Error thrown when unable to close reader
     */
    private String getTextFile(BufferedReader reader) throws DukeException {
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
}
