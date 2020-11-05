package trackitnus.testutil.builder;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import trackitnus.logic.commands.contact.EditContactCommand;
import trackitnus.logic.commands.task.EditTaskCommand;
import trackitnus.logic.parser.ParserUtil;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;
import trackitnus.model.contact.Contact;
import trackitnus.model.task.Task;
import trackitnus.testutil.TestUtil;

/**
 * A utility class to help with building EditTaskDescriptor objects.
 */
public class EditTaskDescriptorBuilder {

    private final EditTaskCommand.EditTaskDescriptor descriptor;

    public EditTaskDescriptorBuilder() {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
    }

    public EditTaskDescriptorBuilder(EditTaskCommand.EditTaskDescriptor descriptor) {
        this.descriptor = new EditTaskCommand.EditTaskDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTaskDescriptor} with fields containing {@code task}'s details
     */
    public EditTaskDescriptorBuilder(Task task) {
        descriptor = new EditTaskCommand.EditTaskDescriptor();
        descriptor.setName(task.getName());
        descriptor.setDate(task.getDate());
        descriptor.setCode(task.getCode().orElse(null));
        descriptor.setRemark(task.getRemark());
    }

    /**
     * Sets the {@code Name} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withName(String name) {
        descriptor.setName(TestUtil.parseName(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withDate(String date) {
        descriptor.setDate(TestUtil.parseDate(date));
        return this;
    }

    /**
     * Sets the {@code Code} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withCode(Optional<String> code) {
        descriptor.setCode(TestUtil.parseOptionalCode(code));
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code EditTaskDescriptor} that we are building.
     */
    public EditTaskDescriptorBuilder withRemark(String remark) {
        descriptor.setRemark(remark);
        return this;
    }

    public EditTaskCommand.EditTaskDescriptor build() {
        return descriptor;
    }
}
