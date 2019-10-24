package Farmio;

import Commands.*;
import Exceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;

class Parser {
    static Command parse(String userInput, Farmio.Stage stage) throws FarmioException {
        System.out.println(stage.name());
        userInput = userInput.toLowerCase().trim();
        if (userInput.equals("quit game")) {
            return new CommandGameQuit();
        }
        if (stage != Farmio.Stage.WELCOME && stage != Farmio.Stage.MENU_START && userInput.matches("^save game$")) {
            return new CommandGameSave();
        }
        if (userInput.matches("^load game$")) {
            return new CommandGameLoad();
        }
        switch (stage) {
            case WELCOME:
                return new CommandMenuStart();
            case MENU_START:
                return parseMenuStart(userInput);
            case LEVEL_START:
                return new CommandLevelStart();
            case TASK_ADD:
                return parseTaskAdd(userInput);
            case RUNNING_DAY:
                return new CommandTasksRun();
            case CHECK_OBJECTIVES:
                return new CommandCheckObjectives();
            case DAY_END:
                return new CommandDayEnd();
            case DAY_START:
                return new CommandDayNew();
            case LEVEL_END:
                return new CommandLevelEnd();
            case LEVEL_FAILED:
                return new CommandLevelReset();
            default:
                //Game should not reach this stage.
                stage = Farmio.Stage.WELCOME;
                throw new FarmioException("Something went wrong! Restarting game.");
        }
    }

    private static Command parseMenuStart(String userInput) throws FarmioException {
        switch (userInput) {
            case "load game":
                return new CommandGameLoad();
            case "new game":
                return new CommandGameNew();
            case "quit game":
                return new CommandGameQuit();
            default:
                throw new FarmioException("Invalid command!");
        }
    }

    private static Command parseDayEnd(String userInput) throws FarmioException {
        if (userInput.length() == 0 ) {
            return new CommandDayEnd();
        }
        if (userInput.equals("reset")) {
            return new CommandLevelReset();
        }
        throw new FarmioException("Invalid Command!");
    }

    private static Command parseTaskAdd(String userInput) throws FarmioException {
        if (userInput.equals("menu")) {
            return new CommandMenuStart();
        }
        if (userInput.equals("deleteall") || userInput.equals("delete all")) {
            return new CommandTaskDeleteAll();
        }
        if (userInput.startsWith("delete")) {
            return parseTaskDelete(userInput);
        }
        if (userInput.toLowerCase().equals("start")) {
            return new CommandDayStart();
        }
        if (userInput.equals("conditions") || userInput.equals("condition")) {
            return new CommandConditionShow();
        }
        if (userInput.equals("actions") || userInput.equals("action")) {
            return new CommandActionShow();
        }
        if (userInput.startsWith("do")) {
            return parseDoTask(userInput);
        } else if (userInput.startsWith("if") || userInput.startsWith("for") || userInput.startsWith("while")) {
            return parseConditionalTask(userInput);
        } else if (userInput.equals(""))
            return new CommandTasksNull();
        return new CommandTasksNull("Invalid command!");
    }

    private static Command parseTaskDelete(String userInput) throws FarmioException {
        if (userInput.matches("(delete)\\s+\\d+")) {
            int taskID = Integer.parseInt((userInput.substring(userInput.indexOf(" "))).trim());
            return new CommandTaskDelete(taskID);
        }
        throw new FarmioException("Invalid Command!");
    }

    private static Command parseDoTask(String userInput) throws FarmioException {
        String userAction = (userInput.substring(userInput.indexOf(" "))).trim();
        if (Action.validateAction(userAction)) {
            return new CommandTaskCreate("do","true", userAction);
        } else {
            throw new FarmioException("Invalid action!");
        }
    }

    private static Command parseConditionalTask (String userInput) throws FarmioException {
        String taskType = "", condition = "", action = "";
        try {
            taskType = (userInput.substring(0, userInput.indexOf(" "))).trim();
            condition = (userInput.substring(userInput.indexOf(" ") + 1, userInput.indexOf("do"))).trim();
            action = userInput.substring(userInput.lastIndexOf(" ") + 1);
        } catch (Exception e) {
            throw new FarmioException("Invalid command format!");
        }
        if (!taskType.equals("if")  && ! taskType.equals("for") && !taskType.equals("while")) {
            throw new FarmioException("Invalid Task Type!");
        }
        if (!Condition.validateCondition(condition)) {
            throw new FarmioException("Invalid Condition!");
        }
        if (!Action.validateAction(action)) {
            throw new FarmioException("Invalid Action!");
        }
        return new CommandTaskCreate(taskType, condition, action);
    }

}
