package trackitnus.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import trackitnus.commons.core.LogsCenter;


public class HelpPanel extends UiPart<Region> {
    public static final String TYPE = "H";
    public static final String USERGUIDE_URL = "https://bit.ly/2HDhjac";
    public static final String HELP_MESSAGE = USERGUIDE_URL;
    private static final String FXML = "HelpPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(trackitnus.ui.HelpPanel.class);

    @FXML
    private Button copyButton;
    @FXML
    private Label helpMessage;
    @FXML
    private TableView helpCardTableView;

    /**
     * Creates a {@code HelpPanel} with the given {@code ObservableList}.
     */
    @SuppressWarnings("unchecked")
    public HelpPanel() {
        super(FXML);
        helpMessage.setText(HELP_MESSAGE);

        helpCardTableView.setItems(helpCommands());
        helpCardTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        helpCardTableView.setColumnResizePolicy((Callback<TableView.ResizeFeatures, Boolean>) p -> true);
        TableColumn<HelpCard, String> actionCol = new TableColumn<>("Action");
        actionCol.setSortable(false);
        actionCol.setCellValueFactory(new PropertyValueFactory<>("Action"));

        TableColumn<HelpCard, String> commandCol = new TableColumn<>("Command");
        commandCol.setSortable(false);
        commandCol.setCellValueFactory(new PropertyValueFactory<>("Command"));

        helpCardTableView.getColumns().addAll(actionCol, commandCol);
    }

    private static ObservableList<HelpCard> helpCommands() {
        ObservableList<HelpCard> helpCommands = FXCollections.observableArrayList();
        helpCommands.add(new HelpCard("Module: Add", "M add m/MODULE_CODE n/NAME"));
        helpCommands.add(new HelpCard("Module: Edit", "M edit m/MODULE_CODE n/NAME"));
        helpCommands.add(new HelpCard("Module: Delete", "M delete m/MODULE_CODE"));
        helpCommands.add(new HelpCard("Lesson: Add", "L add m/MODULE_CODE t/TYPE d/DAY HH:MM-HH:MM"));
        helpCommands.add(new HelpCard("Lesson: Edit", "L edit INDEX [m/MODULE_CODE] [t/TYPE] [d/DAY HH:MM-HH:MM]"));
        helpCommands.add(new HelpCard("Lesson: Delete", "L delete INDEX"));
        helpCommands.add(new HelpCard("Task: Add", "T add n/NAME d/DATE [m/MODULE_CODE] [r/REMARK]"));
        helpCommands.add(new HelpCard("Task: Edit", "T edit INDEX [n/NAME] [d/DATE] [m/MODULE_CODE] [r/REMARK]"));
        helpCommands.add(new HelpCard("Task: Delete", "T delete INDEX"));
        helpCommands.add(new HelpCard("Contacts: Add", "C add n/NAME p/PHONE_NUMBER e/EMAIL [t/TAG]"));
        helpCommands.add(new HelpCard("Contacts: Edit", "C edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [t/TAG]"));
        helpCommands.add(new HelpCard("Contacts: Delete", "C delete INDEX"));
        helpCommands.add(new HelpCard("Getting help", "help"));
        helpCommands.add(new HelpCard("Exiting the app", "exit"));

        return helpCommands;
    }

    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }
}
