package compal.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class CalenderUtil {
    public TabPane tabWindow;

    /**
     * Constructor for Calender Util.
     */

    public CalenderUtil() {
        this.tabWindow = compal.ui.UiUtil.tabWindow;
    }

    /**
     * Refresh view date.
     *
     * @param dateToStore date to view of daily calender
     */
    public void dateViewRefresh(String dateToStore) {
        DailyCalUi dc = new DailyCalUi();
        tabWindow.getTabs().remove(1);
        Tab dailyTab = new Tab();
        dailyTab.setText("Daily View: " + dateToStore);
        dailyTab.setContent(dc.init(dateToStore));
        tabWindow.getTabs().add(1, dailyTab);
    }

}
