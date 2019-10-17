package gazeeebo.commands.Edit;

import gazeeebo.tasks.*;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EditTimeCommand {
    /**
     * This method will receive the user new time and edit the old time in the list.
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param listno_index the index of the list
     * @throws IOException
     */
    public EditTimeCommand(ArrayList<Task> list, Ui ui, int listno_index) throws IOException {
        System.out.print("Type your time:\n");
        ui.ReadCommand();
        String newTime = ui.FullCommand;

        String[] break_list_words = list.get(listno_index).toString().split("\\|");
        if (break_list_words[0].equals("D")) {
            Deadline newdeadline = new Deadline(break_list_words[2], newTime);
            System.out.print("Ok, we have edited your Deadline time." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newdeadline.listFormat() + "\n");
            list.set(listno_index, newdeadline);
        } else if(break_list_words[0].equals("E")) {
            Event newEvent = new Event(break_list_words[2], newTime);
            System.out.print("Ok, we have edited your Event time." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newEvent.listFormat() + "\n");
            list.set(listno_index, newEvent);
        }
        else if(break_list_words[0].equals("DA")) {
            System.out.println("DoAfter only has description.");
        }
        else if(break_list_words[0].equals("FD")) {
            FixedDuration newFD = new FixedDuration(break_list_words[2],newTime);
            System.out.print("Ok, we have edited your FixDuration time." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newFD.listFormat() + "\n");
            list.set(listno_index, newFD);
        }
        else if(break_list_words[0].equals("P")) {
            Timebound newP = new Timebound(break_list_words[2], newTime);
            System.out.print("Ok, we have edited your Timebound time." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newP.listFormat() + "\n");
            list.set(listno_index, newP);
        }
    }
}
