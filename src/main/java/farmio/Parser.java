package farmio;

import commands.*;


import usercode.tasks.Task;
import usercode.tasks.IfTask;
import usercode.tasks.ForTask;
import usercode.tasks.DoTask;
import usercode.tasks.WhileTask;
import exceptions.FarmioException;
import usercode.actions.Action;
import usercode.conditions.Condition;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser class is responsible for parsing all user input and generating the corresponding Command.
 */

public class Parser {

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Returns a Command depending on the current Stage of the game, and the user's input.
     *
     * @param userInput input String either from user or Farmio depending on the game stage
     * @param stage enum that represents the current game stage
     * @return a Command that can be executed, based on the current stage and user input
     * @throws FarmioException if an unknown game stage is passed
     */
    public static Command parse(String userInput, Farmio.Stage stage) throws FarmioException {
        userInput = userInput.toLowerCase().trim();
        if (userInput.equals("quit game") || userInput.equals("exit")) {
            return new CommandGameQuit();
        }
        if (stage != Farmio.Stage.WELCOME && stage != Farmio.Stage.MENU_START && userInput.equals("save game")) {
            return new CommandGameSave();
        }
        if (userInput.equals("load game")) {
            return new CommandGameLoad();
        }
        if (userInput.equals("new game")) {
            return new CommandGameNew();
        }
        switch (stage) {
        case WELCOME:
            return new CommandMenuStart();
        case LEVEL_START:
            return new CommandLevelStart();
//        case MENU:
//            return parseMenu(userInput);
        case RUNNING_DAY:
            return new CommandTasksRun();
        case CHECK_OBJECTIVES:
            return new CommandCheckObjectives();
        case DAY_START:
            return new CommandDayStart();
        case LEVEL_END:
            return new CommandLevelEnd();
        case LEVEL_FAILED:
            return new CommandLevelReset();
        case DAY_END:
            return parseDayEnd(userInput);
        case NAME_ADD:
            return new CommandAddName(userInput);
        case TASK_ADD:
            return parseTaskAdd(userInput);
        default:
            LOGGER.log(Level.INFO, "Deteched invalid command at stage: "
                    + stage.toString() + " command: " + userInput);
            throw new FarmioException("Invalid Command!");
        }
    }

    /**
     * Allows the user to resume the game from the menu.
     *
     * @param userInput input String from the user
     * @return Command that corresponds to the user's actions
     * @throws FarmioException if user input is invalid
     */
//    private static Command parseMenu(String userInput) throws FarmioException {
//        if (userInput.equals("resume game")) {
//            return new CommandLevelStart();
//        } else if (userInput.equals("actions") || userInput.equals("conditions") || userInput.equals("market") || userInput.equals("")) {
//            return new CommandMenuInGame(userInput);
//        } else {
//            throw new FarmioException("Invalid command!");
//        }
//    }

    /**
     * Used to parse the user input during the DAY_END stage. User can choose to either reset the level,
     * or proceed to the next day
     *
     * @param userInput user input String
     * @return Command that either resets the level, or lets the user proceed to the next day
     * @throws FarmioException if user input is invalid
     */
    private static Command parseDayEnd(String userInput) throws FarmioException {
        if (userInput.length() == 0) {
            return new CommandDayEnd();
        }
        if (userInput.equals("reset")) {
            return new CommandLevelReset();
        }
        LOGGER.log(Level.SEVERE, "Deteched invalid command for command: " + userInput);
        throw new FarmioException("Invalid Command!");
    }

    /**
     * Used to parse the user's input during the TASK_ADD stage. Facilitates creating, editing and deleting of tasks,
     * as well as opening in=game menu, or seeing the list of actions or conditions
     *
     * @param userInput user input String
     * @return Command that corresponds to the user's input
     * @throws FarmioException if user input is invalid
     */
    private static Command parseTaskAdd(String userInput) throws FarmioException {
        if (userInput.equals("menu")) {
            return new CommandMenuInGame();
        }
        if (userInput.equals("deleteall") || userInput.equals("delete all")) {
            return new CommandTaskDeleteAll();
        }
        if (userInput.startsWith("delete")) {
            return parseTaskDelete(userInput);
        }
        if (userInput.startsWith("insert")) {
            return insertTask(userInput);
        }
        if (userInput.startsWith("edit")) {
            return editTask(userInput);
        }
        if (userInput.toLowerCase().equals("start")) {
            return new CommandDayStart();
        }
        if (userInput.equals("conditions") || userInput.equals("condition")) {
            return new CommandShowList("ConditionList");
        }
        if (userInput.equals("actions") || userInput.equals("action")) {
            return new CommandShowList("ActionList");
        }
        if (userInput.equals("market")) {
            return new CommandShowList("MarketList");
        }
        if (userInput.equals("task commands") || userInput.equals("task command")) {
            return new CommandShowList("TaskCommands");
        }
        if (userInput.startsWith("do") || userInput.startsWith("if")
                || userInput.startsWith("for") || userInput.startsWith("while")) {
            return new CommandTaskCreate(parseTask(userInput));
        } else if (userInput.equals("hint")) {
            return new CommandTasksHint();
        } else if (userInput.equals("")) {
            return new CommandTaskAddReset();
        }
        LOGGER.log(Level.INFO, "Deteched invalid commandfor command: " + userInput);
        throw new FarmioException("Invalid command!");
    }

