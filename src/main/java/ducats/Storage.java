package ducats;

import ducats.components.Bar;
import ducats.components.Chord;
import ducats.components.Group;
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
    protected File dataFolder;
    private String dirpath;

    private String fileDelimiter = System.getProperty("file.separator");
    //private String currentDir = System.getProperty("user.dir");


    /**
     * Constructor for the duke.Storage class, used to store songs into persistent storage in the form of a .txt file.
     *
     * @param dirpath the String object representing the path to the folder being used to store the task list.
     */
    public Storage(String dirpath) {
        this.dirpath = dirpath;
        dataFolder = new File(dirpath);
    }

    /**
     * Performs the initial creation of the directory to be used to contain the .txt files that contain the song data,
     * and returns true if the directory was indeed created, and false if a current directory of the same name was
     * found.
     *
     * @return a boolean corresponding to whether a new directory has been created or not
     * @throws DucatsException in the case of Security exceptions
     */
    boolean initialize() throws DucatsException {
        try {
            return dataFolder.mkdir();
        } catch (SecurityException e) {
            throw new DucatsException("security");
        }
    }

    /**
     * Takes in an ArrayList of Strings, each representing a line in a song, and stores it in the specified
     * .txt file.
     *
     * @param song the ArrayList of the lines of the song
     * @throws DucatsException in the case of IO exceptions
     */
    private void writeStringsToFile(ArrayList<String> song, String filepath) throws DucatsException {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(filepath);
            StringBuilder sb = new StringBuilder();
            for (String line: song) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            fileWriter.write(sb.toString());
            fileWriter.close();
        } catch (IOException e) {
            throw new DucatsException("","io");
        }
    }

    /**
     * Returns an ArrayList of Strings, with each String corresponding to a line in the .txt file specified through
     * the filepath provided.
     *
     * @param filepath the filepath of the .txt file to be read from
     * @return the lines as an ArrayList of Strings
     * @throws DucatsException in the case of IO exceptions
     */
    private ArrayList<String> readStringsFromFile(String filepath) throws DucatsException {
        // reads file and returns an ArrayList of lines
        ArrayList<String> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filepath))) {
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
     * A function that reads data from a .txt file and loads the song into the song list.
     *
     * @param songList the SongList object containing the list of songs
     * @throws DucatsException in the case of IO exceptions
     */
    private void loadSongToList(SongList songList, String filepath) throws DucatsException {
        // loads data into list
        ArrayList<String> data = readStringsFromFile(filepath);
        songList.add(convertSongFromString(data));

    }

    /**
     * Takes in the SongList object containing the list of songs and loads the directory containing the songs in the
     * form of .txt files.
     *
     * @param songList the SongList object containing the list of songs
     * @throws DucatsException in the case of IO exceptions
     */
    public void loadToList(SongList songList) throws DucatsException {
        for (final File songFile: dataFolder.listFiles()) {
            String filepath = dirpath + fileDelimiter + songFile.getName();
            System.out.println(filepath);
            loadSongToList(songList, filepath);
        }
    }

    /**
     * A function that converts an ArrayList of Strings into a Song object.
     *
     * @param s the ArrayList of Strings to be converted to a song.
     * @throws DucatsException in the case of IO exceptions
     */
    private Song convertSongFromString(ArrayList<String> s) throws DucatsException {
        if (s.size() == 0) {
            throw new DucatsException("io","");
        }
        String[] sections = s.get(0).split(" ");
        String name = sections[0];
        String key = sections[1];
        int tempo = Integer.parseInt(sections[2]);
        Song song = new Song(name, key, tempo);
        int i = 1;
        if (s.size() > 1) {
            while (i < s.size() && !s.get(i).equals("groups:")) {
                song.addBar(convertBarFromString(s.get(i), i));
                i++;
            }
            for (int j = i + 1; j < s.size(); j++) {
                song.addGroup(convertGroupFromString(s.get(j)));
            }
        }
        return song;
    }

    private Group convertGroupFromString(String s) throws DucatsException {
        String[] rawBars = s.split(" ");
        String name = rawBars[0];
        ArrayList<Bar> bars = new ArrayList<>();
        for (int i = 1; i < rawBars.length; i++) {
            bars.add(convertBarFromString(rawBars[i], -1));
        }
        Group group = new Group(name, bars);
        return group;
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
     * Updates the folder containing the .txt files with the current data found within the SongList.
     *
     * @param songList the SongList object containing the list of Song objects
     * @throws DucatsException in the case of IO exceptions
     */
    public void updateFile(SongList songList) throws DucatsException {
        for (Song song: songList.getSongList()) {
            String filepath = dirpath + fileDelimiter + song.getName() + ".txt";
            System.out.println(filepath);
            createFile(filepath);
            writeStringsToFile(song.toStringArrayList(), filepath);
        }
    }

    /**
     * If the file is not found, creates the file according to the filepath provided.
     *
     * @param filepath the filepath of the file to be created, as a String
     * @throws DucatsException in the case of IO exceptions
     */
    private void createFile(String filepath) throws DucatsException {
        File file = new File(filepath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new DucatsException("","io");
        }
    }
}
