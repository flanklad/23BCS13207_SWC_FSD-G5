# Microsoft-Dashboard (Q3)

A content-heavy **admin dashboard** built with **Spring Boot 3** and **JavaFX 21**, styled after Microsoft's Fluent Design System.  
Tab-based navigation switches between three fully independent content panels — no page reloads, no web server.

---

## Features

| Tab | Content |
|---|---|
| **Overview** | 4 live metric cards (Total Users · Active Tasks · Revenue · Performance), scrollable Recent Activity feed, System Status badges |
| **Details** | Searchable TableView with 10 employee records; Status column is color-coded (Active / On Leave / Inactive) |
| **Settings** | Appearance (theme, language), Notifications (checkbox toggles), Account card with editable display name and save confirmation |

### Tab system behaviour
- Only one tab is active at a time — inactive panels are hidden **and** removed from layout (`visible=false` + `managed=false`) so they take up zero space.
- Active tab gets a 3 px `#0078D4` bottom-border indicator via the `tab-active` CSS class, toggled programmatically.

---

## Project Structure

```
Microsoft-Dashboard/
├── pom.xml
└── src/main/
    ├── java/day2fsswc/microsoftdashboard/
    │   ├── MicrosoftDashboardApplication.java   ← entry point (launches JavaFX)
    │   ├── DashboardJavaFXApp.java               ← JavaFX Application + Spring bootstrap
    │   ├── controller/DashboardController.java   ← tab switching + @FXML data binding
    │   ├── service/DashboardService.java         ← Spring @Service — mock data provider
    │   └── model/DashboardItem.java              ← JavaFX StringProperty model for TableView
    └── resources/
        ├── fxml/dashboard.fxml                   ← full UI layout (FXML 2)
        └── css/dashboard.css                     ← Microsoft Fluent Design 2 stylesheet
```

### Key implementation details

- **Spring + JavaFX wiring** — `FXMLLoader.setControllerFactory(springContext::getBean)` hands controller instantiation to Spring, so `@Autowired` works inside FXML controllers.
- **Tab switching** — `DashboardController.showContent()` loops over all three `VBox` panels and toggles `visible` + `managed` so only the target panel occupies space.
- **Color-coded table cells** — a custom `TableCell` factory on the Status column adds one of `status-active`, `status-inactive`, or `status-leave` CSS classes based on the cell value.
- **Save feedback** — the Settings "Save Changes" button shows a confirmation label for 2.5 s using a background thread + `Platform.runLater`.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| UI Framework | JavaFX 21 (`javafx-controls`, `javafx-fxml`) |
| Backend / DI | Spring Boot 3.3 (`spring-boot-starter`) |
| Build | Maven 3 + `javafx-maven-plugin 0.0.8` |

---

## How to Run

### Option 1 — Maven (recommended)

```bash
# Windows
.\mvnw.cmd javafx:run

# macOS / Linux
./mvnw javafx:run
```

### Option 2 — IntelliJ IDEA

Open the `Microsoft-Dashboard` folder as a Maven project, let it sync, then run `MicrosoftDashboardApplication.main()`.

> **Note:** If IntelliJ reports missing JavaFX modules, add these VM options to the run configuration:
> ```
> --module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls,javafx.fxml
> ```

---

## Requirements

- Java 17+
- Maven 3.6+
- Internet access on first run (Maven downloads JavaFX 21 jars ~30 MB)
