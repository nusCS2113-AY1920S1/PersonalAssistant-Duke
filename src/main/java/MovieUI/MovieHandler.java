package MovieUI;

import EPstorage.Commands;
import EPstorage.ProfileStorage;
import EPstorage.UserProfile;

import Contexts.SearchResultContext;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import parser.CommandParser;
import ui.Ui;

import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this is main page of gui
 */
public class MovieHandler extends Controller implements RequestListener{
    @FXML
    private ScrollPane mMoviesScrollPane;

    @FXML
    private VBox mMovieTypeVBox;

    @FXML
    private Label titleLabel;

    @FXML
    private Label titleLabel2;

    @FXML
    private Text ageText;

    @FXML
    private Text genreText;

    @FXML
    private Text text;

    @FXML Label userNameLabel;
    @FXML Label userAgeLabel;
    @FXML Label genreListLabel;
    private ProfileStorage profileStorage;
    private UserProfile userProfile;

    private FlowPane mMoviesFlowPane;

    //@FXML
    //private ListView<String> mMovieTypeListView;

    @FXML
    private Label mStatusLabel;

    @FXML
    private ProgressBar mProgressBar;

    @FXML
    private TextField mSearchTextField;

    //@FXML
    //private Button mSearchButton;

    //@FXML
    //private Button mClearSearchButton;

    private ArrayList<MovieInfoObject> mMovies;
    private double[] mImagesLoadingProgress;

    private RetrieveRequest mMovieRequest;

    class KeyboardClick implements EventHandler<KeyEvent> {

        private Controller control;

        KeyboardClick(Controller control){
            this.control = control;
        }

        @Override
        public void handle(KeyEvent event) {
            if(event.getCode().equals(KeyCode.ENTER)) {
//                    SearchResultContext.AddKeyWord(mSearchTextField.getText());
                // do something
                System.out.println("Hello");
                CommandParser.parseCommands(mSearchTextField.getText() ,control );
            }else if(event.getCode().equals(KeyCode.TAB)){
                System.out.println("Tab presjenksjessed");
                event.consume();
            }
        }
    }



