package compal;

import compal.commons.LogUtils;
import compal.logic.LogicManager;
import compal.logic.command.CommandResult;
import compal.logic.command.ListCommand;
import compal.logic.command.exceptions.CommandException;
import compal.logic.parser.exceptions.ParserException;
import compal.ui.Ui;
import compal.ui.UiManager;
import compal.ui.UiUtil;
import javafx.application.Application;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.logging.Logger;

/**
 * Initializes GUI.
 */
public class Main extends Application {

    private Ui ui;
    private UiUtil uiUtil;
    private LogicManager logicManager;
    private static final Logger logger = LogUtils.getLogger(Main.class);

    /**
     * Constructs a new Main object.
     */
    public Main() {
        this.uiUtil = new UiUtil();
        this.ui = new UiManager(uiUtil);
        this.logicManager = new LogicManager();
    }

    public static void main(String[] args) {
        logger.info("Starting COMPal");
        launch(args);
    }

    /**
     * Initializes and sets up the GUI.
     *
     * @param primaryStage The stage for GUI.
     */
    @Override
    public void start(Stage primaryStage) throws ParseException, ParserException, CommandException {
        ui.start(primaryStage);

        CommandResult cmdResult = logicManager.logicExecute("list");
        if (ListCommand.LIST_EMPTY.equals(cmdResult.feedbackToUser)) {
            createData();
        }

        cmdResult = logicManager.logicExecute("view week");
        uiUtil.printg("Hello there!\n\n" + cmdResult.feedbackToUser);
    }

    private void createData() throws ParseException, ParserException, CommandException {

        //monday
        logicManager.logicExecute("event CS2101 Tutorial /date 30/09/2019 03/10/2019 "
            + "/start 0800 /end 1000 /final-date 14/11/2019 /priority high");
        logicManager.logicExecute("event GEQ TUT /date 30/09/2019 /start 1000 /end 1200 "
            + "/final-date 04/11/2019 /priority medium /interval 14");
        logicManager.logicExecute("event CS2105 TUT /date 30/09/2019 "
            + "/start 1300 /end 1400 /priority high /final-date 11/11/2019");
        logicManager.logicExecute("event CS2105 Lecture /date 30/09/2019 /start 1400 /end 1600 "
            + "/final-date 14/11/2019 /priority high");
        logicManager.logicExecute("event CS2106 Lab /date 30/09/2019 /start 1600 "
            + "/end 1700 /final-date 14/11/2019 /priority medium");
        logicManager.logicExecute("event Lunch Appointment /date 18/11/2019 /start 1200 /end 1400 "
            + "/priority medium");

        logicManager.logicExecute("event ST2334 Lecture /date 01/10/2019 03/10/2019"
            + " /start 1000 /end 1200 /final-date 14/11/2019");
        logicManager.logicExecute("event CCA Training /date 01/10/2019 03/10/2019"
            + " /start 1900 /end 2200 /final-date 14/11/2019 /priority medium");

        logicManager.logicExecute("event CS2106 TUT /date 02/10/2019 /start 1000 "
            + "/end 1100 /final-date 13/11/2019 /priority medium");
        logicManager.logicExecute("event CS2106 Lecture /date 02/10/2019 /start 1400 /end 1600 "
            + "/final-date 13/11/2019 /priority low");
        logicManager.logicExecute("event CS2113T TUT /date 02/10/2019 /start 1700 "
            + "/end 1800 /final-date 13/11/2019 /priority high");
        logicManager.logicExecute("event Lunch Appointment /date 20/11/2019 /start 1330 /end 1430 "
                + "/priority medium");

        logicManager.logicExecute("event CS2113T Lecture /date 04/10/2019 /start 1600 /end 1800"
            + " /final-date 15/11/2019 /priority high");

        logicManager.logicExecute("event Part-time Job /date 10/08/2019 /start 0800 /end 1600"
                + " /final-date 23/11/2019 /priority medium");

        logicManager.logicExecute("deadline CS2106 Assignment 3 /date 16/10/2019 /end 1400 /priority low");
        logicManager.logicExecute("deadline CS2106 Assignment 4 /date 02/10/2019 /end 1400 /priority low");
        logicManager.logicExecute("deadline CS2106 Assignment 5 /date 16/10/2019 "
            + "/end 1400 /priority medium");
        logicManager.logicExecute("deadline CS2113T Weekly Update /date 01/10/2019 "
            + "/end 2359 /final-date 04/11/2019 /priority high");
        logicManager.logicExecute("deadline CS2105 Assignment 1 /date 09/10/2019 /end 2359 /priority high");
        logicManager.logicExecute("deadline CS2105 Assignment 2 /date 30/10/2019 /end 2359 /priority high");
        logicManager.logicExecute("deadline CS2105 Assignment 3 /date 13/11/2019 /end 2359 /priority high");
        logicManager.logicExecute("deadline GEQ1000 Philosophy Quiz /date 08/09/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Physics Quiz /date 22/09/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Computational Thinking Quiz /date 13/10/2019 /end 2359 "
            + "/priority low");
        logicManager.logicExecute("deadline GEQ1000 Engineering Quiz /date 27/10/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Economics Quiz /date 10/11/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Design Quiz /date 17/11/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Design Wallet /date 17/11/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Final Reflection Draft /date 20/10/2019 /end 2359 /priority low");
        logicManager.logicExecute("deadline GEQ1000 Final Reflection Paper /date 17/11/2019 /end 2359 "
            + "/priority low");
        logicManager.logicExecute("deadline CS2101 Prepare OP2 Slides & Script /date 02/11/2019 /end 2359 "
            + "/priority high");
        logicManager.logicExecute("deadline CS2101/CS2113T PPP Submission /date 11/11/2019 /end 2359 /priority high");
        logicManager.logicExecute("deadline CS2101/CS2113T UG Submission /date 11/11/2019 /end 2359 /priority high");
        logicManager.logicExecute("deadline CS2101/CS2113T DG Submission /date 11/11/2019 /end 2359 /priority high");

        logicManager.logicExecute(" event CS2113T Exam /date 23/11/2019"
            + " /start 1300 /end 1500 /priority high");
        logicManager.logicExecute(" event CS2105 Exam /date 02/12/2019"
            + "/start 1700 /end 1900 /priority high");
        logicManager.logicExecute("event CS2106 Exam "
            + "/date 03/12/2019 /start 0900 /end 1100 /priority high");
        logicManager.logicExecute("event ST2334 Exam "
            + "/date 03/12/2019 /start 1700 /end 1900 /priority high");
    }
}
