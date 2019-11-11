package executor.command;

public enum CommandType {
    TASK(CommandNewTask.class),
    BYE(CommandBye.class),
    LIST(CommandList.class),
    BLANK(CommandBlank.class),
    FIND(CommandFind.class),
    DELETE(CommandDelete.class),
    DELETERECEIPT(CommandDeleteReceipt.class),
    DONE(CommandMarkDone.class),
    MAJOREXPENSE(CommandMajorExpense.class),
    QUEUE(CommandQueue.class),
    STATS(CommandStatistics.class),
    VIEWSCHEDULE(CommandSchedule.class),
    REMINDER(CommandReminder.class),
    BALANCE(CommandDisplayBalance.class),
    IN(CommandAddIncomeReceipt.class),
    OUT(CommandAddSpendingReceipt.class),
    BUDGET(CommandBudget.class),
    EXPORT(CommandExport.class),
    EXPENSES(CommandDisplayExpenditure.class),
    HELP(CommandHelp.class),
    DEADLINE(CommandNewTask.class),
    EVENT(CommandNewTask.class),
    TODO(CommandNewTask.class),
    RECUR(CommandNewTask.class),
    FDURATION(CommandNewTask.class),
    EXPENDEDDAY(CommandGetSpendingByDay.class),
    EXPENDEDWEEK(CommandGetSpendingByWeek.class),
    EXPENDEDMONTH(CommandGetSpendingByMonth.class),
    EXPENDEDYEAR(CommandGetSpendingByYear.class),
    EDIT(CommandEdit.class),
    CONVERT(CommandConvert.class),
    DATELIST(CommandDateList.class),
    ERROR(CommandError.class),
    WEATHER(CommandWeather.class),
    HOME(CommandHomeDisplay.class),
    CLI(CommandCliDisplay.class),
    ADD(CommandAdd.class),
    SUB(CommandSub.class),
    DIV(CommandDiv.class),
    MUL(CommandMul.class),
    TRACK(CommandTrackTag.class),
    UNTRACK(CommandUntrackTag.class),
    CLEAR(CommandClearCli.class),
    ENABLETEST(CommandEnableTesting.class);

    private final Class commandClass;

    /**
     * Constructor for 'CommandType' enum.
     */
    private CommandType(Class commandClass) {
        this.commandClass = commandClass;
    }

    public Class getCommandClass() {
        return this.commandClass;
    }

    /**
     * Method to get all the types of this enum.
     *
     * @return A String Array that contains all the types of this enum
     */
    public static String[] getNames() {
        CommandType[] holder = CommandType.values();
        String[] returnArray = new String[holder.length];
        for (int index = 0; index < holder.length; ++index) {
            returnArray[index] = String.valueOf(holder[index]);
        }
        return returnArray;
    }
}
