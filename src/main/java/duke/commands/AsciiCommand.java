package duke.commands;

import duke.components.*;
import java.util.ArrayList;

public class AsciiCommand {

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

    public static ArrayList<String> convertSongToAscii(Song song){
        ArrayList<String> asciiResult = new ArrayList<>();
        ArrayList<Bar> bars = song.getBars();
        for(Bar bar: bars){
            ArrayList<Chord> chords = bar.getChords();
            for(Chord chord : chords){
                ArrayList<Note> notes = chord.getNotes();
                for(Note note : notes){
                    Pitch pitch = note.getPitch();
                    String str = noteToAscii(note);
                    switch(pitch){
                        case REST:
                            for(int i = 0; i < 15; i++){
                                asciiResult.add(str);
                            }
                            break;
                        case UPPER_C:
                            asciiResult.add(0, str);
                            break;
                        case UPPER_B:
                            asciiResult.add(1, str);
                            break;
                        case UPPER_A:
                            asciiResult.add(2, str);
                            break;
                        case UPPER_G:
                            asciiResult.add(3, str);
                            break;
                        case UPPER_F:
                            asciiResult.add(4, str);
                            break;
                        case UPPER_E:
                            asciiResult.add(5, str);
                            break;
                        case UPPER_D:
                            asciiResult.add(6, str);
                            break;
                        case MIDDLE_C:
                            asciiResult.add(7, str);
                            break;
                        case LOWER_B:
                            asciiResult.add(8, str);
                            break;
                        case LOWER_A:
                            asciiResult.add(9, str);
                            break;
                        case LOWER_G:
                            asciiResult.add(10, str);
                            break;
                        case LOWER_F:
                            asciiResult.add(11, str);
                            break;
                        case LOWER_E:
                            asciiResult.add(12, str);
                            break;
                        case LOWER_D:
                            asciiResult.add(13, str);
                            break;
                        case LOWER_C:
                            asciiResult.add(14, str);
                            break;
                    }
                }
            }
        }
        return asciiResult;
    }

    private static String noteToAscii(Note note){
        Pitch pitch = note.getPitch();
        boolean isRest = (pitch == Pitch.REST);
        int relativeDuration = note.getRelativeUnitDuration();
        boolean isStart = note.isStart();
        if(isStart){
            if(isRest){
                switch(relativeDuration){
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
                switch(relativeDuration){
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
        } else {
            return CONT;
        }

    }
}
