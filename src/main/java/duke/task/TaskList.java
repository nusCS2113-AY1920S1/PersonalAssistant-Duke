package duke.task;

import duke.data.Storage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.time.Month;

/**
 * TaskList handles all the operations Duke uses.
 */
public class TaskList {
    /**
     * List to hold all items that are added.
     */
    private ArrayList<Item> list = new ArrayList<>();

    /**
     * Set the list with a newList that has been loaded.
     *
     * @param newList the loaded new list
     */
    public void setList(final ArrayList<Item> newList) {
        this.list = newList;
    }

    /**
     * Accesses the list that has been saved.
     *
     * @return the saved list of items
     */
    public ArrayList<Item> getList() {
        return this.list;
    }

    /**
     * This method loads all the ArrayList items
     * from the previous load file into the current ArrayList.
     *
     * @param storage the storage object of the loaded list.
     */
    public void addAllList(final Storage storage) {
        try {
            setList((Objects.requireNonNull(storage.loadFile())));
        } catch (NullPointerException e) {
            System.out.println("No previous list loaded");
        }
    }

    /**
     * This method adds tasks to the list of tasks in duke.
     *
     * @param i This is the first parameter
     *          it takes the newly created Deadline/ToDo/Event
     */
    public void addTask(final Item i) {
        list.add(i);
        System.out.println("Got it. I've added this task:\n "
            + list.get(getList().size() - 1).toString() + "\n"
            + "Now you have " + (list.size()) + " tasks in the list.");
    }

    /**
     * This function prints out the complete list of tasks in the ArrayList.
     */
    public void showList() {
        int count = 1;
        for (Item i : list) {
            System.out.println(count++ + "." + i.toString());
        }
    }

    /**
     * This function changes the status of a task from incomplete to complete.
     *
     * @param index This is the index location
     *              of the task to be changed in the ArrayList
     */
    public void doneTask(final int index) {
        list.get(index).changeStatus();
        System.out.println("Nice! I've marked this task as done:\n "
            + list.get(index).toString());
    }

    /**
     * This function deletes the task from the list of tasks.
     * After deleting the function will print out
     * what was deleted and the number of remaining
     * tasks left in the list.
     *
     * @param index This is the index location of
     *              the task to be deleted in the ArrayList
     * @throws IndexOutOfBoundsException e
     */
    public void deleteTask(final int index) {
        System.out.println("Noted. I've removed this task:\n "
            + getList().get(index).toString());
        System.out.println("Now you have "
            + (getList().size() - 1) + " tasks in the list.");
        try {
            getList().remove(index);
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Opps! Sorry that Item does not exist!");
        }
    }

    /**
     * This function locates all tasks that
     * contain a keyword as defined by the user.
     *
     * @param word This parameter is the defined
     *             key word that must be searched for
     */
    public void findTask(final String word) {
        int cnt = 1;
        for (Item i : getList()) {
            if (i.getInfo().contains(word)) {
                if (cnt == 1) {
                    System.out.println(
                        "Here are the matching tasks in your list:");
                }
                System.out.println(cnt++ + ". " + i.toString());
            }
        }

        if (cnt == 1) {
            System.out.println(
                "Sorry, there are no tasks matching your search");
        }
    }

    /**
     * This function locates all tasks on a certain date as defined by the user.
     *
     * @param word This parameter is the defined date that much be searched for
     */

    public void findDate(final String word) {
        int index = 1;
        String[] temp = word.split("/");
        String dd = numOrdinal(Integer.parseInt(temp[0]));
        Month mm = Month.of(Integer.parseInt(temp[1]));
        String yy = temp[2];
        String check = dd + " of " + mm + " " + yy;

        for (Item i : getList()) {
            String desc = i.toString();
            if (desc.toLowerCase().contains(check.toLowerCase())) {
                System.out.println("Here are the tasks on " + check);
                System.out.println(index + ". " + desc);
                index++;
            }
        }
        if (index == 1) {
            System.out.println("Sorry, there are no tasks on " + check);
        }
    }

    /**
     * This function takes in an integer number
     * adds its correct number ordinal to the number.
     *
     * @param num This parameter is the number taken
     * @return String of the input number with the
     * ordinal attached to the end of the number
     */
    public String numOrdinal(final int num) {
        final int ten = 10;
        final int eleven = 11;
        final int twelve = 12;
        final int thirteen = 13;
        String[] suffix = new String[] {
            "th", "st", "nd",
            "rd", "th", "th",
            "th", "th", "th",
            "th"
        };
        switch (num) {
        case eleven:
        case twelve:
        case thirteen:
            return num + "th";
        default:
            return num + suffix[num % ten];
        }
    }

    /**
     * This function takes specific date format
     * dd/mm/yyyy hhmm
     * and turns it into a string phrase.
     *
     * @param date The date taken in by the function
     * @return The date that has been converted into a string phrase,
     * if in incorrect format return original date
     * @throws StringIndexOutOfBoundsException e
     * @throws ArrayIndexOutOfBoundsException  e
     * @throws ParseException                  thrown when date
     *                                         input is in incorrect format
     */
    public Date dateConvert(final String date) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "dd/MM/yyyy HHmm");
            Date formatDate = simpleDateFormat.parse(date);
            return formatDate;
        } catch (StringIndexOutOfBoundsException
            | ArrayIndexOutOfBoundsException e) {
            System.out.println("Please enter a valid date format");
            return null;
        } catch (ParseException pe) {
            System.out.println("Date error");
            return null;
        }
    }

    /**
     * Function converts the date object to the
     * date format 2nd of December 2019, 6pm.
     *
     * @param date the date to be converted
     * @return String of new dat format
     */
    public String dateToStringFormat(final Date date) {
        String hour = new SimpleDateFormat("h").format(date);
        String min = new SimpleDateFormat("mm").format(date);
        String marker = new SimpleDateFormat("a").format(date);
        String day = new SimpleDateFormat("d").format(date);
        String monthYear = new SimpleDateFormat("MMMMM yyyy").format(date);
        String newDateFormat = this.numOrdinal(
            Integer.parseInt(day)) + " of " + monthYear + ", "
            + hour + (min.equals("00") ? marker : ("." + min + marker));
        return newDateFormat;
    }

    /**
     * Function checks to see which deadlines
     * are between now and the specified end date.
     *
     * @param todayDate the present time
     * @param endDate   the specified end date and time
     * @return null if there are no deadlines
     */
    public ArrayList<Item> getReminderList(
        final Date todayDate, final Date endDate) {
        ArrayList<Item> deadlineList = new ArrayList<>();
        Boolean isNotEmpty = false;
        for (Item i : getList()) {
            // check if deadline is before today's date,
            if (i.getType().equals("D") && i.getRawDate().before(endDate)
                && todayDate.before(i.getRawDate()) && !i.isDone()) {
                deadlineList.add(i);
                isNotEmpty = true;
            }
        }
        if (isNotEmpty) {
            return deadlineList;
        }

        return null;
    }

}
