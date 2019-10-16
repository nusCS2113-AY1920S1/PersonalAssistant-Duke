package gazeeebo.commands;

import gazeeebo.Tasks.*;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.ArrayList;


public class CategoryListCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        ArrayList<Deadline> DeadlineList = new ArrayList<Deadline>();
        ArrayList<Event> EventList = new ArrayList<Event>();
        ArrayList<Todo> TodoList = new ArrayList<>();
        ArrayList<FixedDuration> FDList = new ArrayList<>();
        ArrayList<Timebound> TBList = new ArrayList<>();

        for (Task task : list) {
            if (task.getClass().getName().equals("gazeeebo.Tasks.Deadline")) {
                Deadline deadline = new Deadline(task.description, task.toString().split("by:")[1].trim());
                DeadlineList.add(deadline);
            } else if (task.getClass().getName().equals("gazeeebo.Tasks.Event")) {
                Event event = new Event(task.description, task.toString().split("at:")[1].trim());
                EventList.add(event);
            } else if (task.getClass().getName().equals("gazeeebo.Tasks.Todo")) {
                Todo todo = new Todo(task.description);
                TodoList.add(todo);
            }
            else if (task.getClass().getName().equals("gazeeebo.Tasks.FixedDuration")) {
                FixedDuration fixedDuration = new FixedDuration(task.description, task.toString().split("\\|")[3].trim());
                FDList.add(fixedDuration);
            }
            else if (task.getClass().getName().equals("gazeeebo.Tasks.Timebound")) {
                Timebound timebound = new Timebound(task.description, task.toString().split("\\|")[3].trim());
                TBList.add(timebound);
            }
        }

        if(ui.FullCommand.equals("deadline list")) {
            System.out.println("List of deadlines tasks:");
            for (int i = 0; i < DeadlineList.size(); i++) {
                System.out.println(i + 1 + "." + DeadlineList.get(i).listFormat());
            }
        } else if (ui.FullCommand.equals("event list")) {
            System.out.println("List of events tasks:");
            for (int i = 0; i < EventList.size(); i++) {
                System.out.println(i + 1 + "." + EventList.get(i).listFormat());
            }
        } else if (ui.FullCommand.equals("todo list")) {
            System.out.println("List of todo tasks:");
            for (int i = 0; i < TodoList.size(); i++) {
                System.out.println(i + 1 + "." + TodoList.get(i).listFormat());
            }
        } else if (ui.FullCommand.equals("fixed duration list")) {
            System.out.println("List of fixed duration tasks:");
            for (int i = 0; i < FDList.size(); i++) {
                System.out.println(i + 1 + "." + FDList.get(i).listFormat());
            }
        }
        else if (ui.FullCommand.equals("timebound list")) {
            System.out.println("List of timebounded tasks:");
            for (int i = 0; i < TBList.size(); i++) {
                System.out.println(i + 1 + "." + TBList.get(i).listFormat());
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
