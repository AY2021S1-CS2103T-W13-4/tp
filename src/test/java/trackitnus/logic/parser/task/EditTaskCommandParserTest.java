package trackitnus.logic.parser.task;

import static trackitnus.commons.core.Messages.MESSAGE_NOT_EDITED;
import static trackitnus.logic.parser.CommandParserTestUtil.assertParseFailure;
import static trackitnus.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static trackitnus.testutil.typical.TypicalIndexes.INDEX_FIRST;
import static trackitnus.testutil.typical.TypicalIndexes.INDEX_SECOND;
import static trackitnus.testutil.typical.TypicalIndexes.INDEX_THIRD;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.task.EditTaskCommand;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;

public class EditTaskCommandParserTest {
    private final EditTaskCommandParser parser = new EditTaskCommandParser();

    public static EditTaskCommand editTaskCommandBuilder(Index index, String name, String date, String code,
                                                         String remark) {
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskCommand.EditTaskDescriptor();
        descriptor.setName(new Name(name));
        descriptor.setDate(ParserUtil.parseValidDate(date));
        if (code != null) {
            descriptor.setCode(new Code(code));
        }
        descriptor.setRemark(remark);
        return new EditTaskCommand(index, descriptor);
    }

    // note all user input needs leading space
    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " 1 m/CS2030S n/Sample d/20/11/2020", editTaskCommandBuilder(INDEX_FIRST, "Sample",
            "20/11/2020", "CS2030S", ""));
        assertParseSuccess(parser, " 1 m/CS2030S n/Sample d/20/11/2020 r/", editTaskCommandBuilder(INDEX_FIRST,
            "Sample",
            "20/11/2020", "CS2030S", ""));
        assertParseSuccess(parser, " 2 m/CS2030S n/Sample d/20/11/2020 r/sample", editTaskCommandBuilder(INDEX_SECOND,
            "Sample",
            "20/11/2020", "CS2030S", "sample"));
        assertParseSuccess(parser, " 3 m/CFG1002 n/Play games d/29/02/2020 r/sample",
            editTaskCommandBuilder(INDEX_THIRD,
                "Play games",
                "29/02/2020", "CFG1002", "sample"));
    }

    @Test
    public void parse_noFieldsProvided_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            EditTaskCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_justIndexProvided_failure() {
        assertParseFailure(parser, " 1", MESSAGE_NOT_EDITED);
    }
}
