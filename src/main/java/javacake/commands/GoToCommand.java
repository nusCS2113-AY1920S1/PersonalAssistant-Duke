package javacake.commands;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.Logic;
import javacake.storage.Storage;
import javacake.ui.Ui;
import javacake.quiz.Question;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class GoToCommand extends Command {

    private Queue<String> indexQueue = new LinkedList<>();

    /**
     * constructor for goto command. Contains a queue of index in which user wants to navigate into.
     * @param inputCommand Parsed goto command by user
     */
    public GoToCommand(String inputCommand) {
        if (inputCommand.matches("\\d+")) { //check if input is numeric
            indexQueue.add(inputCommand);
        } else {
            String[] buffer = inputCommand.split("\\.");
            for (int i = 0; i < buffer.length; i++) {
                indexQueue.add(buffer[i]);
            }
        }
    }

    /**
     * Execute jumping to given index.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storage Storage needed to write the updated data
     * @param profile Profile of the user
     * @throws DukeException Error thrown when unable to close reader
     */
    public String execute(Logic logic, Ui ui, Storage storage, Profile profile)
            throws DukeException, IOException {
        int intIndex = Integer.parseInt(indexQueue.poll()) - 1;
        logic.updateFilePath(logic.gotoFilePath(intIndex));
        String filePath = logic.getFullFilePath();
        if (filePath.contains("Quiz")) {

            if (!filePath.substring(filePath.length() - 4).equals("Quiz")) {
                throw new DukeException("Sorry, please type 'back' or 'list' instead.");
            }

            if (filePath.contains("1. Java Basics")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.BASIC, Duke.isCliMode())
                            .execute(logic, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    logic.insertQueries();
                    QuizCommand.logic = logic;
                    return "!@#_QUIZ_1";
                }
            } else if (filePath.contains("2. Object-Oriented Programming")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.OOP, Duke.isCliMode())
                            .execute(logic, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    logic.insertQueries();
                    QuizCommand.logic = logic;
                    return "!@#_QUIZ_2";
                }
            } else if (filePath.contains("3. Extensions")) {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.EXTENSIONS, Duke.isCliMode())
                            .execute(logic, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    logic.insertQueries();
                    QuizCommand.logic = logic;
                    return "!@#_QUIZ_3";
                }
            } else {
                if (Duke.isCliMode()) {
                    return new QuizCommand(Question.QuestionType.ALL, Duke.isCliMode())
                            .execute(logic, ui, storage, profile);
                } else {
                    QuizCommand.setProfile(profile);
                    logic.insertQueries();
                    QuizCommand.logic = logic;
                    return "!@#_QUIZ_4";
                }
            }
        }
        logic.insertQueries();
        if (logic.containsDirectory()) {
            if (indexQueue.size() != 0) {
                return execute(logic, ui, storage, profile);
            }
            return (logic.displayDirectories());
        } else {
            logic.updateFilePath(logic.gotoFilePath(0));
            if (indexQueue.size() != 0) {
                return execute(logic, ui, storage, profile);
            }
            return (logic.readQuery());
        }
    }
}
