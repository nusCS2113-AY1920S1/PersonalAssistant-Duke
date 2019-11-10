package ducats.components;

import ducats.components.Bar;
import ducats.components.Chord;
import ducats.components.Note;
import ducats.components.Pitch;
import ducats.components.Song;
import ducats.components.SongList;
import ducats.DucatsException;
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

public class SongConverter {


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
}
