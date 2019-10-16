package gazeeebo.commands;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.Exception.DukeException;
import java.io.IOException;
import gazeeebo.Tasks.*;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.Stack;

public class TentativeEventCommand extends Command{
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        String description = "";
        try {
            if (ui.FullCommand.length() == 9) {
                throw new DukeException("OOPS!!! The description of an tentative event cannot be empty.");
            } else {
                description = ui.FullCommand.substring(10);
                System.out.println("You are creating a tentative event: " + description);
                System.out.println("Please enter possible time slots of the event");
                System.out.println("When you are done, key in '/'.");
                ArrayList<String> tentativetimes = new ArrayList<String>();
                ui.ReadCommand();
                while (!ui.FullCommand.equals("/")) {
                    tentativetimes.add(ui.FullCommand);
                    ui.ReadCommand();
                }
                TentativeEvent newtentative = new TentativeEvent(description,tentativetimes);
                System.out.println("Got it. I've added this tentative event:");
                System.out.println(newtentative.listFormat());
                System.out.println("You could confirm one of the slots later.");
                list.add(newtentative);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {
                    sb.append(list.get(i).toString() + "\n");
                }
                storage.Storages(sb.toString());
            }
        }
        catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
    public void undo(String command, ArrayList<Task> list, Storage storage) throws IOException {
        for (Task it : list) {
            if (it.description.contains(command.substring(10)) && it.getClass().getName().equals("TentativeEvent")) {
                list.remove(it);
                break;
            }
        }
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