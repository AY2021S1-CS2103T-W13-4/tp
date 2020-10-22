package trackitnus.logic.parser.module;

import org.junit.jupiter.api.Test;

import trackitnus.logic.commands.module.AddModuleCommand;
import trackitnus.logic.parser.CommandParserTestUtil;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.module.Module;

public class AddModuleCommandParserTest {
    private final AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddModuleCommand expectedCommand = new AddModuleCommand(new Module(new Code("CS1231S"), new Name("Discrete "
            + "Structures")));

        CommandParserTestUtil.assertParseSuccess(parser, " n/Discrete Structures m/CS1231S d/Sample", expectedCommand);
//        assertParseSuccess(parser, " 1 n/Discrete Structures m/CS1231S d/Sample", expectedCommand);
    }

//    @Test
//    public void parse_leadingIndex_failure() {
//        AddModuleCommand expectedCommand = new AddModuleCommand(new Module(new Code("CS1231S"), new Name("Discrete "
//            + "Structures"), "Sample"));
//
//        assertParseFailure(parser, "1 n/Discrete Structures m/CS1231S d/Sample",
//            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddModuleCommand.MESSAGE_USAGE));
//
//
//    }
}
