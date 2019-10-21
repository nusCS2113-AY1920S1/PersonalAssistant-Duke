package com.algosenpai.app.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private Text appTitle;

    @FXML
    private ImageView appImage;

    /**
     * Initialize home scene.
     */
    public SplashScreenController() {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        appTitle.setText("Welcome to AlgoSenpai Adventures!");
        appTitle.setX(60);
        appTitle.setY(150);
        appTitle.setFont(Font.font(30));
        appTitle.setFill(Color.DEEPPINK);
        Image image = new Image(getClass().getResourceAsStream("/images/miku.png"));
        appImage.setImage(image);
        appImage.setFitHeight(300);
        appImage.setFitWidth(300);
        appImage.setX(50);
        appImage.setY(150);
    }
}