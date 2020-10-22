package trackitnus.logic.parser.module;

import org.junit.jupiter.api.Test;

import trackitnus.logic.commands.module.DeleteModuleCommand;
import trackitnus.logic.parser.CommandParserTestUtil;
import trackitnus.testutil.TypicalIndexes;

public class DeleteModuleCommandParserTest {
    private final DeleteModuleCommandParser parser = new DeleteModuleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DeleteModuleCommand expectedCommand = new DeleteModuleCommand(TypicalIndexes.INDEX_FIRST_CONTACT);
        CommandParserTestUtil.assertParseSuccess(parser, "1", expectedCommand);
    }

}
