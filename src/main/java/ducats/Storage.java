package ducats;

import ducats.components.Song;
import ducats.components.SongList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//@@author rohan-av
/**
 * A class to implement persistent storage of the task list using a .txt file.
 */
public class Storage {

    private File dataFolder;
    private String dirpath;
    private StorageParser storageParser;

    private String fileDelimiter = System.getProperty("file.separator");
    //private static final DucatsLogger LOGGER = new DucatsLogger();

    /**
     * Constructor for the duke.Storage class, used to store songs into persistent storage in the form of a .txt file.
     *
     * @param dirpath the String object representing the path to the folder being used to store the task list.
     */
    public Storage(String dirpath) {
        this.dirpath = dirpath;
        dataFolder = new File(dirpath);
        storageParser = new StorageParser();
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
            DucatsLogger.finest("Successfully wrote to file " + filepath);
            fileWriter.close();
        } catch (IOException e) {
            DucatsLogger.severe("An IO exception has occurred in writeStringsToFile()");
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
                    DucatsLogger.finest("Successfully read from file " + filepath);
                    break;
                } else {
                    result.add(nextLine);
                }
            }
        } catch (Exception e) {
            DucatsLogger.severe("An IO exception has occurred in readStringsFromFile()");
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
        songList.add(storageParser.convertSongFromString(data));
    }

    /**
     * Takes in the SongList object containing the list of songs and loads the directory containing the songs in the
     * form of .txt files.
     *
     * @param songList the SongList object containing the list of songs
     * @throws DucatsException in the case of IO exceptions
     */
    public void loadToList(SongList songList) throws DucatsException {
        assert dataFolder.listFiles() != null;
        for (final File songFile: dataFolder.listFiles()) {
            String filepath = dirpath + fileDelimiter + songFile.getName();
            loadSongToList(songList, filepath);
        }
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
            createFile(filepath);
            writeStringsToFile(storageParser.getArrayList(song), filepath);
        }
        DucatsLogger.info("Folder updated with current song data");
    }

    private void createFile(String filepath) throws DucatsException {
        File file = new File(filepath);
        try {
            if (file.createNewFile()) {
                DucatsLogger.info("A new file has been created: " + filepath);
            }
        } catch (IOException e) {
            DucatsLogger.severe("An IO exception has occurred in file creation for " + filepath);
            throw new DucatsException("","io");
        }
    }
}
