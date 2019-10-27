package duke;

import duke.components.Bar;
import duke.components.Chord;
import duke.components.Note;
import duke.components.Pitch;
import duke.components.Song;
import duke.components.SongList;
import duke.tasks.ToDo;

import duke.tasks.Deadline;
import duke.tasks.DoAfter;
import duke.tasks.Event;
import duke.tasks.BetweenTask;
import duke.tasks.RecurringTask;
import duke.tasks.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * A class to implement persistent storage of the task list using a .txt file.
 */
public class Storage {

    private Path file;

    /**
     * Constructor for the duke.Storage class.
     *
     * @param file the Path object representing the path to the file being used to store the task list.
     */
    Storage(Path file) {
        this.file = file;
    }

    // Ducats implementation starts here.


    // Storage structure for Ducats is as follows:
    //
    // List of Songs, with each Song being represented in the following format:
    // s/NAME s/BAR1 s/BAR2 ...
    //
    // BAR is formatted as a two-dimensional array of Notes, with each nested array representing a Chord.
    //
    // E.g.
    // "Hello World! [[UAs;UBs],[UA;UB],[UAs;UB],[UA;UB],[UBs;R],[UB;R],[LFs;R],[LF;R]] [...] ..."
    //
    // TODO: implement convertFromString

    // @@author rohan-av
    private ArrayList<String> formatListToString(ArrayList<Song> list) {
        ArrayList<String> result = new ArrayList<>();
        for (Song song: list) {
            result.add(song.toString());
        }
        return result;
    }

