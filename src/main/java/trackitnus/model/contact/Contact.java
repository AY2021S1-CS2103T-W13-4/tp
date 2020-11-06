package trackitnus.model.contact;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import trackitnus.commons.util.CollectionUtil;
import trackitnus.model.commons.Name;
import trackitnus.model.tag.Tag;

/**
 * Represents a Contact in the app.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {
    public static final String TYPE = "C";

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Contact(Name name, Phone phone, Email email, Set<Tag> tags) {
        CollectionUtil.requireAllNonNull(name, tags);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Optional<Phone> getPhone() {
        return Optional.ofNullable(phone);
    }

    public Optional<Email> getEmail() {
        return Optional.ofNullable(email);
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns a Contact with {@code oldTag} replaced by {@code newTag}
     */
    public Contact setTag(Tag oldTag, Tag newTag) {
        Set<Tag> oldTags = getTags();
        Set<Tag> newTags = new HashSet<>();
        for (Tag tag : oldTags) {
            if (tag.equals(oldTag)) {
                newTags.add(newTag);
            } else {
                newTags.add(tag);
            }
        }
        return new Contact(name, phone, email, newTags);
    }

    /**
     * Returns true if both contacts of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
            && otherContact.getName().equals(getName())
            && (otherContact.getPhone().equals(getPhone()) || otherContact.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return otherContact.getName().equals(getName())
            && otherContact.getPhone().equals(getPhone())
            && otherContact.getEmail().equals(getEmail())
            && otherContact.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        if (getPhone().isPresent()) {
            builder.append(" Phone: ")
                .append(getPhone().get());
        }
        if (getEmail().isPresent()) {
            builder.append(" Email: ")
                .append(getEmail().get());
        }
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
