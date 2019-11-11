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
    private static final int SPLIT_LIMIT = 2;
    private static final String BY_SPACES = "\\s+";
    private static final String BY_DOTS = "\\.";
    private static final String NUMERAL = "\\d+";

    /**
     * constructor for goto command. Contains a queue of index in which user wants to navigate into.
     * Splits command into 2, space delimiter, max size of inputDivider = 2
     * @param inputCommand Parsed goto command by user
     */
    public GoToCommand(String inputCommand) throws CakeException {
        type = CmdType.GOTO;
        String[] parameters = inputCommand.split(BY_SPACES, SPLIT_LIMIT);
        String gotoIndex;
        gotoIndex = findAndReturnIndex(parameters);
        processIndex(gotoIndex);
    }

    /**
     * Checks if the index in the GoToCommand matches a numeral.
     * Processes multiple indexes if specified.
     * @param gotoIndex goto index specified by the user.
     */
    private void processIndex(String gotoIndex) throws CakeException {
        if (gotoIndex.matches(NUMERAL)) { //check if input is numeric
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
        String[] buffer = gotoIndex.split(BY_DOTS);
        for (String s : buffer) {
            checkOverflow(s);
        }
        indexQueue.addAll(Arrays.asList(buffer));
    }

    /**
     * Execute jumping to given index.
     * @param logic tracks current location in program.
     * @param ui the Ui responsible for outputting messages.
     * @param storageManager storage container.
     * @throws CakeException Error thrown when unable to close reader or error in quiz format.
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        int gotoIndex = returnsGoToIndex();
        logic.updateFilePath(logic.gotoFilePath(gotoIndex));
        String filePath = logic.getFullFilePath();
        if (programIsAtQuiz(filePath)) {
            return processQuiz(logic, ui, storageManager, filePath);
        }
        logic.insertQueries();
        return processDirectoryOrText(logic, ui, storageManager);
    }

    /**
     * Checks if program current location is a quiz.
     * @param filePath Current file path in the program.
     * @return True if file path contains quiz.
     */
    private boolean programIsAtQuiz(String filePath) {
        return (filePath.contains("Quiz"));
    }

    /**
     * Checks current program location is directory or text.
     * Checks if queue is empty.
     * Calls executes until queue is empty.
     * Displays content based on logic location.
     * @param logic tracks current location in program.
     * @param ui the Ui responsible for outputting messages.
     * @param storageManager storage container.
     * @return Content based on logic location.
     * @throws CakeException File is not found.
     */
    private String processDirectoryOrText(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
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

    /**
     * Returns first goto index in the queue.
     * Converts index into integer and reduced it to a base zero index.
     * @return goto index.
     */
    private int returnsGoToIndex() {
        String firstIndexInQueue = indexQueue.poll();
        assert firstIndexInQueue != null;
        return Integer.parseInt(firstIndexInQueue) - 1;
    }

    /**
     * Processes quiz when GoToCommand is executed and is at a quiz.
     * @param logic tracks current location of program.
     * @param ui the Ui responsible for outputting messages.
     * @param storageManager storage container.
     * @param filePath File path to the current file.
     * @return Quiz questions.
     * @throws CakeException If quiz is not found.
     */
    private String processQuiz(Logic logic, Ui ui, StorageManager storageManager, String filePath)
            throws CakeException {
        QuizSession.setProfile(storageManager.profile);
        if (!lastFourLettersOfFilePath(filePath).equals("Quiz")) {
            throw new CakeException("Sorry, please type 'back' or 'list' instead.");
        }
        return handleQuiz(logic, ui, storageManager);
    }


    /**
     * Checks if command has a goto index.
     * Returns goto index if found.
     * @param parameters Keywords within goto command.
     * @return goto index.
     * @throws CakeException If no goto index is specified.
     */
    private String findAndReturnIndex(String[] parameters) throws CakeException {
        if (commandHasNoGoToIndex(parameters)) { // no goto index
            throw new CakeException("Please specify the index you wish to go!");
        } else {
            return parameters[1];
        }
    }

    /**
     * Checks if command has no parameter or goto index.
     * @param parameters Keywords within goto command.
     * @return True if there is no parameter.
     */
    private boolean commandHasNoGoToIndex(String[] parameters) {
        return (parameters.length == 1);
    }

    /**
     * Returns last four letters of file path.
     * @param filePath File path of the current program.
     * @return last four letters of file path.
     */
    private String lastFourLettersOfFilePath(String filePath) {
        return filePath.substring(filePath.length() - 4);
    }

    /**
     * Handles quiz when file path leads to quiz questions.
     * @param logic tracks the current location of program.
     * @param ui the Ui responsible for outputting messages.
     * @param storageManager storage container.
     * @return String of questions based on CLI or GUI.
     * @throws CakeException Quiz is not found.
     */
    private String handleQuiz(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        String filePath = logic.getFullFilePath();
        QuestionType qnType;
        QuestionDifficulty qnDifficulty;

        qnType = assignsQuestionType(filePath);
        qnDifficulty = assignsQuestionDifficulty(filePath);

        if (storageManager.profile.isCli) {
            return new QuizSession(qnType, qnDifficulty, true).execute(logic, ui, storageManager);
        } else {
            return processGui(logic, qnType, qnDifficulty);
        }
    }

    /**
     * Assigns type of question.
     * @param filePath File path to the quiz.
     * @return Type of question.
     */
    private QuestionType assignsQuestionType(String filePath) {
        if (filePath.contains("1. Java Basics")) {
            return QuestionType.BASIC;
        } else if (filePath.contains("2. Object-Oriented Programming")) {
            return QuestionType.OOP;
        } else if (filePath.contains("3. Extensions")) {
            return QuestionType.EXTENSIONS;
        } else {
            return QuestionType.ALL;
        }
    }

    /**
     * Assigns difficulty of question.
     * @param filePath Current file path to quiz.
     * @return Difficulty of question.
     */
    private QuestionDifficulty assignsQuestionDifficulty(String filePath) {
        if (filePath.contains("1. Easy Quiz")) {
            return QuestionDifficulty.EASY;
        } else if (filePath.contains("2. Medium Quiz")) {
            return QuestionDifficulty.MEDIUM;
        } else {
            return QuestionDifficulty.HARD;
        }
    }

    /**
     * Processes the response when running in GUI.
     * Creates and returns response based on question type and difficulty.
     * @param logic tracks current location in program
     * @param qnType Type of question.
     * @param qnDifficulty Difficulty of question.
     * @return Response based on question type and difficulty.
     * @throws CakeException NullPointerException when content is not found.
     */
    private String processGui(Logic logic, QuestionType qnType, QuestionDifficulty qnDifficulty) throws CakeException {
        String response;
        logic.insertQueries();
        QuizSession.logic = logic;
        response = assignsQnTypeResponse(qnType);
        response += assignsQnDifficultyResponse(qnDifficulty);
        return response;
    }

    /**
     * Assigns question type to response.
     * @param qnType Type of question.
     * @return question type to response.
     */
    private String assignsQnTypeResponse(QuestionType qnType) {
        switch (qnType) {
        case BASIC:
            return "!@#_QUIZ_1";
        case OOP:
            return "!@#_QUIZ_2";
        case EXTENSIONS:
            return "!@#_QUIZ_3";
        default:
            return "!@#_QUIZ_4";
        }
    }

    /**
     * Assigns question difficulty to response.
     * @param qnDifficulty Difficulty of question.
     * @return question difficulty to response.
     */
    private String assignsQnDifficultyResponse(QuestionDifficulty qnDifficulty) {
        switch (qnDifficulty) {
        case EASY:
            return "EZ";
        case MEDIUM:
            return "MED";
        default:
            return "HARD";
        }
    }

    /**
     * Checks if the index in the goto index contains only numerals.
     * @param input Goto index by the user.
     * @throws CakeException If the goto index contains non-integers.
     */
    private void checkOverflow(String input) throws CakeException {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CakeException("Please input valid index!");
        }
    }
}
