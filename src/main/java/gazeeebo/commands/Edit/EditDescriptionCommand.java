package gazeeebo.commands.Edit;

import gazeeebo.tasks.*;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class EditDescriptionCommand {
    /**
     * This method will receive the user new description and edit the old description in the list.
     * @param list task lists
     * @param ui the object that deals with printing things to the user.
     * @param listno_index the index of the list
     * @throws IOException
     */
    public EditDescriptionCommand(ArrayList<Task> list, Ui ui, int listno_index) throws IOException {
        System.out.print("Type your description:\n");
        ui.ReadCommand();
        String newDescription = ui.FullCommand;

        String[] break_list_words = list.get(listno_index).toString().split("\\|");

        if (break_list_words[0].equals("T")) {
            Todo newtodo = new Todo(newDescription);
            System.out.print("Ok, we have edited your ToDo description." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newtodo.listFormat() + "\n");
            list.set(listno_index, newtodo);
        } else if (break_list_words[0].equals("D")) {
            String[] by = break_list_words[3].split("by: ");
            Deadline newdeadline = new Deadline(newDescription, by[1]);
            System.out.print("Ok, we have edited your Deadline description." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newdeadline.listFormat() + "\n");
            list.set(listno_index, newdeadline);
        } else if(break_list_words[0].equals("E")){
            String[] at = break_list_words[3].split("at: ");
            Event newEvent = new Event(newDescription, at[1]);
            System.out.print("Ok, we have edited your Event description." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newEvent.listFormat() + "\n");
            list.set(listno_index, newEvent);
        }

        else if(break_list_words[0].equals("DA")){
            String[] splitter = newDescription.split(" /after ");
            DoAfter newDA = new DoAfter(splitter[1],splitter[1],splitter[0]);
            System.out.print("Ok, we have edited your DoAfter description." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newDA.listFormat() + "\n");
            list.set(listno_index, newDA);
        }
        else if(break_list_words[0].equals("FD")) {
            String by = break_list_words[3];
            FixedDuration newFA = new FixedDuration(newDescription,by);
            System.out.print("Ok, we have edited your FixedDuration description." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newFA.listFormat() + "\n");
            list.set(listno_index, newFA);
        }
        else if(break_list_words[0].equals("P")) {
            Timebound newP = new Timebound(newDescription,break_list_words[3]);
            System.out.print("Ok, we have edited your Timebound description." +
                    "\n\tFrom: " + list.get(listno_index).listFormat() +
                    "\n\tTo:   " + newP.listFormat() + "\n");
            list.set(listno_index, newP);
        }
    }
}

