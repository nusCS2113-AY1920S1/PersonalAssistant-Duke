package myTasks;

import Exception.DukeException;
import Parser.Parser;

import java.util.ArrayList;

/**
 * Tasklist stores an arraylist of tasks and performs actions on tasks
 * Actions: Modify/Remove/Add Tasks
 * @author Kane Quah
 * @version 1.0
 * @since 08/19
 */
public class TaskList {
    private ArrayList<Task> list = new ArrayList<>();
    public TaskList(){}

    /**
     * Overloaded Initializer which accepts a String Parameter with data
     * @param input String with data separated by newline characters
     * @throws DukeException DukeException Thrown when input is empty or data format is wrong
     */
    public TaskList(String input) throws DukeException {
        if(input.isBlank())
        {
            throw new DukeException("File was blank");
        }
        String[] splitTasks = input.split(Parser.newLine);
        try {
            for (int i = 0; i < splitTasks.length; i++) {
                String[] split = splitTasks[i].split(Parser.taskSeparator);
                switch(split[0]) {
                    case "T":
                        list.add(new Todo(split[1], split[2]));
                        break;
                    case "D":
                        list.add(new Deadline(split[1], split[2], split[3]));
                        break;
                    case "E":
                        list.add(new Event(split[1], split[2], split[3]));
                        break;
                    default:
                        throw new DukeException((i+1) + "has incorrect task format.");
                }
            }
        }
        catch(DukeException e)
        {
            list.clear();
            throw new DukeException("Issues encountered when creating tasks, initializing empty list.");
        }
    }

    /**
     * Fetches current size of Arraylist
     * @return long size of ArrayList
     */
    public long size(){
        return list.size();
    }

    /**
     * Out of Bounds checker
     * @param request int The index to be checked if it exists
     * @return boolean true if within range, false if not
     */
    private boolean isOutOfRange(int request){
        return ((request < 0) || (request >= this.size()));
    }

    /**
     * Mark a Task as Done
     * @param input String which should be an Int type
     * @throws DukeException DukeException thrown when An incorrect type input is given
     *                          or the requested index is out of range
     */
    public void markDone(String input) throws DukeException {
        try {
            int request = Integer.parseInt(input);
            request-=1;
            if(isOutOfRange(request)){
                throw new DukeException("The index was not found within range");
            }
            else {
                this.list.get(request).markDone();
                System.out.println("Nice! I've marked this task as done:\n" +
                        "  " + this.list.get(request).toList());
            }
        }
        catch(DukeException e)
        {
            throw new DukeException(e.getLocalizedMessage());
        }
        catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Sends a Task to the Shadow Realm
     * @param input String which should be an Int type
     * @throws DukeException DukeException thrown when An incorrect type input is given
     *                          or the requested index is out of range
     */
    public void banishDelete(String input) throws DukeException {
        try {
            int request = Integer.parseInt(input);
            request-=1;
            if(isOutOfRange(request)){
                throw new DukeException("The index was not found within range");
            }
            else {
                System.out.println("Noted. I've removed this task:\n" +
                        "  " + list.get(request).toList());
                this.list.remove(request);
                System.out.println("Now you have " + this.list.size() + " tasks in the list.");
            }
        }
        catch(DukeException e)
        {
            throw new DukeException(e.getLocalizedMessage());
        }
        catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Fetches a Task from the ArrayList, given an index
     * @param index int index of Task within ArrayList
     * @return Task Task within ArrayList
     * @throws DukeException DukeException thrown when Task is not found within list
     */
    public Task get(int index) throws DukeException {
        if(!this.isOutOfRange(index))
            return this.list.get(index);
        else
            throw new DukeException("Requested Task not found within list");
    }

    /**
     * Adds another task to the list, given the inputs;
     * @param type String indicating what type of Task should be added
     * @param input raw secondary input to be processed by the method
     * @throws DukeException
     */
    public void add(String type, String input) throws DukeException {
        Task temp;
        try {
            switch (type) {
                case "todo":
                    temp = new Todo(input);
                    break;
                case "deadline":
                    temp = new Deadline(input);
                    break;
                case "event":
                    temp = new Event(input);
                    break;
                default:
                    throw new DukeException("What the Hell happened here?\n"+
                            "Command passed successfully to tasklist.add, not found in any case");
            }
            this.list.add(temp);
            System.out.println("Got it. I've added this task:\n  " +
                    temp.toList() + "\nNow you have "+ this.size() + " tasks in the list.");
        }
        catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        }
    }

    /**
     * Finds a Task if any part of its description/date matches the input
     * @param input String to be matches to description/date
     * @throws DukeException DukeException to be thrown when errors occur somehow
     */
    public void find(String input) throws DukeException {
        ArrayList<Integer> FoundIndex = new ArrayList<>();
        for (int i = 0; i < this.size(); i++)
        {
            if (this.get(i).getDescription().contains(input) || this.get(i).getDueDate().contains(input)) {

                FoundIndex.add(i);
            }
        }
        if(FoundIndex.isEmpty())
            System.out.println("There are no matching tasks in the list");
        else
        {
            System.out.println("Here are the matching tasks in your list:");
            for (Integer foundIndex : FoundIndex) {
                System.out.println((foundIndex + 1) + ". " + this.get(foundIndex).toList());
            }
        }
    }

    /**
     * Prints out all tasks in list
     * If list is empty, prints out message stating that it is empty
     */
    public void print() {
        if (this.size() == 0) {
            System.out.println("Whoops, there doesn't seem to be anything here at the moment");
        } else {
            int counter = 1;
            for (Task task : list) {
                System.out.println(counter++ + ". " + task.toList());
            }
        }
    }

    /**
     * Selects a Task from an Event with Tentative Dates
     * @param input String which should contain two whitespace separated numbers
     */
    public void select(String input) throws DukeException {
        String[] split = input.split("\\s+");
        if(split.length != 2)
        {
            throw new DukeException("Incorrect number of arguments, Index + Index method expected");
        }
        else
        {
            try {
                int index = Integer.parseInt(split[0]);
                int dateIndex = Integer.parseInt(split[1]);
                index -= 1;
                dateIndex -= 1;
                if (isOutOfRange(index))
                {
                    throw new DukeException("The index was not found within task range");
                }
                else if (!list.get(index).getType().equals("E"))
                {
                    throw new DukeException("The Task you chose is not the Event Type!");
                }
                else if (!list.get(index).tentativeExists())
                {
                    throw new DukeException("There are no Tentative Slots to be chosen from");
                }
                else if (list.get(index).outsideTentative(dateIndex))
                {
                    throw new DukeException("Not a valid Selection (out of range)");
                }
                else
                {
                    this.list.get(index).changeDueDate(this.list.get(index).getTentativeDate(dateIndex));
                    this.list.get(index).clearTentative();
                    System.out.println("Tentative Date selected successfully");
                }

            }
            catch(DukeException e)
            {
                throw new DukeException(e.getLocalizedMessage());
            }
            catch(NumberFormatException e)
            {
                throw new DukeException("That is NOT a valid Integer");
            }
        }
    }
}
