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


import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    /**
     * Takes user input of date for add commands and checks for shortcut dates
     * If shortcut is found, converts to the correct date according to shortcut
     * Returns the formatted Date from user inputted date
     * @param dateStr user input of date
     * @return formatted Date based on user inputted date
     * @throws ParseException if invalid date is parsed
     */
    public static Date shortcutTime(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
        String time = dateStr.replaceAll(" ", "");

        if (time.equals("now")) {
            Date currDate = new Date();
            String passDate = simpleDateFormat.format(currDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("ytd")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date ytdDate = cal.getTime();
            String passDate = simpleDateFormat.format(ytdDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("tmr")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +1);
            Date tmrDate = cal.getTime();
            String passDate = simpleDateFormat.format(tmrDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("lstwk")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            Date lastWeekDate = cal.getTime();
            String passDate = simpleDateFormat.format(lastWeekDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("nxtwk")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +7);
            Date nextWeekDate = cal.getTime();
            String passDate = simpleDateFormat.format(nextWeekDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("lstmth")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date lastMonthDate = cal.getTime();
            String passDate = simpleDateFormat.format(lastMonthDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("nxtmth")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, +1);
            Date nextMonthDate = cal.getTime();
            String passDate = simpleDateFormat.format(nextMonthDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("lstyr")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            Date lastYearDate = cal.getTime();
            String passDate = simpleDateFormat.format(lastYearDate);
            return simpleDateFormat.parse(passDate);
        } else if (time.equals("nxtyr")) {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, +1);
            Date nextYearDate = cal.getTime();
            String passDate = simpleDateFormat.format(nextYearDate);
            return simpleDateFormat.parse(passDate);
        } else {
            return simpleDateFormat.parse(dateStr);
        }
    }
}
