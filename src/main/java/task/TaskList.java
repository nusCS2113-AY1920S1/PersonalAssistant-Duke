package task;

import exception.DukeException;
import parser.Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


/**
 * Tasklist stores an arraylist of tasks and performs actions on tasks.
 * Actions: Modify/Remove/Add Tasks.
 *
 * @author Kane Quah
 * @version 1.0
 * @since 08/19
 */
public class TaskList implements Serializable, Cloneable {
    private ArrayList<Task> list = new ArrayList<>();

    public TaskList() {

    }

    /**
     * Method to facilitate the deep cloning of this taskList.
     * Returns a copy of this taskList, but with different references.
     * This is to avoid shallow copying, which will also modify the saved state of the taskList.
     *
     * @return A copy of this taskList with different references to objects.
     */
    public TaskList deepClone() {
        try {
            //Serialization of object
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(this);

            //De-serialization of object
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            return (TaskList) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Overloaded Initializer which accepts a String Parameter with data.
     *
     * @param input String with data separated by newline characters
     * @throws DukeException DukeException Thrown when input is empty or data format is wrong
     */
    public TaskList(String input) throws DukeException {
        if (input.isBlank()) {
            throw new DukeException("File was blank");
        }
        String[] splitTasks = input.split(Parser.newLine);
        try {
            for (int i = 0; i < splitTasks.length; i++) {
                String[] split = splitTasks[i].split(Parser.taskSeparator);
                switch (split[0]) {
                case "T":
                    list.add(new Todo(split[1], split[2], split[3]));
                    break;
                case "D":
                    list.add(new Deadline(split[1], split[2], split[3], split[4]));
                    break;
                case "E":
                    list.add(new Event(split[1], split[2], split[3], split[4]));
                    break;
                default:
                    throw new DukeException((i + 1) + "has incorrect task format.");
                }
            }
        } catch (DukeException e) {
            list.clear();
            throw new DukeException("Issues encountered when creating tasks, initializing empty list.");
        }
    }

    /**
     * Fetches current size of ArrayList.
     *
     * @return long size of ArrayList
     */
    public int size() {
        return list.size();
    }

    /**
     * Out of Bounds checker.
     *
     * @param request int The index to be checked if it exists
     * @return boolean true if within range, false if not
     */
    private boolean isOutOfRange(int request) {
        return ((request < 0) || (request >= this.size()));
    }

    /**
     * Mark a Task as Done.
     *
     * @param input String which should be an Int type
     * @throws DukeException DukeException thrown when An incorrect type input is given
     *                       or the requested index is out of range
     */
    public void markDone(String input) throws DukeException {
        try {
            int request = Integer.parseInt(input);
            request -= 1;
            if (isOutOfRange(request)) {
                throw new DukeException("The index was not found within range");
            } else {
                this.list.get(request).markDone();
                System.out.println("Nice! I've marked this task as done:\n"
                        + "  " + this.list.get(request).toList());
            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Sends a Task to the Shadow Realm.
     *
     * @param input String which should be an Int type
     * @throws DukeException DukeException thrown when An incorrect type input is given
     *                       or the requested index is out of range
     */
    public void banishDelete(String input) throws DukeException {
        try {
            int request = Integer.parseInt(input);
            request -= 1;
            if (isOutOfRange(request)) {
                throw new DukeException("The index was not found within range");
            } else {
                System.out.println("Noted. I've removed this task:\n"
                        + "  " + list.get(request).toList());
                this.list.remove(request);
                System.out.println("Now you have " + this.list.size() + " tasks in the list.");
            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * takes the integers in the Arraylist, and removes the tasks at those index locations
     * @param toDelete
     * @throws DukeException
     */
    public void banishDelete(ArrayList<Integer> toDelete) throws DukeException{
        for (int i = (toDelete.size()-1); i >= 0; i--) {
            if (isOutOfRange(i)){
                throw new DukeException("The index was not found within the range");
            }
            this.list.remove(i);
        }
    }

    /**
     * passes a new date into the event or deadline class.
     *
     * @param input User input of the command to snooze a task as a string.
     * @throws DukeException throws when incorrect range or format was passed.
     */
    public void snoozeTask(String input) throws DukeException {
        try {
            String[] split = input.split(Parser.postpone);
            int request = Integer.parseInt(split[0]);
            request -= 1;
            if (split.length < 2) {
                throw new DukeException("Please use /to to indicate date");
            } else if (isOutOfRange(request)) {
                throw new DukeException("The index was not found within range");
            } else if (!(list.get(request).getType().matches("E") | list.get(request).getType().matches("D"))) {
                throw new DukeException("Only Events and Deadlines can be snoozed");
            } else {
                this.list.get(request).snooze(input);
                System.out.println("Noted. I've snoozed this task:\n"
                        + "  " + list.get(request).toList());

            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Fetches a Task from the ArrayList, given an index.
     *
     * @param index int index of Task within ArrayList
     * @return Task Task within ArrayList
     * @throws DukeException DukeException thrown when Task is not found within list
     */
    public Task get(int index) throws DukeException {
        if (!this.isOutOfRange(index)) {
            return this.list.get(index);
        } else {
            throw new DukeException("Requested Task not found within list");
        }
    }

    public ArrayList<Task> get() {
        return this.list;
    }

    /**
     * Adds another task to the list, given the inputs.
     *
     * @param type  String indicating what type of Task should be added
     * @param input raw secondary input to be processed by the method
     * @throws DukeException throws when invalid add type is passed
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
                throw new DukeException("What the Hell happened here?\n"
                        + "Command passed successfully to tasklist.add, not found in any case");
            }
            this.list.add(temp);
            System.out.println("Got it. I've added this task:\n  "
                    + temp.toList() + "\nNow you have " + this.size() + " tasks in the list.");
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        }
    }


    /**
     * backend method for adding a task to the tasklist
     * @param task
     */
    public void add(Task task) {
        this.list.add(task);
    }


    /**
     * backend method for removing a task from the tasklist
     * @param i
     */
    public void remove(int i){
        this.list.remove(i);
    }

    /**
     * Finds a Task if any part of its description/date matches the input.
     *
     * @param input String to be matches to description/date
     * @throws DukeException DukeException to be thrown when errors occur somehow
     */
    public void find(String input) throws DukeException {
        ArrayList<Integer> foundIndex = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getDescription().contains(input) || this.get(i).getDueDate().contains(input)) {
                foundIndex.add(i);
            }
        }
        if (foundIndex.isEmpty()) {
            System.out.println("There are no matching tasks in the list");
        } else {
            System.out.println("Here are the matching tasks in your list:");
            for (Integer k : foundIndex) {
                System.out.println((k + 1) + ". " + this.get(k).toList());
            }
        }
    }

    /**
     * Shows the schedule if the input matches any of the dates in the tasklist.
     *
     * @param input String to be matches to description/date
     * @throws DukeException DukeException to be thrown when errors occur somehow
     */

    public void view_schedule(String input) throws DukeException {
        ArrayList<Integer> foundDate = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getDueDate().contains(input)) {
                foundDate.add(i);
            }
        }
        if (foundDate.isEmpty()) {
            System.out.println("You have no tasks today. Enjoy!");
        } else {
            System.out.println("Here's what the day looks like:");
            for (Integer d : foundDate) {
                System.out.println((d + 1) + ". " + this.get(d).toList());
            }
        }
    }

    /**
     * Checks if there is any event with the same start time, that could lead to a conflict.
     * The AddCommand class is classified to check for such instances when the type of task is an "Event".
     */
    public void conflict_check() throws DukeException {
        ArrayList<String> checkConflict = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            checkConflict.add(list.get(i).getDueDate());
        }

        for (int i = 0; i < checkConflict.size(); i++) {
            if (checkConflict.get(i).equals(list.get(list.size() - 1).getDueDate())) {
                System.out.println("There is a conflict in the schedule!");
                break;
            }
        }
    }

    /**
     * Prints out all tasks in list.
     * If list is empty, prints out message stating that it is empty
     */
    public void print() {
        if (this.size() == 0) {
            System.out.println("Whoops, there doesn't seem to be anything here at the moment");
        } else {
            System.out.println("Here are your tasks: \n");
            int counter = 1;
            for (Task task : list) {
                System.out.println(counter++ + ". " + task.toList());
            }
        }
    }

    /**
     * Prints out all deadlines and events at startup
     */
    public void printReminders() {
        ArrayList<Task> reminderList = new ArrayList<Task>();
        for (Task task : list) {
            if (task.getType().matches("E")|task.getType().matches("D")){
                reminderList.add(task);
            };
        }

        int counter = 1;
        if (reminderList.size() > 0){
            System.out.println("Here are your reminders for upcoming tasks:");
            for (Task task : reminderList) {
                System.out.println(counter + ". " + task.toList());
                counter++;
            }
        }
    }

    /**
     * Selects a Task from an Event with Tentative Dates.
     *
     * @param input String which should contain two whitespace separated numbers.
     */
    public void select(String input) throws DukeException {
        String[] split = input.split("\\s+");
        if (split.length != 2) {
            throw new DukeException("Incorrect number of arguments, Index + Index method expected");
        } else {
            try {
                int index = Integer.parseInt(split[0]);
                int dateIndex = Integer.parseInt(split[1]);
                index -= 1;
                dateIndex -= 1;
                if (isOutOfRange(index)) {
                    throw new DukeException("The index was not found within task range");
                } else if (!list.get(index).getType().equals("E")) {
                    throw new DukeException("The Task you chose is not the Event Type!");
                } else if (!list.get(index).tentativeExists()) {
                    throw new DukeException("There are no Tentative Slots to be chosen from");
                } else if (list.get(index).outsideTentative(dateIndex)) {
                    throw new DukeException("Not a valid Selection (out of range)");
                } else {
                    this.list.get(index).changeDueDate(this.list.get(index).getTentativeDate(dateIndex));
                    this.list.get(index).clearTentative();
                    System.out.println("Tentative Date selected successfully");
                }

            } catch (DukeException e) {
                throw new DukeException(e.getLocalizedMessage());
            } catch (NumberFormatException e) {
                throw new DukeException("That is NOT a valid Integer");
            }
        }
    }

    /**
     * sorts this Tasklist according to the priority of all the tasks in descending order
     */
    public void sortPriority(){
        for (int i = 0 ; i < this.list.size(); i++){
////            if (this.list.get(i).getTaskPriority() == null){
////                this.list.get(i).setTaskPriority(2);
//                System.out.println(this.list.get(i).toList());
//                System.out.println(this.list.get(i).getTaskPriority());
        }
        Collections.sort(this.list, new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return (o2.taskPriority - o1.taskPriority);
//                return o1.getDescription().compareTo(o2.getDescription());
            }
        });
        System.out.println("Done! Your tasks have been sorted by priority; the most important one is at the top:\n");
        print();
    }



    /**
     * Deletes the entire taskList.
     */
    public void clear() {
        list.clear();
    }
}
