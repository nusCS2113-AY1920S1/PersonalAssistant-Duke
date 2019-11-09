package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.enumerations.Constraint;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.DuplicateRouteException;
import sgtravel.commons.exceptions.DuplicateRouteNodeException;
import sgtravel.commons.exceptions.FileLoadFailException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.QueryFailedException;
import sgtravel.commons.exceptions.RouteGenerateFailException;
import sgtravel.commons.exceptions.UnknownConstraintException;
import sgtravel.logic.commands.results.CommandResultText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RouteGenerateCommandTest {
    @Test
    void execute() throws FileLoadFailException, UnknownConstraintException, DuplicateRouteNodeException,
            FileNotSavedException, RouteGenerateFailException, ApiException,
            DuplicateRouteException, QueryFailedException {
        ModelStub modelStub = new ModelStub();

        RouteGenerateCommand routeGenerateBus =
                new RouteGenerateCommand("sentosa", "amk", Constraint.BUS);
        CommandResultText resultText = routeGenerateBus.execute(modelStub);
        assertEquals(resultText.getMessage(), "Route generated successfully: \n" + "sentosa to amk  (BUS)");

    }
}
