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
        else if(command.contains("event")) {
            return new EventCommand();
        }
        else if(command.contains("todo")) {
            return new TodoCommand();
        }
        else if(command.contains("/require")) {
            return new FixDurationCommand();
        }
        else if(command.contains("find")) {
            return new FindCommand();
        }
        else if(command.contains("bye")) {
            return new ByeCommand();
        }
        else if(command.contains("snooze")){
            return new SnoozeCommand();
        }
        else if(command.contains("tentative")){
            return new TentativeEventCommand();
        }
        else if(command.contains("confirm")){
            return new ConfirmTentativeCommand();
        }
        else {
            throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
//        switch (command){
//            case("list"):
//                return new ListCommand();
//
//            case("done"):
//                return new DoneCommand();
//
//            case ("delete"):
//                return new DeleteCommand();
//
//            case("deadline"):
//                return new DeadlineCommand();
//
//            case ("event"):
//                return new EventCommand();
//
//            case ("todo"):
//                return new TodoCommand();
//
//            case ("find"):
//                return new FindCommand();
//            case ("bye"):
//                return new ByeCommand();
//
//            default:
//                throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
//
//        }
    }
}
