package trackitnus.logic.parser.module;

import static trackitnus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
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

    @Test
    public void parse_noFieldsProvided_failure() {
        assertParseFailure(parser, "",
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteModuleCommand.MESSAGE_USAGE));
    }

}
