package command;

import degree.DegreeManager;
import storage.Storage;
import task.NUSEventList;
import ui.UI;
import task.TaskList;
import exception.DukeException;
import list.DegreeList;

/**
 * AddCommand Class extends the abstract Command class.
 * Called when items should be ADDED to tasks.
 * @author Kane Quah
 * @version 1.0
 * @since 09/19
 */
public class AddCommand extends Command {
    private String arguments;
    private String command;
    private Memento memento;
    private Memento memento1;
    private Memento memento2;
    private int listType = 0; //0 for task list, 1 for degree list, 2 for both

    /**
     * Creates AddCommand.
     * @param command command type to be used.
     * @param arguments to be added.
     */
    public AddCommand(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    /**
     * overwrites default execute to add tasks.
     * @param tasks TasksList has tasks.
     * @param ui UI prints messages.
     * @param storage Storage loads and saves files.
     * @param degreesManager is the class which holds all information of degrees
     * @throws DukeException DukeException throws exception.
     */
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) throws DukeException {
        TaskList tasksBuffer;
        DegreeList degreesBuffer;

        if (this.command.matches("event")) {
            this.listType = 0; //0 for task list

            tasksBuffer = tasks.deepClone();
            memento = new Memento(tasksBuffer);

            tasks.add(this.command, this.arguments);
            tasks.conflict_check();
        }
        else if (this.command.matches("add")) {
            this.listType = 2; //1 for degree list

            degreesBuffer = lists.deepClone();
            tasksBuffer = tasks.deepClone();
            memento1 = new Memento(degreesBuffer);
            memento2 = new Memento(tasksBuffer);


            if(this.arguments.matches("Material Science Engineering|Biomedical Engineering|Chemical Engineering|Civil Engineering|"
                    + "Computer Engineering|Electrical Engineering|Environmental Engineering|"
                    + "Industrial and Systems Engineering|Mechanical Engineering|BME|"
                    + "BioMed|Bio Eng|BM|ChE|Chem Eng|CivE|Civil E|Civil|CEG|Com E|EE|ElecE|ENVE|Mech Eng|ME|ISE|"
                    + "IE|MSE")) {
                lists.add_custom(this.arguments, storage);
                NUSEventList NUSEventList = new NUSEventList();
                NUSEventList.addDegreeTasks(this.arguments, tasks);
            }
            else {
                throw new DukeException("Wrong formatting convention is used to add degree");
            }
        }
        else {
            this.listType = 0;

            tasksBuffer = tasks.deepClone();
            memento = new Memento(tasksBuffer);

            tasks.add(this.command, this.arguments);
        }
    }

    /**
     * Overwrites default unexecute to remove the modification of taskList and degreeList.
     * Recalls the previous state of the taskList/degreeList via memento.
     * It then returns this state to the current state of the taskList/degreeList.
     *
     * @param tasks   TasksList has tasks
     * @param ui      UI.UI prints messages
     * @param storage Storage.Storage loads and saves files
     * @param lists DegreeList has the array for the user to maintain a list of their degree choices.
     * @throws DukeException Throws the wrong amount of arguments.
     */
    @Override
    public void unExecute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreeManager) throws DukeException {
        if (this.listType == 0) {
            TaskList tasksBuffer = memento.getTaskState();
            tasks.clear();

            for (int i = 0; i < tasksBuffer.size(); i++) {
                tasks.add(tasksBuffer.get(i));
            }
        } else if (this.listType == 1) {
            DegreeList degreesBuffer = memento.getDegreeState();
            lists.clear();

            for (int i = 0; i < degreesBuffer.size(); i++) {
                lists.add(degreesBuffer.get(i));
            }
        } else if (this.listType == 2) {
            DegreeList degreesBuffer = memento1.getDegreeState();
            TaskList tasksBuffer = memento2.getTaskState();
            lists.clear();
            tasks.clear();

            for (int i = 0; i < degreesBuffer.size(); i++) {
                lists.add(degreesBuffer.get(i));
            }
            for (int i = 0; i < tasksBuffer.size(); i++) {
                tasks.add(tasksBuffer.get(i));
            }
        }
    }
}
