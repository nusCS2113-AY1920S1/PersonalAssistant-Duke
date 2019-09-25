package javafx;

import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 *
 * @author Lee Zhen Yu
 * @version %I%
 * @since 1.0
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
