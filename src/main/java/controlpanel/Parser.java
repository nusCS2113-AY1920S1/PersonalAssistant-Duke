package controlpanel;

import MoneyCommands.*;
import commands.AddCommand;
import commands.ChooseEventTime;
import commands.Command;
import commands.DeleteCommand;
import commands.DoneCommand;
import commands.ExitCommand;
import commands.FreeTimeCommand;
import commands.RemindersCommand;
import commands.RescheduleCommand;
import commands.SearchCommand;
import commands.ViewCommand;
import commands.ViewScheduleCommand;



import java.text.ParseException;

/**
 * The class which analyze the input command line and initialize a command.
 * according to its type.
 */
public class Parser {
    public Parser() throws DukeException, ParseException {
    }

    /**
     * The constructor which runs the parser.
     * @param cmd the original input string (command).
     * @return return a command object which is initialized based on its type.
     * @throws DukeException if any exception is caught.
     */
    public static Command parse(String cmd) throws DukeException, ParseException {
        Command command;
        if (cmd.equals("bye")) {
            command = new ExitCommand();
        } else if (cmd.equals("list")) {
            command = new ViewCommand();
        } else if (cmd.startsWith("find")) {
            if (cmd.equals("find")) {
                throw new DukeException("OOPS!!! The description of a find cannot be empty.");
            }
            String keyword = cmd.split(" ")[1];
            command = new SearchCommand(keyword);
        } else if (cmd.startsWith("done")) {
            String temp = cmd.replaceAll("[^0-9]", "");
            int serialNo = Integer.parseInt(temp);
            command = new DoneCommand(serialNo);
        } else if (cmd.startsWith("delete")) {
            String temp = cmd.replaceAll("[^0-9]", "");
            int serialNo = Integer.parseInt(temp);
            command = new DeleteCommand(serialNo);
        } else if (cmd.startsWith("reminders")) {
            String keyword = cmd.split(" ")[1];
            command = new RemindersCommand(keyword);
        } else if (cmd.startsWith("schedule")) {
            command  = new ViewScheduleCommand(cmd);
        } else if (cmd.startsWith("reschedule")) {
            command = new RescheduleCommand(cmd);
        } else if (cmd.contains("choose")) {
            command = new ChooseEventTime(cmd);
        } else if (cmd.startsWith("free-time")) {
            String[] words = cmd.split(" ");
            int duration = Integer.parseInt(words[1]);
            command = new FreeTimeCommand(words[4] + " " + words[5], duration);
        } else {
            String keyword = cmd.split(" ")[0];
            if (!(keyword.equals("deadline") || keyword.equals("event")
                    || keyword.equals("todo") || keyword.equals("period")
                    || keyword.equals("duration") || keyword.equals("multiEvent"))) {
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means");
            }
            command = new AddCommand(keyword, cmd);
        }
        return command;
    }

    public static MoneyCommand moneyParse(String cmd, boolean isNewUser) throws DukeException, ParseException {
        MoneyCommand moneyCommand = null;

        if(cmd.equals("start")){
            moneyCommand = new startCommand(isNewUser);
        }
        else if (cmd.startsWith("init")) {
            moneyCommand = new initCommand(cmd, isNewUser);
        } else if(cmd.equals("bye")){
            moneyCommand = new ExitMoneyCommand();
        } else if(isNewUser){
            throw new DukeException("You are a new user please type: init [existing savings] [Avg Monthly Expenditure]");
        }
        else if (cmd.startsWith("goal-short")) {
            moneyCommand = new AddShortGoalCommand(cmd);
        } else if (cmd.equals("list goals")) {
            moneyCommand = new ListGoalsCommand();
        } else if (cmd.startsWith("delete goal")) {
            String temp = cmd.replaceAll("[^0-9]", "");
            int serialNo = Integer.parseInt(temp);
            moneyCommand = new DeleteGoalCommand(serialNo);
        } else if (cmd.startsWith("add income")) {
            moneyCommand = new AddIncomeCommand(cmd);
        } else if (cmd.startsWith("spent")) {
            moneyCommand = new AddExpenditureCommand(cmd);
        } else if (cmd.equals("list all income")) {
            moneyCommand = new ListTotalIncomeCommand();
        } else if (cmd.equals("list all expenditure")) {
            moneyCommand = new ListTotalExpenditureCommand();
        } else if (cmd.startsWith("delete income")) {
            moneyCommand = new DeleteIncomeCommand(cmd);
        } else if (cmd.startsWith("delete expenditure")) {
            moneyCommand = new DeleteExpenditureCommand(cmd);

        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means");
        }



        return moneyCommand;
    }
}
