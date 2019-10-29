package planner;

import org.junit.jupiter.api.Test;
import planner.logic.command.Arguments;
import planner.logic.command.GradeCommand;
import planner.logic.exceptions.legacy.ModException;
import planner.logic.parser.Parser;
import planner.logic.command.SearchThenAddCommand;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleTasksList;
import planner.logic.modules.module.ModuleTask;
import planner.ui.cli.PlannerUi;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradeTest extends InputTest {
    @Test
    public void GradeTest() {
        String[] moduleCodes = new String[]{"CS1231", "CS1010", "CG1111", "CG1112", "CS2040C", ""};
        GradeCommand gradeCommand = new GradeCommand(moduleCodes[0], "B");
        assertEquals();
    }

    @Test
    public void GradeInputTest() {

    }



}
