package com.fitness.storage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fitness.model.Goal;
import com.google.gson.Gson;

public class FileStorage {

    private static final String FILE = "goals.json";
    private final Gson gson = new Gson();

    public void save(List<Goal> goals) {
        try (Writer writer = new FileWriter(FILE)) {
            gson.toJson(goals, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Goal> load() {
        try (Reader reader = new FileReader(FILE)) {
            Goal[] arr = gson.fromJson(reader, Goal[].class);
            return arr == null ? new ArrayList<>() : Arrays.asList(arr);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }
}
