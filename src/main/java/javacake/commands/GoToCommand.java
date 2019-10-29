package javacake.commands;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.storage.Profile;
import javacake.Logic;
import javacake.storage.Storage;
import javacake.storage.StorageManager;
import javacake.ui.Ui;
import javacake.quiz.Question;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class GoToCommand extends Command {

    private Queue<String> indexQueue = new LinkedList<>();

    /**
     * constructor for goto command. Contains a queue of index in which user wants to navigate into.
     * Splits command into 2, space delimiter, max size of inputDivider = 2
     * @param inputCommand Parsed goto command by user
     */
    public GoToCommand(String inputCommand) throws DukeException {
        String[] inputDivider = inputCommand.split("\\s+", 2);
        String gotoIndex;

        if (inputDivider.length == 1) { // no goto index
            throw new DukeException("Please specify the index you wish to go!");
        } else {
            gotoIndex = inputDivider[1];
        }
        if (gotoIndex.matches("\\d+")) { //check if input is numeric
            indexQueue.add(gotoIndex);
        } else {
            processMultipleIndexes(gotoIndex);
        }
    }

    /**
     * Queues the index when multiple indexes are detected.
     * @param gotoIndex Index user wants to view.
     */
    private void processMultipleIndexes(String gotoIndex) {
        String[] buffer = gotoIndex.split("\\.");
        indexQueue.addAll(Arrays.asList(buffer));
    }

    /**
     * Execute jumping to given index.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @throws DukeException Error thrown when unable to close reader or error in quiz format
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager)
            throws DukeException {
        int intIndex = Integer.parseInt(indexQueue.poll()) - 1;
        logic.updateFilePath(logic.gotoFilePath(intIndex));
        String filePath = logic.getFullFilePath();
        if (filePath.contains("Quiz")) {
            if (!filePath.substring(filePath.length() - 4).equals("Quiz")) {
                throw new DukeException("Sorry, please type 'back' or 'list' instead.");
            }
            return handleQuiz(logic, ui, storageManager);
        }
        logic.insertQueries();
        if (logic.containsDirectory()) {
            if (indexQueue.size() != 0) {
                return execute(logic, ui, storageManager);
            }
            return (logic.displayDirectories());
        } else {
            logic.updateFilePath(logic.gotoFilePath(0));
            if (indexQueue.size() != 0) {
                return execute(logic, ui, storageManager);
            }
            return (logic.readQuery());
        }
    }

    private String handleQuiz(Logic logic, Ui ui, StorageManager storageManager) throws DukeException {
        String filePath = logic.getFullFilePath();
        Question.QuestionType qnType;
        Question.QuestionDifficulty qnDifficulity;

        if (filePath.contains("1. Java Basics")) {
            qnType = Question.QuestionType.BASIC;
        } else if (filePath.contains("2. Object-Oriented Programming")) {
            qnType = Question.QuestionType.OOP;
        } else if (filePath.contains("3. Extensions")) {
            qnType = Question.QuestionType.EXTENSIONS;
        } else {
            qnType = Question.QuestionType.ALL;
        }


        if (filePath.contains("1. Easy Quiz")) {
            qnDifficulity = Question.QuestionDifficulty.EASY;
        } else if (filePath.contains("2. Medium Quiz")) {
            qnDifficulity = Question.QuestionDifficulty.MEDIUM;
        } else {
            qnDifficulity = Question.QuestionDifficulty.HARD;
        }

        if (Duke.isCliMode()) {
            return new QuizCommand(qnType, qnDifficulity, Duke.isCliMode())
                    .execute(logic, ui, storageManager);
        } else {
            String response = null;
            QuizCommand.setProfile(storageManager.profile);
            logic.insertQueries();
            QuizCommand.logic = logic;

            switch (qnType) {
            case BASIC:
                response = "!@#_QUIZ_1";
                break;
            case OOP:
                response = "!@#_QUIZ_2";
                break;
            case EXTENSIONS:
                response = "!@#_QUIZ_3";
                break;
            default:
                response = "!@#_QUIZ_4";
                break;
            }

            switch (qnDifficulity) {
            case EASY:
                response += "EZ";
                break;
            case MEDIUM:
                response += "MED";
                break;
            default:
                response += "HARD";
                break;
            }
            return response;
        }
    }
}
