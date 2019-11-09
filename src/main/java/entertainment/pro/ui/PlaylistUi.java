package entertainment.pro.ui;

import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.model.Playlist;
import entertainment.pro.model.PlaylistMovieInfoObject;
import entertainment.pro.storage.utils.EditPlaylistJson;
import entertainment.pro.storage.user.ProfileCommands;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlaylistUi {
    private String playlistName;
    private ArrayList<String> playlists;

    public PlaylistUi(String playlistName, ArrayList<String> playlists) {
        this.playlistName = playlistName;
        this.playlists = playlists;
    }

    public Node getPlaylistScrollPaneContent() throws IOException {
        return buildPlaylistVBox(playlists);
    }

    private VBox buildPlaylistVBox(ArrayList<String> playlists) throws IOException {
        VBox playlistVBox = new VBox();
        int count = 1;
        if (playlists.isEmpty()) {
            Label emptyLabel = new Label("u do not have any playlist currently :( "
                    + "\n try making some using command: playlist create <playlist name>");
            playlistVBox.getChildren().add(emptyLabel);
        } else {
            for (String log : playlists) {
                Playlist playlist = new EditPlaylistJson(log).load();
                System.out.println(playlist.getPlaylistName());
                System.out.println(playlist.getMovies().size());
                AnchorPane playlistPane = buildPlaylistPane(playlist, count);
                playlistVBox.getChildren().add(playlistPane);
                count++;
            }
        }
        return playlistVBox;
    }

    private AnchorPane buildPlaylistPane(Playlist playlist, int i) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PlaylistPane.fxml"));
            AnchorPane playlistPane = loader.load();
            playlistPane.setOnMouseClicked((mouseEvent) -> {
                playlistPaneClicked(playlist);
            });
            // set the movie info
            PlaylistController controller = loader.getController();
            controller.setVBoxColour(i);
            controller.setTextColour();
            controller.getPlaylistNameLabel().setText(playlist.getPlaylistName());
            if (playlist.getDescription().trim().length() == 0) {
                controller.getPlaylistDescriptionLabel().setText("*this playlist does not have a description :(*");
            } else {
                controller.getPlaylistDescriptionLabel().setText(playlist.getDescription());
            }
            controller.getPlaylistMoviesLabel()
                    .setText("No. of movies: " + Integer.toString(playlist.getMovies().size()));
            System.out.println("no lei here");
            return playlistPane;
        } catch (IOException ex) {
//            Ui.printLine();
        }
        System.out.println("fk lah here");
        return null;
    }

    private VBox playlistPaneClicked(Playlist playlist) {
        playlistName = playlist.getPlaylistName();
        return buildPlaylistInfo(playlist);
    }

    public VBox buildPlaylistInfo(Playlist playlist) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PlaylistInfo.fxml"));
            AnchorPane playlistPane = loader.load();
            PlaylistInfoController controller = loader.getController();
            controller.getPlaylistNameLabel().setText(playlist.getPlaylistName());
            if (playlist.getDescription().trim().length() == 0) {
                controller.getPlaylistDescriptionLabel().setStyle("-fx-font-style: italic");
                controller.getPlaylistDescriptionLabel().setText("*this playlist does not have a description :(*");
            } else {
                controller.getPlaylistDescriptionLabel().setText(playlist.getDescription());
            }
            if (playlist.getMovies().size() != 0) {
                controller.getPlaylistInfoVBox().getChildren().add(buildPlaylistMoviesFlowPane(playlist.getMovies()));
            } else {
                Label emptyMoviesLabel = new Label(playlist.getPlaylistName() + " does not contain any movies :(");
                controller.getPlaylistInfoVBox().getChildren().add(2, emptyMoviesLabel);
            }
            return controller.getPlaylistInfoVBox();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FlowPane buildPlaylistMoviesFlowPane(ArrayList<PlaylistMovieInfoObject> movies) {
//        // Setup progress bar and status label
//        mProgressBar.setProgress(0.0);
//        mProgressBar.setVisible(true);
//        mStatusLabel.setText("Loading..");
//        mMovies = convert(movies);

        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL);
        flowPane.setHgap(4);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(10, 8, 4, 8));
        flowPane.setPrefWidth(510);

        for (int i = 0; i < movies.size(); i++) {
            AnchorPane posterPane = buildPlaylistMoviePosterPane(movies.get(i), i + 1);
            flowPane.getChildren().add(posterPane);
        }
        return flowPane;
    }

    private AnchorPane buildPlaylistMoviePosterPane(MovieInfoObject movie, int index) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MoviePoster.fxml"));
            AnchorPane posterView = loader.load();
            //posterView.setOnScroll();
            posterView.setOnMouseClicked((mouseEvent) -> {
                try {
                    playlistMoviePosterClicked(movie);
                } catch (Exceptions exceptions) {
                    exceptions.printStackTrace();
                }
            });

            // set the movie info
            MoviePosterController controller = loader.getController();
            try {
                Image posterImage = new Image("/images/FakeMoviePoster.png");
//                posterImage.progressProperty().addListener((observable, oldValue, newValue) -> {
//                    updateProgressBar(index, size);
//                    updateProgressLabel(index, size);
//                });
                controller.getPosterImageView().setImage(posterImage);
            } catch (NullPointerException ex) {

            }
            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieNumberLabel().setText(Integer.toString(index));
            return posterView;
        } catch (IOException ex) {
            //Ui.printLine();
        }

        return null;
    }

    /**
     * Tis function is called when the user wants to see more information about a movie.
     */
    private AnchorPane playlistMoviePosterClicked(MovieInfoObject movie) throws Exceptions {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PlaylistMoreInfo.fxml"));
            AnchorPane posterView = loader.load();
            PlaylistMovieController controller = loader.getController();

            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getMovieRatingLabel().setText(String.format("%.2f", movie.getRatingInfo()));
            if (movie.getReleaseDateInfo() != null) {
                Date date = movie.getReleaseDateInfo();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String printDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                controller.getMovieDateLabel().setText(printDate);
            } else {
                controller.getMovieDateLabel().setText("N/A");
            }
            controller.getMovieSummaryLabel().setText(movie.getSummaryInfo());
            ArrayList<Long> genreList = movie.getGenreIdInfo();
            String genres = "";
            for (int i = 0; i < genreList.size(); i++) {
                if (genreList.size() == 0) {
                    genres = "no genres";
                }
                if (i != genreList.size() - 1) {
                    genres += ProfileCommands.findGenreName(genreList.get(i).intValue());
                    genres += " , ";
                } else {
                    genres += ProfileCommands.findGenreName(genreList.get(i).intValue());
                }
            }
            controller.getMovieGenresLabel().setText(genres);
//            mMoviesScrollPane.setContent(controller.getPlaylistMovieInfoAnchorPane());
//            mMoviesScrollPane.setVvalue(0);
//            pageTracker.setToPlaylistMovieInfo();
            return controller.getPlaylistMovieInfoAnchorPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void showPlaylistList() throws IOException {
//        buildPlaylistVBox(playlists);
//    }

    private ArrayList<MovieInfoObject> convert(ArrayList<PlaylistMovieInfoObject> toConvert) {
        ArrayList<MovieInfoObject> converted = new ArrayList<>();
        boolean isMovie = false;
        for (PlaylistMovieInfoObject log : toConvert) {
            converted.add(new MovieInfoObject(log.getId(), log.getTitle(), isMovie,log.getReleaseDateInfo(), log.getSummaryInfo(), log.getFullPosterPathInfo(), log.getFullBackdropPathInfo(),
                    log.getRatingInfo(), log.getGenreIdInfo(), log.isAdultContent()));

        }
        return converted;
    }
//
//    /**
//     * Tis function is called when the user wants to see more information about a movie.
//     */
//    public void playlistMoviePosterClicked(MovieInfoObject movie) throws Exceptions {
//        try {
//            FXMLLoader loader = new FXMLLoader();
//            loader.setLocation(getClass().getClassLoader().getResource("PlaylistMoreInfo.fxml"));
//            AnchorPane posterView = loader.load();
//            PlaylistMovieController controller = loader.getController();
//
//            controller.getMovieTitleLabel().setText(movie.getTitle());
//            controller.getMovieRatingLabel().setText(String.format("%.2f", movie.getRatingInfo()));
//            if (movie.getReleaseDateInfo() != null) {
//                Date date = movie.getReleaseDateInfo();
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(date);
//                String printDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
//                controller.getMovieDateLabel().setText(printDate);
//            } else {
//                controller.getMovieDateLabel().setText("N/A");
//            }
//            controller.getMovieSummaryLabel().setText(movie.getSummaryInfo());
//            ArrayList<Long> genreList = movie.getGenreIdInfo();
//            String genres = "";
//            for (int i = 0; i < genreList.size(); i++) {
//                if (genreList.size() == 0) {
//                    genres = "no genres";
//                }
//                if (i != genreList.size() - 1) {
//                    genres += ProfileCommands.findGenreName(genreList.get(i).intValue());
//                    genres += " , ";
//                } else {
//                    genres += ProfileCommands.findGenreName(genreList.get(i).intValue());
//                }
//            }
//            controller.getMovieGenresLabel().setText(genres);
//            mMoviesScrollPane.setContent(controller.getPlaylistMovieInfoAnchorPane());
//            mMoviesScrollPane.setVvalue(0);
//            pageTracker.setToPlaylistMovieInfo();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * This funcion updates the progress bar as the movie poster is being displayed.
     *
     * @param movie    Object that contains all the information about a particular movie.
     * @param progress contains the progress value.
     */
    private ProgressBar updateProgressBar(int index, int size) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setVisible(true);
        progressBar.setProgress(((double) index) / size);
        if (index == size) {
            progressBar.setVisible(false);
        }
        return progressBar;
    }

    /**
     * This funcion updates the progress bar as the movie poster is being displayed.
     *
     * @param movie    Object that contains all the information about a particular movie.
     * @param progress contains the progress value.
     */
    private Label updateProgressLabel(int index, int size) {
        Label progressLabel = new Label("Loading..");
        if (index == size) {
            progressLabel.setText("");
        }
        return progressLabel;
    }
}
