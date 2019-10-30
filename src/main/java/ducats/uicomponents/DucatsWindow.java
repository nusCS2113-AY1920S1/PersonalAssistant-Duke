package ducats.uicomponents;

import ducats.Ducats;
import ducats.components.Note;
import ducats.components.Chord;
import ducats.components.Bar;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class DucatsWindow {

    //@@author Samuel787
    @FXML
    private TextField commandLine;

    @FXML
    private VBox songDisplay;

    @FXML
    private VBox groupDisplay;

    @FXML
    private Label bottomStrip;

    @FXML
    private HBox songPanel;

    //CONSTANTS
    private String noSongs = "You have no songs in your song list.";
    private Ducats ducats = new Ducats();

    /**
     * This method gets called when DucatsGui FXML Loader loads the Scene.
     */
    @FXML
    public void initialize() {
        if (ducats.getSongs().getSize() > 0) {
            listSongs();
            listGroups(ducats.getSongs().getSongIndex(0));
            displaySong(ducats.getSongs().getSongIndex(0));
            displayBottomMessage("Current Song: " + ducats.getSongs().getSongIndex(0).getName());
        } else {
            displayBottomMessage(noSongs);
        }
    }


    private void listSongs() {
        SongList songs = ducats.getSongs();
        ArrayList<Song> mySongs = songs.getSongList();
        for (Song s : mySongs) {
            songDisplay.getChildren().add(new Label(s.getName()));
        }
    }


    private void listGroups(Song currentSong) {
        ArrayList<Group> groups = currentSong.getGroups();
        for (Group g : groups) {
            groupDisplay.getChildren().add(new Label(g.getName()));
        }
    }


    /**
     * Displays messages at the bottom of the window.
     * Can be used to display exceptions as well.
     *
     * @param msg message to display to the user
     */
    private void displayBottomMessage(String msg) {
        bottomStrip.setText(msg);
    }


    public void getUserCommand() {
        System.out.println(commandLine.getText());
        commandLine.clear();
    }


    private void displaySong(Song song) {
        ArrayList<Bar> bars = song.getBars();
        for (Bar b : bars) {
            ArrayList<Chord> chords = b.getChords();
            for (Chord c : chords) {
                displayChord(c);
            }
        }
    }

    // TODO: 29/10/2019 method to display chord as GUI
    private void displayChord(Chord chord) {
        VBox chordBox = new VBox();
        chordBox.setSpacing(4);

        ArrayList<Button> panes = new ArrayList<>();
        Background backgroundInactive = new Background(new BackgroundFill(Color.GREY,
                CornerRadii.EMPTY, Insets.EMPTY));
        for (int i = 0; i < 15; i++) {
            Button pane = new Button();
            pane.setPrefHeight(20);
            pane.setPrefWidth(20);
            pane.setBackground(backgroundInactive);
            panes.add(pane);
        }

        //TODO: set the respective notes
        Background backgroundActive = new Background(new BackgroundFill(Color.TURQUOISE,
                CornerRadii.EMPTY, Insets.EMPTY));
        ArrayList<Note> notes = chord.getNotes();
        for (Note n : notes) {
            switch (n.getPitch()) {
            case UPPER_C:
                panes.get(0).setBackground(backgroundActive);
                break;
            case UPPER_B:
                panes.get(1).setBackground(backgroundActive);
                break;
            case UPPER_A:
                panes.get(2).setBackground(backgroundActive);
                break;
            case UPPER_G:
                panes.get(3).setBackground(backgroundActive);
                break;
            case UPPER_F:
                panes.get(4).setBackground(backgroundActive);
                break;
            case UPPER_E:
                panes.get(5).setBackground(backgroundActive);
                break;
            case UPPER_D:
                panes.get(6).setBackground(backgroundActive);
                break;
            case MIDDLE_C:
                panes.get(7).setBackground(backgroundActive);
                break;
            case LOWER_B:
                panes.get(8).setBackground(backgroundActive);
                break;
            case LOWER_A:
                panes.get(9).setBackground(backgroundActive);
                break;
            case LOWER_G:
                panes.get(10).setBackground(backgroundActive);
                break;
            case LOWER_F:
                panes.get(11).setBackground(backgroundActive);
                break;
            case LOWER_E:
                panes.get(12).setBackground(backgroundActive);
                break;
            case LOWER_D:
                panes.get(13).setBackground(backgroundActive);
                break;
            case LOWER_C:
                panes.get(14).setBackground(backgroundActive);
                break;
            default:
                break;
            }
        }


        for (Button p : panes) {
            chordBox.getChildren().add(p);
        }
        songPanel.getChildren().add(chordBox);
    }

    //@@author
}
