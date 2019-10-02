import command.*;
import exception.DukeException;
import storage.Storage;
import task.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Creates a Command object after extracting information needed
 * @author Zhang Yue Han
 */
public class Parser {

    public static Command parse(String input) {
        try {
            String[] taskInfo = input.split(" ", 2);
            if (taskInfo[0].equals("bye")) {
                //create an ExitCommand
                return new ExitCommand();
            } else if (taskInfo[0].equals("done")) {
                if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.INDEX_MISSING);
                //create a DoneCommand object
                return new DoneCommand(Integer.parseInt(taskInfo[1]));
            } else if (taskInfo[0].equals("delete")) {
                if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.INDEX_MISSING);
                //create a DeleteCommand
                return new DeleteCommand(Integer.parseInt(taskInfo[1]));
            } else if (taskInfo[0].equals("find")) {
                if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.SEARCHPHRASE_MISSING);
                //create a FindCommand
                return new FindCommand(taskInfo[1]);
            } else if (taskInfo[0].equals("list")) {
                return new ListCommand();
            } else {
                if (taskInfo[0].equals("todo")) {
                    //parse and throw into AddCommand
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) throw new DukeException(DukeException.ErrorType.FORMAT_TODO);
                    Todo t = new Todo(taskInfo[1]);
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("deadline")) {
                    //parse date
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    ArrayList<String> dateInfo = parseDate("deadline", taskInfo);
                    //throw exception if there is no date entered; did not find "/by" keyword
                    if ((dateInfo.isEmpty())  || (dateInfo.size() < 2)) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    Date d = new Date();
                    Deadline t;
                    //parse remindDate if it exists
                    if (dateInfo.size() == 3) {
                        dateInfo.set(1,(d.convertDate(dateInfo.get(1))));
                        dateInfo.set(2,(d.convertDate(dateInfo.get(2))));
                        if (dateInfo.get(1).equals("[null]") || dateInfo.get(2).equals("[null]")) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                        t = new Deadline(dateInfo.get(0), dateInfo.get(1), dateInfo.get(2));
                    } else {
                        //no reminder parse date
                        dateInfo.set(1,(d.convertDate(dateInfo.get(1))));
                        if (dateInfo.get(1).equals("[null]")) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                        t = new Deadline(dateInfo.get(0), dateInfo.get(1));
                    }
                    //create deadline object
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("event")) {
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) { throw new DukeException(DukeException.ErrorType.FORMAT_EVENT); }
                    ArrayList<String> dateInfo = parseDate("event", taskInfo);
                    if ((dateInfo.isEmpty())  || (dateInfo.size() < 2)) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    Date d = new Date();
                    dateInfo.set(1,(d.convertDate(dateInfo.get(1))));
                    if (dateInfo.get(1).equals("[null]")) { throw new DukeException(DukeException.ErrorType.FORMAT_DEADLINE); }
                    Event t = new Event(dateInfo.get(0), dateInfo.get(1));
                    //create event object
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("recurring")) {
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) {
                        throw new DukeException(DukeException.ErrorType.FORMAT_RECURRING);
                    }
                    ArrayList<String> dateInfo = parseDate("recurring", taskInfo);
                    if (dateInfo.isEmpty() || (dateInfo.size() < 2)) {
                        throw new DukeException(DukeException.ErrorType.FORMAT_RECURRING);
                    }
                    Date d = new Date();
                    String temp = dateInfo.get(1);
                    dateInfo.set(1,(d.convertDate(dateInfo.get(1))));
                    if (!dateInfo.get(1).equals("null")) {
                        throw new DukeException(DukeException.ErrorType.FORMAT_RECURRING_DATE);
                    }
                    //create event object
                    Recurring t = new Recurring(dateInfo.get(0), temp);
                    return new AddCommand(t);
                } else if (taskInfo[0].equals("fixed")) {
                    if ((taskInfo.length < 2) || !(taskInfo[1].trim().length() > 0)) { throw new DukeException(DukeException.ErrorType.FORMAT_FIXED); }
                    ArrayList<String> dateInfo = parseDate("fixed", taskInfo);
                    if ((dateInfo.isEmpty() || (dateInfo.size() < 2))) { throw new DukeException(DukeException.ErrorType.FORMAT_FIXED); }
                    Date d = new Date();
                    dateInfo.set(1, d.getDuration(dateInfo.get(1)));
                    if (dateInfo.get(1) == null) { throw new DukeException(DukeException.ErrorType.FORMAT_FIXED); }
                    //create event object
                    FixedDuration t = new FixedDuration(dateInfo.get(0), dateInfo.get(1));
                    return new AddCommand(t);
                }
                else if(taskInfo[0].equals("view")){
                    //System.out.println("lookUpDate before conversion is "+taskInfo[1]);
                    if ((taskInfo.length <2) || !(taskInfo[1].trim().length() > 0)) { throw new DukeException(DukeException.ErrorType.FORMAT_VIEW); }
                    String dateInfo = taskInfo[1];
                    if ((dateInfo.equals("[null]"))) { throw new DukeException(DukeException.ErrorType.FORMAT_VIEW); }
                    Date d = new Date();
                    dateInfo = d.convertDate(dateInfo);
                    String lookUpDate = dateInfo;
                    //System.out.println("lookUpDate after conversion is : " +lookUpDate);
                    return new ViewSchedule(lookUpDate);
                }
                else {
                    try {
                        throw new DukeException(DukeException.ErrorType.COMMAND_INVALID);
                    } catch (DukeException e){
                        e.showError();
                        return new BadCommand();
                    }
                }
            }
        } catch (DukeException e) {
            e.showError();
            return new BadCommand();
        }
    }

    public static ArrayList<String> parseDate(String type, String[] taskInfo) {
        ArrayList<String> dateInfo = new ArrayList<String>();
        String[] a;
        String[] b;

        if (type.equals("deadline")) {
            a = taskInfo[1].split("/by ");
            b = a[1].split("/remind");
            if (b.length > 1) {
                //there is a remind command
                dateInfo.add(a[0].trim()); //description
                dateInfo.add(b[0].trim()); //deadline date
                dateInfo.add(b[1].trim()); //reminder date

                String filePath = "src/main/data/reminders.txt";
                String reminderInfo = dateInfo.get(0) + " | " + dateInfo.get(1) + " | " + dateInfo.get(2);
                Storage.writeReminderFile(reminderInfo, filePath);

            } else {
                //there is no remind command
                dateInfo.addAll(Arrays.asList(a)); //contains description and deadline
            }

        } else if (type.equals("event")) {
            //tell AddCommand to go add itself
            a = taskInfo[1].split("/at ");
            dateInfo.addAll(Arrays.asList(a));
        } else if (type.equals("recurring")) {
            //tell AddCommand to go add itself
            a = taskInfo[1].split("/every ");
            dateInfo.addAll(Arrays.asList(a));
        } else if (type.equals("fixed")) {
            a = taskInfo[1].split("/take ");
            dateInfo.addAll(Arrays.asList(a));
            //tell AddCommand to go add itself
        }
        return dateInfo;
    }
}