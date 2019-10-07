package parsers;


import commands.*;
import Exception.DukeException;

public class Parser {
    public static Command parse(String command) throws DukeException {
        String[] splitcommand = command.split(" ");
        if (splitcommand[0].equals("list")) {
            if (command.contains("event")) {
                return new CategoryListCommand();
            } else if (command.contains("deadline")) {
                return new CategoryListCommand();
            } else if (command.contains("todo")) {
                return new CategoryListCommand();
            } else if (command.contains("fixed")) {
                return new CategoryListCommand();
            } else if (command.contains("timebound")) {
                return new CategoryListCommand();
            }
//            else if (command.contains("done")) {
//                return new DoneListCommand();
//            }
            else {
                return new ListCommand();
            }
        } else if (splitcommand[0].equals("done")) {
            return new DoneCommand();
        } else if (splitcommand[0].equals("delete")) {
            return new DeleteCommand();
        } else if (splitcommand[0].equals("deadline")) {
            return new DeadlineCommand();
        } else if (command.contains("/after")) {
            return new DoAfterCommand();
        } else if (splitcommand[0].equals("event")) {
            return new EventCommand();
        } else if (splitcommand[0].equals("todo")) {
            return new TodoCommand();
        } else if (command.contains("/between")) {
            return new TimeboundCommand();
        } else if (splitcommand[0].equals("find")) {
            return new FindCommand();
        } else if (splitcommand[0].equals("bye")) {
            return new ByeCommand();
        } else if (command.contains("/require")) {
            return new FixDurationCommand();
        } else if (splitcommand[0].equals("reschedule")) {
            return new RescheduleCommand();
        } else if (splitcommand[0].equals("schedule")) {
            return new ScheduleCommand();
        } else if (splitcommand[0].equals("snooze")) {
            return new SnoozeCommand();
        } else if (splitcommand[0].equals("tentative")) {
            return new TentativeEventCommand();
        } else if (splitcommand[0].equals("confirm")) {
            return new ConfirmTentativeCommand();
        } else if (splitcommand[0].equals("undo")) {
            return new UndoCommand();
        } else if (splitcommand[0].equals("edit")) {
            return new EditCommand();
        } else if (splitcommand[0].equals("change password")) {
            return new ChangePasswordCommand();
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}