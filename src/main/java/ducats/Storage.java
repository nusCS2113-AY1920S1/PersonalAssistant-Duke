package ducats;

import ducats.components.Bar;
import ducats.components.Chord;
import ducats.components.Note;
import ducats.components.Pitch;
import ducats.components.Song;
import ducats.components.SongList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

//@@author rohan-av
/**
 * A class to implement persistent storage of the task list using a .txt file.
 */
public class Storage {

    protected File file;
    private String filepath;

    /**
     * Constructor for the duke.Storage class.
     *
     * @param filepath the String object representing the path to the file being used to store the task list.
     */
    public Storage(String filepath) {
        this.filepath = filepath;
        file = new File(filepath);
    }

    boolean initialize() throws DucatsException {
        try {
            return file.createNewFile();
        } catch (IOException e) {
            throw new DucatsException("","io");
        } catch (SecurityException e) {
            throw new DucatsException("security");
        }
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

    private ArrayList<String> formatListToString(ArrayList<Song> list) {
        ArrayList<String> result = new ArrayList<>();
        for (Song song: list) {
            result.add(song.toString());
        }
        return result;
    }

    /**
     * Takes in an ArrayList of Strings, each representing a song, and stores it in the .txt file.
     *
     * @param songs the ArrayList of songs as Strings
     * @throws DucatsException in the case of IO exceptions
     */
    private void writeStringsToFile(ArrayList<String> songs) throws DucatsException {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filepath);
            StringBuilder sb = new StringBuilder();
            for (String song: songs) {
                sb.append(song);
                sb.append(System.lineSeparator());
            }
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new DucatsException("","io");
        }
    }

    /**
     * Returns an ArrayList of Strings, with each String corresponding to a line in the file.
     *
     * @return the lines as an ArrayList of Strings
     * @throws DucatsException in the case of IO exceptions
     */
    private ArrayList<String> readStringsFromFile() throws DucatsException {
        // reads file and returns an ArrayList of lines
        ArrayList<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine();
                if (nextLine.equals("")) {
                    break;
                } else {
                    result.add(nextLine);
                }
            }
        } catch (Exception e) {
            System.out.println("que?");
            throw new DucatsException("", "io");
        }
        return result;
    }

    /**
     * A function that gets from a text file and loads into the song list.
     *
     * @param songList - This is the list of songs by the user.
     */

    public void loadToList(SongList songList) throws DucatsException {
        // loads data into list
        ArrayList<String> data = readStringsFromFile();
        for (String line: data) {
            songList.add(convertSongFromString(line));
        }

    }

    // twinkle [[UAs;UAs],[UA;UA],[UAs;UAs],[UA;UA],[UAs;UAs],[UA;UA],[UAs;UAs],[UA;UA]]
    /**
     * A function that converts a string into a Song object.
     *
     * @param s this is the string to be converted to a song.
     */
    public Song convertSongFromString(String s) throws DucatsException {
        String[] sections = s.split(" ");
        if (sections.length == 1) {
            throw new DucatsException("io","");
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

    private Bar convertBarFromString(String s, int barIndex) throws DucatsException {
        String barData = s.substring(1, s.length() - 1);
        String[] rawChords = barData.split(",");
        ArrayList<Chord> chords = new ArrayList<>();
        for (String rawChord: rawChords) {
            chords.add(convertChordFromString(rawChord));
        }
        Bar bar = new Bar(barIndex, chords);
        return bar;
    }

    private Chord convertChordFromString(String s) throws DucatsException {
        String noteData = s.substring(1, s.length() - 1);
        String[] rawNotes = noteData.split(";");
        ArrayList<Note> notes = new ArrayList<>();
        for (String rawNote: rawNotes) {
            notes.add(convertNoteFromString(rawNote));
        }
        Chord chord = new Chord(notes);
        return chord;
    }

    private Note convertNoteFromString(String s) throws DucatsException {
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
            throw new DucatsException("data","");
        }
        return new Note(duration, pitch, isStart);
    }

    /**
     * Updates the .txt file with the current data found within the SongList.
     *
     * @param songList the SongList object containing the list of Song objects
     * @throws DucatsException in the case of IO exceptions
     */
    public void updateFile(SongList songList) throws DucatsException {
        // System.out.println(songList.getSongList().get(0).toString());
        writeStringsToFile(formatListToString(songList.getSongList()));
    }
}
