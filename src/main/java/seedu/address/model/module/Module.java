package seedu.address.model.module;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.commons.Code;
import seedu.address.model.commons.Name;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {
    public static final String TYPE = "M";

    private final Code code;
    private final Name name;
    private final String gradeDist;

    /**
     * Every field must be present and not null.
     *
     * @param code
     * @param name
     * @param gradeDist
     */
    public Module(Code code, Name name, String gradeDist) {
        requireAllNonNull(code, name, gradeDist);
        this.code = code;
        this.name = name;
        this.gradeDist = gradeDist;
    }

    public Code getCode() {
        return code;
    }

    public Name getName() {
        return name;
    }

    public String getGradeDist() {
        return gradeDist;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherLesson = (Module) other;
        return otherLesson.code.equals(code)
            && otherLesson.name.equals(name)
            && otherLesson.gradeDist.equals(gradeDist);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, name, gradeDist);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        // TODO: Implement this toString
        return "Module's toString hasn't been implemented";
    }

    /**
     * Returns true if the two modules are the same
     * This methods is here for to act as a compatibility layer for UniqueModuleList
     */
    public boolean isSameModule(Module module) {
        return this.equals(module);
    }
}
