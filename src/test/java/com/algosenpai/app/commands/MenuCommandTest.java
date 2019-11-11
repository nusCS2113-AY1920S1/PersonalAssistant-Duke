package com.algosenpai.app.commands;

import com.algosenpai.app.logic.Logic;
import com.algosenpai.app.stats.UserStats;
import com.algosenpai.app.ui.Ui;
import com.algosenpai.app.ui.components.DialogBox;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public class MenuCommandTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MenuCommandTest.class.getResource("/view/MainWindow.fxml"));
        AnchorPane ap = fxmlLoader.load();
        Scene scene = new Scene(ap, 500, 650);
        stage.setScene(scene);
        UserStats stats = new UserStats("./UserData.txt");
        Logic logic = new Logic(stats);
        fxmlLoader.<Ui>getController().setLogic(logic, stats, false);
        stage.setResizable(false);
        stage.setTitle("AlgoSenpai Adventures");
        stage.show();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        FxToolkit.hideStage();
    }

    @Test
    void testMenuMousePress() {
        clickOn("#userInput").write("menu");
        clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("menu", actualText);
    }

    @Test
    void testMenuKeyPress() {
        clickOn("#userInput").write("menu").press(KeyCode.ENTER);
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("menu", actualText);
    }

    @Test
    void testMenuWithSpace() {
        clickOn("#userInput").write(" menu ").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(1);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals(" menu ", actualText);
    }

    @Test
    void testMenuOutput() {
        clickOn("#userInput").write("menu").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("Senpai will teach you! Try these commands :\n"
                + "Critical :\n"
                + "*\tlecture\n"
                + "*\tquiz\n"
                + "*\tarcade\n"
                + "*\treset\n"
                + "*\texit\n"
                + "\n"
                + "Utility :\n"
                + "*\treview\n"
                + "*\thistory\n"
                + "*\tdelete\n"
                + "*\thelp\n"
                + "*\tprint\n"
                + "*\tarchive\n"
                + "*\tsave\n"
                + "\n"
                + "Misc :\n"
                + "*\tchapters\n"
                + "*\tclear\n"
                + "*\tvolume\n"
                + "*\tresult\n"
                + "*\tstats\n"
                + "Type `menu <command>` to see how to use certain commands.\n", actualText);
    }

    @Test
    void testMenuLectureCommandOutput() {
        clickOn("#userInput").write("menu lecture").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to pick a chapter that you are "
                + "interested in revising.\n"
                + "The lecture ends after all the slides are done or if you type `end`.\n"
                + "Format :\n"
                + "`lecture` will list the current chapters available for the lecture.\n"
                + "`lecture sorting` will pick the lecture on sorting.\n"
                + "**Tips\n"
                + "You can start the chapter after you selected a chapter using `start`.\n", actualText);
    }

    @Test
    void testMenuQuizCommandOutput() {
        clickOn("#userInput").write("menu quiz").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to pick a chapter that you are interested in"
                + " practising.\n"
                + "The quiz ends after 10 questions or if you type `end`.\n"
                + "Format :\n"
                + "`quiz` will list the current chapters available for the lecture.\n"
                + "`quiz linkedlist` will pick the quiz questions from the Linked List chapter.\n"
                + "**Tips\n"
                + "You can start the chapter after you selected a chapter using `start`.\n", actualText);
    }

    @Test
    void testMenuArcadeCommandOutput() {
        clickOn("#userInput").write("menu arcade").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to play the arcade version of the quiz.\n"
                + "The game ends only when you get a wrong answer.\n"
                + "Format :\n"
                + "`arcade` will start the arcade immediately.\n"
                + "**Tips\n"
                + "You can use this mode during your final revision to consolidate your learning.\n", actualText);
    }

    @Test
    void testMenuResetCommandOutput() {
        clickOn("#userInput").write("menu reset").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to reset all the progress you have made.\n"
                + "This is for users who wish to achieve higher stats after clearing all the chapters.\n"
                + "Format :\n"
                + "`reset` will reset the game.\n", actualText);
    }

    @Test
    void testMenuExitCommandOutput() {
        clickOn("#userInput").write("menu exit").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to exit the game, and can be done anytime in"
                + " the game.\n"
                + "Format :\n"
                + "`exit` will exit the game.\n", actualText);
    }

    @Test
    void testMenuReviewCommandOutput() {
        clickOn("#userInput").write("menu review").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to review the questions which has went wrong "
                + "during your quiz.\n"
                + "Format :\n"
                + "`review 2` will show how the answer was derived for Question 2 of your last quiz.\n", actualText);
    }

    @Test
    void testMenuHistoryCommandOutput() {
        clickOn("#userInput").write("menu history").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to see the past commands you have made.\n"
                + "Format :\n"
                + "`history 5` will show the past 5 commands made by you.\n"
                + "**Tips\n"
                + "The history command can only display as many commands as you have made.\n", actualText);
    }

    @Test
    void testMenuDeleteCommandOutput() {
        clickOn("#userInput").write("menu delete").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to delete a particular number of chat bubbles "
                + "on the screen.\n"
                + "Format :\n"
                + "`delete 5` will delete the past 5 chat bubbles on the screen.\n"
                + "**Tips\n"
                + "The delete command can be used as an alternative to the clear command in the event that\n"
                + "you do not want to clear all the past chat bubbles.\n", actualText);
    }

    @Test
    void testMenuHelpCommandOutput() {
        clickOn("#userInput").write("menu help").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command will recommend you problems that you can find on Kattis that "
                + "are suitable to\n"
                + "your current proficiency level. Your proficiency is measured by the percentage of questions that\n"
                + "you get correct for that particular chapter.\n"
                + "Format :\n"
                + "`help bitmask` will list recommended questions from Kattis on the bitmask topic.\n"
                + "that can help you to improve and enhance your understanding on the topic.\n"
                + "**Tips\n"
                + "You can practise the questions available after doing the quizzes and attempting the lectures\n"
                + " so that you will be able to apply the concepts you've learnt to solve algorithmic problems.\n",
                actualText);
    }

    @Test
    void testMenuPrintCommandOutput() {
        clickOn("#userInput").write("menu print").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to print different documents into a pdf format.\n"
                + "Format :\n"
                + "`print` will show you the exact format for the command.\n"
                + "`print archive archive.pdf` will print your archived questions into a pdf named archive.pdf.\n"
                + "`print quiz quiz.pdf` will print the current quiz into a pdf named quiz.pdf.\n"
                + "`print user user.pdf` will print your stats into a pdf called user.pdf.\n", actualText);
    }

    @Test
    void testMenuArchiveCommandOutput() {
        clickOn("#userInput").write("menu archive").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to archive particular questions from the quiz.\n"
                + "Format :\n"
                + "`archive 1` will archive the first question of the quiz.\n"
                + "**Tips\n"
                + "Use this to save questions that you are unsure of, and want to review at a "
                + "later date.\n", actualText);
    }

    @Test
    void testMenuSaveCommandOutput() {
        clickOn("#userInput").write("menu save").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to save your current progress.\n"
                + "Format :\n"
                + "`save` will save the game.\n", actualText);
    }

    @Test
    void testMenuChaptersCommandOutput() {
        clickOn("#userInput").write("menu chapters").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to see all the chapters that are "
                + "currently available in the game.\n"
                + "Format :\n"
                + "`chapters` will display all the chapters.\n", actualText);
    }

    @Test
    void testMenuClearCommandOutput() {
        clickOn("#userInput").write("menu clear").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to clear the screen should you "
                + "feel that it is too cluttered.\n"
                + "Format :\n"
                + "`clear` will clear the screen.\n"
                + "**Tips\n"
                + "This is useful for when there are too many messages on your screen.\n", actualText);
    }

    @Test
    void testMenuVolumeCommandOutput() {
        clickOn("#userInput").write("menu volume").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command allows you to change the volume.\n"
                + "Format :\n"
                + "`volume` will show you the correct format.\n"
                + "`volume 0` will set the volume to 0.\n"
                + "`volume 100` will set it to the maximum volume.\n", actualText);
    }

    @Test
    void testMenuResultCommandOutput() {
        clickOn("#userInput").write("menu result").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command displays the previous result attained by you.\n"
                + "Format :\n"
                + "`result` will display the previous result attained by the latest quiz.\n", actualText);
    }

    @Test
    void testMenuStatsCommandOutput() {
        clickOn("#userInput").write("menu stats").clickOn("#sendButton");
        VBox container = find();
        DialogBox dialogBox = (DialogBox) container.getChildren().get(2);
        String actualText = dialogBox.getDialog().getText();
        Assertions.assertEquals("This command will show you the current statistics in game attained by you.\n"
                + "Format :\n"
                + "`stats` will display the current statistics from different chapters.\n", actualText);
    }



    <T extends Node> T find() {
        return lookup("#dialogContainer").query();
    }
}