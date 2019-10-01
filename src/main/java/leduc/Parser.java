package leduc;

import leduc.command.*;
import leduc.exception.NonExistentDateException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a leduc.Parser which deals with making sense of the user command.
 */
public class Parser {
    /**
     * Constructor of a leduc.Parser.
     */
    public Parser(){
    }

    /**
     * Returns the leduc.command.Command instanced according to the input string of the user.
     * @param user String which represent the input string of the user.
     * @return a leduc.command.Command which is asked by the user.
     */
    public Command parse(String user){
        Command c ;
        if (user.equals("list")) {
            c = new ListCommand(user);
        }
        else if (user.matches("find (.*)")) {
            c = new FindCommand(user);
        }
        else if (user.matches("done \\d+")) {// if it is done and a number of task
            c = new DoneCommand(user);
        }
        else if (user.matches("snooze \\d+")){ // if it is snooze and a number of task
            c = new SnoozeCommand(user);
        }
        else if (user.matches("postpone \\d+ (.*)")){ //example: postpone 1 /by 12/12/2012 22:22
            c = new PostponeCommand(user);
        }
        else if (user.trim().matches("edit")){
            c = new EditCommand(user);
        }
        else if (user.matches("delete \\d+")) {// if it is done and a number of task
            c = new DeleteCommand(user);
        }
        else if (user.matches("todo(.*)")) {
            c = new TodoCommand(user);
        }
        else if (user.matches("deadline (.*)")) {
            c = new DeadlineCommand(user);
        }
        else if (user.matches("event (.*)")) {
            c = new EventCommand(user);
        }
        else if (user.matches("reschedule \\d+ (.*)")) {
            c = new RescheduleCommand(user);
        }
        else if (user.matches("remind \\d+ (.*)")) {
            c = new RescheduleCommand(user);
        }
        else if (user.matches(("bye"))){
            c = new ByeCommand(user);
        }
        else {
            c = new MeaninglessCommand(user);
        }
        return c ;

    }

}
