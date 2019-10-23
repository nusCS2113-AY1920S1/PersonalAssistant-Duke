package Results;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import movieRequesterAPI.RequestListener;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;

import java.util.ArrayList;

public class ResultHandler {

    public static RetrieveRequest mMovieRequest  = new RetrieveRequest(new RequestListener() {

        @FXML
        private Label mStatusLabel;

        @FXML
        private ProgressBar mProgressBar;

        @FXML
        private TextField mSearchTextField;

        @FXML
        private FlowPane mMoviesFlowPane;
        @FXML
        private ScrollPane mMoviesScrollPane;

        @Override
        public void requestCompleted(ArrayList<MovieInfoObject> moviesInfo) {
            System.out.println("HOOYRAY" + moviesInfo.size());
//            Platform.runLater(() -> buildMoviesFlowPane(moviesInfo));
        }

        @Override
        public void requestTimedOut() {

        }

        @Override
        public void requestFailed() {

        }
//
//        private AnchorPane buildMoviePosterPane(MovieInfoObject movie) {
//            try {
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(MovieUI.MovieHandler.class.getResource("MoviePoster.fxml"));
//                AnchorPane posterView = loader.load();
//                posterView.setOnMouseClicked((mouseEvent) -> moviePosterClicked(movie));
//
//                // set the movie info
//                MovieUI.MoviePosterController controller = loader.getController();
//                Image posterImage = new Image(movie.getFullPosterPath(), true);
//                posterImage.progressProperty().addListener((observable, oldValue, newValue) -> updateProgressBar(movie, newValue.doubleValue()));
//
//                controller.getMovieTitleLabel().setText(movie.getTitle());
//                controller.getPosterImageView().setImage(posterImage);
//
//                return posterView;
//            } catch (IOException ex){
//                Ui.printLine();
//            }
//
//            return null;
//        }
//
//        private void buildMoviesFlowPane(ArrayList<MovieInfoObject> movies)
//        {
//            // Setup progress bar and status label
//            mProgressBar.setProgress(0.0);
//            mProgressBar.setVisible(true);
//            mStatusLabel.setText("Loading..");
//
//            // Build a flow pane layout with the width and size of the
//            mMoviesFlowPane = new FlowPane(Orientation.HORIZONTAL);
//            mMoviesFlowPane.setHgap(4);
//            mMoviesFlowPane.setVgap(10);
//            mMoviesFlowPane.setPadding(new Insets(10, 8, 4, 8));
//            mMoviesFlowPane.prefWrapLengthProperty().bind(mMoviesScrollPane.widthProperty());   // bind to scroll pane width
//
//            for (MovieInfoObject movie : movies)
//            {
//                AnchorPane posterPane = buildMoviePosterPane(movie);
//                mMoviesFlowPane.getChildren().add(posterPane);
//            }
//
//            mMoviesScrollPane.setContent(mMoviesFlowPane);
//        }
    });



    public RetrieveRequest getmMovieRequest() {
        return mMovieRequest;
    }


}
