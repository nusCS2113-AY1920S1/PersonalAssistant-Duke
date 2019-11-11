package ducats.commands;

import ducats.DucatsException;
import ducats.Storage;
import ducats.Ui;
import ducats.components.Bar;
import ducats.components.Chord;
import ducats.components.Group;
import ducats.components.Note;
import ducats.components.Pitch;
import ducats.components.Song;
import ducats.components.SongList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class displays songs in a musical song sheet format for visual representation
 * of the song being created.
 */
public class AsciiCommand extends Command<SongList> {

    //@@author Samuel787
    private static final String MUSIC_8 = "*";
    private static final String MUSIC_6 = "$.";
    private static final String MUSIC_4 = "$";
    private static final String MUSIC_3 = "@.";
    private static final String MUSIC_2 = "@";
    private static final String MUSIC_1 = "!";
    private static final String REST_8 = "#";
    private static final String REST_6 = "%.";
    private static final String REST_4 = "%";
    private static final String REST_3 = "^.";
    private static final String REST_2 = "^";
    private static final String REST_1 = "&";
    private static final String CONT = "-";

    private static final int MAX_ROWS = 15;
    private static final int NUM_CHORDS_IN_BAR = 8;

    //symbol to indicate start of musical note in songsheet
    private static final String START_MUSICAL_NOTE_SONGSHEET = "@";
    //symbol to indicate continuation of musical note in songsheet
    private static final String CONTINUE_MUSICAL_NOTE_SONGSHEET = "p";
    //symbol to indicate absence of note and rest as dash in songsheet
    private static final String LINE_SONGSHEET = "-";
    //symbol to indicate absence of note and rest as blank in songsheet
    private static final String BLANK_SONGSHEET = " ";
    //symbol to indicate start of rest in the songsheet
    private static final String START_REST_SONGSHEET = "R";
    //symbol to indicate continuation of rest in the songsheet
    private static final String CONTINUE_REST_SONGSHEET = "X";

    //number of string after which to wrap the display of ascii song to new line
    private static final int WRAP_AFTER_NUM_STRINGS = 72;

    private String message;


    //ArrayList containing rows that are lines on a musical songsheet. Row starts from 0
    private static final ArrayList<Integer> linedRows = new ArrayList<>(Arrays.asList(3, 5, 7, 9, 11));
    //ArrayList containing rows that are non-lines on a musical songsheet.
    private static final ArrayList<Integer> nonLinedRows = new ArrayList<>(Arrays.asList(0, 1, 2, 4, 6, 8, 10, 12, 13,
            14));
    //HashMap that stores the row of corresponding pitch. Key will be pitch and value will be row
    private static final HashMap<Pitch, Integer> pitchRows = new HashMap<>();
    //dotted durations in music theory have relative durations of 3 and 6 units
    private static final ArrayList<Integer> dottedDurations = new ArrayList<>(Arrays.asList(3, 6));

    static {
        pitchRows.put(Pitch.UPPER_C, 0);
        pitchRows.put(Pitch.UPPER_B, 1);
        pitchRows.put(Pitch.UPPER_A, 2);
        pitchRows.put(Pitch.UPPER_G, 3);
        pitchRows.put(Pitch.UPPER_F, 4);
        pitchRows.put(Pitch.UPPER_E, 5);
        pitchRows.put(Pitch.UPPER_D, 6);
        pitchRows.put(Pitch.MIDDLE_C, 7);
        pitchRows.put(Pitch.LOWER_B, 8);
        pitchRows.put(Pitch.LOWER_A, 9);
        pitchRows.put(Pitch.LOWER_G, 10);
        pitchRows.put(Pitch.LOWER_F, 11);
        pitchRows.put(Pitch.LOWER_E, 12);
        pitchRows.put(Pitch.LOWER_D, 13);
        pitchRows.put(Pitch.LOWER_C, 14);
        pitchRows.put(Pitch.REST, 7);
    }

