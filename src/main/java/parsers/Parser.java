package parsers;
import commands.*;
import Exception.DukeException;

public class Parser {
    public static Command parse(String command) throws DukeException {
        if(command.contains("list")) {
            return new ListCommand();
        }
        else if(command.contains("done")) {
            return new DoneCommand();
        }
        else if(command.contains("delete")) {
            return new DeleteCommand();
        }
        else if(command.contains("deadline")) {
            return new DeadlineCommand();
        }

        else if(command.contains("/after")) {
            return new DoAfterCommand();
        }

        else if(command.contains("event")) {
            return new EventCommand();
        }
        else if(command.contains("todo")) {
            return new TodoCommand();
        }
        else if(command.contains("bye")) {
            return new ByeCommand();
        }
        else if(command.contains("/require")) {
            return new FixDurationCommand();
        }
        else if(command.contains("find")) {
            return new FindCommand();
        }
        else if(command.contains("schedule")) {
            return new ScheduleCommand();
        }
        else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
