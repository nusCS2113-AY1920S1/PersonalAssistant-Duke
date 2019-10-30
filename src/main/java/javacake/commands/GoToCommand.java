package javacake.commands;

import javacake.Duke;
import javacake.exceptions.DukeException;
import javacake.quiz.QuestionDifficulty;
import javacake.quiz.QuestionType;
import javacake.quiz.QuizSession;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.util.LinkedList;
import java.util.Queue;

public class GoToCommand extends Command {

    private Queue<String> indexQueue = new LinkedList<>();

    /**
     * constructor for goto command. Contains a queue of index in which user wants to navigate into.
     * @param inputCommand Parsed goto command by user
     */
    public GoToCommand(String inputCommand) throws DukeException {
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
     * @param storageManager storage container.
     * @throws DukeException Error thrown when unable to close reader or error in quiz format
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager)
            throws DukeException {
        int intIndex = Integer.parseInt(indexQueue.poll()) - 1;
        logic.updateFilePath(logic.gotoFilePath(intIndex));
        String filePath = logic.getFullFilePath();
        if (filePath.contains("Quiz")) {
            QuizSession.setProfile(storageManager.profile);
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

        if (Duke.isCliMode()) {
            return new QuizSession(qnType, qnDifficulty, Duke.isCliMode())
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
}
