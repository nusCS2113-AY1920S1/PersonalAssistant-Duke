package duke;

import javafx.application.Application;

//@@author talesrune-reused
//Reused from https://github.com/nusCS2113-AY1920S1/duke/blob/master/tutorials/javaFxTutorialPart4.md with minor modifications
/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}