package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;


/**
 * Represents a Task in the address book.
 * Guarantees: details are present and not null, field values are validated and immutable.
 */
public class Task {
    public static final String TYPE = "T";

    private final Name name;
    private final LocalDate time;
    private final Address location;
    private final double weightage;
    private final String note;

    /**
     * Every field must be present and not null.
     *
     * @param name
     * @param time
     * @param location
     * @param weightage
     * @param note
     */
    public Task(Name name, LocalDate time, Address location, double weightage, String note) {
        requireAllNonNull(name, time, location, weightage, note);
        this.name = name;
        this.time = time;
        this.location = location;
        this.weightage = weightage;
        this.note = note;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherLesson = (Task) other;
        return otherLesson.name.equals(name)
            && otherLesson.time.equals(time)
            && otherLesson.location.equals(location)
            && (otherLesson.weightage == weightage)
            && otherLesson.note.equals(note);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, time, location, weightage, note);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        // TODO: Implement this toString
        return "Task's toString hasn't been implemented";
    }

    /**
     * Returns true if the two tasks are the same
     * This methods is here for to act as a compatibility layer for UniqueTaskList
     */
    public boolean isSameTask(Task task) {
        return this.equals(task);
    }
}
