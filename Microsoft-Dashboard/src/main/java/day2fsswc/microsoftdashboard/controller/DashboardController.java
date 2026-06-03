package day2fsswc.microsoftdashboard.controller;

import day2fsswc.microsoftdashboard.model.DashboardItem;
import day2fsswc.microsoftdashboard.service.DashboardService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardController implements Initializable {

    @Autowired
    private DashboardService dashboardService;

    // ── Tab buttons ──────────────────────────────────────────────────────────
    @FXML private Button overviewTab;
    @FXML private Button detailsTab;
    @FXML private Button settingsTab;

    // ── Content panels ───────────────────────────────────────────────────────
    @FXML private VBox overviewContent;
    @FXML private VBox detailsContent;
    @FXML private VBox settingsContent;

    // ── Overview ─────────────────────────────────────────────────────────────
    @FXML private Label totalUsersValue;
    @FXML private Label activeTasksValue;
    @FXML private Label revenueValue;
    @FXML private Label performanceValue;
    @FXML private ListView<String> activityList;

    // ── Details table ────────────────────────────────────────────────────────
    @FXML private TableView<DashboardItem> detailsTable;
    @FXML private TableColumn<DashboardItem, String> nameCol;
    @FXML private TableColumn<DashboardItem, String> deptCol;
    @FXML private TableColumn<DashboardItem, String> statusCol;
    @FXML private TableColumn<DashboardItem, String> dateCol;
    @FXML private TableColumn<DashboardItem, String> valueCol;

    // ── Settings ─────────────────────────────────────────────────────────────
    @FXML private ChoiceBox<String> themeChoice;
    @FXML private ChoiceBox<String> languageChoice;
    @FXML private CheckBox emailNotifCheck;
    @FXML private CheckBox desktopNotifCheck;
    @FXML private CheckBox weeklyReportCheck;
    @FXML private TextField displayNameField;
    @FXML private Label settingsSavedLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadOverviewData();
        setupDetailsTable();
        setupSettings();
    }

    // ── Tab switching ─────────────────────────────────────────────────────────

    @FXML
    private void switchToOverview(ActionEvent e) {
        setActiveTab(overviewTab);
        showContent(overviewContent);
    }

    @FXML
    private void switchToDetails(ActionEvent e) {
        setActiveTab(detailsTab);
        showContent(detailsContent);
    }

    @FXML
    private void switchToSettings(ActionEvent e) {
        setActiveTab(settingsTab);
        showContent(settingsContent);
    }

    @FXML
    private void saveSettings(ActionEvent e) {
        settingsSavedLabel.setVisible(true);
        new Thread(() -> {
            try { Thread.sleep(2500); } catch (InterruptedException ignored) {}
            Platform.runLater(() -> settingsSavedLabel.setVisible(false));
        }).start();
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void setActiveTab(Button active) {
        for (Button btn : new Button[]{overviewTab, detailsTab, settingsTab}) {
            btn.getStyleClass().remove("tab-active");
        }
        active.getStyleClass().add("tab-active");
    }

    private void showContent(VBox content) {
        for (VBox panel : new VBox[]{overviewContent, detailsContent, settingsContent}) {
            panel.setVisible(false);
            panel.setManaged(false);
        }
        content.setVisible(true);
        content.setManaged(true);
    }

    private void loadOverviewData() {
        totalUsersValue.setText(dashboardService.getTotalUsers());
        activeTasksValue.setText(dashboardService.getActiveTasks());
        revenueValue.setText(dashboardService.getRevenue());
        performanceValue.setText(dashboardService.getPerformance());
        activityList.setItems(FXCollections.observableArrayList(dashboardService.getRecentActivity()));
    }

    private void setupDetailsTable() {
        nameCol.setCellValueFactory(d -> d.getValue().nameProperty());
        deptCol.setCellValueFactory(d -> d.getValue().departmentProperty());
        statusCol.setCellValueFactory(d -> d.getValue().statusProperty());
        dateCol.setCellValueFactory(d -> d.getValue().dateProperty());
        valueCol.setCellValueFactory(d -> d.getValue().valueProperty());

        // Color-coded status column
        statusCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                getStyleClass().removeAll("status-active", "status-inactive", "status-leave");
                if (empty || item == null) { setText(null); return; }
                setText(item);
                switch (item) {
                    case "Active"   -> getStyleClass().add("status-active");
                    case "Inactive" -> getStyleClass().add("status-inactive");
                    default         -> getStyleClass().add("status-leave");
                }
            }
        });

        detailsTable.setItems(FXCollections.observableArrayList(dashboardService.getDashboardItems()));
    }

    private void setupSettings() {
        themeChoice.setItems(FXCollections.observableArrayList("Light", "Dark", "System Default"));
        themeChoice.setValue("Light");

        languageChoice.setItems(FXCollections.observableArrayList("English", "French", "Spanish", "German", "Japanese"));
        languageChoice.setValue("English");

        emailNotifCheck.setSelected(true);
        desktopNotifCheck.setSelected(true);
        weeklyReportCheck.setSelected(false);

        displayNameField.setText("Sidak Sharma");
    }
}
