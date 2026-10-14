package eventflow.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import eventflow.model.Event;

public class EventDatasetLoader {

    public static List<Event> loadEvents() throws IOException {

        Path path = Path.of("data", "eventflow_events_dataset.csv");

        // Also support running from the folder above EventFlow.
        if (!Files.exists(path)) {
            path = Path.of(
                "EventFlow", "data", "eventflow_events_dataset.csv"
            );
        }

        if (!Files.exists(path)) {
            throw new IOException(
                "CSV not found. Keep eventflow_events_dataset.csv "
                + "inside the project's data folder."
            );
        }

        List<Event> events = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(
                path, StandardCharsets.UTF_8)) {

            String line = reader.readLine(); // Skip CSV header.
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                List<String> fields = parseCsvLine(line);

                if (fields.size() < 9) {
                    throw new IOException(
                        "Invalid CSV row at line " + lineNumber
                    );
                }

                try {
                    int id = Integer.parseInt(fields.get(0).trim());
                    String name = fields.get(1).trim();
                    String code = fields.get(2).trim();
                    String description = fields.get(3).trim();
                    int capacity = Integer.parseInt(fields.get(4).trim());
                    String category = fields.get(5).trim();
                    int registrations =
                        Integer.parseInt(fields.get(6).trim());
                    int startHour =
                        Integer.parseInt(fields.get(7).trim());
                    int finishHour =
                        Integer.parseInt(fields.get(8).trim());

                    if (id <= 0 || name.isEmpty()
                            || capacity <= 0 || registrations < 0
                            || startHour < 0 || startHour >= 24
                            || finishHour <= startHour
                            || finishHour > 24) {
                        throw new IllegalArgumentException(
                            "Invalid event values"
                        );
                    }

                    events.add(new Event(
                        id, name, code, description, capacity,
                        registrations, category, startHour, finishHour
                    ));

                } catch (RuntimeException ex) {
                    throw new IOException(
                        "Error reading CSV line " + lineNumber
                        + ": " + ex.getMessage(), ex
                    );
                }
            }
        }

        return events;
    }

    // Supports quoted CSV fields containing commas.
    private static List<String> parseCsvLine(String line) {

        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (quoted && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {
                    field.append('"');
                    i++;
                } else {
                    quoted = !quoted;
                }
            } else if (ch == ',' && !quoted) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(ch);
            }
        }

        fields.add(field.toString());
        return fields;
    }
}