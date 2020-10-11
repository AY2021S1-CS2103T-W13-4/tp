package seedu.address.logic.parser.module;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.module.AddModuleCommand;
import seedu.address.model.commons.Code;
import seedu.address.model.commons.Name;
import seedu.address.model.module.Module;

public class AddModuleCommandParserTest {
    private final AddModuleCommandParser parser = new AddModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddModuleCommand expectedCommand = new AddModuleCommand(new Module(new Code("CS1231S"), new Name("Discrete "
            + "Structures"), "Sample"));

        assertParseSuccess(parser, " n/Discrete Structures m/CS1231S d/Sample", expectedCommand);
    }

}
