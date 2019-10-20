package seedu.hustler;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import seedu.hustler.game.achievement.AchievementList;
import seedu.hustler.parser.DateTimeParser;
import seedu.hustler.task.Reminders;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Controller for MainWindow. Provides the layout for the other controls.
 */
public class MainWindow extends AnchorPane{
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private TextArea console;
    @FXML
    private PrintStream ps;

    @FXML
    private Button sendButton;

    @FXML
    private Button task;

    @FXML
    private Button taskCompletionMode;

    @FXML
    private Button achievement;

    @FXML
    private Button statistics;

    @FXML
    private Button avatar;

    @FXML
    private Button shop;

    @FXML
    private Button arena;

    @FXML
    private Button settings;

    @FXML
    private ImageView menu;

    @FXML
    private ImageView timer;

    @FXML
    private ImageView trophy;

    @FXML
    private ImageView spiderGraph;

    @FXML
    private ImageView profile;

    @FXML
    private ImageView trolley;

    @FXML
    private ImageView swords;

    @FXML
    private ImageView gear;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane welcomeScreen;

    private Hustler hustler;

    @FXML
    private FlowPane flowPane = new FlowPane();

    @FXML
    private ScrollPane scrollPANEE;

    @FXML
    private FlowPane heading;

    /**
     * Initializes essential components to run Hustler.
     * @throws IOException if text area could not be found.
     */
    @FXML
    public void initialize() throws IOException {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        ps = new PrintStream(new Console(console));
        System.setOut(ps);
        System.setErr(ps);
        Hustler.initialize();
        StackPane stackPane = new StackPane();
        welcomeScreen.setStyle("-fx-background-color:#2dcb70");
        Rectangle whiteSpace = new Rectangle();
        whiteSpace.setOpacity(0.0);
        whiteSpace.setHeight(50);
        whiteSpace.widthProperty().bind(flowPane.widthProperty());
        Text text = new Text("Hi, I am Hustler!\n What can I do for you?");
        text.setFont(Font.font("Gill Sans", 30));
        text.setFill(Color.WHITE);
        text.setTextAlignment(TextAlignment.CENTER);
        stackPane.setMargin(text,new Insets(0,50,0,50));
        stackPane.setAlignment(text,Pos.CENTER);
        stackPane.getChildren().addAll(text);
        stackPane.prefWidthProperty().bind(welcomeScreen.widthProperty());
        welcomeScreen.getChildren().addAll(whiteSpace,stackPane);
    }

    public void setHustler(Hustler h) {
        hustler = h;
    }

    /**
     * Changes printing stream to text area in GUI.
     */
    public class Console extends OutputStream {
        private TextArea console;

        public Console(TextArea console) {
            this.console = console;
        }

        public void appendText(String valueOf) {
            Platform.runLater(() -> console.appendText(valueOf));
        }

        public void write(int b) {
            appendText(String.valueOf((char)b));
        }
    }

    /**
     * Handles operations after each user's input.
     */
    @FXML
    public void handleUserInput() throws IOException{

        if (!userInput.getText().isEmpty()) {
            removeWelcome();
            sendClicked();
            flowPane.setStyle("-fx-background-color:#ffffff");
            String input = userInput.getText();
            //Hustler.run(input);
            String token[] = input.split(" ");
            if(input.equals("achievement")) {
                achievementAction();
            } else if(token[0].equals("/add") || input.equals("list") || token[0].equals("done") || token[0].equals("delete")) {
                taskAction();
            } else if(input.equals("bye")) {
                Hustler.run("bye");
            } else {
                Hustler.run(userInput.getText());
                scrollPANEE.setContent(console);
                console.prefHeightProperty().bind(scrollPANEE.heightProperty());
            }
            userInput.clear();
            sendReleased();
        }
    }

    @FXML
    public void sendClicked() {
        ColorAdjust color = new ColorAdjust();
        color.setBrightness(-0.16);
        sendButton.setEffect(color);
    }

    @FXML
    public void sendReleased() {
        ColorAdjust color = new ColorAdjust();
        color.setBrightness(0);
        sendButton.setEffect(color);
    }

    @FXML
    public void removeWelcome() {
        rootPane.getChildren().remove(welcomeScreen);
    }

    @FXML
    public void taskAction() throws IOException{

        heading.getChildren().clear();
        Text title1 = new Text("TASKS");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);

        Hustler.run(userInput.getText());
        flowPane.getChildren().clear();
        flowPane.setStyle("-fx-background-color:#ffffff");

