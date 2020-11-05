package trackitnus.logic.commands.task;

import static java.util.Objects.requireNonNull;
import static trackitnus.commons.core.Messages.MESSAGE_DUPLICATE_TASK;
import static trackitnus.commons.core.Messages.MESSAGE_MODULE_DOES_NOT_EXIST;
import static trackitnus.logic.parser.CliSyntax.PREFIX_CODE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_DATE;
import static trackitnus.logic.parser.CliSyntax.PREFIX_NAME;
import static trackitnus.logic.parser.CliSyntax.PREFIX_REMARK;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import trackitnus.commons.core.Messages;
import trackitnus.commons.core.index.Index;
import trackitnus.commons.util.CollectionUtil;
import trackitnus.logic.commands.Command;
import trackitnus.logic.commands.CommandResult;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.Model;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.task.Task;

public final class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = Task.TYPE + " " + COMMAND_WORD
        + ": Edits the details of the task identified "
        + "by the index number currently displayed on the screen. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_NAME + "NAME] "
        + "[" + PREFIX_DATE + "DATE] "
        + "[" + PREFIX_CODE + "MODULE_CODE] "
        + "[" + PREFIX_REMARK + "REMARK]...\n"
        + "Example: " + Task.TYPE + " " + COMMAND_WORD + " 1 "
        + PREFIX_REMARK + "New remark";

    private final Index index;
    private final EditTaskCommand.EditTaskDescriptor editTaskDescriptor;

    /**
     * @param index              of the task in the filtered contact list to edit
     * @param editTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskCommand.EditTaskDescriptor editTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(editTaskDescriptor);

        this.index = index;
        this.editTaskDescriptor = new EditTaskCommand.EditTaskDescriptor(editTaskDescriptor);
    }

    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor editTaskDescriptor) {
        assert taskToEdit != null;

        Name updatedName = editTaskDescriptor.getName().orElse(taskToEdit.getName());
        LocalDate updatedDate = editTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        Code updatedCode = editTaskDescriptor.getIsCodeChanged()
            ? editTaskDescriptor.getCode().orElse(null)
            : taskToEdit.getCode().orElse(null);
        String updatedRemarks = editTaskDescriptor.getIsRemarkChanged()
            ? editTaskDescriptor.getRemark().orElse("")
            : taskToEdit.getRemark();

        return new Task(updatedName, updatedDate, updatedCode, updatedRemarks);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        if (!editTaskDescriptor.isAnyFieldEdited()) {
            throw new CommandException(Messages.MESSAGE_NOT_EDITED);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task editedTask = createEditedTask(taskToEdit, editTaskDescriptor);

        if (taskToEdit.equals(editedTask)) {
            throw new CommandException(Messages.MESSAGE_TASK_UNCHANGED);
        }

        if (!taskToEdit.isSameTask(editedTask) && model.hasTask(editedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        if (editedTask.getCode().isPresent() && !model.hasModule(editedTask.getCode().get())) {
            throw new CommandException(MESSAGE_MODULE_DOES_NOT_EXIST);
        }

        model.setTask(taskToEdit, editedTask);
        return new CommandResult(String.format(Messages.MESSAGE_EDIT_TASK_SUCCESS, editedTask));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
            && editTaskDescriptor.equals(e.editTaskDescriptor);
    }

    @Override
    public String toString() {
        return "EditTaskCommand{" +
            "index=" + index +
            ", editTaskDescriptor=" + editTaskDescriptor +
            '}';
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static final class EditTaskDescriptor {
        private Name name;
        private LocalDate date;
        private Code code;
        private String remark;
        private boolean isCodeChanged;
        private boolean isRemarkChanged;

        /**
         * Constructor for EditTaskDescriptor.
         */
        public EditTaskDescriptor() {
            isCodeChanged = false;
            isRemarkChanged = false;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskCommand.EditTaskDescriptor toCopy) {
            setName(toCopy.name);
            setDate(toCopy.date);
            setCode(toCopy.code);
            setRemark(toCopy.remark);
            isCodeChanged = toCopy.isCodeChanged;
            isRemarkChanged = toCopy.isRemarkChanged;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, date) || isRemarkChanged || isCodeChanged;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Optional<Code> getCode() {
            return Optional.ofNullable(code);
        }

        public void setCode(Code code) {
            this.code = code;
            this.isCodeChanged = true;
        }

        public Optional<String> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setRemark(String remark) {
            this.remark = (remark == null ? "" : remark);
            this.isRemarkChanged = true;
        }

        public boolean getIsCodeChanged() {
            return isCodeChanged;
        }

        public boolean getIsRemarkChanged() {
            return isRemarkChanged;
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskCommand.EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskCommand.EditTaskDescriptor e = (EditTaskCommand.EditTaskDescriptor) other;

            return getName().equals(e.getName())
                && getDate().equals(e.getDate())
                && getCode().equals(e.getCode())
                && getRemark().equals(e.getRemark());
        }

        @Override
        public String toString() {
            return "EditTaskDescriptor{" +
                "name=" + name +
                ", date=" + date +
                ", code=" + code +
                ", remark='" + remark +
                '}';
        }
    }
}
