package duke.command;

public class PatientDischargeSpec extends ArgSpec {
        private static final duke.command.PatientDischargeSpec spec = new duke.command.PatientDischargeSpec();

        public static duke.command.PatientDischargeSpec getSpec() {
            return spec;
        }

        private PatientDischargeSpec() {
            emptyArgMsg = "";
            cmdArgLevel = ArgLevel.NONE;
            initSwitches(new Switch("summary", String.class, true, ArgLevel.OPTIONAL, "sum"));
        }
    }