package MovieUI;

import EPparser.CommandParser;
import EPstorage.*;

import javafx.application.Platform;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import org.apache.commons.lang3.ObjectUtils;
import parser.TimeParser;
import ui.Ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MovieInfoController extends Controller {

    @FXML
    private ScrollPane mMoviesScrollPane;

    @FXML
    private VBox vbox0, vBox1, vBox2, vBox3, gneresVBox, mainVBox, searchCommandVBox, generalFeedbackVBox, autoCompleteVBox;

    @FXML
    private HBox nameHBox, adultHBox, genresHBox, alphaSortHBox, latestDatesHBox, highestRatingHBox;

    @FXML
    private Label userPreferenceLabel, userNameLabel, userAgeLabel, userAdultLabel1, userAdultLabel2,
            userGenreLabel, sortAlphaOrderLabel, sortLatestDateLabel, sortHighestRatingLabel,
            sortHighestRatingText, autoCompleteLabel, generalFeedbackLabel;

    @FXML
    private Text userPreferenceText, userNameText, userAgeText, generalFeedbackText,
            sortAlphaOrderText, sortLatestDateText, autoCompleteText;

    @FXML
    private Label movieTitleLabel;
    @FXML
    private Label movieGenresLabel;
    @FXML
    private Label movieReleaseDateLabel;
    @FXML
    private Label movieRatingLabel;
    @FXML
    private Label movieSummaryLabel;
    @FXML
    private ImageView movieBackdropImageView;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private GridPane movieGridPane;
    @FXML
    private TextField mSearchTextField;
    @FXML
    private Label mStatusLabel;
    @FXML
    private ProgressBar mProgressBar;
    @FXML
    private Label movieCastLabel;
    @FXML
    private Label movieCertLabel;
    private FlowPane mMoviesFlowPane;

    // private RetrieveRequest mMovieRequest = new RetrieveRequest(this);
    private MovieHandler movieHandler = new MovieHandler();
    private ArrayList<MovieInfoObject> mMovies;
    private double[] mImagesLoadingProgress;

    private MovieInfoObject mMovie;

    @FXML TextFlow genreListText;

    private UserProfile userProfile;
    private ArrayList<Playlist> playlists;

    class KeyboardClick implements EventHandler<KeyEvent> {

        private Controller control;

        KeyboardClick(Controller control) {
            this.control = control;
        }

        @Override
        public void handle(KeyEvent event) {
            if (event.getCode().equals(KeyCode.ENTER)) {
                System.out.println("Hello");
                try {
                    if (mSearchTextField.getText().equals("go back")) {
                        backToMoviesButtonClick();
                    } else {
                        mMainApplication.transitionBackToMoviesController();
                        CommandParser.parseCommands(mSearchTextField.getText(), control);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (event.getCode().equals(KeyCode.TAB)) {
                System.out.println("Tab presjenksjessed");
                event.consume();
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                //if (mMoviesScrollPane.)
                mMoviesScrollPane.requestFocus();
            }
        }
    }

    // Set the movie for this controller
    public void setMovie(MovieInfoObject movie) throws IOException {
        mMovie = movie;
        initialize();
    }

    @FXML public void setLabels() throws IOException {
        EditProfileJson editProfileJson = new EditProfileJson();
        userProfile = editProfileJson.load();
        EditPlaylistJson editPlaylistJson = new EditPlaylistJson();
        mProgressBar.setProgress(0.1);
        playlists = editPlaylistJson.load();
        ProfileCommands command = new ProfileCommands(userProfile);
        mProgressBar.setProgress(0.2);
        userNameLabel.setText(userProfile.getUserName());
        userAgeLabel.setText(Integer.toString(userProfile.getUserAge()));
        //setting adult label
        if (command.getAdultLabel().equals("allow")) {
            userAdultLabel2.setStyle("-fx-text-fill: \"#48C9B0\";");
        }
        if (command.getAdultLabel().equals("restrict")) {
            userAdultLabel2.setText(command.getAdultLabel());
            //setting text for preference & restrictions
            Text preferences = new Text(command.convertToLabel(userProfile.getGenreIdPreference()));
            preferences.setFill(Paint.valueOf("#48C9B0"));
            Text restrictions = new Text(command.convertToLabel(userProfile.getGenreIdRestriction()));
            restrictions.setFill(Paint.valueOf("#EC7063"));
            genreListText.getChildren().clear();
            genreListText.getChildren().addAll(preferences, restrictions);
        }
    }

    @FXML public void initialize() throws IOException {
        setLabels();

        mProgressBar.setProgress(0.3);
        //mMovieRequest = new RetrieveRequest(this);
        // Load the movie info if movie has been set
        if (mMovie != null) {
            movieTitleLabel.setText(mMovie.getTitle());
            //movieCastLabel.setText(mMovie.getmCast());
            movieRatingLabel.setText(String.format("%.2f", mMovie.getRating()));
            mProgressBar.setProgress(0.4);
            if (mMovie.getReleaseDate() != null) {
                Date date = mMovie.getReleaseDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                String printDate = new SimpleDateFormat("dd/MM/yyyy").format(date);
                movieReleaseDateLabel.setText(String.format("%s", mMovie.getReleaseDate().toString()));
                movieReleaseDateLabel.setText(printDate);
                mProgressBar.setProgress(0.5);
            } else {
                movieReleaseDateLabel.setText("N/A");
            }

            loadGenres();
            loadMovieBackdrop();
            movieSummaryLabel.setText(mMovie.getSummary());
            mMoviesScrollPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if (event.getCode().equals(KeyCode.UP)) {
                        double num = (double) mMoviesScrollPane.getVvalue();
                        num *= 10;
                        if (num == 0) {
                            mSearchTextField.requestFocus();
                        }
                    }
                    // } else if (event.getCode().equals(KeyCode.RIGHT)) {
                    //    System.out.println("yesssx");
                    //    mMoviesFlowPane.getChildren().get(num).requestFocus();
                    //   num += 1;
                    //mMoviesFlowPane.getChildren().get(num).();
                    // } else if (event.getCode().equals(KeyCode.ENTER)) {
                    //    moviePosterClicked(mMovies.get(num));
                    // }
                }
            });
        }


        mSearchTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                System.out.println("Tab pressed");
                event.consume();
            } else if (event.getCode().equals(KeyCode.ENTER)) {
                System.out.println("Enter pressed");
                //CommandParser.parseCommands(mSearchTextField.getText(), control);
//
            }
        });
        //Real time changes to text field
        mSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        System.out.println(generalFeedbackText.getText());

        //Enter is Pressed
        mSearchTextField.setOnKeyPressed(new KeyboardClick(this));
        Platform.runLater(() -> buildMoviesFlowPane());

    }

    private void buildMoviesFlowPane() {
        // Setup progress bar and status label
        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());   // bind to scroll pane width
        mMoviesFlowPane.getChildren().add(anchorPane);
        mMoviesScrollPane.setContent(mMoviesFlowPane);
        mMoviesScrollPane.setVvalue(0);

    }


    // User clicks on the back button to navigate back to the movies scene
    public void backToMoviesButtonClick() {
        mMainApplication.transitionBackToMoviesController();
    }


    // Loads the movie backdrop image asynchronously
    private void loadMovieBackdrop() {
        if (mMovie.getFullBackdropPath() != null) {
            Image backdropImage = new Image(mMovie.getFullBackdropPath(), true);
            mProgressBar.setProgress(0.9);
            //backdropImage.progressProperty().addListener((observable, oldValue, newValue) -> updateProgressBar(mMovie, newValue.doubleValue()));
            movieBackdropImageView.setImage(backdropImage);
            mProgressBar.setProgress(1.0);
            mProgressBar.setVisible(false);
            mStatusLabel.setText("");
        }
    }


    private void loadGenres() {
        movieGenresLabel.setText("");
        movieCastLabel.setText("");
        movieCertLabel.setText("");

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] genres = RetrieveRequest.getGenreStrings(mMovie);
                StringBuilder builder = new StringBuilder();

                try {
                    for (String genre : genres) {
                        builder.append(genre);
                        System.out.println(genre + "  " + genres.length);
                        // if not last string in array, append a ,
                        if (genres.length == 0) {
                            System.out.println("no genres");
                        } else if (!genres[genres.length - 1].equals(genre)) {
                            builder.append(", ");
                        }
                    }
                } catch (NullPointerException ex) {

                }
                System.out.println("this is builder " + builder.toString());
                mProgressBar.setProgress(0.6);

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        movieGenresLabel.setText(builder.toString());
                        movieCastLabel.setText(RetrieveRequest.getCastStrings(mMovie));
                        movieCertLabel.setText(RetrieveRequest.getCertStrings(mMovie));
                        mProgressBar.setProgress(0.8);
                    }
                });
            }
        });

        t.start();
    }

    private void clearText(TextField textField) {
        textField.setText("");
    }

    // Menu item events
    @FXML
    public void exitMenuItemClicked() {
        System.exit(0);
    }

    @FXML
    public void aboutMenuItemClicked() {
    }

}