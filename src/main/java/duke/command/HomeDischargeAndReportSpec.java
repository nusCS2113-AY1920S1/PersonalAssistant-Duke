package duke.command;

public class HomeDischargeAndReportSpec extends ArgSpec {
    private static final HomeDischargeAndReportSpec spec = new HomeDischargeAndReportSpec();

    public static HomeDischargeAndReportSpec getSpec() {
        return spec;
    }

    private HomeDischargeAndReportSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum"));
    }
}
