package commands;
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import Tasks.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ConfirmTentativeCommand extends Command{
    protected Task ev;
    protected Task Removed;
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if (ui.FullCommand.length() == 7) {
                throw new DukeException("OOPS!!! The index of tentative event cannot be empty.");

                } else {
                    int index = Integer.parseInt(ui.FullCommand.substring(7).trim()) - 1;
                    Removed = list.get(index);
                    if(list.get(index).listFormat().getBytes()[1] !='T' && list.get(index).listFormat().getBytes()[2] != 'E'){
                        throw new DukeException("OOPS!!! You can only confirm tentative event task.");
                    }else {
                        String tempstring = list.get(index).listFormat();
                        System.out.println("You are confirming this tentative event: " + list.get(index).description);
                        System.out.println(tempstring);
                        System.out.println("Please indicate which time slot you want to confirm");
                        ui.ReadCommand();
                        int WhichTimeSlot = Integer.parseInt(ui.FullCommand);
                        String[] timeslots = list.get(index).toString().split("\\|");
                        System.out.println(timeslots[WhichTimeSlot+2]);
                        ev = new Event(list.get(index).description, timeslots[WhichTimeSlot+2]);
                        System.out.println("Are you sure you want to confirm this time slot: ");
                        System.out.println(ev.listFormat());
                        ui.ReadCommand();
                        if (ui.FullCommand.equals("yes")) {
                            list.add(ev);
                            list.remove(index);
                            System.out.println("Confirmed.");
                        }
                    }
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");

                    }
                }
                storage.Storages(sb.toString());

            }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void undo(String command, ArrayList<Task> list,Storage storage) throws IOException {
        for (Task it : list) {
            if (it == ev) {
                list.remove(it);
                break;
            }
        }
        list.add(Removed);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.Storages(sb.toString());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}