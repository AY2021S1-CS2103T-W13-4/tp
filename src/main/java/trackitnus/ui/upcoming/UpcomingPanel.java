package trackitnus.ui.upcoming;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import trackitnus.commons.core.LogsCenter;
import trackitnus.logic.Logic;
import trackitnus.ui.UiPart;

public class UpcomingPanel extends UiPart<Region> {
    public static final String TYPE = "U";
    private static final String FXML = "Upcoming/UpcomingPanel.fxml";

    private final Logic logic;
    private final Logger logger = LogsCenter.getLogger(UpcomingPanel.class);
    private final ObservableList<UpcomingSection> calendarDates = FXCollections.observableArrayList();
    private final LocalDate today = LocalDate.now();
    @FXML
    private ListView<UpcomingSection> calendarView;

    /**
     * Constructor for Upcoming Panel
     */
    public UpcomingPanel(Logic logic) {
        super(FXML);
        this.logic = logic;

        getDatesForTheWeek(today);
        calendarView.setItems(calendarDates);
        calendarView.setCellFactory(listView -> new SectionListViewCell());

    }

    private void getDatesForTheWeek(LocalDate today) {
        List<LocalDate> list = today.datesUntil(today.plusDays(7)).collect(Collectors.toList());

        calendarDates.add(new UpcomingSection("Overdue"));

        for (LocalDate date : list) {
            calendarDates.add(new UpcomingSection(date));
        }

        calendarDates.add(new UpcomingSection("Future"));
    }

    class SectionListViewCell extends ListCell<UpcomingSection> {
        @Override
        protected void updateItem(UpcomingSection section, boolean empty) {
            super.updateItem(section, empty);

            if (empty || section == null) {
                setGraphic(null);
                setText(null);
            } else {
                if (section.isDay()) {
                    LocalDate date = section.getDate();
                    setGraphic(new UpcomingSectionDayCard(section, logic.getDayUpcomingTasks(date),
                        logic.getDayUpcomingLessons(date), logic).getRoot());
                } else {
                    assert (section.getTitle() != null);
                    allocateCalendarSections(section);
                }
            }
        }

        public void allocateCalendarSections(UpcomingSection section) {
            if (section.getTitle().equals("Overdue")) {
                setGraphic(new UpcomingSectionCard(section, logic.getOverdueTasks(), logic).getRoot());
            } else {
                assert (section.getTitle().equals("Future"));
                setGraphic(new UpcomingSectionCard(section, logic.getFutureTasks(), logic).getRoot());
            }
        }
    }

}
