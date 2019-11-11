package compal.ui;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

//@@author SholihinK
public class CalendarUtil {
    public TabPane tabWindow;

    /**
     * Constructor for Calender Util.
     */

    public CalendarUtil() {
        this.tabWindow = compal.ui.UiUtil.tabWindow;
    }

    /**
     * Refresh view date.
     *
     * @param dateToStore date to view of daily calender
     */
    public void dateViewRefresh(String dateToStore,String type) {
        DailyCalUi dc = new DailyCalUi();

        int totalTabSize = tabWindow.getTabs().size();

        if (totalTabSize > 1) {
            for (int i = totalTabSize - 1; i >= 1; i--) {
                tabWindow.getTabs().remove(i);
            }
        }

        Tab dailyTab = new Tab();
        dailyTab.setText("Daily Task: " + dateToStore);
        dailyTab.setContent(dc.init(dateToStore,type));

        totalTabSize = tabWindow.getTabs().size();
        tabWindow.getTabs().add(totalTabSize, dailyTab);
        tabWindow.getSelectionModel().select(1);
    }
}
