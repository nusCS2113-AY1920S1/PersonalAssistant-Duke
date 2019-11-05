package duke.logic.commands;

import duke.ModelStub;
import duke.commons.enumerations.Constraint;
import duke.commons.exceptions.ApiException;
import duke.commons.exceptions.DuplicateRouteException;
import duke.commons.exceptions.DuplicateRouteNodeException;
import duke.commons.exceptions.FileLoadFailException;
import duke.commons.exceptions.FileNotSavedException;
import duke.commons.exceptions.QueryFailedException;
import duke.commons.exceptions.RouteGenerateFailException;
import duke.commons.exceptions.UnknownConstraintException;
import duke.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteGenerateCommandTest {
    @Test
    void execute() throws FileLoadFailException, UnknownConstraintException, DuplicateRouteNodeException,
            FileNotSavedException, RouteGenerateFailException, ApiException,
            DuplicateRouteException, QueryFailedException {
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
