package day1fsswc.basictwitter;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class
BasicTwitterApplication extends Application {

    private static final int    MAX_CHARS    = 200;
    private static final double WARN_RATIO   = 0.80;
    private static final double DANGER_RATIO = 0.90;

    // circumference of the progress ring circle (r = 15)
    private static final double CIRCUMFERENCE = 2 * Math.PI * 15;

    private TextArea tweetBox;
    private Label    counterLabel;
    private Circle   progressArc;
    private Button   submitBtn;
    private Label    warningBanner;
    private VBox     feedContainer;

    // -------------------------------------------------------------------------
    // Application entry point
    // -------------------------------------------------------------------------

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(16);
        root.setPadding(new Insets(36, 24, 36, 24));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #f0f2f5;");

        HBox header   = buildHeader();
        VBox composer = buildComposer();
        feedContainer = new VBox(12);

        header.setMaxWidth(600);
        composer.setMaxWidth(600);
        feedContainer.setMaxWidth(600);

        Label placeholder = new Label("No posts yet — be the first!");
        placeholder.setStyle("-fx-text-fill: #536471; -fx-font-size: 14px;");
        placeholder.setMaxWidth(Double.MAX_VALUE);
        placeholder.setAlignment(Pos.CENTER);
        feedContainer.getChildren().add(placeholder);

        root.getChildren().addAll(header, composer, feedContainer);

        ScrollPane scroll = new ScrollPane(root);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background-color: #f0f2f5; -fx-background: #f0f2f5;");

        stage.setTitle("Basic Twitter");
        stage.setScene(new Scene(scroll, 680, 760));
        stage.setMinWidth(480);
        stage.show();
    }

    // -------------------------------------------------------------------------
    // UI builders
    // -------------------------------------------------------------------------

    private HBox buildHeader() {
        Label icon  = new Label("𝕏");
        icon.setStyle("-fx-text-fill: #1d9bf0; -fx-font-size: 26px; -fx-font-weight: bold;");

        Label title = new Label("Basic Twitter");
        title.setStyle("-fx-text-fill: #0f1419; -fx-font-size: 20px; -fx-font-weight: bold;");

        HBox header = new HBox(10, icon, title);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private VBox buildComposer() {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.12), 8, 0, 0, 1);"
        );

        // avatar + textarea
        tweetBox = new TextArea();
        tweetBox.setPromptText("What's happening?");
        tweetBox.setWrapText(true);
        tweetBox.setPrefRowCount(4);
        tweetBox.setStyle(
            "-fx-font-size: 16px;" +
            "-fx-background-color: transparent;" +
            "-fx-border-color: transparent;" +
            "-fx-focus-color: transparent;" +
            "-fx-faint-focus-color: transparent;"
        );
        HBox.setHgrow(tweetBox, Priority.ALWAYS);

        HBox composerRow = new HBox(12, buildAvatar("Y", "#1d9bf0"), tweetBox);
        composerRow.setAlignment(Pos.TOP_LEFT);

        // warning banner (hidden initially)
        warningBanner = new Label();
        warningBanner.setWrapText(true);
        warningBanner.setMaxWidth(Double.MAX_VALUE);
        warningBanner.setPadding(new Insets(8, 12, 8, 12));
        warningBanner.setVisible(false);
        warningBanner.setManaged(false);

        // footer: ring + button
        HBox footer = buildFooter();

        card.getChildren().addAll(composerRow, warningBanner, footer);

        // listener — enforce limit and update visuals on every keystroke
        tweetBox.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() > MAX_CHARS) {
                tweetBox.setText(oldVal.length() <= MAX_CHARS ? oldVal : newVal.substring(0, MAX_CHARS));
                return;
            }
            refreshCounter(newVal.length());
        });

        return card;
    }

    private HBox buildFooter() {
        HBox footer = new HBox(14, buildRingCounter(), buildSubmitButton());
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(12, 0, 0, 0));
        footer.setStyle("-fx-border-color: #eff3f4; -fx-border-width: 1 0 0 0;");
        return footer;
    }

    private StackPane buildRingCounter() {
        // grey track
        Circle track = new Circle(15);
        track.setFill(Color.TRANSPARENT);
        track.setStroke(Color.web("#eff3f4"));
        track.setStrokeWidth(3);
        track.setStrokeType(StrokeType.CENTERED);

        // coloured progress arc (starts empty)
        progressArc = new Circle(15);
        progressArc.setFill(Color.TRANSPARENT);
        progressArc.setStroke(Color.web("#1d9bf0"));
        progressArc.setStrokeWidth(3);
        progressArc.setStrokeType(StrokeType.CENTERED);
        progressArc.setStrokeLineCap(StrokeLineCap.ROUND);
        progressArc.setRotate(-90);                        // start from 12 o'clock
        progressArc.getStrokeDashArray().setAll(0.0, CIRCUMFERENCE); // empty initially

        counterLabel = new Label("200");
        counterLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: #536471;");

        StackPane ring = new StackPane(track, progressArc, counterLabel);
        ring.setPrefSize(36, 36);
        ring.setMinSize(36, 36);
        ring.setMaxSize(36, 36);
        return ring;
    }

    private Button buildSubmitButton() {
        submitBtn = new Button("Post");
        submitBtn.setDisable(true);
        applySubmitStyle(submitBtn, "#1d9bf0");

        submitBtn.setOnMouseEntered(e -> { if (!submitBtn.isDisabled()) applySubmitStyle(submitBtn, "#1a8cd8"); });
        submitBtn.setOnMouseExited (e -> { if (!submitBtn.isDisabled()) applySubmitStyle(submitBtn, "#1d9bf0"); });
        submitBtn.setOnAction(e -> handleSubmit());
        return submitBtn;
    }

    private static void applySubmitStyle(Button btn, String bg) {
        btn.setStyle(
            "-fx-background-color: " + bg + ";" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 999;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 10 22 10 22;" +
            "-fx-cursor: hand;"
        );
    }

    private StackPane buildAvatar(String initial, String hexColor) {
        Circle bg = new Circle(22);
        bg.setFill(Color.web(hexColor));

        Label lbl = new Label(initial);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        StackPane avatar = new StackPane(bg, lbl);
        avatar.setPrefSize(44, 44);
        avatar.setMinSize(44, 44);
        avatar.setMaxSize(44, 44);
        return avatar;
    }

    // -------------------------------------------------------------------------
    // Logic
    // -------------------------------------------------------------------------

    private void refreshCounter(int length) {
        int    remaining = MAX_CHARS - length;
        double ratio     = (double) length / MAX_CHARS;
        double drawn     = CIRCUMFERENCE * ratio;

        // update ring arc
        progressArc.getStrokeDashArray().setAll(drawn, CIRCUMFERENCE);

        if (ratio >= 1.0) {
            setRingColor("#f4212e");
            counterLabel.setText("0");
            showBanner("Character limit reached (200/200).", true);

        } else if (ratio >= DANGER_RATIO) {
            setRingColor("#f4212e");
            counterLabel.setText(String.valueOf(remaining));
            showBanner("Almost there — only " + remaining + " character" + (remaining == 1 ? "" : "s") + " left!", true);

        } else if (ratio >= WARN_RATIO) {
            setRingColor("#f4a118");
            counterLabel.setText(String.valueOf(remaining));
            showBanner("Getting close — " + remaining + " character" + (remaining == 1 ? "" : "s") + " remaining.", false);

        } else {
            setRingColor("#1d9bf0");
            counterLabel.setText(String.valueOf(remaining));
            hideBanner();
        }

        submitBtn.setDisable(length == 0 || tweetBox.getText().trim().isEmpty());
    }

    private void setRingColor(String hex) {
        progressArc.setStroke(Color.web(hex));
        counterLabel.setStyle("-fx-font-size: 10px; -fx-font-weight: bold; -fx-text-fill: " + hex + ";");
    }

    private void showBanner(String message, boolean isDanger) {
        warningBanner.setText(message);
        warningBanner.setStyle(isDanger
            ? "-fx-background-color: #fdecea; -fx-text-fill: #c0392b; -fx-background-radius: 8; -fx-font-size: 13px; -fx-font-weight: bold;"
            : "-fx-background-color: #fff8e1; -fx-text-fill: #b36200; -fx-background-radius: 8; -fx-font-size: 13px; -fx-font-weight: bold;"
        );
        warningBanner.setVisible(true);
        warningBanner.setManaged(true);
    }

    private void hideBanner() {
        warningBanner.setVisible(false);
        warningBanner.setManaged(false);
    }

    private void handleSubmit() {
        String text = tweetBox.getText().trim();
        if (text.isEmpty() || text.length() > MAX_CHARS) return;

        // remove the "no posts" placeholder on first post
        feedContainer.getChildren().removeIf(n -> n instanceof Label);

        feedContainer.getChildren().add(0, buildPostCard(text));

        tweetBox.clear();
        refreshCounter(0);
    }

    private VBox buildPostCard(String text) {
        StackPane avatar = buildAvatar("Y", "#0ea5e9");

        Label meta = new Label("You  ·  just now");
        meta.setStyle("-fx-text-fill: #536471; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label body = new Label(text);
        body.setWrapText(true);
        body.setStyle("-fx-text-fill: #0f1419; -fx-font-size: 14px;");
        body.setMaxWidth(500);

        VBox content = new VBox(4, meta, body);
        HBox.setHgrow(content, Priority.ALWAYS);

        HBox inner = new HBox(12, avatar, content);
        inner.setAlignment(Pos.TOP_LEFT);

        VBox card = new VBox(inner);
        card.setPadding(new Insets(18, 20, 18, 20));
        card.setStyle(
            "-fx-background-color: white;" +
            "-fx-background-radius: 16;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.10), 6, 0, 0, 1);"
        );
        return card;
    }

}
