package duke.command;

public class DischargeOrReportSpec extends ArgSpec {
    private static final DischargeOrReportSpec spec = new DischargeOrReportSpec();

    public static DischargeOrReportSpec getSpec() {
        return spec;
    }

    private DischargeOrReportSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum"));
    }
}
