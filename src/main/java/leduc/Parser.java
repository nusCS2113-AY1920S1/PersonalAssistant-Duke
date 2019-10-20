package leduc;

import leduc.command.*;

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
        else if (user.matches("find (.*)") | user.matches("find")){
            c = new FindCommand(user);
        }
        else if (user.matches(FindCommand.getFindShortcut()+" (.*)") | user.matches(FindCommand.getFindShortcut())) {
            c = new FindCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("done \\d+")) {// if it is done and a number of task
            c = new DoneCommand(user);
        }
        else if (user.matches(DoneCommand.getDoneShortcut() + " \\d+")) {// if it is done and a number of task
            c = new DoneCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("snooze \\d+")){ // if it is snooze and a number of task
            c = new SnoozeCommand(user);
        }
        else if (user.matches(SnoozeCommand.getSnoozeShortcut() + " \\d+")){ // if it is snooze and a number of task
            c = new SnoozeCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("postpone \\d+ (.*)")){ //example: postpone 1 /by 12/12/2012 22:22
            c = new PostponeCommand(user);
        }
        else if (user.matches(PostponeCommand.getPostponeShortcut() + " \\d+ (.*)")){ //example: postpone 1 /by 12/12/2012 22:22
            c = new PostponeCommand(user);
            c.calledByShortcut();
        }
        else if (user.trim().matches(EditCommand.getEditShortcut())){
            c = new EditCommand(user);
        }
        else if (user.matches("delete \\d+")) {// if it is done and a number of task
            c = new DeleteCommand(user);
        }
        else if (user.matches(DeleteCommand.getDeleteShortcut() + " \\d+")) {// if it is done and a number of task
            c = new DeleteCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("sort (.*)") | user.matches("sort")){
            c = new SortCommand(user);
        }
        else if (user.matches(SortCommand.getSortShortcut() + " (.*)") | user.matches(SortCommand.getSortShortcut())){
            c = new SortCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("todo (.*)") | user.matches("todo")) {
            c = new TodoCommand(user);
        }
        else if (user.matches(TodoCommand.getTodoShortcut() + " (.*)") | user.matches(TodoCommand.getTodoShortcut())) {
            c = new TodoCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches(" deadline (.*)") | user.matches(" deadline")) {
            c = new DeadlineCommand(user);
        }
        else if (user.matches(DeadlineCommand.getDeadlineShortcut() + "(.*)") | user.matches(DeadlineCommand.getDeadlineShortcut())) {
            c = new DeadlineCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("event (.*)") | user.matches("event")) {
            c = new EventCommand(user);
        }
        else if (user.matches(EventCommand.getEventShortcut() + " (.*)") | user.matches(EventCommand.getEventShortcut())) {
            c = new EventCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("reschedule \\d+ (.*)") | user.matches("reschedule")) {
            c = new RescheduleCommand(user);
        }
        else if (user.matches(RescheduleCommand.getRescheduleShortcut() + " \\d+ (.*)") | user.matches(RescheduleCommand.getRescheduleShortcut())) {
            c = new RescheduleCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("prioritize \\d+ (.*)") | user.matches("prioritize")){
            c = new PrioritizeCommand(user);
        }
        else if (user.matches(PrioritizeCommand.getPrioritizeShortcut() + " \\d+ (.*)") | user.matches(PrioritizeCommand.getPrioritizeShortcut())) { // prioritize 1 prio 2
            c = new PrioritizeCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("setwelcome (.*)") | user.matches("setwelcome")) {
            c = new SetWelcomeCommand(user);
        }
        else if (user.matches(SetWelcomeCommand.getSetWelcomeShortcut() + " (.*)") | user.matches(SetWelcomeCommand.getSetWelcomeShortcut())) {
            c = new SetWelcomeCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches("show (.*)") | user.matches("show")) {
            c = new ShowCommand(user);
        }
        else if (user.matches( ShowCommand.getShowShortcut() + " (.*)") | user.matches(ShowCommand.getShowShortcut())) {
            c = new ShowCommand(user);
            c.calledByShortcut();
        }
        else if (user.matches(RemindCommand.getRemindShortcut()) || user.matches("remind")) {
            c = new RemindCommand(user);
        }
        else if(user.matches(HelpCommand.getHelpShortcut()) || user.matches("help")){
            c = new HelpCommand(user);
        }
        else if (user.matches(ByeCommand.getByeShortcut()) || user.matches("bye")){
            c = new ByeCommand(user);
        }

        else if (user.matches(StatsCommand.getStatsShortcut()) || user.matches("stats")){
            c = new StatsCommand(user);
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
