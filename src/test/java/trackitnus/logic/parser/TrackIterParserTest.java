package trackitnus.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.logic.commands.ExitCommand;
import trackitnus.logic.commands.HelpCommand;
import trackitnus.logic.commands.contact.AddContactCommand;
import trackitnus.logic.commands.contact.DeleteContactCommand;
import trackitnus.logic.commands.contact.EditContactCommand;
import trackitnus.logic.commands.task.AddTaskCommand;
import trackitnus.logic.commands.task.DeleteTaskCommand;
import trackitnus.logic.commands.task.EditTaskCommand;
import trackitnus.logic.parser.exceptions.ParseException;
import trackitnus.model.contact.Contact;
import trackitnus.model.task.Task;
import trackitnus.testutil.Assert;
import trackitnus.testutil.ContactUtil;
import trackitnus.testutil.TaskUtil;
import trackitnus.testutil.builder.ContactBuilder;
import trackitnus.testutil.builder.EditContactDescriptorBuilder;
import trackitnus.testutil.builder.EditTaskDescriptorBuilder;
import trackitnus.testutil.builder.TaskBuilder;
import trackitnus.testutil.typical.TypicalIndexes;

public class TrackIterParserTest {

    private final TrackIterParser parser = new TrackIterParser();

    @Test
    public void parseContactCommand_add() throws Exception {
        Contact contact = new ContactBuilder().build();
        AddContactCommand command = (AddContactCommand) parser.parseCommand(Contact.TYPE
            + " " + ContactUtil.getAddCommand(contact));
        assertEquals(new AddContactCommand(contact), command);
    }

    @Test
    public void parseContactCommand_delete() throws Exception {
        DeleteContactCommand command = (DeleteContactCommand) parser.parseCommand(
            Contact.TYPE + " " + DeleteContactCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST
                .getOneBased());
        assertEquals(new DeleteContactCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseContactCommand_edit() throws Exception {
        Contact contact = new ContactBuilder().build();
        EditContactCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder(contact).build();
        EditContactCommand command = (EditContactCommand) parser.parseCommand(Contact.TYPE + " "
            + EditContactCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST.getOneBased() + " "
            + ContactUtil.getEditContactDescriptorDetails(descriptor));
        assertEquals(new EditContactCommand(TypicalIndexes.INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseTaskCommand_add() throws Exception {
        Task task = new TaskBuilder().build();
        AddTaskCommand command = (AddTaskCommand) parser.parseCommand(Task.TYPE
            + " " + TaskUtil.getAddCommand(task));
        assertEquals(new AddTaskCommand(task), command);
    }

    @Test
    public void parseTaskCommand_delete() throws Exception {
        DeleteTaskCommand command = (DeleteTaskCommand) parser.parseCommand(
            Task.TYPE + " " + DeleteTaskCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST
                .getOneBased());
        assertEquals(new DeleteTaskCommand(TypicalIndexes.INDEX_FIRST), command);
    }

    @Test
    public void parseTaskCommand_edit() throws Exception {
        Task task = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(task).build();
        EditTaskCommand command = (EditTaskCommand) parser.parseCommand(Task.TYPE + " "
            + EditTaskCommand.COMMAND_WORD + " " + TypicalIndexes.INDEX_FIRST.getOneBased() + " "
            + TaskUtil.getEditTaskDescriptorDetails(descriptor));
        assertEquals(new EditTaskCommand(TypicalIndexes.INDEX_FIRST, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        Assert.assertThrows(ParseException.class, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
            HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        Assert.assertThrows(ParseException.class, Messages.MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
            "unknownCommand"));
    }
}
