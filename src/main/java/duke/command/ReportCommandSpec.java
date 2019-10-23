package duke.command;

public class ReportCommandSpec extends ArgSpec {
    private static final ReportCommandSpec spec = new ReportCommandSpec();

    public static ReportCommandSpec getSpec() {
        return spec;
    }

    private ReportCommandSpec() {
        emptyArgMsg = "You need to tell me what patient you wish to discharge";
    }
}
