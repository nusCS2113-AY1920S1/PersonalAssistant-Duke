package gazeeebo.commands.tasks;

import gazeeebo.commands.Command;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ConfirmTentativeCommand extends Command {

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<ArrayList<Task>> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException {
        try {
            int index = 0;
            Event ev;
            TentativeEvent tev;
            if (ui.fullCommand.length() == 7) {
                throw new DukeException("OOPS!!! The index of tentative event cannot be empty.");
                } else {
                    index = Integer.parseInt(ui.fullCommand.substring(7).trim()) - 1;
                    if(list.get(index).listFormat().getBytes()[1] != 'T' && list.get(index).listFormat().getBytes()[2] != 'E'){
                        throw new DukeException("OOPS!!! You can only confirm tentative event task.");
                    }else {
                        String tempstring = list.get(index).listFormat();
                        System.out.println("You are confirming this tentative event: " + list.get(index).description);
                        System.out.println(tempstring);
                        System.out.println("Please indicate which time slot you want to confirm");
                        ui.readCommand();
                        int WhichTimeSlot = Integer.parseInt(ui.fullCommand);
                        String[] timeslots = list.get(index).toString().split("\\|");
                        System.out.println(timeslots[WhichTimeSlot+2]);
                        ev = new Event(list.get(index).description, timeslots[WhichTimeSlot+2]);
                        tev = (TentativeEvent)list.get(index);
                        System.out.println("Are you sure you want to confirm this time slot: ");
                        System.out.println(ev.listFormat());
                        ui.readCommand();
                        if (ui.fullCommand.equals("yes")) {
                            list.add(ev);
                            list.remove(index);
                            System.out.println("Confirmed.");
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("gazeeebo.Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("gazeeebo.Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.writeToSaveFile(sb.toString());
            }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}