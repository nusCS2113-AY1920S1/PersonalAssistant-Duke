package duke.data;

import duke.ui.context.Context;

public class Help {
    private String command;
    private String summary;
    private String format;
    private String switches;
    private String info;
    private String example;
    private Context context;

    public String getCommand() {
        return command;
    }

    public String getSummary() {
        return summary;
    }

    public String getFormat() {
        return format;
    }

    public String getSwitches() {
        return switches;
    }

    public String getInfo() {
        return info;
    }

    public String getExample() {
        return example;
    }

    public Context getContext() {
        return context;
    }
}