    /**
     * Constructor for the command to print out a bar, group or song in ASCII.
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AsciiCommand(String message) {
        this.message = message.trim();
    }

    /**
     * Prints out a bar, group or song in ASCII to represent the song sheet for that component.
     *
     * @param songList the duke.TaskList or duke.components.SongList object that contains the task list in use
     * @param ui       the Ui object responsible for the reading of user input and the display of
     *                 the responses
     * @param storage  the Storage object used to read and manipulate the .txt file
     * @return the string corresponding to the ASCII song sheet as requested by user
     * @throws DucatsException if an exception occurs in the parsing of the message or in IO
     */
    @Override
    public String execute(SongList songList, Ui ui, Storage storage) throws DucatsException {
        String result;
        if (message.length() < 6 || !message.substring(0, 6).equals("ascii ")) {
            throw new DucatsException(message);
        }
        try {
            message = message.substring(6).trim();
            String command = message.split(" ", 2)[0];
            if (command.equals("bar")) {
                String barNumberString = message.split(" ", 2)[1].trim();
                int barNum = Integer.parseInt(barNumberString);
                Song barAsSong = getBarAsSong(songList, barNum, message);
                result = printSongAscii(barAsSong);
            } else if (command.equals("group")) {
                String groupName = message.split(" ", 2)[1].trim();
                Song groupSong = getGroupAsSong(songList, groupName, message);
                result = printSongAscii(groupSong);
            } else if (command.equals("song")) {
                String songName = message.split(" ", 2)[1].trim();
                Song song = findSong(songList, songName);
                result = printSongAscii(song);
            } else {
                //wrong command
                throw new DucatsException(message, "AsciiCommand");
            }
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DucatsException(message, "AsciiCommand");
        }
        return result;

    }

    private Song getBarAsSong(SongList songList, int barNum, String message) throws DucatsException {
        //bar index for user is assumed to start from 1
        if (barNum > songList.getSongIndex(songList.getActiveIndex()).getNumBars() || barNum < 1) {
            throw new DucatsException(message, "no_index");
        }
        Song tempSong = new Song("Test song", "C-Major", 120);
        Bar displayBar = songList.getSongIndex(songList.getActiveIndex()).getBars().get(barNum - 1);
        tempSong.addBar(displayBar);
        return tempSong;
    }

    private Song getGroupAsSong(SongList songList, String groupName, String message) throws DucatsException {
        int activeSongIndex = songList.getActiveIndex();
        Song activeSong = songList.getSongIndex(activeSongIndex);
        Group group = activeSong.findGroup(groupName);
        Song groupAsSong = new Song("temp", "aminor", 120);
        if (group == null) {
            throw new DucatsException(message, "group_not_found");
        } else {
            //wrap the group as a song
            ArrayList<Bar> bars = group.getBars();
            for (Bar bar : bars) {
                groupAsSong.addBar(bar);
            }
        }
        return groupAsSong;
    }

