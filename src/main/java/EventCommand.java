import java.io.IOException;

/**
 * Represents a event task Command.
 * Allow to add a event task to the task list and to the data file.
 */
public class EventCommand extends Command {
    /**
     * Constructor of EventCommand.
     * @param user String which represent the input string of the user.
     */
    public  EventCommand(String user){
        super(user);
    }

    /**
     * Allow to add a event task to the task list and to the data file.
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     * @throws EmptyEventDateException Exception caught when the period of the event task is not given by the user.
     * @throws EmptyEventException Exception caught when the description of the event task is not given by the user.
     * @throws DateEventFormatException Exception caught when the format of the period of the event task is not correct.
     * @throws InexistentDateException Exception caught when one of the two date given does not exist.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser)
            throws EmptyEventDateException , EmptyEventException , DateEventFormatException, InexistentDateException{
        String[] taskDescription = user.substring(5).split("/at");
        if (taskDescription[0].isBlank()) {
            throw new EmptyEventException(ui);
        }
        else if (taskDescription.length == 1) { // no /by in input
            throw new EmptyEventDateException(ui);
        }
        else {
            String description = taskDescription[0].trim();
            String periodString = taskDescription[1].trim();
            //date format used: dd/MM/yyyy HH:mm - dd/MM/yyyy HH:mm
            String regex ="[0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9] " +
                    "- [0-9][0-9]/[0-9][0-9]/[0-9][0-9][0-9][0-9] [0-9][0-9]:[0-9][0-9]";
            if (!periodString.matches(regex)) {
                throw new DateEventFormatException(ui);
            }
            else {
                String[] dateString = periodString.split(" - ");
                Date dateFirst = parser.stringToDate(dateString[0],ui);
                Date dateSecond = parser.stringToDate(dateString[1],ui);
                tasks.add(new EventsTask(description, dateFirst,dateSecond));
                EventsTask newTask = (EventsTask) tasks.get(tasks.size() - 1);
                try {
                    storage.getAppendWrite().write(tasks.size() + "//" + newTask.getTag() + "//" +
                            newTask.getMark() + "//" + newTask.getTask() + "//"+
                            " at:" + newTask.getDateFirst() + "//" + newTask.getDateSecond()+"\n");
                }
                catch (IOException e){
                    ui.display("\t IOException:\n\t\t error when writing a event to file");
                }
                ui.display("\t Got it. I've added this task:\n\t   "
                        + newTask.getTag() + newTask.getMark() + newTask.getTask() + " at:"
                        + newTask.getDateFirst() + " - " + newTask.getDateSecond() +
                        "\n\t Now you have " + tasks.size() + " tasks in the list.");
            }
        }
    }


    /**
     * Returns a boolean false as it is a event command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
