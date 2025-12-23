package com.fitness.model;

import java.time.LocalTime;

public class Goal {

    private String name;
    private String description;
    private LocalTime reminderTime;
    private boolean completed;

    public Goal(String name, String description, LocalTime reminderTime) {
        this.name = name;
        this.description = description;
        this.reminderTime = reminderTime;
    }

    public String getName() {
        return name;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public void markCompleted() {
        completed = true;
    }
}
