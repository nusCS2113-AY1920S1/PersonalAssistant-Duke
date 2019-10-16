package executor.command;

public enum CommandType {
    TASK, BYE, LIST, BLANK, FIND, DELETE, DONE, QUEUE, VIEWSCHEDULE, REMINDER, BALANCE, HELP;

    /**
     * Constructor for 'CommandType' enum.
     */
    private CommandType() {
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
