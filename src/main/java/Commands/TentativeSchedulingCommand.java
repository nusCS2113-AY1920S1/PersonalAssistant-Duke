package Commands;

import Interface.Duke;
import Interface.Storage;
import Interface.Ui;
import Tasks.Event;
import Tasks.Task;
import Tasks.TaskList;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TentativeSchedulingCommand extends Command{
    private TaskList tentativeDates = Duke.getTentativeDates();
    private String description;
    private ArrayList<String> dateString;
    private ArrayList<String> startTimeString;
    private ArrayList<String> endTimeString;
    public TentativeSchedulingCommand(String des,ArrayList<String> dateString, ArrayList<String> startTimeString, ArrayList<String> endTimeString) throws FileNotFoundException {
        this.description = des;
        this.dateString = dateString;
        this.startTimeString = startTimeString;
        this.endTimeString = endTimeString;
        for (int i = 0; i < dateString.size();i++){
            Event event = new Event(description, dateString.get(i),startTimeString.get(i),endTimeString.get(i));
            this.tentativeDates.addTask(event);
        }
        Storage.updateTentativeDates(tentativeDates);
    }

    @Override
    public String execute(TaskList todos, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        return ui.showTentativeSchedule(this.tentativeDates);
    }
}
