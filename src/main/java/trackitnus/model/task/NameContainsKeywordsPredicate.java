package trackitnus.model.task;

import java.util.List;
import java.util.function.Predicate;

import trackitnus.commons.util.StringUtil;

public class NameContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    // TODO: Delete this class
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        return keywords.stream()
            .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(task.getName().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
            && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state
        // check
    }

}
