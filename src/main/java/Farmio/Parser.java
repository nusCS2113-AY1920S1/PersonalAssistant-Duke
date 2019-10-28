package Farmio;

import Commands.*;
import Exceptions.FarmioException;
import UserCode.Actions.Action;
import UserCode.Conditions.Condition;
import UserCode.Tasks.TemplateTask;

public class Parser {
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
            case MENU:
                return parseMenu(userInput);
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
                return new CommandDayEnd(); //TODO check if reset for dayend
            case NAME_ADD:
                return new CommandAddName(userInput);
            case TASK_ADD:
                return parseTaskAdd(userInput);
            default:
                throw new FarmioException("Invalid Command!");
        }
    }

    private static Command parseMenu(String userInput) throws FarmioException {
        if(userInput.equals("resume game")){
            return new CommandLevelStart();
        }else {
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
            return new CommandMenu();
        }
        if (userInput.equals("deleteall") || userInput.equals("delete all")) {
            return new CommandTaskDeleteAll();
        }
        if (userInput.startsWith("delete")) {
            return parseTaskDelete(userInput);
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
        if (userInput.startsWith("do") || userInput.startsWith("if") || userInput.startsWith("for") || userInput.startsWith("while")) {
            return addTask(userInput);
        } else if (userInput.equals("hint") || userInput.equals(""))
            return new CommandTasksHint();
        throw new FarmioException("Invalid command!");
    }

    private static Command parseTaskDelete(String userInput) throws FarmioException {
        if (userInput.matches("(delete)\\s+\\d+")) {
            int taskID = Integer.parseInt((userInput.substring(userInput.indexOf(" "))).trim());
            return new CommandTaskDelete(taskID);
        }
        throw new FarmioException("Invalid Command!");
    }

    private static Command addTask(String userInput) throws FarmioException {
        TemplateTask t = parseTask(userInput);
        return new CommandTaskCreate(t);
    }

    private static TemplateTask parseTask(String userInput) throws FarmioException {
        if (userInput.startsWith("do")) {
            return parseDoTask(userInput);
        } else {
            return parseConditionalTask(userInput);
        }
    }

    private static TemplateTask parseDoTask(String userInput) throws FarmioException {
        String taskType = "", userAction = "";
        try {
            taskType = userInput.substring(0, userInput.indexOf(" "));
            userAction = (userInput.substring(userInput.indexOf(" "))).trim();
        } catch (Exception e ) {
            throw new FarmioException("Invalid command format!");
        }
        if (!taskType.equals("do")) {
            throw new FarmioException("Invalid task type!");
        }
        if (Action.validateAction(userAction)) {
            return new TemplateTask("do","true", userAction);
        } else {
            throw new FarmioException("Invalid action!");
        }
    }

    private static TemplateTask parseConditionalTask (String userInput) throws FarmioException {
        String taskType = "", condition = "", action = "";
        try {
            taskType = (userInput.substring(0, userInput.indexOf(" "))).trim();
            condition = (userInput.substring(userInput.indexOf(" ") + 1, userInput.indexOf("do"))).trim();
            action = userInput.substring(userInput.lastIndexOf(" ") + 1);
        } catch (Exception e) {
            throw new FarmioException("Invalid command format!");
        }
        if (!taskType.equals("if")  && !taskType.equals("for") && !taskType.equals("while")) {
            throw new FarmioException("Invalid task type!");
        }
        if (!Condition.isValidCondition(condition)) {
            throw new FarmioException("Invalid Condition!");
        }
        if (!Action.validateAction(action)) {
            throw new FarmioException("Invalid Action!");
        }
        return new TemplateTask(taskType, condition, action);
    }

    private static Command editTask(String userInput) throws FarmioException {
        String keyword = "", taskID = "",  task = "";
        if (userInput.matches("(edit)\\s+\\d+\\s+.+")) {
            try {
                keyword = userInput.substring(0, userInput.indexOf(" "));
                userInput = (userInput.substring(userInput.indexOf(" ") + 1)).trim();
                taskID = (userInput.substring(0, userInput.indexOf(" "))).trim();
                task = (userInput.substring(userInput.indexOf(" ") + 1)).trim();
            } catch (Exception e) {
                throw new FarmioException("Invalid command format!");
            }
            if (!keyword.equals("edit")) {
                throw new FarmioException("Invalid Command!");
            }
            TemplateTask templateTask = parseTask(task);
            return new CommandTaskEdit(Integer.parseInt(taskID), templateTask);
        } else {
            throw new FarmioException("Invalid Command Format");
        }
    }
}
