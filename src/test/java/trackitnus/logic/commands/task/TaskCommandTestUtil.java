package trackitnus.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Predicate;

import trackitnus.commons.core.index.Index;
import trackitnus.logic.commands.contact.EditContactCommand;
import trackitnus.model.Model;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.task.Task;
import trackitnus.testutil.builder.EditContactDescriptorBuilder;
import trackitnus.testutil.builder.EditTaskDescriptorBuilder;

public class TaskCommandTestUtil {
    public static final String VALID_NAME_ONE = "Do assignment";
    public static final String VALID_NAME_TWO = "Do tutorial";
    public static final String VALID_DATE_ONE = "12/12/2020";
    public static final String VALID_DATE_TWO = "11/11/2020";
    public static final String VALID_CODE_ONE = null;
    public static final String VALID_CODE_TWO = "CS2103T";
    public static final String VALID_REMARK_ONE = "Test remark";
    public static final String VALID_REMARK_TWO = "";

    public static final EditTaskCommand.EditTaskDescriptor DESC_ONE;
    public static final EditTaskCommand.EditTaskDescriptor DESC_TWO;

    static {
        DESC_ONE = new EditTaskDescriptorBuilder().withName(VALID_NAME_ONE)
            .withDate(VALID_DATE_ONE).withCode(Optional.ofNullable(VALID_CODE_ONE))
            .withRemark(VALID_REMARK_ONE).build();
        DESC_TWO = new EditTaskDescriptorBuilder().withName(VALID_NAME_TWO)
            .withDate(VALID_DATE_TWO).withCode(Optional.ofNullable(VALID_CODE_TWO))
            .withRemark(VALID_REMARK_TWO).build();
    }

    /**
     * Updates {@code model}'s filtered list to show only the task at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showTaskAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredTaskList().size());

        Task task = model.getFilteredTaskList().get(targetIndex.getZeroBased());
        Predicate<Task> p = t -> t.getName().equals(task.getName());
        model.updateFilteredTaskList(p);

        assertEquals(1, model.getFilteredTaskList().size());
    }
}
