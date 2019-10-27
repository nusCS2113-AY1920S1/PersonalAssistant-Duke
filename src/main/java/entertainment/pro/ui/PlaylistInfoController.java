package entertainment.pro.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class PlaylistInfoController {
    @FXML
    private Label playlistNameLabel;
    @FXML
    private Label playlistDescriptionLabel;
//    @FXML
//    private ScrollPane playlistMoviesScrollPane;
    @FXML
    private VBox playlistInfoVBox;

    public Label getPlaylistNameLabel() {
        return playlistNameLabel;
    }

    public void setPlaylistNameLabel(Label playlistNameLabel) {
        this.playlistNameLabel = playlistNameLabel;
    }

    public Label getPlaylistDescriptionLabel() {
        return playlistDescriptionLabel;
    }

    public void setPlaylistDescriptionLabel(Label playlistDescriptionLabel) {
        this.playlistDescriptionLabel = playlistDescriptionLabel;
    }

//    public ScrollPane getPlaylistMoviesScrollPane() {
//        return playlistMoviesScrollPane;
//    }
//
//    public void setPlaylistMoviesScrollPane(ScrollPane playlistMoviesScrollPane) {
//        this.playlistMoviesScrollPane = playlistMoviesScrollPane;
//    }

    public VBox getPlaylistInfoVBox() {
        return playlistInfoVBox;
    }

    public void setPlaylistInfoVBox(VBox playlistInfoVBox) {
        this.playlistInfoVBox = playlistInfoVBox;
    }
}
