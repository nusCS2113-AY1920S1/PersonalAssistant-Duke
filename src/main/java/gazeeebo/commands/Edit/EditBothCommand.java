package gazeeebo.commands.Edit;

import gazeeebo.tasks.*;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EditBothCommand {
    /**
     * This method will receive the user new description and time and edit the old description and time in the list.
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param listno_index  the index of the list
     * @throws IOException
     */
    public EditBothCommand(ArrayList<Task> list, Ui ui, int listno_index) throws IOException {
        System.out.print("Type your description & date:\n");
        ui.ReadCommand();
        String[] break_list_words = list.get(listno_index).toString().split("\\|");
        if (break_list_words[0].equals("T")) {
            Todo newtodo = new Todo(ui.FullCommand);
            System.out.print("Ok, we have edited your ToDo description.\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newtodo.listFormat() + "\n");
            list.set(listno_index, newtodo);
        } else if (break_list_words[0].equals("D")) {
            String[] break_deadline = ui.FullCommand.split("/by ");
            String description = break_deadline[0];
            String by = break_deadline[1];
            Deadline newdeadline = new Deadline(description, by);
            System.out.print("Ok, we have edited your Deadline description and time.\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newdeadline.listFormat() + "\n");
            list.set(listno_index, newdeadline);
        } else if(break_list_words[0].equals("E")) {
            String[] break_event = ui.FullCommand.split("/at ");
            String description = break_event[0];
            String at = break_event[1];
            Event newEvent = new Event(description, at);
            System.out.print("Ok, we have edited your Event description and time." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newEvent.listFormat() + "\n");
            list.set(listno_index, newEvent);
        } else if(break_list_words[0].equals("DA")) {


        } else if(break_list_words[0].equals("FD")) {
            String[] break_FD = ui.FullCommand.split(" /require ");
            FixedDuration newFD = new FixedDuration(break_FD[0],break_FD[1]);
            System.out.print("Ok, we have edited your FixDuration description and time.\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newFD.listFormat() + "\n");
            list.set(listno_index, newFD);
        }
        else if(break_list_words[0].equals("P")) {
            String[] break_P = ui.FullCommand.split(" /between ");
            Timebound newP = new Timebound(break_P[0], break_P[1]);
            System.out.print("Ok, we have edited your FixDuration description and time.\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newP.listFormat() + "\n");
            list.set(listno_index, newP);
        }
    }
}
