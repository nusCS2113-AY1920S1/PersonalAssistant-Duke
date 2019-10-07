package controlpanel;

import moneycommands.*;
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

import moneycommands.AddExpenditureCommand;
import moneycommands.AddIncomeCommand;
import moneycommands.AddShortGoalCommand;
import moneycommands.CreateBankAccountCommand;
import moneycommands.DeleteExpenditureCommand;
import moneycommands.DeleteGoalCommand;
import moneycommands.DeleteIncomeCommand;
import moneycommands.ExitMoneyCommand;
import moneycommands.initCommand;
import moneycommands.ListBankTrackerCommand;
import moneycommands.ListGoalsCommand;
import moneycommands.ListTotalExpenditureCommand;
import moneycommands.ListTotalIncomeCommand;
import moneycommands.startCommand;
import moneycommands.MoneyCommand;
import moneycommands.ViewPastMonthIncome;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class which takes in the user input from command line and identifies the
 * correct command type. Calls the appropriate MoneyCommand from control panel
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

    /**
     * Constructor which runs the parser to parse all commands
     * @param cmd Original input command from the user
     * @param isNewUser Boolean to identify if the user if a new or returning user
     * @return MoneyCommand to be called according to the user commands
     * @throws DukeException If the user input is invalid
     * @throws ParseException If invalid date is parsed
     */
    public static MoneyCommand moneyParse(String cmd, boolean isNewUser) throws DukeException, ParseException {
        MoneyCommand moneyCommand = null;

        if (cmd.equals("start")) {
            moneyCommand = new startCommand(isNewUser);
        } else if (cmd.startsWith("init")) {
            moneyCommand = new initCommand(cmd, isNewUser);
        } else if(cmd.equals("bye")) {
            moneyCommand = new ExitMoneyCommand();
        } else if(isNewUser) {
            throw new DukeException("You are a new user please type: init [existing savings] [Avg Monthly Expenditure]");
        } else if (cmd.startsWith("bank-account")) {
            moneyCommand = new CreateBankAccountCommand(cmd);
        } else if (cmd.equals("list bank trackers")) {
            moneyCommand = new ListBankTrackerCommand();
        } else if (cmd.startsWith("check-balance ")) {
            moneyCommand = new CheckFutureBalanceCommand(cmd);
        } else if (cmd.startsWith("withdraw ") || cmd.startsWith("deposit")) {
            moneyCommand = new InternalTransferCommand(cmd);
        } else if (cmd.startsWith("goal-short")) {
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
        } else if (cmd.startsWith("done goal")) {
            moneyCommand = new DoneGoalCommand(cmd);
        } else if (cmd.startsWith("graph")) {
            moneyCommand = new GraphCommand();
        } else if (cmd.equals("list month income")) {
            moneyCommand = new ListMonthIncomeCommand();
        } else if (cmd.equals("list month expenditure")) {
            moneyCommand = new ListMonthExpenditureCommand();
        } else if (cmd.startsWith("check income")) {
            moneyCommand = new ViewPastMonthIncome(cmd);
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means");
        }
        return moneyCommand;
    }

    /**
     * Takes user input of date for add commands and checks for shortcut dates.
     * If shortcut is found, converts to the correct date according to shortcut.
     * Returns the formatted Date from user inputted date.
     * @param dateStr user input of date
     * @return formatted Date based on user inputted date
     * @throws ParseException if invalid date is parsed
     */
    public static LocalDate shortcutTime(String dateStr) throws ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String time = dateStr.replaceAll(" ", "");

        switch (time) {
        case "now": {
            LocalDate currDate = LocalDate.now();
            String passDate = dateTimeFormatter.format(currDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "ytd": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -1);
            Date ytdDate = cal.getTime();
            LocalDate ytdLocalDate = ytdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(ytdLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "tmr": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +1);
            Date tmrDate = cal.getTime();
            LocalDate tmrLocalDate = tmrDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(tmrLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "lstwk": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            Date lastWeekDate = cal.getTime();
            LocalDate lastWeekLocalDate = lastWeekDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(lastWeekLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "nxtwk": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, +7);
            Date nextWeekDate = cal.getTime();
            LocalDate nextWeekLocalDate = nextWeekDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(nextWeekLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "lstmth": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Date lastMonthDate = cal.getTime();
            LocalDate lastMonthLocalDate = lastMonthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(lastMonthLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "nxtmth": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, +1);
            Date nextMonthDate = cal.getTime();
            LocalDate nextMonthLocalDate = nextMonthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(nextMonthLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "lstyr": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -1);
            Date lastYearDate = cal.getTime();
            LocalDate lastYearLocalDate = lastYearDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(lastYearLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        case "nxtyr": {
            final Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, +1);
            Date nextYearDate = cal.getTime();
            LocalDate nextYearLocalDate = nextYearDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String passDate = dateTimeFormatter.format(nextYearLocalDate);
            return LocalDate.parse(passDate, dateTimeFormatter);
        }
        default:
            return LocalDate.parse(dateStr, dateTimeFormatter);
        }
    }
}
