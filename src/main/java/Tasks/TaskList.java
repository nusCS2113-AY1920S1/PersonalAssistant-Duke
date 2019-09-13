package Tasks;

import COMPal.Duke;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;

import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {

    public ArrayList<Task> arrlist;
    public Duke d;

    public TaskList(Duke d){
        this.d=d;
        arrlist=new ArrayList<>();
    }


    /**
     * @Function
     * @param cmd, No Return Value
     * @calls dateParse(String when)
     * This function handles the adding of the tasks (Events, Deadlines, Todos)
     * It tests for the event type, then parses it according to the correct syntax
     * @UsedIn: parser.processCommands
     */
    public void addTask(String cmd){
        System.out.println("Got it. I've added this task:");
        Scanner sc1= new Scanner(cmd);
        String s=sc1.next(); //get the command string
        String cs=sc1.nextLine(); //get the description string
        String token;
        String description;
        switch(s){
            case "todo":
                arrlist.add(new Todo(cs.trim()));
                d.ui.printg("[T][\u2718] "+cs);
                break;
            case "event":
                token="/at";

                description= getDescription(cs,token);
                arrlist.add(new Event(description));
                d.ui.printg("[E][\u2718] "+description);
                break;
            case "deadline":
                token="/by";

                description=getDescription(cs,token);
                arrlist.add(new Deadline(description));
                d.ui.printg("[D][\u2718] "+description);
                break;

        }

        //at this point, an update is made to the task list, so save to file
        d.storage.saveDuke(arrlist);
        d.ui.showSize();
    }



    /**
     * @Function
     * @param cmd, No Return Value
     * This function handles the deletion of tasks
     * @UsedIn: parser.processCommands
     */
    public void deleteTask(String cmd){
        Scanner sc1= new Scanner(cmd);
        sc1.next(); //skip over the 'delete'
        System.out.println("Noted. I've removed this task:");
        Task t = arrlist.remove(sc1.nextInt()-1);
        t.markAsDone();
        d.ui.showTask(t);
        d.storage.saveDuke(arrlist);
        d.ui.showSize();
    }


    /**
     * @Function
     * @param cmd, No Return Value
     * This function handles the completion of tasks by marking them as done
     * @UsedIn: parser.processCommands
     */
    public void taskDone(String cmd){
        Scanner sc1= new Scanner(cmd);
        sc1.next(); //skip over the 'done'
        System.out.println("Nice! I've marked this task as done:");
        Task t = arrlist.get(sc1.nextInt()-1);
        t.markAsDone();

        d.ui.showTask(t);
        d.storage.saveDuke(arrlist);
    }



    /**
     * @Function
     * @param cmd, No Return Value
     * This function handles the deletion of tasks
     * @UsedIn: parser.processCommands
     */
    public void findTask(String cmd){
        Scanner sc1= new Scanner(cmd);
        sc1.next(); //skip over the 'find'
        String pattern = sc1.next();
        System.out.println("Here are the matching tasks in your list:");

        int count=1;

        for(Task t:arrlist){
            if(t.description.contains(pattern)){
                System.out.print(count+++".");
                d.ui.showTask(t);
            }
        }

    }


    /**
     * @Function
     * @param cs
     * @param token
     * @return description
     * @UsedIn: : addTask
     * This function builds a description from the description string according to the token (/at or /by etc)
     */
    public String getDescription(String cs, String token){
        int splitPoint=cs.indexOf(token);
        String when=cs.substring(splitPoint+token.length()+1);

        //call the date parser to parse and return a date string
        String check=Duke.dateParse(when);
        if(!check.equals("false")){
            when=check;
        }


        token=token.replace("/","");
        String what = cs.substring(0,splitPoint).trim();
        return what+" ("+token+": "+when+")";

    }





}
