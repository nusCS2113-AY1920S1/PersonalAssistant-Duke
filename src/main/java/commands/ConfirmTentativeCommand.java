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

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
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
                    if (list.get(i).getClass().getName().equals("Tasks.Deadline")) {
                        sb.append(list.get(i).toString() + "\n");
                    } else if (list.get(i).getClass().getName().equals("Tasks.Event")) {
                        sb.append(list.get(i).toString() + "\n");
                    }
                }
                storage.storages(sb.toString());
                commandStack.push("confirm"+"~"+ev.toString()+"~"+tev.toString());
            }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void undo(String command, ArrayList<Task> list,Storage storage) throws IOException {
        String[] splitCommand = command.split("~");
        String[] details = splitCommand[1].split("\\|");
        Event e = new Event(details[2].trim(), details[3].substring(3).trim());
        if (details[1].equals("D")) {
            e.isDone = true;
        } else {
            e.isDone = false;
        }
        for (Task it : list) {
            if (it.toString().equals(e.toString())) {
                list.remove(it);
                break;
            }
        }
        ArrayList<String> timeslots = new ArrayList<String>();
        String[] TEdetails = splitCommand[2].split("\\|");
        for(int i=3;i<TEdetails.length;i++){
            timeslots.add(TEdetails[i]);
        }
        TentativeEvent TE = new TentativeEvent(TEdetails[2].trim(),timeslots);
        if (details[1].equals("D")) {
            TE.isDone = true;
        } else {
            TE.isDone = false;
        }
        list.add(TE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).toString() + "\n");
        }
        storage.storages(sb.toString());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}