        Rectangle whiteSpace0 = new Rectangle();
        whiteSpace0.setOpacity(0.0);
        whiteSpace0.setHeight(10);
        whiteSpace0.widthProperty().bind(scrollPANEE.widthProperty());
        flowPane.getChildren().add(whiteSpace0);


        Image dateTimeIcon = new Image(new FileInputStream("images/datetime.png"));


        flowPane.setVgap(10);
        for(int i = 0; i < Hustler.list.size(); i += 1) {
            ImageView imageView = new ImageView();
            imageView.setImage(dateTimeIcon);
            imageView.setFitHeight(10);
            imageView.setFitWidth(10);
            StackPane stackPane = new StackPane();
            Rectangle rect = new Rectangle();

            Text text = new Text(i + 1 + ". " + Hustler.list.get(i).getDescription());
            text.setFont(Font.font("Gill Sans", 20));
            text.setFill(Color.WHITE);
            rect.setOpacity(0.3);
            rect.setHeight(50);
            rect.widthProperty().bind(scrollPANEE.widthProperty());
            rect.setArcHeight(30.0);
            rect.setArcWidth(30.0);
            rect.setStyle("#ffffff");

            stackPane.setMargin(text,new Insets(0,0,3,50));
            stackPane.setAlignment(text,Pos.CENTER_LEFT);

            Image notDoneCheck = new Image(new FileInputStream("images/notDone.png"));
            ImageView notdoneCheckImage = new ImageView();
            notdoneCheckImage.setFitHeight(20);
            notdoneCheckImage.setFitWidth(20);
            notdoneCheckImage.setImage(notDoneCheck);


            Image doneCheck = new Image(new FileInputStream("images/done.png"));
            ImageView doneCheckImage = new ImageView();
            doneCheckImage.setFitHeight(25);
            doneCheckImage.setFitWidth(30);
            doneCheckImage.setImage(doneCheck);

            Button doneButton = new Button();
            doneButton.setGraphic(notdoneCheckImage);
            doneButton.setBackground(Background.EMPTY);
            stackPane.setAlignment(doneButton,Pos.CENTER_LEFT);
            stackPane.setMargin(doneButton, new Insets(0,0,0,10));

            if(Hustler.list.get(i).getStatusIcon().contains("\u2713")) {
                doneButton.setGraphic(doneCheckImage);
                stackPane.setMargin(doneButton, new Insets(0,0,0,5));
                text.setStrikethrough(true);
                text.setOpacity(0.5);
            }
            if(Hustler.list.get(i).getDateTime() == null) {
                stackPane.getChildren().addAll(rect, text,doneButton);
                flowPane.getChildren().add(stackPane);
            }
            else if(Hustler.list.get(i).getDateTime() != null) {
                Text dateTime = new Text(DateTimeParser.convertDateTime(Hustler.list.get(i).getDateTime()));
                stackPane.setMargin(dateTime, new Insets(0,0,3,70));
                stackPane.setAlignment(dateTime,Pos.BOTTOM_LEFT);
                stackPane.setMargin(imageView, new Insets(0,0,3,53));
                stackPane.setAlignment(imageView,Pos.BOTTOM_LEFT);

                dateTime.setFont(Font.font("Gill Sans", 10));
                ColorAdjust colorAdjust = new ColorAdjust();
                if(Reminders.checkOverdue(i)) {
                    colorAdjust.setContrast(0.12);
                    colorAdjust.setHue(-0.02);
                    colorAdjust.setSaturation(0.81);
                    imageView.setEffect(colorAdjust);
                    dateTime.setFill(Color.RED);
                } else if(Reminders.checkThirty(i)) {
                    colorAdjust.setContrast(0.12);
                    colorAdjust.setHue(0.2);
                    colorAdjust.setSaturation(0.81);
                    imageView.setEffect(colorAdjust);
                    dateTime.setFill(Color.ORANGE);
                } else if(Reminders.checkLastDay(i)) {
                    colorAdjust.setContrast(0.14);
                    colorAdjust.setHue(0.33);
                    colorAdjust.setSaturation(0.81);
                    imageView.setEffect(colorAdjust);
                    dateTime.setFill(Color.YELLOW);
                } else {
                    dateTime.setFill(Color.WHITE);
                }
                stackPane.getChildren().addAll(rect, text, dateTime,imageView, doneButton);
                flowPane.getChildren().add(stackPane);
            }
        }

