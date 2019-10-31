package javacake.ui;

import javacake.JavaCake;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.logging.Level;

public class Handler {

    private static JavaCake javaCake;
    /*public void handleExit() {
        System.out.println("EXIT");
        JavaCake.logger.log(Level.INFO, "EXITING PROGRAM!");
        // find out if exit condition
        MainWindow.setExitToTrue();
        MainWindow.setResponse(javaCake.getResponse(MainWindow.getInput()));
        MainWindow.showContentContainer();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> MainWindow.primaryStage.hide());
        pause.play();
    }*/

}
