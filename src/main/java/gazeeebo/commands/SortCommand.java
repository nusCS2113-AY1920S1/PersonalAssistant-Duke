package gazeeebo.commands;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import java.io.IOException;
import gazeeebo.exception.DukeException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

public class SortCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        try {
            if(ui.FullCommand.length() == 4) {
                throw new DukeException("OOPS!!! Please enter method of sorting.");
            }else{
                ArrayList<Task> SortedList = new ArrayList<Task>(list);
                if(ui.FullCommand.split(" ")[1].equals("name")){
                    Collections.sort(SortedList,new CustomNameComparator());
                    for(int i =0;i<SortedList.size();i++){
                        System.out.println(SortedList.get(i).listFormat());
                    }
                }else if(ui.FullCommand.split(" ")[1].equals("date")){
                    Collections.sort(SortedList,new CustomeDateComparator());
                        for(int i =0;i<SortedList.size();i++){
                            System.out.println(SortedList.get(i).listFormat());
                        }
                }else if(ui.FullCommand.split(" ")[1].equals("priority")){
                    Collections.sort(SortedList,new CustomPriorityComparator());
                    for(int i =0;i<SortedList.size();i++){
                        System.out.println(SortedList.get(i).listFormat()+" priority: "+SortedList.get(i).priority);
                    }
                }else{
                    throw new DukeException("OOPS!!! Gazeebo currently does not support this sorting method.");
                }
            }
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
class CustomPriorityComparator implements Comparator<Task>{
    public int compare(Task a, Task b) {
        return b.priority-a.priority;
    }
}

class CustomNameComparator implements Comparator<Task>{
    public int compare(Task a, Task b){
        return a.description.compareTo(b.description);
    }
}
class CustomeDateComparator implements Comparator<Task>{
    public int compare(Task a,Task b){
        if(a.toString().split("\\|")[0].equals("E") && b.toString().split("\\|")[0].equals("E")){
            Event ea = (Event)a;
            Event eb = (Event)b;
            return LocalDateTime.of(ea.date,ea.start).compareTo(LocalDateTime.of(eb.date,eb.start));
        }else if(a.toString().split("\\|")[0].equals("D") && b.toString().split("\\|")[0].equals("D")){
            Deadline da = (Deadline)a;
            Deadline db = (Deadline)b;
            return da.by.compareTo(db.by);
        }else if(a.toString().split("\\|")[0].equals("E") && b.toString().split("\\|")[0].equals("D")){
            Event ea = (Event)a;
            Deadline db = (Deadline)b;
            LocalDateTime temp = LocalDateTime.of(ea.date,ea.start);
            int result =temp.compareTo(db.by);
            return LocalDateTime.of(ea.date,ea.start).compareTo(db.by);
        }else if(a.toString().split("\\|")[0].equals("D") && b.toString().split("\\|")[0].equals("E")){
            Event eb = (Event)b;
            Deadline da = (Deadline)a;
            LocalDateTime temp =LocalDateTime.of(eb.date,eb.start);
            int result = temp.compareTo(da.by);
            return LocalDateTime.of(eb.date,eb.start).compareTo(da.by);
        }else if((a.toString().split("\\|")[0].equals("E")||a.toString().split("\\|")[0].equals("D"))&& !(b.toString().split("\\|")[0].equals("E")||b.toString().split("\\|")[0].equals("D"))){
            return -1;
        }else if((b.toString().split("\\|")[0].equals("E")||b.toString().split("\\|")[0].equals("D"))&& !(a.toString().split("\\|")[0].equals("E")||a.toString().split("\\|")[0].equals("D"))){
            return 1;
        }else{
            return 0;
        }
    }
}
