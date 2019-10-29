package ducats.uicomponents;

import ducats.Ducats;
import ducats.components.Group;
import ducats.components.Song;
import ducats.components.SongList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

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
    String noSongs = "You have no songs in your song list.";
    private Ducats ducats = new Ducats();

    @FXML
    public void initialize() {
        if(ducats.getSongs().getSize() > 0){
            displaySongs();
            displayGroups(ducats.getSongs().getSongIndex(0));
            displayBottomMessage("Current Song: "+ducats.getSongs().getSongIndex(0).getName());
        } else {
            displayBottomMessage(noSongs);
        }
    }



    private void displaySongs(){
        SongList songs = ducats.getSongs();
        ArrayList<Song> mySongs = songs.getSongList();
        for(Song s : mySongs){
            songDisplay.getChildren().add(new Label(s.getName()));
        }
    }



    private void displayGroups(Song currentSong){
        ArrayList<Group> groups = currentSong.getGroups();
        for(Group g : groups){
            groupDisplay.getChildren().add(new Label(g.getName()));
        }
    }


    /**
     * Displays messages at the bottom of the window.
     * Can be used to display exceptions as well.
     * @param msg message to display to the user
     */
    private void displayBottomMessage(String msg){
        bottomStrip.setText(msg);
    }



    public void getUserCommand(){
        System.out.println(commandLine.getText());
        displayChord();
        commandLine.clear();
    }


    // TODO: 29/10/2019 method to display chord as GUI
    private void displayChord(){
        VBox chordBox = new VBox();
        chordBox.setSpacing(4);

        ArrayList<Button> panes = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            Button pane = new Button();

            pane.setPrefHeight(20);
            pane.setPrefWidth(20);
            // create a background fill
            BackgroundFill background_fill = new BackgroundFill(Color.PINK,
                    CornerRadii.EMPTY, Insets.EMPTY);
            // create Background
            Background background = new Background(background_fill);
            pane.setBackground(background);
            panes.add(pane);
        }

        //TODO: set the respective notes

        for(Button p : panes){
            chordBox.getChildren().add(p);
        }
        songPanel.getChildren().add(chordBox);
    }

    //@@author
}
