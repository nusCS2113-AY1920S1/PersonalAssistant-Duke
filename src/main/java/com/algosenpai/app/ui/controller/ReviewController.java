package com.algosenpai.app.ui.controller;

import com.algosenpai.app.logic.constant.ViewConstant;
import com.algosenpai.app.logic.constant.ImagesConstant;
import com.algosenpai.app.logic.constant.SoundConstant;
import com.algosenpai.app.model.ReviewQuestionListModel;
import com.algosenpai.app.model.ReviewTracingListModel;
import com.algosenpai.app.model.ReviewQuestionModel;
import com.algosenpai.app.model.Triplet;
import com.algosenpai.app.model.ReviewTracingModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;
import java.util.LinkedList;
import java.util.Queue;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ReviewController extends SceneController implements Initializable {

    @FXML
    private Text sceneTitle;

    @FXML
    private TextField userInput;

    @FXML
    private ImageView characterImage;

    @FXML
    private ScrollPane reviewPane;

    private AnimationTimerController backgroundSceneTimer;

    private ReviewQuestionListModel reviewQuestionListModel;

    private ReviewTracingListModel reviewTracingListModel;

    private Queue<Triplet<ReviewTracingListModel, ReviewQuestionModel, Integer>> animation;

    /**
     * Initializes questions and ui for review scene.
     */
    public ReviewController() {
        reviewQuestionListModel = new ReviewQuestionListModel();
        reviewTracingListModel = new ReviewTracingListModel();
        animation = new LinkedList<>();
        handle();
        setUpReview();
    }

    private void setUpReview() {
        // setup sample questions for review scene
        for (int i = 0; i < 6; i++) {
            ReviewQuestionModel reviewQuizModel = new ReviewQuestionModel();
            reviewQuizModel.setQuestion("Q" + i + ") What is the sequence of numbers after 3 swaps using bubble sort?");
            reviewQuizModel.setMyAnswer("Your Answer: 3, 2, 1, 5, 4");
            reviewQuizModel.setActualAnswer("Correct Answer: 1, 2, 3, 4, 5");
            reviewQuizModel.setList(new ArrayList<>() {{
                    add(5);
                    add(1);
                    add(2);
                    add(3);
                    add(4);
                }
            });
            reviewQuestionListModel.addReviewQuizModel(reviewQuizModel);
        }
        for (int i = 0; i < 4; i++) {
            reviewTracingListModel.addReviewTracingModel(new ReviewTracingModel(i, i + 1, "C"));
            reviewTracingListModel.addReviewTracingModel(new ReviewTracingModel(i, i + 1, "S"));
        }
        for (int i = 0; i < 4; i++) {
            reviewTracingListModel.addReviewTracingModel(new ReviewTracingModel(i, i + 1, "C"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userInput.setPrefWidth(500.0);
        setNodePos(userInput, 500.0, -250);

        displayCharacterImage(characterImage, "miku.png", 400, 400);
        setNodePos(characterImage, 250.0, -300);

        VBox scrollingPaneVBox = new VBox();
        scrollingPaneVBox.setSpacing(30);
        for (int i = 0; i < reviewQuestionListModel.size(); i++) {
            ReviewQuestionModel reviewQuizModel = reviewQuestionListModel.get(i);
            loadQuestion(scrollingPaneVBox, reviewQuizModel.getQuestion(),
                    0, 0, 0, false, 20, "arial", 600);
            loadQuestion(scrollingPaneVBox, reviewQuizModel.getMyAnswer(),
                    0, 0, 0, false, 15, "arial", 600);
            loadQuestion(scrollingPaneVBox, reviewQuizModel.getActualAnswer(),
                    0, 0, 0, false, 15, "arial", 600);
            ArrayList<Integer> animationList = (ArrayList<Integer>) reviewQuizModel.getList();
            FlowPane flowPane = new FlowPane();
            loadAnimation(flowPane, animationList, 20, Color.LIGHTGREEN, 20);
            scrollingPaneVBox.getChildren().add(flowPane);
        }

        displayScrollPane(reviewPane, 20, 20, 20, 20, 600, 550,
                255, 255, 255, 0.3,
                255, 255, 255, 0);
        reviewPane.setContent(scrollingPaneVBox);
        setNodePos(reviewPane, 0, -150);
    }

    private void loadQuestion(VBox scrollingPaneVBox, String str, int red, int green, int blue,
                              boolean bold, int fontSize, String fontStyle, int wrappingWidth) {
        Text text = new Text(str);
        setTextStyle(text, red, green, blue, bold, fontSize, fontStyle);
        text.setWrappingWidth(wrappingWidth);
        scrollingPaneVBox.getChildren().add(text);
    }

    private void loadAnimation(FlowPane flowPane, ArrayList<Integer> animationList, int radius, Color color, int
            flowPaneHGap) {
        for (int bubbleNode: animationList) {
            StackPane stackPane = new StackPane();
            displayBubble(stackPane, Integer.toString(bubbleNode), radius, color);
            flowPane.setHgap(flowPaneHGap);
            flowPane.getChildren().add(stackPane);
        }
    }

    private void handle() {

        backgroundSceneTimer = new AnimationTimerController(1000) {
            private boolean isRunning = false;
            int tracingIndex;
            ReviewTracingListModel steps;
            ReviewQuestionModel original;
            int reviewIndex;
            private boolean isReset = false;

            private void unpack(Triplet<ReviewTracingListModel, ReviewQuestionModel, Integer> triplet) {
                isRunning = true;
                tracingIndex = 0;
                steps = triplet.getFirst();
                original = triplet.getSecond();
                reviewIndex = triplet.getThird();
            }

            private void reset(ArrayList<Integer> animationList) {
                FlowPane flowPane = new FlowPane();
                loadAnimation(flowPane, animationList, 20, Color.LIGHTGREEN, 20);
                VBox scrollingPaneVBox = (VBox) reviewPane.getContent();
                scrollingPaneVBox.getChildren().set(3 + 4 * reviewIndex, flowPane);
                isReset = false;
            }

            private void end() {
                isRunning = false;
                isReset = true;
                animation.remove();
            }

            @Override
            public void handle() {
                if (isReset) {
                    reset((ArrayList<Integer>) original.getList());
                }
                if (!animation.isEmpty()) {
                    if (!isRunning) {
                        unpack(animation.peek());
                    }
                    if (tracingIndex == steps.size()) {
                        end();
                        ReviewTracingModel previous = steps.getReviewTracingModel(tracingIndex - 1);
                        if (previous.getType().equals("C")) {
                            compareAnimation(previous, Color.LIGHTGREEN, reviewIndex);
                        }
                    }
                    if (tracingIndex < steps.size()) {
                        if (tracingIndex > 0) {
                            ReviewTracingModel previous = steps.getReviewTracingModel(tracingIndex - 1);
                            if (previous.getType().equals("C")) {
                                compareAnimation(previous, Color.LIGHTGREEN, reviewIndex);
                            }
                        }
                        ReviewTracingModel current = steps.getReviewTracingModel(tracingIndex);
                        if (current.getType().equals("C")) {
                            compareAnimation(current, Color.RED, reviewIndex);
                        }
                        if (current.getType().equals("S")) {
                            swapAnimation(current, reviewIndex);
                        }
                        tracingIndex += 1;
                    }
                }
            }
        };
        backgroundSceneTimer.start();
    }

    private Pair<StackPane, StackPane> getPairOfBubbles(ReviewTracingModel reviewTracingModel, int reviewIndex) {
        int indexLeft = reviewTracingModel.getIndexLeft();
        int indexRight = reviewTracingModel.getIndexRight();
        VBox scrollingPaneVBox = (VBox) reviewPane.getContent();
        FlowPane scrollingPaneHBox = (FlowPane) scrollingPaneVBox.getChildren().get(3 + 4 * reviewIndex);
        StackPane stackPaneLeft = (StackPane) scrollingPaneHBox.getChildren().get(indexLeft);
        StackPane stackPaneRight = (StackPane) scrollingPaneHBox.getChildren().get(indexRight);
        return new Pair<>(stackPaneLeft, stackPaneRight);
    }

    private void compareAnimation(ReviewTracingModel reviewTracingModel, Color color, int reviewIndex) {
        Pair<StackPane, StackPane> pairOfBubbles = getPairOfBubbles(reviewTracingModel, reviewIndex);
        Circle circleLeft = (Circle) pairOfBubbles.getKey().getChildren().get(0);
        circleLeft.setFill(color);
        Circle circleRight = (Circle) pairOfBubbles.getValue().getChildren().get(0);
        circleRight.setFill(color);

    }

    private void swapAnimation(ReviewTracingModel reviewTracingModel, int reviewIndex) {
        Pair<StackPane, StackPane> pairOfBubbles = getPairOfBubbles(reviewTracingModel, reviewIndex);
        Text textLeft = (Text) pairOfBubbles.getKey().getChildren().get(1);
        Text textRight = (Text) pairOfBubbles.getValue().getChildren().get(1);
        Text tmp = new Text(textLeft.getText());
        textLeft.setText(textRight.getText());
        textRight.setText(tmp.getText());
    }

    private void loadReview(int i) {
        animation.add(new Triplet<>(reviewTracingListModel, reviewQuestionListModel.get(i), i));
    }

    /**
     * Handle shortcut key inputs.
     * @param keyEvent key inputs.
     * @throws IOException key input error.
     */
    @FXML
    public void handleKeyPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.H) {
            changeSceneOnKeyPressed(ViewConstant.homeView, ImagesConstant.homeImages, SoundConstant.homeSound);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.G) {
            changeSceneOnKeyPressed(ViewConstant.girlsView, ImagesConstant.girlsImages, SoundConstant.girlsSound);
            backgroundSceneTimer.stop();
        }
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            userInput.getParent().requestFocus();
        }
        if (keyEvent.getCode() == KeyCode.M) {
            toggleVolume();
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (userInput.getText().startsWith("/review")) {
                String []c = userInput.getText().split(" ");
                int reviewIndex = Integer.parseInt(c[1]);
                loadReview(reviewIndex);
            }
        }
    }

    /**
     * Handle mouse clicking inputs.
     * @param mouseEvent mouse inputs.
     */
    @FXML
    public void handleMouseClicked(MouseEvent mouseEvent) {
        if (!userInput.equals(mouseEvent.getSource())) {
            userInput.getParent().requestFocus();
        }
    }
}