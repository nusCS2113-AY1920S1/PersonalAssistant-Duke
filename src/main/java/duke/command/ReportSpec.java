package duke.command;

public class ReportSpec extends ArgSpec {
    private static final ReportSpec spec = new ReportSpec();

    public static ReportSpec getSpec() {
        return spec;
    }

    private ReportSpec() {
        emptyArgMsg = "You didn't tell me anything about the patient, cant discharge";
        cmdArgLevel = ArgLevel.REQUIRED;
        initSwitches(new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum"));
    }
}
