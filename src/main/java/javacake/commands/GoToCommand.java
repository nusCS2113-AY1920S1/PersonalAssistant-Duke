package javacake.commands;

import javacake.DukeException;
import javacake.ProgressStack;
import javacake.Profile;
import javacake.Ui;
import javacake.Storage;
import javacake.quiz.Question;

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
        int intIndex = Integer.parseInt(index)-1;
        progressStack.updateFilePath(progressStack.gotoFilePath(intIndex));
        String filePath = progressStack.getFullFilePath();
        if (filePath.contains("Quiz")) {
            if (filePath.contains("1. Java Basics")){
                return new QuizCommand(Question.QuestionType.BASIC).execute(progressStack, ui, storage, profile);
            } else if (filePath.contains("2. Object-Oriented Programming")) {
                return new QuizCommand(Question.QuestionType.OOP).execute(progressStack, ui, storage, profile);
            } else if (filePath.contains("3. Extensions")) {
                return new QuizCommand(Question.QuestionType.EXTENSIONS).execute(progressStack, ui, storage, profile);
            } else {
                return new QuizCommand(Question.QuestionType.ALL).execute(progressStack, ui, storage, profile);
            }
        }
        progressStack.insertQueries();
        try {
            if (progressStack.containsDirectory()) {
                    return (progressStack.displayDirectories());
                } else {
                    progressStack.updateFilePath(progressStack.gotoFilePath(0));
                    return (progressStack.readQuery());
                }
            } catch (IOException e) {
                throw new DukeException(e.getMessage());
            }
            //progressStack.processQueries();



            /*if (progressStack.checkProgress() == 1 && index.equals("1")) {
                progressStack.mainListToListIndex1();
                new ListIndex1().printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("2")) {
                progressStack.mainListToListIndex2();
                new ListIndex2().printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("3")) {
                progressStack.mainListToListIndex3();
                new ListIndex3().printList();
            } else if (progressStack.checkProgress() == 1 && index.equals("4")) {
                new QuizCommand().execute(progressStack, ui, storage, profile);
            } else if (progressStack.checkProgress() == 2 && index.equals("1.1")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/JavaBasics/1.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("1.2")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/JavaBasics/2.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("1.3")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex1/JavaBasics/3.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("1.4")) {
                new QuizCommand(Question.QuestionType.BASIC).execute(progressStack, ui, storage, profile);
            } else if (progressStack.checkProgress() == 2 && index.equals("2.1")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/1.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.2")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/2.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.3")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/oop/3.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.4")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex2/content/oop/4.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("2.5")) {
                new QuizCommand(Question.QuestionType.OOP).execute(progressStack, ui, storage, profile);

            } else if (progressStack.checkProgress() == 2 && index.equals("3.1")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Enumerations/Enumerations.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("3.2")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Varargs/Varargs.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("3.3")) {
                progressStack.listIndexToSubList();
                ui.displayTextFile(new BufferedReader(
                        new FileReader("content/MainList/ListIndex3/Exceptions/Exceptions.txt")));
            } else if (progressStack.checkProgress() == 2 && index.equals("3.4")) {
                new QuizCommand(Question.QuestionType.EXTENSIONS).execute(progressStack, ui, storage, profile);
            } else {
                throw new DukeException("Please enter a valid index!");
            }*/
    }
}
