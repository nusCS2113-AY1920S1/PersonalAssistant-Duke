package ducats;

import ducats.components.Bar;
import ducats.components.Chord;
import ducats.components.Group;
import ducats.components.Note;
import ducats.components.Pitch;
import ducats.components.Song;

import java.util.ArrayList;

/**
 * StorageParser is a class used for the parsing of the data found within persistent storage.
 */
public class StorageParser {

    //@@author rohan-av
    /**
     * A function that converts an ArrayList of Strings into a Song object.
     *
     * @param s the ArrayList of Strings to be converted to a song.
     * @throws DucatsException in the case of IO exceptions
     */
    public Song convertSongFromString(ArrayList<String> s) throws DucatsException {
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
     * Returns an ArrayList of Strings representing the Song object provided.
     *
     * @param song the Song whose ArrayList representation is to be obtained
     * @return the song represented as an ArrayList of Strings
     */
    public ArrayList<String> getArrayList(Song song) {
        return song.toStringArrayList();
    }
}