    private void writeStringsToFile(ArrayList<String> songs) throws DukeException {
        try {
            Files.write(file, songs, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DukeException("","io");
        }
    }

    private ArrayList<String> readStringsFromFile() throws DukeException {
        // reads file and returns an ArrayList of lines
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(file)) {
            String line;
            while ((line = br.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            throw new DukeException("", "io");
        }
        return result;
    }

    void loadToList(SongList songList) throws DukeException {
        // loads data into list
        ArrayList<String> data = readStringsFromFile();
        for (String line: data) {
            songList.add(convertSongFromString(line));
        }
    }

    // twinkle [[UAs;UAs],[UA;UA],[UAs;UAs],[UA;UA],[UAs;UAs],[UA;UA],[UAs;UAs],[UA;UA]]
    private Song convertSongFromString(String s) throws DukeException {
        String[] sections = s.split(" ");
        if (sections.length == 1) {
            throw new DukeException("io","");
        }
        String name = sections[0];
        String key = sections[1];
        int tempo = Integer.parseInt(sections[2]);
        Song song = new Song(name, key, tempo);
        for (int i = 3; i < sections.length; i++) {
            song.addBar(convertBarFromString(sections[i], i - 3));
        }
        return song;
    }

    private Bar convertBarFromString(String s, int barIndex) throws DukeException {
        String barData = s.substring(1, s.length() - 1);
        String[] rawChords = barData.split(",");
        ArrayList<Chord> chords = new ArrayList<>();
        for (String rawChord: rawChords) {
            chords.add(convertChordFromString(rawChord));
        }
        Bar bar = new Bar(barIndex, chords);
        return bar;
    }

    private Chord convertChordFromString(String s) throws DukeException {
        String noteData = s.substring(1, s.length() - 1);
        String[] rawNotes = noteData.split(";");
        ArrayList<Note> notes = new ArrayList<>();
        for (String rawNote: rawNotes) {
            notes.add(convertNoteFromString(rawNote));
        }
        Chord chord = new Chord(notes);
        return chord;
    }

    private Note convertNoteFromString(String s) throws DukeException {
        String duration = "8"; //duration for each chord
        boolean isStart = false;
        if (s.length() == 3) { //unit note is the start of the note
            isStart = true;
        }
        Pitch pitch;
        switch (s.length() == 3 ? s.substring(0,2) : s) {
        case "LC":
            pitch = Pitch.LOWER_C;
            break;
        case "LD":
            pitch = Pitch.LOWER_D;
            break;
        case "LE":
            pitch = Pitch.LOWER_E;
            break;
        case "LF":
            pitch = Pitch.LOWER_F;
            break;
        case "LG":
            pitch = Pitch.LOWER_G;
            break;
        case "LA":
            pitch = Pitch.LOWER_A;
            break;
        case "LB":
            pitch = Pitch.LOWER_B;
            break;
        case "MC":
            pitch = Pitch.MIDDLE_C;
            break;
        case "UD":
            pitch = Pitch.UPPER_D;
            break;
        case "UE":
            pitch = Pitch.UPPER_E;
            break;
        case "UF":
            pitch = Pitch.UPPER_F;
            break;
        case "UG":
            pitch = Pitch.UPPER_G;
            break;
        case "UA":
            pitch = Pitch.UPPER_A;
            break;
        case "UB":
            pitch = Pitch.UPPER_B;
            break;
        case "UC":
            pitch = Pitch.UPPER_C;
            break;
        case "RT":
            pitch = Pitch.REST;
            break;
        default:
            throw new DukeException("data","");
        }
        return new Note(duration, pitch, isStart);
    }


    public void updateFile(SongList songList) throws DukeException {
        // System.out.println(songList.getSongList().get(0).toString());
        writeStringsToFile(formatListToString(songList.getSongList()));
    }

    // Ducats implementation ends here. (TODO: delete below when appropriate)

    //    /**
    //     * Returns an ArrayList of the String representations of all the duke.tasks.Task objects in the task list.
    //     *
    //     * @param list the task list containing all the duke.tasks.Task objects
    //     * @return an ArrayList of the String representations of the tasks in the task list
    //     */
    //    private ArrayList<String> formatFile(ArrayList<Task> list) {
    //        ArrayList<String> result = new ArrayList<>();
    //        for (Task task : list) {
    //            result.add(task.toString());
    //        }
    //        return result;
    //    }
    //
    //    /**
    //     * Writes the task list to the .txt file.
    //     *
    //     * @param tasks an ArrayList of the String representations of the tasks in the task list
    //     * @throws DukeException in the case of input or output exceptions
    //     */
    //    private void writeFile(ArrayList<String> tasks) throws DukeException {
    //        try {
    //            Files.write(file, tasks, StandardCharsets.UTF_8);
    //        } catch (IOException e) {
    //            throw new DukeException("","io");
    //        }
    //    }
    //
    //    /**
    //     * Reads the .txt fil and returns an ArrayList of Strings that represent the tasks in the task
    //     * list
    //     *
    //     * @return an ArrayList of Strings that represent the tasks in the task list
    //     * @throws DukeException in the case of input or output exceptions
    //     */
    //    private ArrayList<String> readFile() throws DukeException {
    //        // reads file and returns an ArrayList of lines
    //        ArrayList<String> result = new ArrayList<>();
    //        try (BufferedReader br = Files.newBufferedReader(file)) {
    //            String line;
    //            while ((line = br.readLine()) != null) {
    //                result.add(line);
    //            }
    //        } catch (Exception e) {
    //            throw new DukeException("", "io");
    //        }
    //        return result;
    //    }
    //
    //    /**
    //     * After reading the file, converts each String representation back into its corresponding
    //     * duke.tasks.Task object and pushes it into the duke.TaskList.
    //     *
    //     * @param taskList the duke.TaskList object used to store the task list
    //     * @throws DukeException in the case of input or output exceptions
    //     */
    //    void loadList(TaskList taskList) throws DukeException {
    //        // loads data into list
    //        ArrayList<String> data = readFile();
    //        for (String line: data) {
    //            //System.out.println(line);
    //            convertString(taskList, line);
    //        }
    //    }
    //
    //    /**
    //     * Interprets the String, translates it to the appropriate duke.tasks.Task object, and adds it
    //     * to the duke.TaskList.
    //     *
    //     * @param taskList the duke.TaskList object used to store the task list
    //     * @param s the String representation to be converted
    //     * @throws DukeException in the case of input or output exceptions
    //     */
    //    private void convertString(TaskList taskList, String s) throws DukeException {
    //        try {
    //            String type = s.substring(1,2); // T, D, E or A
    //            boolean isDone = s.substring(4,5).equals("v");
    //            String description;
    //            String addendum;
    //
    //            switch (type) {
    //            case "T":
    //                description = s.substring(7);
    //                ToDo todo = new ToDo(description);
    //                if (isDone) {
    //                    todo.setDone();
    //                }
    //                taskList.add(todo);
    //                break;
    //            case "E": {
    //                String[] sections = s.substring(7).split("\\(from:");
    //
    //                sections[1] = sections[1].replace("to","-");
    //                sections[1] = sections[1].replace(")","");
    //                //System.out.println(sections[1]);
    //                //description = sections[0].substring(0, sections[0].length() - 2);
    //                //addendum = sections[1].substring(1, sections[1].length() - 1);
    //                //String[] to_from  = addendum.split("to");
    //
    //                Event event = (Event)taskList.get_first_e(sections,1);
    //                if (isDone) {
    //                    event.setDone();
    //                }
    //                taskList.add(event);
    //                break;
    //            }
    //            case "D": {
    //                String[] sections = s.substring(7).split("by:");
    //                description = sections[0].substring(0, sections[0].length() - 2);
    //                addendum = sections[1].substring(1, sections[1].length() - 1);
    //                Deadline deadline = new Deadline(description, addendum);
    //                if (isDone) {
    //                    deadline.setDone();
    //                }
    //                taskList.add(deadline);
    //                break;
    //            }
    //            case "A": {
    //                String[] sections = s.substring(7).split("after:");
    //                description = sections[0].substring(0, sections[0].length() - 2);
    //                addendum = sections[1].substring(6, sections[1].length() - 1);
    //                int previousTaskNumber = Integer.parseInt(addendum);
    //                DoAfter doAfter = new DoAfter(description, previousTaskNumber, taskList.getSize() + 1);
    //                DoAfterList.add(previousTaskNumber);
    //                if (isDone) {
    //                    doAfter.setDone();
    //                }
    //                taskList.add(doAfter);
    //                break;
    //            }
    //            case "B": {
    //                String[] sections = s.substring(7).split("between");
    //                description = sections[0].substring(0, sections[0].length() - 2);
    //                String[] sections2 = sections[1].split("and");
    //                String start = sections2[0].substring(1, sections2[0].length() - 1).trim();
    //                String end = sections2[1].substring(0, sections2[1].length() - 1).trim();
    //                BetweenTask betweenTask = new BetweenTask(description, start, end);
    //                if (isDone) {
    //                    betweenTask.setDone();
    //                }
    //                taskList.add(betweenTask);
    //                break;
    //            }
    //            case "R": {
    //                String[] sections = s.substring(7).split("\\(");
    //                description = sections[0];
    //                String frequency = sections[1].split(" ")[0];
    //
    //                String[] dateInfo = sections[1].split("on: ");
    //                String[] dateNewInfo = dateInfo[1].split(" ");
    //                String date = dateNewInfo[0];
    //                String time;
    //                if (dateNewInfo.length == 3) {
    //                    time = dateNewInfo[2].substring(0, dateNewInfo[2].length() - 1);
    //                } else {
    //                    time = "";
    //                }
    //                //String date = "";
    //                //       String time = "";
    //                RecurringTask recurringTask = new RecurringTask(description, date, time, frequency);
    //                taskList.add(recurringTask);
    //                break;
    //            }
    //            default:
    //                throw new DukeException("","io");
    //            }
    //
    //        } catch (Exception e) {
    //            throw new DukeException("","io");
    //        }
    //    }
    //
    //    /**
    //     * Updates the .txt file with the latest task list found within the duke.Duke program.
    //     *
    //     * @param taskList the duke.TaskList object used to store the task list
    //     * @throws DukeException in the case of input or output exceptions
    //     */
    //    public void updateFile(TaskList taskList) throws DukeException {
    //        writeFile(formatFile(taskList.getTaskList()));
    //    }
}
