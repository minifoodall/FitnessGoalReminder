package com.fitness.service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fitness.model.Goal;

public class ReminderService {

    private final ScheduledExecutorService scheduler =
            Executors.newScheduledThreadPool(1);

    public void scheduleReminder(Goal goal) {
        long delay = Duration.between(
                LocalTime.now(), goal.getReminderTime()).getSeconds();

        if (delay < 0) delay += 86400;

        scheduler.schedule(() ->
            System.out.println("⏰ Reminder: " + goal.getName()),
            delay, TimeUnit.SECONDS);
    }
}
