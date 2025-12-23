package com.fitness;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

public class App extends Application {

    private ComboBox<String> goalBox;
    private ComboBox<String> hourBox;
    private ComboBox<String> minuteBox;
    private ListView<String> goalList;

    private Timer timer = new Timer(true);

    @Override
    public void start(Stage stage) {

        // 🔹 Title
        Label title = new Label("🏋 Fitness Goal Reminder");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        // 🔹 Goal ComboBox (Editable)
        goalBox = new ComboBox<>();
        goalBox.setEditable(true);
        goalBox.getItems().addAll(
                "Morning Workout",
                "Drink Water",
                "Yoga Session",
                "Evening Walk",
                "Meditation",
                "Protein Intake"
        );
        goalBox.setPromptText("Type or select a goal");

        // 🔹 Time selection
        hourBox = new ComboBox<>();
        minuteBox = new ComboBox<>();
        loadHours();
        loadMinutes();

        HBox timeRow = new HBox(5,
                hourBox,
                new Label(":"),
                minuteBox
        );
        timeRow.setAlignment(Pos.CENTER_LEFT);

        // 🔹 Button
        Button addButton = new Button("Set Reminder");
        addButton.setOnAction(e -> setReminder());

        // 🔹 List of set goals
        goalList = new ListView<>();
        goalList.setPrefHeight(140);

        // 🔹 Layout
        VBox root = new VBox(15,
                title,
                new Label("Goal:"),
                goalBox,
                new Label("Reminder Time:"),
                timeRow,
                addButton,
                new Label("Set Reminders:"),
                goalList
        );

        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_LEFT);
        root.setStyle("""
                -fx-background-color: linear-gradient(to bottom, #15aefaff, #f5a422ff);
                -fx-font-size: 14px;
                -fx-text-fill: white;
                """);

        Scene scene = new Scene(root, 450, 520);
        stage.setTitle("Fitness Goal Reminder");
        stage.setScene(scene);
        stage.show();
    }

    // 🔔 Set Reminder
    private void setReminder() {
        String goal = goalBox.getEditor().getText();
        String hour = hourBox.getValue();
        String minute = minuteBox.getValue();

        if (goal == null || goal.isBlank() || hour == null || minute == null) {
            showAlert("Please select or enter goal and time.");
            return;
        }

        int h = Integer.parseInt(hour);
        int m = Integer.parseInt(minute);

        LocalTime now = LocalTime.now();
        LocalTime reminderTime = LocalTime.of(h, m);

        long delaySeconds = now.until(reminderTime, ChronoUnit.SECONDS);
        if (delaySeconds < 0) {
            delaySeconds += 24 * 60 * 60;
        }

        String displayText = goal + " ⏰ " + hour + ":" + minute;
        goalList.getItems().add(displayText);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> showNotification(goal));
            }
        }, delaySeconds * 1000);

        showAlert("Reminder set successfully!");
    }

    // 🔔 Notification + ringtone
    private void showNotification(String goal) {
        AudioClip clip = new AudioClip(
                getClass().getResource("/beep.mp3").toString()
        );
        clip.play();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Reminder");
        alert.setHeaderText("Fitness Reminder ⏰");
        alert.setContentText("Time to: " + goal);
        alert.show();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }

    private void loadHours() {
        for (int i = 0; i < 24; i++) {
            hourBox.getItems().add(String.format("%02d", i));
        }
        hourBox.setPromptText("HH");
    }

    private void loadMinutes() {
        for (int i = 0; i < 60; i++) {
            minuteBox.getItems().add(String.format("%02d", i));
        }
        minuteBox.setPromptText("MM");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
