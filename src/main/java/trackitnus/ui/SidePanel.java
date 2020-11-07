package trackitnus.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import trackitnus.commons.core.LogsCenter;
import trackitnus.logic.Logic;
import trackitnus.logic.commands.exceptions.CommandException;
import trackitnus.model.contact.Contact;
import trackitnus.model.module.Module;
import trackitnus.ui.upcoming.UpcomingPanel;

public class SidePanel extends UiPart<Region> {
    private static final String FXML = "SidePanel.fxml";
    private static final int MODULE_ROW_HEIGHT = 32;
    private final Logger logger = LogsCenter.getLogger(SidePanel.class);
    private final Consumer<ArrayList<Object>> tabConsumer;
    private final Logic logic;
    private Button selectedTabButton;

    @FXML
    private Button upcomingButton;

    @FXML
    private Button contactButton;

    @FXML
    private ListView<Module> moduleListView;

    @FXML
    private Button helpButton;


    /**
     * Constructor for SidePanel
     *
     * @param tabConsumer carries values to MainWindow
     */
    public SidePanel(Consumer<ArrayList<Object>> tabConsumer, Logic logic) {
        super(FXML);
        this.tabConsumer = tabConsumer;
        this.logic = logic;
        initialize();
    }

    /**
     * Initialises/reloads the tab buttons in the side panel.
     */
    public void initialize() {
        if (logic != null) {
            // Get modules tab buttons.
            ObservableList<Module> modules = logic.getFilteredModuleList();
            moduleListView.setPrefHeight(modules.size() * MODULE_ROW_HEIGHT);
            moduleListView.setItems(modules);
            moduleListView.setCellFactory(listView -> new ModuleListViewCell());

            // Set Default tab as upcoming tab.
            selectedTabButton = upcomingButton;
            updateButtonDetails(upcomingButton);
        }
    }

    /**
     * Relays message to MainWindow to get Upcoming panel information in TabPanel.
     */
    public void toggleUpcomingTab() {
        ArrayList<Object> upcomingValues = new ArrayList<>(Arrays.asList((Object) UpcomingPanel.TYPE));
        updateButtonDetails(upcomingButton);
        tabConsumer.accept(upcomingValues);
    }

    /**
     * Relays message to MainWindow to get Contact panel information in TabPanel.
     */
    public void toggleContactTab() {
        ArrayList<Object> contactValues = new ArrayList<>(Arrays.asList((Object) Contact.TYPE));
        updateButtonDetails(contactButton);
        tabConsumer.accept(contactValues);
    }

    /**
     * Relays message to MainWindow to get Help tab information in TabPanel.
     */
    public void toggleHelpTab() {
        ArrayList<Object> helpValues = new ArrayList<>(Arrays.asList((Object) HelpPanel.TYPE));
        updateButtonDetails(helpButton);
        tabConsumer.accept(helpValues);
    }

    /**
     * Updates the details of the current selected tab button.
     *
     * @param button The new selected tab button.
     */
    public void updateButtonDetails(Button button) {
        selectedTabButton.setStyle("-fx-text-fill: white;");
        selectedTabButton = button;
        selectedTabButton.setStyle("-fx-text-fill: #68C2E8;");
    }

    /**
     * Configure the module section that will include the module button as well as a coloured module circle label.
     *
     * @return HBox The module section.
     */
    public HBox getModuleSection() {
        HBox moduleSection = new HBox();
        moduleSection.setAlignment(Pos.CENTER_LEFT);
        moduleSection.setPadding(new Insets(0, 0, 0, 10));
        return moduleSection;
    }

    /**
     * Configure the module button tab in the side panel.
     *
     * @return moduleButton The module button.
     */
    public Button getModuleButton(Module module) {
        Button button = new Button(module.getCode().code);
        ArrayList<Object> moduleValues = new ArrayList<>(Arrays.asList(Module.TYPE, module));
        button.setOnAction(actionEvent -> {
            updateButtonDetails(button);
            tabConsumer.accept(moduleValues);
        });
        button.setPadding(new Insets(5));
        return button;
    }

    /**
     * Configure the coloured module circle that is displayed beside the module code.
     *
     * @return moduleCircle The module circle.
     */
    public Circle getModuleCircle(Module module, Logic logic) throws CommandException {
        Circle moduleCircle = new Circle(0, 0, 6);
        int moduleIndex = logic.getModuleIndex(module).getZeroBased();
        moduleCircle.setFill(Module.COLORS.get(moduleIndex % 10));
        return moduleCircle;
    }

    /**
     * Sets the view of the module tab in the side panel.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                try {
                    HBox moduleSection = getModuleSection();
                    Button updatedButton = getModuleButton(module);
                    Circle moduleCircle = getModuleCircle(module, logic);
                    moduleSection.getChildren().add(moduleCircle);
                    moduleSection.getChildren().add(updatedButton);
                    setGraphic(moduleSection);

                } catch (CommandException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