    @FXML public void initialize() throws IOException {
        profileStorage = new ProfileStorage();
        userProfile = profileStorage.load();
        Commands command = new Commands(userProfile);
        userNameLabel.setText(userProfile.getUserName());
        userAgeLabel.setText(Integer.toString(userProfile.getUserAge()));
        genreListLabel.setText(command.convertToLabel(userProfile.getGenreId()));


        mMovieRequest = new RetrieveRequest(this);

        //mMovieTypeVBox.setStyle("-fx-border-color: white;");





        //mMovieTypeListView.getItems().addAll("Now Showing", "Popular", "TV Shows", "Upcoming Movies");
        //mMovieTypeListView.getSelectionModel().select(0);


        mSearchTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.TAB) {
                System.out.println("Tab pressed");
                event.consume();
            }else if(event.getCode().equals(KeyCode.ENTER)) {
                System.out.println("Enter pressed");
//
            }
        });

        mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.NOW_SHOWING);


        //mSearchButton.disableProperty().bind(mSearchTextField.textProperty().isEmpty());
        //mClearSearchButton.disableProperty().bind(mSearchTextField.textProperty().isEmpty());

        //Real time changes to text field
        mSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println("textfield changed from " + oldValue + " to " + newValue);
        });

        System.out.println(text.getText());

        //Enter is Pressed
        mSearchTextField.setOnKeyPressed(new KeyboardClick(this));

        // mMovieTypeListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> moviesTypeSelectionChanged(oldValue.intValue(), newValue.intValue()));
    }

    // Called when the fetch request for the movie data is completed
    @Override
    public void requestCompleted(ArrayList<MovieInfoObject> moviesInfo)
    {
        // Build the Movie poster views and add to the flow pane on the main thread
        mMovies = moviesInfo;
        mImagesLoadingProgress = new double[mMovies.size()];
        Platform.runLater(() -> buildMoviesFlowPane(moviesInfo));
    }

    // Called when the request to fetch movie data timed out.
    @Override
    public void requestTimedOut()
    {
        Platform.runLater(() -> showDownloadFailureAlert("Request timed out"));
    }

    // Called when the request fails due to an invalid internet connection
    @Override
    public void requestFailed()
    {
        Platform.runLater(() -> showDownloadFailureAlert("No internet connection"));
    }


    private void showDownloadFailureAlert(String headerText)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Failed to download");
        alert.setHeaderText(headerText);
        alert.setContentText("Please ensure you have an active internet connection.");
        alert.showAndWait();
    }

    private void buildMoviesFlowPane(ArrayList<MovieInfoObject> movies)
    {
        // Setup progress bar and status label
        mProgressBar.setProgress(0.0);
        mProgressBar.setVisible(true);
        mStatusLabel.setText("Loading..");

        // Build a flow pane layout with the width and size of the
        mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
        mMoviesFlowPane.setHgap(4);
        mMoviesFlowPane.setVgap(10);
        mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
        mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());   // bind to scroll pane width

        for (MovieInfoObject movie : movies)
        {
            AnchorPane posterPane = buildMoviePosterPane(movie);
            mMoviesFlowPane.getChildren().add(posterPane);
        }

        mMoviesScrollPane.setContent(mMoviesFlowPane);
    }


    private AnchorPane buildMoviePosterPane(MovieInfoObject movie) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("MoviePoster.fxml"));
            AnchorPane posterView = loader.load();
            posterView.setOnMouseClicked((mouseEvent) -> moviePosterClicked(movie));

            // set the movie info
            MoviePosterController controller = loader.getController();
            Image posterImage = new Image(movie.getFullPosterPath(), true);
            posterImage.progressProperty().addListener((observable, oldValue, newValue) -> updateProgressBar(movie, newValue.doubleValue()));

            controller.getMovieTitleLabel().setText(movie.getTitle());
            controller.getPosterImageView().setImage(posterImage);

            return posterView;
        } catch (IOException ex){
            Ui.printLine();
        }

        return null;
    }

    private void updateProgressBar(MovieInfoObject movie, double progress)
    {
        // update the progress for that movie in the array
        int index = mMovies.indexOf(movie);
        if (index >= 0){
            mImagesLoadingProgress[index] = progress;
        }

        double currentTotalProgress = 0.0;
        for (double value : mImagesLoadingProgress){
            currentTotalProgress += value;
        }

        //System.out.println("Current total progress: " + currentTotalProgress);
        mProgressBar.setProgress((currentTotalProgress / mMovies.size()));

        if(currentTotalProgress >= mMovies.size()){
            mProgressBar.setVisible(false);
            mStatusLabel.setText("");
        }
    }

    // movieTypeSelectionChanged event - Reload the movies for the new movies request type
    private void moviesTypeSelectionChanged(int oldIndex, int newIndex)
    {
        if(newIndex >= 0 && (newIndex != oldIndex)){

            if (mMoviesFlowPane != null) {
                mMoviesFlowPane.getChildren().clear();
            }


            switch (newIndex) {
                case 0:
                    mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.NOW_SHOWING);
                    break;

                case 1:
                    mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR);
                    break;

                case 2:
                    mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TV_SHOWS);
                    break;

                case 3:
                    mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING);
                    break;

                default:
                    break;
            }
        }
    }

    // User clicks on a movie poster
    private void moviePosterClicked(MovieInfoObject movie) {

        mMainApplication.transitToMovieInfoController(movie);
    }

    private void clearText(TextField textField){
        textField.setText("");
    }

    @FXML private void searchButtonClicked() throws IOException {
        String userInput = mSearchTextField.getText();
        Commands command = new Commands(userProfile);
        //for setting up profile preferences
        String[] tokens = userInput.split((" "), 3);
        if (tokens.length == 3) {
            if (tokens[0].equals("set") && tokens[1].equals("name")) {
                command.setName(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            } else if (tokens[0].equals("set") && tokens[1].equals("age")) {
                command.setAge(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            } else if (tokens[0].equals("add") && tokens[1].equals("preference")) {
                command.addPreference(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            } else if (tokens[0].equals("remove") && tokens[1].equals("preference")) {
                command.removePreference(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            } else if (tokens[0].equals("set") && tokens[1].equals("preference")) {
                command.setPreference(tokens[2]);
                clearText(mSearchTextField);
                initialize();
            }
        }
        //for searching movies
        if (userInput.equals("show current movie")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.NOW_SHOWING);
        } else if (userInput.equals("show upcoming movie")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.UPCOMING);
        } else if (userInput.equals("show popular movie")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.POPULAR);
        } else if (userInput.equals("show current tv")) {
            mMovieRequest.beginMovieRequest(RetrieveRequest.MoviesRequestType.TV_SHOWS);
        } else if (!userInput.isEmpty()) {
            if (userInput.contains(" -g ")) {
                ArrayList<Integer> inputGenre = new ArrayList<>(10);
                String[] token = userInput.split(" -");
                for (int i = 1; i < token.length; i++) {
                    if (token[i].charAt(0) == 'g') {
                        token[i] = token[i].substring(2);
                        if (token[i].equals("my preference")) {
                            inputGenre.addAll(userProfile.getGenreId());
                        } else {
                            inputGenre.add(Integer.parseInt(command.findGenreID(token[i])));
                        }
                    }
                }
                mMovieRequest.beginSearchRequestWithGenre(token[0], inputGenre);
                clearText(mSearchTextField);
            } else {
                mMovieRequest.beginSearchRequest(userInput);
                clearText(mSearchTextField);
            }
        }
    }

    public void setFeedbackText(String txt){
        text.setText(txt);
    }

    public RetrieveRequest getAPIRequester(){
        return mMovieRequest;
    }


    @FXML private void clearSearchButtonClicked()
    {
        mSearchTextField.clear();
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