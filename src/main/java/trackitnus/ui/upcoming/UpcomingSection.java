package trackitnus.ui.upcoming;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * A section in the Upcoming Tab
 */
public class UpcomingSection {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM EEEE");
    private final String title;
    private final LocalDate date;

    /**
     * Constructor for UpcomingSection, namely for Overdue and Future sections
     *
     * @param title the header of the section
     */
    public UpcomingSection(String title) {
        this.title = title;
        this.date = null;
    }

    /**
     * Constructor for UpcomingSection, for the dates in the calendar view
     *
     * @param date the date to be the header
     */
    public UpcomingSection(LocalDate date) {
        this.date = date;
        this.title = date.format(DATE_TIME_FORMATTER);
    }

    /**
     * Returns the title of the section
     *
     * @return title of the section
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return the date of the section
     *
     * @return the date of the section
     */
    public LocalDate getDate() {
        assert (date != null);
        return date;
    }

    /**
     * Checks if the section is a date in the calendar view, or either of the Overdue or Future sections
     *
     * @return true if it is a date in the calendar view with a valid date
     */
    public boolean isDay() {
        return date != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UpcomingSection)) {
            return false;
        }

        UpcomingSection otherCalSection = (UpcomingSection) other;
        return otherCalSection.title.equals(title);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title);
    }
}
