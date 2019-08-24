public class Tasks {
    public int roll_no;
    public String todo;
    public boolean done;

    public Tasks(int roll_no, String todo, boolean b)
    {
        this.roll_no = roll_no;
        this.todo = todo;
        this.done = false;
    }

    public String getStatusIcon() {
        return (done ? "\u2713" : "\u2718"); //return tick or X symbols
    }

}
