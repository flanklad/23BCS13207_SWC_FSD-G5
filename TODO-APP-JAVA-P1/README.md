# TODO-APP-JAVA-P1

A desktop To-Do list application built with **Core Java 21** and **Java Swing**.  
No frameworks — pure AWT/Swing with custom-painted components.

---

## Features

| Feature | Detail |
|---|---|
| Add tasks | Type in the input field and press Enter or click **+ Add Task** |
| Complete tasks | Click the circle checkbox — text gets strikethrough and row dims |
| Edit tasks | Click **Edit** to inline-edit the text, **Save** to confirm or **Cancel** to discard |
| Delete tasks | Click **Delete** to remove a task permanently |
| Live stats | Header shows total task count and how many are completed |
| Empty state | Friendly placeholder shown when the list is empty |
| Input validation | Input border flashes red for 1.5 s if you try to add an empty task |

---

## Project Structure

```
TODO-APP-JAVA-P1/
├── pom.xml
└── src/main/java/com/seatbookingsystem/todoappjavap1/
    ├── TodoApp.java     # JFrame — layout, header, input row, scroll list
    ├── Todo.java        # Model — id, text, completed flag, toggle()
    └── TaskRow.java     # JPanel — checkbox, label/edit-field, action buttons
```

---

## Tech Stack

- **Java 21**
- **Java Swing** (JFrame, JPanel, JTextField, JScrollPane, custom paintComponent)
- **Maven** (build + runnable JAR)

---

## How to Run

### Option 1 — Maven (recommended)

```bash
cd TODO-APP-JAVA-P1
mvn package
java -jar target/TODO-APP-JAVA-P1-0.0.1-SNAPSHOT.jar
```

### Option 2 — IntelliJ IDEA

Open the `TODO-APP-JAVA-P1` folder as a Maven project, then run `TodoApp.main()`.

---

## Requirements

- Java 21+
- Maven 3.6+
