package parsers;


import commands.*;
import Exception.DukeException;

public class Parser {
    public static Command parse(String command) throws DukeException {
        if (command.contains("list")) {
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
        }else if (command.contains("done")) {
            return new DoneCommand();
        } else if (command.contains("delete")) {
            return new DeleteCommand();
        } else if (command.contains("deadline")) {
            return new DeadlineCommand();
        } else if (command.contains("/after")) {
            return new DoAfterCommand();
        } else if (command.contains("event")) {
            return new EventCommand();
        } else if (command.contains("todo")) {
            return new TodoCommand();
        }
        else if (command.contains("/between")) {
            return new TimeboundCommand();
        }
        else if (command.contains("find")) {
            return new FindCommand();
        } else if (command.contains("bye")) {
            return new ByeCommand();
        } else if (command.contains("/require")) {
            return new FixDurationCommand();
        } else if (command.contains("reschedule")) {
            return new RescheduleCommand();
        } else if (command.contains("schedule")) {
            return new ScheduleCommand();
        } else if (command.contains("snooze")) {
            return new SnoozeCommand();
        } else if (command.contains("tentative")) {
            return new TentativeEventCommand();
        } else if (command.contains("confirm")) {
            return new ConfirmTentativeCommand();
        } else if (command.contains("undone")) {
            return new UndoneCommand();
        } else if(command.contains("edit")) {
            return new EditCommand();
        } else if(command.contains("change password")) {
            return new ChangePasswordCommand();
        } else if(command.equals("undo")) {
            return new UndoCommand();
        } else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}