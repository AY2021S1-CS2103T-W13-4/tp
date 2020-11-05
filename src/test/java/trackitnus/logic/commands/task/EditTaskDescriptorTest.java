package trackitnus.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import trackitnus.testutil.builder.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskCommand.EditTaskDescriptor descriptorWithSameValues =
            new EditTaskCommand.EditTaskDescriptor(TaskCommandTestUtil.DESC_ONE);
        assertTrue(TaskCommandTestUtil.DESC_ONE.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(TaskCommandTestUtil.DESC_ONE.equals(TaskCommandTestUtil.DESC_ONE));

        // null -> returns false
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(null));

        // different types -> returns false
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(5));

        // different values -> returns false
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(TaskCommandTestUtil.DESC_TWO));

        // different name -> returns false
        EditTaskCommand.EditTaskDescriptor editedOne =
            new EditTaskDescriptorBuilder(TaskCommandTestUtil.DESC_ONE)
                .withName(TaskCommandTestUtil.VALID_NAME_TWO).build();
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(editedOne));

        // different date -> returns false
        editedOne =
            new EditTaskDescriptorBuilder(TaskCommandTestUtil.DESC_ONE)
                .withDate(TaskCommandTestUtil.VALID_DATE_TWO).build();
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(editedOne));

        // different code -> returns false
        editedOne =
            new EditTaskDescriptorBuilder(TaskCommandTestUtil.DESC_ONE)
                .withCode(Optional.ofNullable(TaskCommandTestUtil.VALID_CODE_TWO)).build();
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(editedOne));

        // different remark -> returns false
        editedOne =
            new EditTaskDescriptorBuilder(TaskCommandTestUtil.DESC_ONE)
                .withRemark(TaskCommandTestUtil.VALID_REMARK_TWO).build();
        assertFalse(TaskCommandTestUtil.DESC_ONE.equals(editedOne));
    }
}
