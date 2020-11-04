package trackitnus.logic.parser.module;

import static trackitnus.commons.core.Messages.MESSAGE_NOT_EDITED;
import static trackitnus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackitnus.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.module.EditModuleCommand;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;

public class EditModuleCommandParserTest {
    private final EditModuleCommandParser parser = new EditModuleCommandParser();

    public static EditModuleCommand editModuleCommandBuilder(Code originalCode, Code newCode, Name name) {
        EditModuleCommand.EditModuleDescriptor descriptor = new EditModuleCommand.EditModuleDescriptor();
        descriptor.setCode(newCode);
        descriptor.setName(name);
        return new EditModuleCommand(originalCode, descriptor);
    }

    // note for Module: all user input needs leading space
    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " CS2030 m/CS2030S n/Sample", editModuleCommandBuilder(new Code("CS2030"),
            new Code("CS2030S"), new Name("Sample")));
    }

    @Test
    public void parse_noFieldsProvided_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            EditModuleCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_justModuleProvided_failure() {
        assertParseFailure(parser, " CS1231S", MESSAGE_NOT_EDITED);
    }
}
