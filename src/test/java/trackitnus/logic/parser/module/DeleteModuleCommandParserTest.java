package trackitnus.logic.parser.module;

import org.junit.jupiter.api.Test;

import trackitnus.logic.commands.module.DeleteModuleCommand;
import trackitnus.logic.parser.CommandParserTestUtil;
import trackitnus.model.commons.Code;

public class DeleteModuleCommandParserTest {
    private final DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DeleteModuleCommand expectedCommand = new DeleteModuleCommand(new Code("CS1231S"));
        CommandParserTestUtil.assertParseSuccess(parser, " CS1231S", expectedCommand);
    }

}
