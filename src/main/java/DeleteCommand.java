import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class DeleteCommand extends Command {

    @Override
    public void execute(List<Task> list, Ui ui, Storage storage) throws DukeException, ParseException, IOException {
        if(ui.FullCommand.length() == 6) {
            throw new DukeException("OOPS!!! The description of a deletion cannot be empty.");
        }
        else {
            int index = Integer.parseInt(ui.FullCommand.substring(6).trim()) - 1;
            String taskremoved = list.get(index).listformat();
            list.remove(index);
            System.out.println("Noted. I've removed this task: ");
            System.out.println(taskremoved);
            System.out.println("Now you have " + list.size() + " tasks in the list.");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClass().getName().equals("Deadline")) {
                    sb.append(list.get(i).toString()+"\n");
                }
                else if(list.get(i).getClass().getName().equals("Event")){
                    sb.append(list.get(i).toString()+"\n");
                }
                else{
                    sb.append(list.get(i).toString()+"\n");
                }
            }
            storage.Storages(sb.toString());
        }

    }

    @Override
    public boolean isExit() {
        return false;
    }
}