    private Song findSong(SongList songList, String songName) throws DucatsException {
        ArrayList<Song> songs = songList.findSong(songName);
        Song song;
        if (songs.size() == 1) {
            song = songs.get(0);
        } else {
            //song does not exist
            throw new DucatsException(message, "no_song");
        }
        return song;
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * Prints out the selected bar in ASCII format to represnt the song sheet.
     *
     * @param bar the bar that user wants to print in ASCII
     */
    public static String printBarAscii(Bar bar) {
        Song tempSong = new Song("Test Song", "C-Major", 120);
        tempSong.addBar(bar);
        return printSongAscii(tempSong);
    }

    /**
     * Prints out the selected verse in ASCII format to represent the song sheet.
     *
     * @param group the verse that user wants to print in ASCII
     */
    public static String printGroupAscii(Group group) {
        Song tempSong = new Song("Test Song", "C-Major", 120);
        for (int i = 0; i < group.size(); i++) {
            tempSong.addBar(group.get(i));
        }
        return printSongAscii(tempSong);
    }

    private static String wrapContent(String str) {
        String[] strings = str.split("\n");
        ArrayList<ArrayList<String>> rows = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            rows.add(new ArrayList<>());
        }
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].substring(4);
            for (int j = 0; j < strings[i].length(); j++) {
                if (j % WRAP_AFTER_NUM_STRINGS == 0) {
                    if (j + WRAP_AFTER_NUM_STRINGS < strings[i].length()) {
                        rows.get(i).add(strings[i].substring(j, j + WRAP_AFTER_NUM_STRINGS));
                    } else {
                        rows.get(i).add(strings[i].substring(j));
                    }
                }
            }
        }

        StringBuilder result = new StringBuilder();
        int count = rows.get(0).size();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < 15; j++) {
                switch (j) {
                case 0:
                    result.append("UC: ");
                    break;
                case 1:
                    result.append("UB: ");
                    break;
                case 2:
                    result.append("UA: ");
                    break;
                case 3:
                    result.append("UG: ");
                    break;
                case 4:
                    result.append("UF: ");
                    break;
                case 5:
                    result.append("UE: ");
                    break;
                case 6:
                    result.append("UD: ");
                    break;
                case 7:
                    result.append("MC: ");
                    break;
                case 8:
                    result.append("LB: ");
                    break;
                case 9:
                    result.append("LA: ");
                    break;
                case 10:
                    result.append("LG: ");
                    break;
                case 11:
                    result.append("LF: ");
                    break;
                case 12:
                    result.append("LE: ");
                    break;
                case 13:
                    result.append("LD: ");
                    break;
                case 14:
                    result.append("LC: ");
                    break;
                default:
                    break;
                }

                result.append(rows.get(j).get(i) + "\n");
                if (j == 14) {
                    //wrapping takes place here when song is too long
                    result.append("\n");
                }
            }
        }
        return result.toString();
    }

    /**
     * Prints out the selected song in ASCII format to represent the song sheet.
     * @param song the song that user wants to print in ASCII
     */
    public static String printSongAscii(Song song) {
        ArrayList<ArrayList<String>> songAscii = secondLayerParseAscii(firstLayerParseAscii(song));
        StringBuilder stringResult = new StringBuilder();
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < songAscii.get(i).size(); j++) {
                if (j > 8 && (j - 1) % 8 == 0) {
                    if ((i > 2 && i < 12)) {
                        stringResult.append("|");
                    } else {
                        stringResult.append(" ");
                    }
                }
                stringResult.append(songAscii.get(i).get(j));
            }
            stringResult.append("\n");
        }
        return wrapContent(stringResult.toString());
    }


    private static ArrayList<ArrayList<String>> firstLayerParseAscii(Song song) {
        ArrayList<ArrayList<String>> songAscii = new ArrayList<>();
        initialiseFirstLayerParse(songAscii, song);
        return songAscii;
    }

    private static void initialiseFirstLayerParse(ArrayList<ArrayList<String>> songAscii, Song song) {
        for (int i = 0; i < 15; i++) {
            songAscii.add(i, new ArrayList<>());
        }
        songAscii.get(0).add("UC: ");
        songAscii.get(1).add("UB: ");
        songAscii.get(2).add("UA: ");
        songAscii.get(3).add("UG: ");
        songAscii.get(4).add("UF: ");
        songAscii.get(5).add("UE: ");
        songAscii.get(6).add("UD: ");
        songAscii.get(7).add("MC: ");
        songAscii.get(8).add("LB: ");
        songAscii.get(9).add("LA: ");
        songAscii.get(10).add("LG: ");
        songAscii.get(11).add("LF: ");
        songAscii.get(12).add("LE: ");
        songAscii.get(13).add("LD: ");
        songAscii.get(14).add("LC: ");
        ArrayList<Bar> bars = song.getBars();
        for (Bar bar : bars) {
            ArrayList<ArrayList<String>> barAscii = getBarAscii(bar);
            generateBarAscii(songAscii, barAscii);
        }
    }

    private static void generateBarAscii(ArrayList<ArrayList<String>> songAscii,
                                         ArrayList<ArrayList<String>> barAscii) {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < barAscii.get(col).size(); col++) {
                songAscii.get(row).add(barAscii.get(row).get(col));
            }
        }
    }

    private static ArrayList<ArrayList<String>> secondLayerParseAscii(ArrayList<ArrayList<String>> rawSongAscii) {
        ArrayList<ArrayList<String>> resultAscii = new ArrayList<>();
        for (ArrayList<String> arr : rawSongAscii) {
            resultAscii.add(new ArrayList<>(arr));
        }
        for (int i = 0; i < MAX_ROWS; i++) {
            if (i == pitchRows.get(Pitch.UPPER_C) || i == pitchRows.get(Pitch.LOWER_E)) {
                setRowsUpperCUpperE(resultAscii, rawSongAscii, i);
            } else if (i == pitchRows.get(Pitch.UPPER_B) || i == pitchRows.get(Pitch.LOWER_D)) {
                setRowsUpperBLowerD(resultAscii, rawSongAscii, i);
            } else if (i == pitchRows.get(Pitch.MIDDLE_C)) {
                setRowsMiddleC(resultAscii, rawSongAscii, i);
            } else if (linedRows.contains(i)) {
                setLinedRows(resultAscii, rawSongAscii, i);
            } else {
                setNonLinedRows(resultAscii, rawSongAscii, i);
            }
        }
        return resultAscii;
    }


    private static void setDottedNotes(int duration, ArrayList<ArrayList<String>> resultAscii, int row, int startPos,
                                       String symbol) {
        if (dottedDurations.contains(duration)) {
            String part1 = Character.toString(symbol.charAt(0));
            String part2 = Character.toString(symbol.charAt(1));
            resultAscii.get(row).set(startPos, part1);
            resultAscii.get(row).set(startPos + 1, part2);
        } else {
            resultAscii.get(row).set(startPos, symbol);
        }
    }

    private static void setRowsUpperCUpperE(ArrayList<ArrayList<String>> resultAscii,
                                            ArrayList<ArrayList<String>> rawSongAscii, int row) {
        int length = rawSongAscii.get(row).size();
        for (int col = 1; col < length; col++) {
            if (rawSongAscii.get(row).get(col).equals(START_MUSICAL_NOTE_SONGSHEET)) {
                int startPos = col;
                int counter = 1;
                col++;
                while (col < length && rawSongAscii.get(row).get(col).equals(CONTINUE_MUSICAL_NOTE_SONGSHEET)) {
                    resultAscii.get(row).set(col, BLANK_SONGSHEET);
                    counter++;
                    col++;
                }
                String symbol = getSymbol(false, counter);
                setDottedNotes(counter, resultAscii, row, startPos, symbol);
                if (startPos != 1 && (startPos - 1) % NUM_CHORDS_IN_BAR != 0) {
                    resultAscii.get(row + 1).set(startPos - 1, "-");
                }
                resultAscii.get(row + 1).set(startPos, "-");
                if (startPos != length - 1 && (startPos + 1) % NUM_CHORDS_IN_BAR != 1) {
                    resultAscii.get(row + 1).set(startPos + 1, "-");
                }
                col--;
            }
        }
    }

    private static void setRowsUpperBLowerD(ArrayList<ArrayList<String>> resultAscii,
                                            ArrayList<ArrayList<String>> rawSongAscii, int row) {
        int length = rawSongAscii.get(row).size();
        for (int j = 1; j < length; j++) {
            if (rawSongAscii.get(row).get(j).equals(START_MUSICAL_NOTE_SONGSHEET)) {
                int startPos = j;
                int counter = 1;
                j++;
                while (j < length && rawSongAscii.get(row).get(j).equals(CONTINUE_MUSICAL_NOTE_SONGSHEET)) {
                    if (resultAscii.get(row).get(j).equals(CONTINUE_MUSICAL_NOTE_SONGSHEET)) {
                        resultAscii.get(row).set(j, BLANK_SONGSHEET);
                    }
                    counter++;
                    j++;
                }
                String symbol = getSymbol(false, counter);
                setDottedNotes(counter, resultAscii, row, startPos, symbol);
                if (startPos % MAX_ROWS != 1 && resultAscii.get(row).get(startPos - 1).equals(BLANK_SONGSHEET)) {
                    resultAscii.get(row).set(startPos - 1, LINE_SONGSHEET);
                }
                if (startPos != length - 1 && resultAscii.get(row).get(startPos + 1).equals(BLANK_SONGSHEET)) {
                    resultAscii.get(row).set(startPos + 1, LINE_SONGSHEET);
                }
                j--;
            }
        }
    }

    private static void setRowsMiddleC(ArrayList<ArrayList<String>> resultAscii,
                                       ArrayList<ArrayList<String>> rawSongAscii, int i) {
        int length = rawSongAscii.get(i).size();
        for (int j = 1; j < length; j++) {
            if (rawSongAscii.get(i).get(j).equals(START_MUSICAL_NOTE_SONGSHEET)) {
                int startPos = j;
                int counter = 1;
                j++;
                while (j < length && rawSongAscii.get(i).get(j).equals(CONTINUE_MUSICAL_NOTE_SONGSHEET)) {
                    resultAscii.get(i).set(j, LINE_SONGSHEET);
                    counter++;
                    j++;
                }
                String symbol = getSymbol(false, counter);
                setDottedNotes(counter, resultAscii, i, startPos, symbol);
                j--;
            } else if (rawSongAscii.get(i).get(j).equals(START_REST_SONGSHEET)) {
                int startPos = j;
                int counter = 1;
                j++;
                while (j < length && rawSongAscii.get(i).get(j).equals(CONTINUE_REST_SONGSHEET)) {
                    resultAscii.get(i).set(j, LINE_SONGSHEET);
                    counter++;
                    j++;
                }
                String symbol = getSymbol(true, counter);
                setDottedNotes(counter, resultAscii, i, startPos, symbol);
                j--;
            }
        }
    }

    private static void setLinedRows(ArrayList<ArrayList<String>> resultAscii,
                                     ArrayList<ArrayList<String>> rawSongAscii, int i) {
        int length = rawSongAscii.get(i).size();
        for (int j = 1; j < length; j++) {
            if (rawSongAscii.get(i).get(j).equals(START_MUSICAL_NOTE_SONGSHEET)) {
                int startPos = j;
                int counter = 1;
                j++;
                while (j < length && rawSongAscii.get(i).get(j).equals(CONTINUE_MUSICAL_NOTE_SONGSHEET)) {
                    resultAscii.get(i).set(j, LINE_SONGSHEET);
                    counter++;
                    j++;
                }
                String symbol = getSymbol(false, counter);
                setDottedNotes(counter, resultAscii, i, startPos, symbol);
                j--;
            }
        }
    }

    private static void setNonLinedRows(ArrayList<ArrayList<String>> resultAscii,
                                        ArrayList<ArrayList<String>> rawSongAscii, int i) {
        int length = rawSongAscii.get(i).size();
        for (int j = 1; j < length; j++) {
            if (rawSongAscii.get(i).get(j).equals(START_MUSICAL_NOTE_SONGSHEET)) {
                int startPos = j;
                int counter = 1;
                j++;
                while (j < length && rawSongAscii.get(i).get(j).equals(CONTINUE_MUSICAL_NOTE_SONGSHEET)) {
                    resultAscii.get(i).set(j, BLANK_SONGSHEET);
                    counter++;
                    j++;
                }
                String symbol = getSymbol(false, counter);
                setDottedNotes(counter, resultAscii, i, startPos, symbol);
                j--;
            }
        }
    }

    /**
     * The fact that a non-start note does not know when it's corresponding
     * start note occurs results in a loss of information. As a result, a note will have no sense of its duration
     * at an object level.
     * To retrieve this information, this method does an initial level parsing to map out all the the notes from which
     * the duration of each notes can be retrieved.
     * Hence, general symbols are used to map out the start notes and continuation notes for Musical notes and
     * Rest.
     *
     * @param bar the bar to be parsed
     * @return a 2D ArrayList containing the layout of all the notes and rest
     */
    private static ArrayList<ArrayList<String>> getBarAscii(Bar bar) {
        ArrayList<Chord> chords = bar.getChords();
        ArrayList<ArrayList<String>> barAscii = new ArrayList<>();
        for (int i = 0; i < MAX_ROWS; i++) {
            generateBareSheetRow(i, barAscii);
        }
        for (int i = 0; i < chords.size(); i++) {
            ArrayList<Note> notes = chords.get(i).getNotes();
            generateChordSheet(notes, barAscii, i);
        }
        return barAscii;
    }

    //used by getBarAscii
    private static void generateBareSheetRow(int rowNum, ArrayList<ArrayList<String>> barAscii) {
        barAscii.add(new ArrayList<>());
        if (linedRows.contains(rowNum)) {
            for (int j = 0; j < NUM_CHORDS_IN_BAR; j++) {
                barAscii.get(rowNum).add(j, LINE_SONGSHEET);
            }
        } else {
            for (int j = 0; j < NUM_CHORDS_IN_BAR; j++) {
                barAscii.get(rowNum).add(j, BLANK_SONGSHEET);
            }
        }
    }

    //used by getBarAscii
    private static void generateChordSheet(ArrayList<Note> notes, ArrayList<ArrayList<String>> barAscii, int chordNum) {
        for (Note note : notes) {
            if (note.isStart() && note.getPitch() != Pitch.REST) {
                setStartNonRestNotes(note, chordNum, barAscii);
            } else if (note.isStart() && note.getPitch() == Pitch.REST) {
                setStartRestNotes(chordNum, barAscii);
            } else if (!note.isStart() && note.getPitch() != Pitch.REST) {
                setNonStartNonRestNotes(note, chordNum, barAscii);
            } else {
                setNonStartRestNotes(chordNum, barAscii);
            }
        }
    }

    //used by generateChordSheet
    private static void setStartNonRestNotes(Note note, int chordNum, ArrayList<ArrayList<String>> barAscii) {
        int rowOfPitch = pitchRows.get(note.getPitch());
        barAscii.get(rowOfPitch).set(chordNum, START_MUSICAL_NOTE_SONGSHEET);
    }

    //used by generateChordSheet
    private static void setStartRestNotes(int chordNum, ArrayList<ArrayList<String>> barAscii) {
        int rowOfRest = pitchRows.get(Pitch.REST);
        for (int k = 0; k < MAX_ROWS; k++) {
            if (k == rowOfRest) {
                barAscii.get(rowOfRest).set(chordNum, START_REST_SONGSHEET);
            } else if (linedRows.contains(k)) {
                barAscii.get(k).set(chordNum, LINE_SONGSHEET);
            } else {
                barAscii.get(k).set(chordNum, BLANK_SONGSHEET);
            }
        }
    }

    //used by generateChordSheet
    private static void setNonStartNonRestNotes(Note note, int chordNum, ArrayList<ArrayList<String>> barAscii) {
        int rowOfPitch = pitchRows.get(note.getPitch());
        barAscii.get(rowOfPitch).set(chordNum, CONTINUE_MUSICAL_NOTE_SONGSHEET);
    }

    //used by generateChordSheet
    private static void setNonStartRestNotes(int chordNum, ArrayList<ArrayList<String>> barAscii) {
        int rowOfRest = pitchRows.get(Pitch.REST);
        for (int k = 0; k < MAX_ROWS; k++) {
            if (k == rowOfRest) {
                barAscii.get(rowOfRest).set(chordNum, CONTINUE_REST_SONGSHEET);
            } else if (linedRows.contains(k)) {
                barAscii.get(k).set(chordNum, LINE_SONGSHEET);
            } else {
                barAscii.get(k).set(chordNum, BLANK_SONGSHEET);
            }
        }
    }

    private static String getSymbol(boolean isRest, int duration) {
        if (isRest) {
            switch (duration) {
            case 1:
                return REST_1;
            case 2:
                return REST_2;
            case 3:
                return REST_3;
            case 4:
                return REST_4;
            case 6:
                return REST_6;
            case 8:
                return REST_8;
            default:
                return "";
            }
        } else {
            switch (duration) {
            case 1:
                return MUSIC_1;
            case 2:
                return MUSIC_2;
            case 3:
                return MUSIC_3;
            case 4:
                return MUSIC_4;
            case 6:
                return MUSIC_6;
            case 8:
                return MUSIC_8;
            default:
                return "";
            }
        }
    }

    //@@author rohan-av

    /**
     * Returns an integer corresponding to the duration, tempo and time signature if the command starts a metronome.
     * Else, returns an array containing -1.
     *
     * @return the integer array corresponding to the parameters of the Metronome class
     */
    @Override
    public int[] startMetronome() {
        return new int[]{-1, -1, -1, -1};
    }
}
