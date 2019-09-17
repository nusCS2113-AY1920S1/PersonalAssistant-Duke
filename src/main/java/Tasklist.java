import java.util.ArrayList;
public class Tasklist {
    private ArrayList<Task> ls;

    public Tasklist(ArrayList<Task> ls){
        this.ls = ls;
    }
    public Tasklist(){
        this.ls = new ArrayList<Task>();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("    Here are the tasks in your list:\n");
        for (int i = 1; i <= ls.size(); i++){
            sb.append("    " + i + "." + ls.get(i-1).toString() + "\n");
        }
        return sb.toString();
    }

    public ArrayList<Task> list(){
        return ls;
    }

    public String add(Task task){
        ls.add(task);
        StringBuilder sb = new StringBuilder();
        sb.append("     Noted! I've added this task:\n");
        sb.append("         " + task.toString() +"\n");
        sb.append("    Now you have " + ls.size() + " tasks in the list\n");
        return sb.toString();
    }

    public String done(int index){
        StringBuilder sb = new StringBuilder();
        ls.get(index-1).have_done();
        sb.append("     Nice! I've marked this task as done:\n");
        sb.append("         " + ls.get(index-1).toString() + "\n");
        return sb.toString();
    }

    public String find(String keyword){
        StringBuilder sb = new StringBuilder();
        sb.append("    Here are the matching tasks in your list:\n");
        for (int i = 1; i <= ls.size(); i++){
            if (ls.get(i-1).toString().contains(keyword))
            sb.append("    " + i + "." + ls.get(i-1).toString() + "\n");
        }
        return sb.toString();
    }

    public String delete(int index){
        StringBuilder sb = new StringBuilder();
        sb.append("     Noted! I've removed this task:\n");
        sb.append("       " + ls.get(index - 1).toString() + "\n");
        ls.remove(index - 1);
        sb.append("    Now you have " + ls.size() + " tasks in the list.\n");
        return sb.toString();
    }


}
