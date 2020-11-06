package trackitnus.model.task;

import java.time.LocalDate;
import java.util.function.Predicate;

public class TaskIsOverduePredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
        LocalDate today = LocalDate.now();
        return task.getDate().isBefore(today);
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof TaskIsOverduePredicate); // instanceof handles nulls
    }
}