    /**
     * Used to parse the user's command if it is determined to be a delete task command.
     *
     * @param userInput user input String
     * @return Command that deletes the specified task when executed
     * @throws FarmioException if user input is invalid
     */
    private static Command parseTaskDelete(String userInput) throws FarmioException {
        Matcher matcher = Pattern.compile("^delete\\s+(?<index>\\d+)$").matcher(userInput);
        if (matcher.find()) {
            int taskID = Integer.parseInt(matcher.group("index"));
            return new CommandTaskDelete(taskID);
        }
        LOGGER.log(Level.INFO, "Deteched invalid command for command: " + userInput);
        throw new FarmioException("Invalid argument.");
    }

    /**
     * Determines if the user is creating a DoTask or a ConditionalTask, and calls the corresponding function.
     * to further parse the user input
     *
     * @param userInput user input String
     * @return Task generated from the user's input
     * @throws FarmioException if there is an error in generating a Task from the user's input
     */
    private static Task parseTask(String userInput) throws FarmioException {
        if (userInput.startsWith("do")) {
            return parseDoTask(userInput);
        } else {
            return parseConditionalTask(userInput);
        }
    }

    /**
     * Used to generate a DoTask from the user's input.
     *
     * @param userInput user input String
     * @return Task corresponding to the user input
     * @throws FarmioException if user input is of incorrect format, or the taskType or action is invalid
     */
    private static Task parseDoTask(String userInput) throws FarmioException {
        String taskType = "";
        String userAction = "";
        try {
            taskType = userInput.substring(0, userInput.indexOf(" "));
            userAction = (userInput.substring(userInput.indexOf(" "))).trim();
        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Invalid command format!");
        }
        if (!taskType.equals("do")) {
            LOGGER.log(Level.INFO, "Deteched invalid task type for command: " + userInput);
            throw new FarmioException("Invalid task type!");
        }
        if (Action.isValidAction(userAction)) {
            return new DoTask(Condition.toCondition("true"), Action.toAction(userAction));
        } else {
            LOGGER.log(Level.INFO, "Deteched invalid action for command: " + userInput);
            throw new FarmioException("Invalid action!");
        }
    }

    /**
     * Creates a Conditional Task from the user input.
     *
     * @param userInput user input String
     * @return Task corresponding to the user input
     * @throws FarmioException if user input is of wrong format, or either the tasktype, action or condition is invalid
     */
    private static Task parseConditionalTask(String userInput) throws FarmioException {
        String taskType = "";
        String condition = "";
        String action = "";
        try {
            taskType = (userInput.substring(0, userInput.indexOf(" "))).trim();
            condition = (userInput.substring(userInput.indexOf(" ") + 1, userInput.indexOf("do"))).trim();
            action = userInput.substring(userInput.lastIndexOf(" ") + 1);
        } catch (IndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, e.toString());
            throw new FarmioException("Invalid command format!");
        }
        if (!taskType.equals("if")  && !taskType.equals("for") && !taskType.equals("while")) {
            LOGGER.log(Level.INFO, "Deteched invalid task type for command: " + userInput);
            throw new FarmioException("Invalid task type!");
        }
        if (!Condition.isValidCondition(condition)) {
            LOGGER.log(Level.INFO, "Deteched invalid condition for command: " + userInput);
            throw new FarmioException("Invalid Condition!");
        }
        if (!Action.isValidAction(action)) {
            LOGGER.log(Level.INFO, "Deteched invalid action for command: " + userInput);
            throw new FarmioException("Invalid Action!");
        }
        Task task;
        switch (taskType) {
        case "if":
            task = new IfTask(Condition.toCondition(condition), Action.toAction(action));
            break;
        case "for":
            task = new ForTask(Condition.toCondition(condition), Action.toAction(action));
            break;
        case "while":
            task = new WhileTask(Condition.toCondition(condition), Action.toAction(action));
            break;
        default:
            LOGGER.log(Level.SEVERE, "Impossible exception reached! command:" + userInput);
            throw new FarmioException("Error Creating Task!");
        }
        return task;
    }

    /**
     * Parses commands meant to edit any Task in the TaskList.
     * Edit commands must be of the form 'edit [TaskID] [taskType] [Condition] do [Action].
     *
     * @param userInput user input String
     * @return Command that will edit the Task in the TaskList with the specified ID when executed
     * @throws FarmioException if the user's input is of wrong format or the task description is invalid
     */
    private static Command editTask(String userInput) throws FarmioException {
        Matcher matcher = Pattern.compile("^(?<key>edit)\\s+(?<index>-?\\d+)\\s(?<cmd>.+)$").matcher(userInput);
        if(matcher.find()){
            int taskID = Integer.parseInt(matcher.group("index"));
            Task task = parseTask(matcher.group("cmd"));
            return new CommandTaskEdit(taskID, task);
        }
        LOGGER.log(Level.SEVERE, "Deteched invalid command for command: " + userInput);
        throw new FarmioException("Invalid Command");
    }

    /**
     * Parses commands meant to insert a Task at a specific position in the TaskList.
     *
     * @param userInput user input String
     * @return Command that inserts a Task at the specified position
     * @throws FarmioException if the user input is of invalid format, or the task description is invalid
     */
    private static Command insertTask(String userInput) throws FarmioException {
        Matcher matcher = Pattern.compile("^(?<key>insert)\\s+(?<id>-?\\d+)\\s+(?<task>.+)$").matcher(userInput);
        if(matcher.find()){
            int taskID = Integer.parseInt(matcher.group("id"));
            Task task = parseTask(matcher.group("task"));
            return new CommandTaskInsert(taskID, task);
        }
        LOGGER.log(Level.SEVERE, "Deteched invalid command for command: " + userInput);
        throw new FarmioException("Invalid Command");
    }
}
