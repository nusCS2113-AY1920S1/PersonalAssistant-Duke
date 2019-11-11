//@@author jessteoxizhi

package gazeeebo.storage;

import gazeeebo.tasks.Task;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.DoAfter;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.TentativeEvent;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Todo;
import gazeeebo.tasks.Timebound;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TasksPageStorage {

    private final String relativePathResource
            = "Save.txt";

    /**
     * Save the task list to Save.txt
     * @param fileContent concatenate the list into a single string.
     * @throws IOException exception when there is an error writing to the file
     */

    public void writeToSaveFile(final String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }


    /**
     * Read from Save.txt file
     * @return ArrayList of Tasks
     * @throws FileNotFoundException exception if the file path is invalid.
     */
    public ArrayList<Task> readFromSaveFile() throws FileNotFoundException {
        ArrayList<Task> tlist = new ArrayList<Task>();
        File f = new File(relativePathResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String[] details = sc.nextLine().split("\\|");
            if (details[0].equals("T")) {
                Todo t = new Todo(details[2].trim());
                if (details[1].equals("D")) {
                    t.isDone = true;
                } else {
                    t.isDone = false;
                }
                tlist.add(t);
            } else if (details[0].equals("D")) {
                Deadline d = new Deadline(details[2].trim(), details[3].substring(3).trim());
                if (details[1].equals("D")) {
                    d.isDone = true;
                } else {
                    d.isDone = false;
                }
                tlist.add(d);
            } else if (details[0].equals("E)")) {
                Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                if (details[1].equals("D")) {
                    e.isDone = true;
                } else {
                    e.isDone = false;
                }
                tlist.add(e);
            } else if (details[0].equals("P")) {
                Timebound tb = new Timebound(details[2].trim(), details[3].trim());
                if (details[1].equals("D")) {
                    tb.isDone = true;
                } else {
                    tb.isDone = false;
                }
                tlist.add(tb);
            } else if (details[0].equals("FD")) {
                FixedDuration fd = new FixedDuration(details[2].trim(), details[3].trim());
                if (details[1].equals("D")) {
                    fd.isDone = true;
                } else {
                    fd.isDone = false;
                }
                tlist.add(fd);
            } else if (details[0].equals("DA")) {
                DoAfter da = new DoAfter(details[3].trim(), details[3].trim(), details[2].trim());
                if (details[1].equals("D")) {
                    da.isDone = true;
                } else {
                    da.isDone = false;
                }
                tlist.add(da);
            } else if (details[0].equals("TE")) {
                ArrayList<String> timeslots = new ArrayList<String>();
                for (int i = 3; i < details.length; i++) {
                    timeslots.add(details[i]);
                }
                TentativeEvent te = new TentativeEvent(details[2].trim(), timeslots);
                if (details[1].equals("D")) {
                    te.isDone = true;
                } else {
                    te.isDone = false;
                }
                tlist.add(te);
            } else {
                if (details[3].contains("at:") || details[3].contains("by:")) {
                    Event e = new Event(details[2].trim(), details[3].substring(3).trim());
                    if (details[1].equals("D")) {
                        e.isDone = true;
                    } else {
                        e.isDone = false;
                    }
                    tlist.add(e);
                } else if (details[0].contains("P")) {
                    Timebound tb = new Timebound(details[2].trim(), details[3].trim());
                    if (details[1].equals("D")) {
                        tb.isDone = true;
                    } else {
                        tb.isDone = false;
                    }
                    tlist.add(tb);
                } else {
                    FixedDuration fd = new FixedDuration(details[2].trim(), details[3].trim());
                    if (details[1].equals("D")) {
                        fd.isDone = true;
                    } else {
                        fd.isDone = false;
                    }
                    tlist.add(fd);
                }
            }
        }
        return tlist;
    }
}
