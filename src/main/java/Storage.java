import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private String filepath;

    public Storage(String filepath){
        this.filepath = filepath;
    }

    public ArrayList<Task> load() throws Exception{
        ArrayList<Task> ls = new ArrayList<Task>();
        Scanner sc = new Scanner(new File(this.filepath));
        while (sc.hasNext()){
            String st = sc.nextLine();
            String arr[] = st.split(":", 0);
            switch (arr[0]){
                case "[T]":
                    Todo new_todo = new Todo(arr[2]);
                    if (arr[1].equals("[V]")) new_todo.have_done();
                    ls.add(new_todo);
                    break;
                case "[E]":
                    Event new_event = new Event(arr[2],arr[3]);
                    if (arr[1].equals("[V]")) new_event.have_done();
                    ls.add(new_event);
                    break;
                case "[D]":
                    Deadline new_deadline = new Deadline(arr[2],arr[3]);
                    if (arr[1].equals("[V]")) new_deadline.have_done();
                    ls.add(new_deadline);
                    break;
            }
        }
        return ls;
    }

    public void write (ArrayList<Task> ls) throws Exception {
        PrintWriter writer = new PrintWriter(new File(this.filepath));
        for (int i = 0; i < ls.size(); i++){
            writer.println(ls.get(i).saveForm());
        }
        writer.close();
    }

}
