package duke.logic.commands;

import duke.ModelStub;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.CorruptedFileException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.QueryOutOfBoundsException;
import duke.commons.exceptions.RouteDuplicateException;
import duke.commons.exceptions.RouteGenerateFailException;
import duke.commons.exceptions.RouteNodeDuplicateException;
import duke.commons.exceptions.UnknownConstraintException;
import duke.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteGenerateCommandTest {
    @Test
    void execute() throws FileLoadFailException, ApiException, RouteNodeDuplicateException,
            UnknownConstraintException, CorruptedFileException, RouteDuplicateException,
            RouteGenerateFailException, QueryOutOfBoundsException, FileNotSavedException, QueryFailedException {
        ModelStub modelStub = new ModelStub();
        RouteGenerateCommand routeGenerateMrt =
                new RouteGenerateCommand("sentosa", "amk", Constraint.MRT);
        CommandResultText resultText = routeGenerateMrt.execute(modelStub);
        assertEquals(resultText.getMessage(), "Route generated successfully: \n" + "sentosa to amk  (MRT)");

        RouteGenerateCommand routeGenerateBus =
                new RouteGenerateCommand("sentosa", "amk", Constraint.BUS);
        resultText = routeGenerateBus.execute(modelStub);
        assertEquals(resultText.getMessage(), "Route generated successfully: \n" + "sentosa to amk  (BUS)");

    }
}
