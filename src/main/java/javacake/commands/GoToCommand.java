package javacake.commands;

import javacake.exceptions.CakeException;
import javacake.quiz.QuestionDifficulty;
import javacake.quiz.QuestionType;
import javacake.quiz.QuizSession;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

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
    public GoToCommand(String inputCommand) throws CakeException {
        String[] inputDivider = inputCommand.split("\\s+", 2);
        String gotoIndex;

        if (inputDivider.length == 1) { // no goto index
            throw new CakeException("Please specify the index you wish to go!");
        }
        gotoIndex = inputDivider[1];
        if (gotoIndex.matches("\\d+")) { //check if input is numeric
            checkOverflow(gotoIndex);
            indexQueue.add(gotoIndex);
        } else {
            processMultipleIndexes(gotoIndex);
        }
    }


    /**
     * Queues the index when multiple indexes are detected.
     * @param gotoIndex Index user wants to view.
     */
    private void processMultipleIndexes(String gotoIndex) throws CakeException {
        String[] buffer = gotoIndex.split("\\.");
        for (int i = 0; i < buffer.length; i++) {
            checkOverflow(buffer[i]);
        }
        indexQueue.addAll(Arrays.asList(buffer));
    }

    /**
     * Execute jumping to given index.
     * @param logic tracks current location in program
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container.
     * @throws CakeException Error thrown when unable to close reader or error in quiz format
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager)
            throws CakeException {
        int intIndex = Integer.parseInt(indexQueue.poll()) - 1;
        logic.updateFilePath(logic.gotoFilePath(intIndex));
        String filePath = logic.getFullFilePath();
        if (filePath.contains("Quiz")) {
            QuizSession.setProfile(storageManager.profile);
            if (!filePath.substring(filePath.length() - 4).equals("Quiz")) {
                throw new CakeException("Sorry, please type 'back' or 'list' instead.");
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

    private String handleQuiz(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        String filePath = logic.getFullFilePath();
        QuestionType qnType;
        QuestionDifficulty qnDifficulty;

        if (filePath.contains("1. Java Basics")) {
            qnType = QuestionType.BASIC;
        } else if (filePath.contains("2. Object-Oriented Programming")) {
            qnType = QuestionType.OOP;
        } else if (filePath.contains("3. Extensions")) {
            qnType = QuestionType.EXTENSIONS;
        } else {
            qnType = QuestionType.ALL;
        }

        if (filePath.contains("1. Easy Quiz")) {
            qnDifficulty = QuestionDifficulty.EASY;
        } else if (filePath.contains("2. Medium Quiz")) {
            qnDifficulty = QuestionDifficulty.MEDIUM;
        } else {
            qnDifficulty = QuestionDifficulty.HARD;
        }

        if (storageManager.profile.isCli) {
            return new QuizSession(qnType, qnDifficulty, true)
                    .execute(logic, ui, storageManager);
        } else {
            String response = null;
            logic.insertQueries();
            QuizSession.logic = logic;

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

            switch (qnDifficulty) {
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

    private void checkOverflow(String input) throws CakeException {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CakeException("Please input valid index!");
        }
    }
}
