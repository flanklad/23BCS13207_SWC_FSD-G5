# Basic Twitter(P2)

A Twitter / Amazon-review–style **post composer** built with **Core Java 17** and **JavaFX 21**.  
No Spring Boot, no web server — a standalone desktop GUI application.

---

## Features

| Feature | Detail |
|---|---|
| Textarea with hard limit | 200-character maximum; excess input is blocked at the keystroke level |
| Live character counter | Circular SVG-style progress ring fills as you type; numeric label shows characters remaining |
| Yellow warning | Ring + counter + banner turn amber when usage reaches **80 %** (160 chars) |
| Red danger | Ring + counter + banner turn red when usage reaches **90 %** (180 chars) |
| Submit button | Disabled until at least one non-whitespace character is present; re-disabled at the limit |
| Post feed | Each submitted post is prepended to a scrollable card feed below the composer |

---

## Project Structure

```
BASIC-TWITTER/
├── pom.xml
└── src/main/java/day1fsswc/basictwitter/
    └── BasicTwitterApplication.java   # Application + all UI logic (single file)
```

### Key implementation details

- **Progress ring** — two stacked `Circle` shapes (gray track + colored progress).  
  The progress circle uses `strokeDashArray` updated on every keystroke:  
  `[drawn = circumference × ratio, circumference]` — identical to the CSS `stroke-dasharray` trick.
- **Input enforcement** — the `textProperty` listener rolls back any change that would exceed 200 chars, so paste-overflow is blocked too.
- **Warning thresholds** — `WARN_RATIO = 0.80` and `DANGER_RATIO = 0.90` are named constants at the top of the class.

---

## Tech Stack

- **Java 17**
- **JavaFX 21** (`javafx-controls`)
- **Maven** + `javafx-maven-plugin 0.0.8`

---

## How to Run

### Option 1 — Maven wrapper (recommended)

```bash
# Windows
.\mvnw.cmd javafx:run

# macOS / Linux
./mvnw javafx:run
```

### Option 2 — IntelliJ IDEA

Open the project, let Maven sync, then run `BasicTwitterApplication.main()`.  
If the IDE reports missing JavaFX modules, add these VM options to the run configuration:

```
--module-path <path-to-javafx-sdk>/lib --add-modules javafx.controls
```

---

## Requirements

- Java 17+
- Maven 3.6+
- Internet access on first run (Maven downloads JavaFX 21 jars ~30 MB)
