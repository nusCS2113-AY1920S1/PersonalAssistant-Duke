package ControlPanel;

import Commands.*;

/**
 * The class which analyze the input command line and initialize a command
 * according to its type
 */
public class Parser {
    public Parser(){}

    /**
     * The constructor which runs the parser
     * @param cmd the original input string (command)
     * @return return a command object which is initialized based on its type
     * @throws DukeException if any exception is caught
     */
    public static Command parse(String cmd) throws DukeException {
        Command command = null;
        if (cmd.equals("bye")){
            command = new ExitCommand();
        }
        else if (cmd.equals("list")){
            command = new ViewCommand();
        }
        else if (cmd.contains("find")){
            if (cmd.equals("find"))
                throw new DukeException("OOPS!!! The description of a find cannot be empty.");
            String keyword = cmd.split(" ")[1];
            command = new SearchCommand(keyword);
        }
        else if(cmd.startsWith("done")) {
            String temp = cmd.replaceAll("[^0-9]", "");
            int serialNo = Integer.parseInt(temp);
            command = new DoneCommand(serialNo);
        }
        else if (cmd.contains("delete")){
            String temp = cmd.replaceAll("[^0-9]", "");
            int serialNo = Integer.parseInt(temp);
            command = new DeleteCommand(serialNo);

        }else if(cmd.contains("reminders")) {
            String keyword = cmd.split(" ")[0];
            command = new RemindersCommand(keyword);

        }else if(cmd.startsWith("schedule")){
            command  = new ViewScheduleCommand(cmd);

        }else if(cmd.startsWith("reschedule")){
            command = new RescheduleCommand(cmd);

        }else {
            String keyword = cmd.split(" ")[0];
            if (!(keyword.equals("deadline") || keyword.equals("event") || keyword.equals("todo") || keyword.equals("period") ||keyword.equals("duration"))){
                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means");
            }
            command = new AddCommand(keyword, cmd);
        }


        return command;
    }
}
