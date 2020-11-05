package trackitnus.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static trackitnus.logic.commands.CommandTestUtil.assertCommandFailure;
import static trackitnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static trackitnus.testutil.typical.TypicalTrackIter.getTypicalTrackIter;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.contact.ContactCommandTestUtil;
import trackitnus.logic.commands.contact.EditContactCommand;
import trackitnus.model.Model;
import trackitnus.model.ModelManager;
import trackitnus.model.TrackIter;
import trackitnus.model.UserPrefs;
import trackitnus.model.contact.Contact;
import trackitnus.model.task.Task;
import trackitnus.testutil.builder.ContactBuilder;
import trackitnus.testutil.builder.EditContactDescriptorBuilder;
import trackitnus.testutil.builder.EditTaskDescriptorBuilder;
import trackitnus.testutil.builder.TaskBuilder;
import trackitnus.testutil.typical.TypicalIndexes;

public class EditTaskCommandTest {

    private final Model model = new ModelManager(getTypicalTrackIter(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Task editedTask = new TaskBuilder().build();
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastTask = Index.fromOneBased(model.getFilteredContactList().size());
        Task lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask =
            taskInList.withName(TaskCommandTestUtil.VALID_NAME_ONE)
                .withDate(TaskCommandTestUtil.VALID_DATE_ONE)
                .withCode(Optional.ofNullable(TaskCommandTestUtil.VALID_CODE_ONE))
                .withRemark(TaskCommandTestUtil.VALID_REMARK_ONE).build();

        EditTaskCommand.EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_ONE)
                .withDate(TaskCommandTestUtil.VALID_DATE_ONE)
                .withCode(Optional.ofNullable(TaskCommandTestUtil.VALID_CODE_ONE))
                .withRemark(TaskCommandTestUtil.VALID_REMARK_ONE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(Messages.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        TaskCommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST);

        Task taskInFilteredList =
            model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        Task editedTask =
            new TaskBuilder(taskInFilteredList).withRemark(TaskCommandTestUtil.VALID_REMARK_TWO).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            new EditTaskDescriptorBuilder().withRemark(TaskCommandTestUtil.VALID_REMARK_TWO).build());

        Model expectedModel = new ModelManager(new TrackIter(model.getTrackIter()), new UserPrefs());
        expectedModel.setTask(model.getFilteredTaskList().get(0), editedTask);
        TaskCommandTestUtil.showTaskAtIndex(expectedModel, TypicalIndexes.INDEX_FIRST);

        String expectedMessage = String.format(Messages.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            new EditTaskCommand.EditTaskDescriptor());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_NOT_EDITED);
    }

    @Test
    public void execute_unchangedFilteredList_failure() {
        TaskCommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST);
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            new EditTaskDescriptorBuilder().withCode(Optional.ofNullable("CS2100")).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_TASK_UNCHANGED);
    }

    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = model.getFilteredTaskList().get(TypicalIndexes.INDEX_FIRST.getZeroBased());
        EditTaskCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_SECOND, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_duplicateTaskFilteredList_failure() {
        TaskCommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST);

        // edit task in filtered list into a duplicate in address book
        Task taskInList =
            model.getTrackIter().getTaskList().get(TypicalIndexes.INDEX_SECOND.getZeroBased());
        EditTaskCommand editTaskCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            new EditTaskDescriptorBuilder(taskInList).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_DUPLICATE_TASK);
    }

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskCommand.EditTaskDescriptor descriptor =
            new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_ONE).build();
        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of trackiter
     */
    @Test
    public void execute_invalidContactIndexFilteredList_failure() {
        TaskCommandTestUtil.showTaskAtIndex(model, TypicalIndexes.INDEX_FIRST);
        Index outOfBoundIndex = TypicalIndexes.INDEX_SECOND;
        // ensures that outOfBoundIndex is still in bounds of trackiter list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTrackIter().getTaskList().size());

        EditTaskCommand editTaskCommand = new EditTaskCommand(outOfBoundIndex,
            new EditTaskDescriptorBuilder().withName(TaskCommandTestUtil.VALID_NAME_ONE).build());

        assertCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            TaskCommandTestUtil.DESC_ONE);

        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor copyDescriptor =
            new EditTaskCommand.EditTaskDescriptor(TaskCommandTestUtil.DESC_ONE);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(TypicalIndexes.INDEX_SECOND,
            TaskCommandTestUtil.DESC_ONE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(TypicalIndexes.INDEX_FIRST,
            TaskCommandTestUtil.DESC_TWO)));
    }
}
