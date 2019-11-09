package javacake.unused;

import javacake.JavaCake;
import javacake.ui.MainWindow;

public class Handler {

    private static JavaCake javaCake;
    private static MainWindow mw = new MainWindow();

    /*void handleExit() {
        System.out.println("EXIT");
        JavaCake.logger.log(Level.INFO, "EXITING PROGRAM!");
        // find out if exit condition
        AvatarScreen.avatarMode = AvatarScreen.AvatarMode.SAD;
        mw.setIsExitToTrue();
        String input = mw.getInput();
        mw.setResponse(input);
        mw.getShowContentContainer();
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> mw.hidePrimaryStage());
        pause.play();
    }*/


}
