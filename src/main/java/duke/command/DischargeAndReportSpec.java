package duke.command;

public class DischargeAndReportSpec extends ArgSpec {
    private static final DischargeAndReportSpec spec = new DischargeAndReportSpec();

    public static DischargeAndReportSpec getSpec() {
        return spec;
    }

    private DischargeAndReportSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum"));
    }
}
