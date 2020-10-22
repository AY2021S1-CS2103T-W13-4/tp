package trackitnus.model.module;

import java.util.Objects;

import trackitnus.commons.util.CollectionUtil;
import trackitnus.model.commons.Code;
import trackitnus.model.commons.Name;

/**
 * Represents a Lesson in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {
    public static final String TYPE = "M";

    private final Code code;
    private final Name name;

    /**
     * Every field must be present and not null.
     *
     * @param code
     * @param name
     */
    public Module(Code code, Name name) {
        CollectionUtil.requireAllNonNull(code, name);
        this.code = code;
        this.name = name;
    }

    public Code getCode() {
        return code;
    }

    public Name getName() {
        return name;
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
            && otherLesson.name.equals(name);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(code, name);
    }

    @Override
    public String toString() {
        return " Code: "
            + getCode()
            + " Name: "
            + getName();
    }

    /**
     * Returns true if the two modules are the same
     * This methods is here for to act as a compatibility layer for UniqueModuleList
     */
    public boolean isSameModule(Module module) {
        return this.equals(module);
    }
}
