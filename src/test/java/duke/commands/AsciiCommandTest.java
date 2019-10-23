package duke.commands;

import duke.components.Bar;
import duke.components.Chord;
import duke.components.Note;
import duke.components.Song;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class AsciiCommandTest {

    Song song = new Song("Test Song", "C-Major", 120);
    Chord chord_1 = new Chord();
    Chord chord_2 = new Chord();
    Chord chord_3 = new Chord();
    Chord chord_4 = new Chord();
    Chord chord_5 = new Chord();
    Chord chord_6 = new Chord();
    Chord chord_7 = new Chord();
    Chord chord_8 = new Chord();

    Bar bar_1 = new Bar(0, "4_UA 4_UA");
    Bar bar_2 = new Bar(1, "2_UA 2_UB");
    Bar bar_3 = new Bar(2, "1_UC");
    Bar bar_4 = new Bar(3, "1_REST");
    Bar bar_5 = new Bar(4, "2_MC 2_MC");
    Bar bar_6 = new Bar(5, "4_LE 4_LD 4_LC");


    @Test
    public void convertSongToAscii(){
        song.addBar(bar_1);
        song.addBar(bar_2);
        song.addBar(bar_3);
        song.addBar(bar_4);
        song.addBar(bar_5);
        song.addBar(bar_6);

        AsciiCommand.printSongAscii(song);
    }

//    @Test
//    public void convertSongToAscii(){
//        song.addBar(bar_1);
//        song.addBar(bar_2);
//        song.addBar(bar_3);
//        song.addBar(bar_4);
//        song.addBar(bar_5);
//        ArrayList<Chord> chords = bar_1.getChords();
//        for(Chord chord : chords){
//            ArrayList<Note> notes = chord.getNotes();
//            for(Note note: notes){
//                if(note.isStart())
//                System.out.println(note.getNumericalDuration() + "_test_"+note.getPitch());
//            }
//        }
//        ArrayList<String> ascii = AsciiCommand.convertSongToAscii(song);
//        for(int i = 0; i < ascii.size(); i++){
//            System.out.println(ascii.get(i));
//        }
//    }

//    Bar testbar_1 = new Bar(0, "4_UA 4_UA");
//    Bar testbar_2 = new Bar(1, "2_UA 2_UB");
//    Bar testbar_3 = new Bar(2, "1_UC");
//    Bar testbar_4 = new Bar(3, "1_REST");
//    Bar testbar_5 = new Bar(4, "2_MC 2_MC");
//    @Test
//    public void convertBarToAscii(){
//        //System.out.println(testBar_1.getChords().size());
//        AsciiCommand2.printBarAscii(testbar_2);
//    }

}
