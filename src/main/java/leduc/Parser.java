package leduc;

import leduc.command.*;
import leduc.exception.NonExistentDateException;
import leduc.task.DeadlinesTask;

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
        if (user.equals(ListCommand.getListShortcut())) {
            c = new ListCommand(user);
        }
        else if (user.matches(FindCommand.getFindShortcut()+" (.*)") | user.matches(FindCommand.getFindShortcut()) | user.matches("find (.*)") | user.matches("find")) {
            c = new FindCommand(user);
        }
        else if (user.matches(DoneCommand.getDoneShortcut() + " \\d+") | user.matches("done \\d+")) {// if it is done and a number of task
            c = new DoneCommand(user);
        }
        else if (user.matches(SnoozeCommand.getSnoozeShortcut() + " \\d+") | user.matches("snooze \\d+")){ // if it is snooze and a number of task
            c = new SnoozeCommand(user);
        }
        else if (user.matches(PostponeCommand.getPostponeShortcut() + " \\d+ (.*)") | user.matches("postpone \\d+ (.*)")){ //example: postpone 1 /by 12/12/2012 22:22
            c = new PostponeCommand(user);
        }
        else if (user.matches(PrioritizeCommand.getPrioritizeShortcut() + " \\d+ (.*)") | user.matches("prioritize \\d+ (.*)")){ // prioritize 1 prio 2
            c = new PrioritizeCommand(user);
        }
        else if (user.trim().matches(EditCommand.getEditShortcut())){
            c = new EditCommand(user);
        }
        else if (user.matches(DeleteCommand.getDeleteShortcut() + " \\d+") | user.matches("delete \\d+")) {// if it is done and a number of task
            c = new DeleteCommand(user);
        }
        else if (user.matches(SortCommand.getSortShortcut() + " (.*)") | user.matches(SortCommand.getSortShortcut()) | user.matches("sort (.*)") | user.matches("sort")){
            c = new SortCommand(user);
        }
        else if (user.matches(TodoCommand.getTodoShortcut() + " (.*)") | user.matches(TodoCommand.getTodoShortcut()) | user.matches("todo (.*)") | user.matches("todo")) {
            c = new TodoCommand(user);
        }
        else if (user.matches(DeadlineCommand.getDeadlineShortcut() + "(.*)") | user.matches(DeadlineCommand.getDeadlineShortcut()) | user.matches(" deadline (.*)") | user.matches(" deadline")) {
            c = new DeadlineCommand(user);
        }
        else if (user.matches(EventCommand.getEventShortcut() + " (.*)") | user.matches(EventCommand.getEventShortcut()) | user.matches("event (.*)") | user.matches("event")) {
            c = new EventCommand(user);
        }
        else if (user.matches(RescheduleCommand.getRescheduleShortcut() + " \\d+ (.*)") | user.matches(RescheduleCommand.getRescheduleShortcut()) | user.matches("reschedule \\d+ (.*)") | user.matches("reschedule")) {
            c = new RescheduleCommand(user);
        }

        else if (user.matches(SetWelcomeCommand.getSetWelcomeShortcut() + " (.*)") | user.matches(SetWelcomeCommand.getSetWelcomeShortcut()) | user.matches("setwelcome (.*)") | user.matches("setwelcome")) {
            c = new SetWelcomeCommand(user);
        }
        else if (user.matches(RemindCommand.getRemindShortcut())) {
            c = new RemindCommand(user);
        }
        else if(user.matches(HelpCommand.getHelpShortcut())){
            c = new HelpCommand(user);
        }
        else if (user.matches(ByeCommand.getByeShortcut())){
            c = new ByeCommand(user);
        }
        else if(user.matches("shortcut") | user.matches("shortcut (.*)")){
            c = new ShortcutCommand(user);
        }
        else {
            c = new MeaninglessCommand(user);
        }
        return c ;

    }

}
