package MovieUI;

import EPparser.CommandParser;
import EPstorage.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import parser.TimeParser;
import ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MovieInfoController extends Controller {

    @FXML private Label movieTitleLabel;
    @FXML private Label movieGenresLabel;
    @FXML private Label movieReleaseDateLabel;
    @FXML private Label movieRatingLabel;
    @FXML private Label movieSummaryLabel;
    @FXML private ImageView movieBackdropImageView;
    @FXML private ScrollPane movieScrollPane;
    @FXML private VBox movieMainVBox;
    @FXML private GridPane movieGridPane;
    @FXML private AnchorPane anchorPane;
    @FXML private TextField mSearchTextField;
    @FXML private Label movieCastLabel;
    @FXML private Label movieCertLabel;
    @FXML private Label mStatusLabel;
    @FXML private ProgressBar mProgressBar;
    private FlowPane mMoviesFlowPane;

    //private RetrieveRequest mMovieRequest = new RetrieveRequest(this);
    private MovieHandler movieHandler = new MovieHandler();
    private ArrayList<MovieInfoObject> mMovies;
    private double[] mImagesLoadingProgress;

    private MovieInfoObject mMovie;

    @FXML
    private Text text;

    @FXML Label userNameLabel;
    @FXML Label userAgeLabel;
    @FXML Label genreListLabel;
    private UserProfile userProfile;
    private ArrayList<Playlist> playlists;



    class KeyboardClick implements EventHandler<KeyEvent> {

        private Controller control;

        KeyboardClick(Controller control){
            this.control = control;
        }

        @Override
        public void handle(KeyEvent event) {
            if(event.getCode().equals(KeyCode.ENTER)) {
                System.out.println("Hello");
                try {
                    CommandParser.parseCommands(mSearchTextField.getText() ,control);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getCode().equals(KeyCode.TAB)){
                System.out.println("Tab presjenksjessed");
                event.consume();
            }
        }
    }

    // Set the movie for this controller
    public void setMovie(MovieInfoObject movie) throws IOException {
        mMovie = movie;
        initialize();
    }

    @FXML public void initialize() throws IOException {
        EditProfileJson editProfileJson = new EditProfileJson();
        userProfile = editProfileJson.load();
        EditPlaylistJson editPlaylistJson = new EditPlaylistJson();
        playlists = editPlaylistJson.load();
        ProfileCommands command = new ProfileCommands(userProfile);
        userNameLabel.setText(userProfile.getUserName());
        userAgeLabel.setText(Integer.toString(userProfile.getUserAge()));
        genreListLabel.setText(command.convertToLabel(userProfile.getGenreId()));

        // Load the movie info if movie has been set
        if (mMovie != null) {
            movieTitleLabel.setText(mMovie.getTitle());
            movieRatingLabel.setText(String.format("%.2f", mMovie.getRating()));

            if (mMovie.getReleaseDate() != null) {
                Date date = mMovie.getReleaseDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String printDate = new SimpleDateFormat("dd-MM-yyyy").format(date);
                movieReleaseDateLabel.setText(String.format("%s", mMovie.getReleaseDate().toString()));
                movieReleaseDateLabel.setText(printDate);
            } else {
                movieReleaseDateLabel.setText("N/A");
            }

            String text = (mMovie.getSummary());
           /** for (int i = 1; i < text.length(); i += 1) {
                if (i % 80 == 0) {
                   String text1 = text.substring(0, i);
                   String text2 = text.substring(i + 1, text.length());
                   String text3 = text1 + "\n" + text2;
                   text = text3;
                }
            }

            //text.append("\n");
            **/
            movieSummaryLabel.setText(mMovie.getSummary());
            //movieSummaryLabel.setText(text.toString());
            loadMovieBackdrop();
            loadGenres();
            loadCast();
            loadCert();
        }

        //movieScrollPane.setContent(movieMainVBox);
        //movieScrollPane.vvalueProperty().bind(movieMainVBox.heightProperty());
        //movieMainVBox.prefWidthProperty().bind(movieScrollPane.widthProperty());
        //movieBackdropImageView.setPreserveRatio(true);
        //movieGridPane.prefWidthProperty().bind(movieScrollPane.widthProperty());


        mSearchTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                System.out.println("Tab pressed");
                event.consume();
            }else if(event.getCode().equals(KeyCode.ENTER)) {
                System.out.println("Enter pressed");
//
            }
        });
        //Real time changes to text field
        mSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        System.out.println(text.getText());

        //Enter is Pressed
        mSearchTextField.setOnKeyPressed(new KeyboardClick(this));

        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(movieScrollPane.widthProperty());

        mMoviesFlowPane.getChildren().add(movieMainVBox);

        movieScrollPane.setContent(mMoviesFlowPane);

        //movieGridPane.prefWidthProperty().bind(movieScrollPane.widthProperty());
    }

    private void loadCert() {
        movieCastLabel.setText("");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String casts = RetrieveRequest.getCertStrings(mMovie);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        movieCertLabel.setText(casts);
                    }
                });
            }
        });
        t.start();
    }


    private void loadCast() {
        movieCastLabel.setText("");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String casts = RetrieveRequest.getCastStrings(mMovie);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        movieCastLabel.setText(casts);
                    }
                });
            }
        });
        t.start();
    }


    // Loads the movie backdrop image asynchronously
    private void loadMovieBackdrop()
    {
        if (mMovie.getFullBackdropPath() != null){
            Image backdropImage = new Image(mMovie.getFullBackdropPath(), true);
            movieBackdropImageView.setImage(backdropImage);
        }
    }

    private void loadGenres()
    {
        movieGenresLabel.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] genres = RetrieveRequest.getGenreStrings(mMovie);
                StringBuilder builder = new StringBuilder();

                for (String genre : genres){
                    builder.append(genre);
                    System.out.println(genre);
                    // if not last string in array, append a ,
                    if (genres.length == 0) {
                        System.out.println("no genres");
                    }
                    if (!genres[genres.length - 1].equals(genre)){
                        builder.append(", ");
                    }
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        movieGenresLabel.setText(builder.toString());
                    }
                });
            }
        });

        t.start();
    }


    // Menu item events
    @FXML public void exitMenuItemClicked()
    {
        System.exit(0);
    }

    @FXML public void aboutMenuItemClicked()
    {
    }

}