package com.algosenpai.app.controller;

import com.algosenpai.app.Parser;
import com.algosenpai.app.constant.ViewConstant;
import com.algosenpai.app.constant.ImagesConstant;
import com.algosenpai.app.constant.SoundConstant;
import com.algosenpai.app.constant.ResourcePathConstant;
import com.algosenpai.app.constant.ViewEnum;
import com.algosenpai.app.constant.ImagesEnum;
import com.algosenpai.app.constant.SoundEnum;
import com.algosenpai.app.constant.JavaFxConstant;
import com.algosenpai.app.utility.ResourceRandomUtility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DialogPane;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class SceneController extends Application {

    protected Text sceneText;

    private static MusicController musicController;

    private static Stage stage;

    private static Pane root;

    @Override
    public void start(Stage stage) throws Exception {
        SceneController.stage = stage;
        SceneController.musicController = new MusicController();
        changeSceneOnKeyPressed(ViewConstant.homeView, ImagesConstant.homeImages, SoundConstant.homeSound);
    }

    private static Stage getStage() {
        return stage;
    }

    static void toggleVolume() {
        MusicController.toggleVolume();
    }

    public static void setRoot(Pane root) {
        SceneController.root = root;
    }

    void displayBubble(StackPane stackPane, String str, int radius, Color color) {
        Circle circle = new Circle();
        circle.setRadius(radius);
        circle.setFill(color);
        Text text = new Text(str);
        stackPane.getChildren().addAll(circle, text);
    }

    void displayScrollPane(ScrollPane scrollPane, int top, int right, int bottom, int left, int width, int height,
                           int bgcRed, int bgcGreen, int bgcBlue, double bgcOpacity,
                           int bgRed, int bgGreen, int bgBlue, double bgOpacity) {
        scrollPane.setPadding(new Insets(top, right, bottom, left));
        scrollPane.setPrefViewportWidth(width);
        scrollPane.setPrefViewportHeight(height);
        scrollPane.setStyle(
                "-fx-background-color: rgb(" + bgcRed + ", " + bgcGreen + ", " + bgcBlue + ", " + bgcOpacity + "); "
                        + "-fx-background: rgb(" + bgRed + ", " + bgGreen + ", " + bgBlue + ", " + bgOpacity + ");");
    }

    void displayDialogPane(DialogPane dialogPane, double opacity, int scaleX, int scaleY,
                           int red, int green, int blue, int radii) {
        dialogPane.setOpacity(opacity);
        dialogPane.setScaleX(scaleX);
        dialogPane.setScaleY(scaleY);
        dialogPane.setBackground(new Background(
                new BackgroundFill(Color.rgb(red, green, blue), new CornerRadii(radii), Insets.EMPTY)));
    }

    void displayCommandList(Pane container, ArrayList<String> commandsList,
                            int red, int green, int blue, boolean bold, int fontSize, String fontStyle,
                            double downShift, double rightShift, int spacing) {
        for (int i = 0; i < commandsList.size(); i++) {
            Text text = new Text(commandsList.get(i));
            setTextStyle(text, red, green, blue, bold, fontSize, fontStyle);
            setNodePos(text, downShift + i * spacing, rightShift);
            container.getChildren().add(text);
        }
    }

    void displayCharacterImage(ImageView characterImage, String imageName, int height, int width) {
        Image image = new Image(getClass().getResourceAsStream(
                ResourcePathConstant.imagesResourcePath + imageName));
        characterImage.setImage(image);
        characterImage.setFitHeight(height);
        characterImage.setFitWidth(width);
    }

    void changeSceneOnKeyPressed(
            Map<ViewEnum, String> view,
            Map<ImagesEnum, String> image,
            Map<SoundEnum, String> sound) throws IOException {
        MusicController.playMusic(ResourceRandomUtility.randomResources(sound));
        changeScene(ResourceRandomUtility.randomResources(view), ResourceRandomUtility.randomResources(image));
    }

    private void changeScene(String sceneName, String imageName) throws IOException {
        Pane root = FXMLLoader.load(getClass().getResource(
                ResourcePathConstant.viewResourcePath + sceneName));
        SceneController.root = root;
        Scene scene = new Scene(root, JavaFxConstant.windowWidth, JavaFxConstant.windowHeight);
        (SceneController.getStage()).setScene(scene);
        stage.setResizable(false);
        stage.setTitle(JavaFxConstant.windowTitle);
        stage.show();
        changeBackgroundImage(imageName);
    }

    void changeBackgroundImage(String imageName) {
        String fxBackgroundImageStyle = getFxBackgroundImageStyle(
                ResourcePathConstant.imagesResourcePath + imageName);
        root.setStyle(fxBackgroundImageStyle);
    }

    private String getFxBackgroundImageStyle(String imageName) {
        return "-fx-background-image: url('" + imageName + "'); -fx-background-size: cover;";
    }

    void setNodePos(Node node, double downShift, double rightShift) {
        node.applyCss();
        final double width = node.getLayoutBounds().getWidth();
        node.setTranslateX((double) JavaFxConstant.windowWidth / 2 - width / 2 + rightShift);
        node.setTranslateY(downShift);
    }

    void setTextStyle(Node node, int red, int green, int blue, boolean bold, int fontSize, String fontStyle) {
        node.applyCss();
        node.setStyle("-fx-fill:rgb(" + red + "," + green + "," + blue + "); -fx-font: " + fontSize
                + " " + fontStyle + ";" + (bold ? "-fx-font-weight:bold;" : ""));
    }
}