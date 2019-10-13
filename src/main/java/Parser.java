import Commands.*;
import FarmioExceptions.FarmioException;
import Places.ChickenFarm;
import Places.CowFarm;
import Places.Market;
import Places.WheatFarm;
import UserCode.Actions.Action;
import UserCode.Actions.ActionType;
import UserCode.Conditions.*;
import UserCode.Tasks.*;
import UserInterfaces.Ui;

public class Parser {
    Ui ui;
    TaskList tasks;
    Farmer farmer;
    WheatFarm wheatFarm;
    ChickenFarm chickenFarm;
    CowFarm cowFarm;
    Market market;
    ConditionChecker conditionChecker;

    public Parser(Ui ui, Farmer farmer, ConditionChecker conditionChecker) {
        this.ui = ui;
        this.tasks = farmer.tasks;
        this.farmer = farmer;
        this.wheatFarm = farmer.wheatFarm;
        this.chickenFarm = farmer.chickenFarm;
        this.cowFarm = farmer.cowFarm;
        this.market = farmer.market;
        this.conditionChecker = conditionChecker;
    }

    public Command parse(String fullCommand) throws FarmioException {
        System.out.println("Received command " + fullCommand);
        if (fullCommand.equals("exit")) {
            return new ExitCommand();
        }
        if (fullCommand.equals("start")) {
            return new StartCommand();
        }
        if (fullCommand.equals("test")) {
            return new TestCommand(ui, tasks, wheatFarm, chickenFarm, cowFarm, market, conditionChecker);
        }
        //---- implement checking if its a edit or delete command here -------
        if (fullCommand.startsWith("do")) {
            String action = "";
            try {
                action = (fullCommand.substring(fullCommand.indexOf("{") + 1, fullCommand.indexOf("}"))).trim();
            } catch (Exception e) {
                ui.showWarning("Invalid task input format");
            }
            if (Action.validateAction(action)) {
                try {
                    Task doTask = new DoTask(new BooleanCondition(BooleanConditionType.TRUE, conditionChecker),
                            Action.stringToAction(action, wheatFarm, chickenFarm, cowFarm, market));
                    return new CreateTaskCommand(doTask, tasks);
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                throw new FarmioException("Invalid action");
            }
        }
        if (fullCommand.startsWith("if") || fullCommand.startsWith("while") || fullCommand.startsWith("for")) {
            return new CreateTaskCommand(extractTask(fullCommand), tasks);
        }
        throw new FarmioException("Invalid or Unknown Command");
    }

    private Task extractTask(String userInput) throws FarmioException {
        String strTaskType = "", strCondition = "", strAction = "";
        Task task;
        Condition condition = null;
        Action action;
        try {
            strTaskType = (userInput.substring(0, userInput.indexOf("{"))).trim();
            strCondition = (userInput.substring(userInput.indexOf("{") + 1, userInput.indexOf("}"))).trim();
            strAction = (userInput.substring(userInput.indexOf("{", userInput.indexOf("}") + 1) + 1, userInput.lastIndexOf("}"))).trim();
        } catch (Exception e) {
            ui.showWarning("Invalid task input format");
        }
        if (Condition.validateBooleanCondition(strCondition)) {
            condition = new BooleanCondition(Condition.getConditionTypeFromString(strCondition), conditionChecker);
        } else if (Condition.validateMoneyCondition(strCondition)) {
            String comp = strCondition.substring(strCondition.indexOf(" ") + 1, strCondition.lastIndexOf(" "));
            String val = strCondition.substring(strCondition.lastIndexOf(" ") + 1);
            Comparator c = Comparator.greaterThanOrEquals;
            switch (comp) {
                case ">":
                    c = Comparator.greaterThan;
                    break;
                case ">=":
                    c = Comparator.greaterThanOrEquals;
                    break;
                case "<":
                    c = Comparator.lessThan;
                    break;
                case "<=":
                    c = Comparator.lessThanOrEquals;
                    break;
            }
            condition = new MoneyCondition(c, Integer.parseInt(val), conditionChecker);
        } else {
            throw new FarmioException("Invalid Condition");
        }
        if (Action.validateAction(strAction)) {
            action = Action.stringToAction(strAction, wheatFarm, chickenFarm, cowFarm, market);
        } else {
            throw new FarmioException("Invalid Action");
        }
        if (strTaskType.equals("if")) return new IfTask(condition, action);
        if (strTaskType.equals("for")) return new ForTask(condition, action);
        if (strTaskType.equals("while")) return new WhileTask(condition, action);
        throw new FarmioException("Unable to create task from your input");
    }

}
