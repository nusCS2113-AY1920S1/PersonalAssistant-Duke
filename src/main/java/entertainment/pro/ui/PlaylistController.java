package entertainment.pro.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlaylistController {
    @FXML
    private Label playlistNameLabel;

    @FXML
    private Label playlistDescriptionLabel;

    @FXML
    private Label playlistMoviesLabel;

    @FXML
    private VBox individualPlaylistVBox;

    private final String colour1 = "#B0E0E6";
    private final String colour2 = "#D8BFD8";

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

    public Label getPlaylistMoviesLabel() {
        return playlistMoviesLabel;
    }

    public void setPlaylistMoviesLabel(Label playlistMoviesLabel) {
        this.playlistMoviesLabel = playlistMoviesLabel;
    }

    /**
     * to set the VBox background colour.
     */
    public void setVBoxColour(int i) {
        if (i % 2 == 0) {
            individualPlaylistVBox.setStyle("-fx-background-color: " + colour1);
        } else {
            individualPlaylistVBox.setStyle("-fx-background-color: " + colour2);
        }
    }

    /**
     * to set the text colour in VBox.
     */
    public void setTextColour() {
        playlistNameLabel.setStyle("-fx-text-fill: #000000; -fx-font-size: 12pt; -fx-font-weight: bold");
        playlistDescriptionLabel.setStyle("-fx-text-fill: #000000");
        playlistMoviesLabel.setStyle("-fx-text-fill: #000000");
    }
}
