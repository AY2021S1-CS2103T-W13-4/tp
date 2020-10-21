package trackitnus.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class DayCard extends UiPart<Region> {

    private static final String FXML = "DayCard.fxml";

    public final Day day;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;

    public DayCard(Day day) {
        super(FXML);
        this.day = day;
        date.setText(day.getDate());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayCard)) {
            return false;
        }

        // state check
        DayCard card = (DayCard) other;
        return date.getText().equals(card.date.getText());
    }
}
