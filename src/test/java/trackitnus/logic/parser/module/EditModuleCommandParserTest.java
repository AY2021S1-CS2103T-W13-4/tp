package trackitnus.logic.parser.module;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static trackitnus.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.module.EditModuleCommand;

public class EditModuleCommandParserTest {
    private final EditModuleCommandParser parser = new EditModuleCommandParser();

    @Test
    public void parse_noFieldsProvided_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            EditModuleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexMissing_failure() {
        assertParseFailure(parser, "m/CS1231S", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            EditModuleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_indexOnly_failure() {
        assertParseFailure(parser, "1", EditModuleCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_indexAndModuleProvided_success() {
        assertDoesNotThrow(() -> parser.parse("1 m/CS1231S"));
    }

    @Test
    public void parse_indexAndNameProvided_success() {
        assertDoesNotThrow(() -> parser.parse("100 n/Sample"));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        assertDoesNotThrow(() -> parser.parse("100 m/CS1231S n/Sample"));
    }
}
