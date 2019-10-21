package javacake.commands;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.ProgressStack;
import javacake.storage.Storage;
import javacake.ui.Ui;
import javacake.quiz.Question;

import java.util.LinkedList;
import java.util.Queue;

public class GoToCommand extends Command {

    private Queue<String> index = new LinkedList<>();

    /**
     * constructor for goto command. Contains a queue of index in which user wants to navigate into.
     * @param inputCommand Parsed goto command by user
     */
    public GoToCommand(String inputCommand) {
        if (inputCommand.matches("\\d+")) { //check if input is numeric
            index.add(inputCommand);
        } else {
            String[] buffer = inputCommand.split("\\.");
            for (int i = 0; i < buffer.length; i++) {
                index.add(buffer[i]);
            }
        }
    }

    /**
     * Execute jumping to given index.
     * @param progressStack tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @throws DukeException Error thrown when unable to close reader
     */
    public String execute(ProgressStack progressStack, Ui ui, Storage storage, Profile profile) throws DukeException {
        int intIndex = Integer.parseInt(index.poll()) - 1;
        progressStack.updateFilePath(progressStack.gotoFilePath(intIndex));
        String filePath = progressStack.getFullFilePath();
        if (filePath.contains("Quiz")) {
            if (filePath.contains("1. Java Basics")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.BASIC, Duke.isCliMode())
                            .execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    progressStack.insertQueries();
                    QuizCommand.progressStack = progressStack;
                    return "!@#_QUIZ_1";
                }
            } else if (filePath.contains("2. Object-Oriented Programming")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.OOP, Duke.isCliMode())
                            .execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    progressStack.insertQueries();
                    QuizCommand.progressStack = progressStack;
                    return "!@#_QUIZ_2";
                }
            } else if (filePath.contains("3. Extensions")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.EXTENSIONS, Duke.isCliMode())
                            .execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    progressStack.insertQueries();
                    QuizCommand.progressStack = progressStack;
                    return "!@#_QUIZ_3";
                }
            } else {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.ALL, Duke.isCliMode())
                            .execute(progressStack, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    progressStack.insertQueries();
                    QuizCommand.progressStack = progressStack;
                    return "!@#_QUIZ_4";
                }
            }
        }
        progressStack.insertQueries();
        if (progressStack.containsDirectory()) {
            if (index.size() != 0) {
                return execute(progressStack, ui, storage, profile);
            }
            return (progressStack.displayDirectories());
        } else {
            progressStack.updateFilePath(progressStack.gotoFilePath(0));
            if (index.size() != 0) {
                return execute(progressStack, ui, storage, profile);
            }
            return (progressStack.readQuery());
        }
    }
}