        flowPane.prefWidthProperty().bind(scrollPANEE.widthProperty());
        scrollPANEE.setContent(flowPane);

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);
        menu.setEffect(color);
        timer.setEffect(null);
        trophy.setEffect(null);
        spiderGraph.setEffect(null);
        profile.setEffect(null);
        trolley.setEffect(null);
        swords.setEffect(null);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#243342");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#34495E");

        task.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
    }

    @FXML
    public void taskCompletionModeAction() {

        heading.getChildren().clear();
        Text title1 = new Text("TASK COMPLETION MODE");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);


        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();

        console.clear();

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(color);
        trophy.setEffect(null);
        spiderGraph.setEffect(null);
        profile.setEffect(null);
        trolley.setEffect(null);
        swords.setEffect(null);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#243342");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#34495E");

        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
    }

    @FXML
    public void achievementAction() throws IOException {

        heading.getChildren().clear();
        Text title1 = new Text("ACHIEVEMENTS");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);



        Image lock = new Image(new FileInputStream("images/locked_padlock.png"));
        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();
        Hustler.run("achievement");

        flowPane.setVgap(10);

        Rectangle whiteSpace0 = new Rectangle();
        whiteSpace0.setOpacity(0.0);
        whiteSpace0.setHeight(10);
        whiteSpace0.widthProperty().bind(scrollPANEE.widthProperty());
        flowPane.getChildren().add(whiteSpace0);

        StackPane SP = new StackPane();
        Rectangle R = new Rectangle();
        Text T = new Text("ACHIEVEMENTS UNLOCKED");
        T.setFont(Font.font("Gill Sans", 20));
        T.setFill(Color.WHITE);
        R.setOpacity(0.3);
        R.setHeight(30);

        R.widthProperty().bind(scrollPANEE.widthProperty());
        R.setArcHeight(30.0);
        R.setArcWidth(30.0);
        R.setStyle("#ffffff");


        SP.setAlignment(T,Pos.CENTER);
        SP.getChildren().addAll(R,T);
        flowPane.getChildren().add(SP);

        for(int i = 0; i < AchievementList.achievementList.size(); i += 1) {
            if(!AchievementList.achievementList.get(i).checkLock()) {
                StackPane stackPane = new StackPane();
                Rectangle rect = new Rectangle();
                Text text = new Text(AchievementList.achievementList.get(i).toString());
                text.setFont(Font.font("Gill Sans", 20));
                text.setFill(Color.WHITE);
                rect.setOpacity(0.3);
                rect.setHeight(50);
                rect.widthProperty().bind(scrollPANEE.widthProperty());
                rect.setArcHeight(30.0);
                rect.setArcWidth(30.0);
                rect.setStyle("#ffffff");
                stackPane.setMargin(text,new Insets(0,0,0,50));
                stackPane.setAlignment(text,Pos.CENTER_LEFT);
                stackPane.getChildren().addAll(rect, text);
                flowPane.getChildren().add(stackPane);
            }
        }
        Rectangle whiteSpace1 = new Rectangle();
        whiteSpace1.setOpacity(0.0);
        whiteSpace1.setHeight(10);
        whiteSpace1.widthProperty().bind(scrollPANEE.widthProperty());
        flowPane.getChildren().add(whiteSpace1);

        StackPane StackPanel = new StackPane();
        Rectangle Rec = new Rectangle();
        Text Txt = new Text("ACHIEVEMENTS LOCKED");
        Txt.setFont(Font.font("Gill Sans", 20));
        Txt.setFill(Color.WHITE);
        Rec.setOpacity(0.3);
        Rec.setHeight(50);
        Rec.widthProperty().bind(scrollPANEE.widthProperty());
        Rec.setArcHeight(30.0);
        Rec.setArcWidth(30.0);
        Rec.setStyle("#ffffff");
        StackPanel.setAlignment(Txt,Pos.CENTER);
        StackPanel.getChildren().addAll(Rec,Txt);
        flowPane.getChildren().add(StackPanel);

        for(int i = 0; i < AchievementList.achievementList.size(); i += 1) {
            if(AchievementList.achievementList.get(i).checkLock()) {
                ImageView imageview = new ImageView();
                imageview.setImage(lock);
                imageview.setFitHeight(30);
                imageview.setFitWidth(30);
                imageview.setOpacity(0.3);
                StackPane stackPane = new StackPane();
                Rectangle rect = new Rectangle();
                Text text = new Text(AchievementList.achievementList.get(i).toString());
                text.setFont(Font.font("Gill Sans", 20));
                text.setStyle("-fx-fill: white");
                text.setOpacity(0.3);
                rect.setOpacity(0.3);
                rect.setHeight(50);
                rect.widthProperty().bind(flowPane.widthProperty());
                rect.setArcHeight(30.0);
                rect.setArcWidth(30.0);
                rect.setStyle("#ffffff");
                stackPane.setMargin(text,new Insets(0,0,0,80));
                stackPane.setAlignment(text, Pos.CENTER_LEFT);
                stackPane.setMargin(imageview, new Insets(0,0,0,30));
                stackPane.setAlignment(imageview,Pos.CENTER_LEFT);
                stackPane.getChildren().addAll(rect,text,imageview);
                flowPane.getChildren().addAll(stackPane);
            }
        }

        flowPane.prefWidthProperty().bind(scrollPANEE.widthProperty());
        scrollPANEE.setContent(flowPane);

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(null);
        trophy.setEffect(color);
        spiderGraph.setEffect(null);
        profile.setEffect(null);
        trolley.setEffect(null);
        swords.setEffect(null);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#243342");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#34495E");

        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        achievement.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
    }

    @FXML
    public void statisticsAction() {

        heading.getChildren().clear();
        Text title1 = new Text("STATISTICS");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);


        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(null);
        trophy.setEffect(null);
        spiderGraph.setEffect(color);
        profile.setEffect(null);
        trolley.setEffect(null);
        swords.setEffect(null);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#243342");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#34495E");

        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));

    }

    @FXML
    public void avatarAction() {

        heading.getChildren().clear();
        Text title1 = new Text("AVATAR");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);


        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(null);
        trophy.setEffect(null);
        spiderGraph.setEffect(null);
        profile.setEffect(color);
        trolley.setEffect(null);
        swords.setEffect(null);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#243342");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#34495E");

        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        ps = new PrintStream(new Console(console));
        console.clear();
        Hustler.run("/avatar stats");
        scrollPANEE.setContent(console);
    }

    @FXML
    public void shopAction() {

        heading.getChildren().clear();
        Text title1 = new Text("SHOP");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        //titlePane.setMargin(title1, new Insets(0,0,0,50));
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);

        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(null);
        trophy.setEffect(null);
        spiderGraph.setEffect(null);
        profile.setEffect(null);
        trolley.setEffect(color);
        swords.setEffect(null);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#243342");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#34495E");

        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));

        ps = new PrintStream(new Console(console));
        console.clear();
        Hustler.run("/shop");
        scrollPANEE.setContent(console);

    }

    @FXML
    public void arenaAction() {

        heading.getChildren().clear();
        Text title1 = new Text("ARENA");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        //titlePane.setMargin(title1, new Insets(0,0,0,50));
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);


        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(null);
        trophy.setEffect(null);
        spiderGraph.setEffect(null);
        profile.setEffect(null);
        trolley.setEffect(null);
        swords.setEffect(color);
        gear.setEffect(null);

        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#243342");
        settings.setStyle("-fx-background-color:#34495E");

        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#ffffff"));
        settings.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
    }

    @FXML
    public void settingsAction() {

        heading.getChildren().clear();
        Text title1 = new Text("SETTINGS");
        title1.setFont(Font.font("Gill Sans", 15));
        title1.setFill(Color.GRAY);
        StackPane titlePane = new StackPane();
        titlePane.setAlignment(title1, Pos.CENTER);
        titlePane.prefHeightProperty().bind(heading.prefHeightProperty());
        titlePane.prefWidthProperty().bind(heading.widthProperty());
        titlePane.getChildren().addAll(title1);
        heading.getChildren().addAll(titlePane);

        flowPane.setStyle("-fx-background-color:#ffffff");
        flowPane.getChildren().clear();

        ColorAdjust color = new ColorAdjust();
        color.setContrast(0.35);
        color.setHue(-0.21);
        color.setBrightness(0.09);
        color.setSaturation(1.0);

        menu.setEffect(null);
        timer.setEffect(null);
        trophy.setEffect(null);
        spiderGraph.setEffect(null);
        profile.setEffect(null);
        trolley.setEffect(null);
        swords.setEffect(null);
        gear.setEffect(color);


        task.setStyle("-fx-background-color:#34495E");
        taskCompletionMode.setStyle("-fx-background-color:#34495E");
        achievement.setStyle("-fx-background-color:#34495E");
        statistics.setStyle("-fx-background-color:#34495E");
        avatar.setStyle("-fx-background-color:#34495E");
        shop.setStyle("-fx-background-color:#34495E");
        arena.setStyle("-fx-background-color:#34495E");
        settings.setStyle("-fx-background-color:#243342");

        achievement.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        task.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        taskCompletionMode.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        statistics.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        avatar.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        shop.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        arena.textFillProperty().setValue(Paint.valueOf("#95a5a6"));
        settings.textFillProperty().setValue(Paint.valueOf("#ffffff"));
    }



}