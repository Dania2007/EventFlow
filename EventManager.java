package eventflow.service;

import java.util.*;

import eventflow.model.*;
import eventflow.algorithms.*;

public class EventManager {

    private final List<Event> events = new ArrayList<>();
    private final List<Participant> participants = new ArrayList<>();
    private final List<Registration> registrations = new ArrayList<>();

    private int nextParticipantId = 1;
    private int bookingNumber = 1001;

    public EventManager() {
        try {
            reloadEventsFromDataset();
            System.out.println(
                "Dataset loaded successfully: "
                + events.size() + " events."
            );
        } catch (java.io.IOException ex) {
            System.err.println(
                "Could not load CSV dataset: " + ex.getMessage()
            );
        }
    }

    // Load or reload events from the CSV file.
    public int reloadEventsFromDataset()
            throws java.io.IOException {

        List<Event> loaded = EventDatasetLoader.loadEvents();
        Set<Integer> ids = new HashSet<>();

        for (Event event : loaded) {
            if (!ids.add(event.getId())) {
                throw new java.io.IOException(
                    "Duplicate event ID: " + event.getId()
                );
            }
        }

        events.clear();
        events.addAll(loaded);

        return events.size();
    }

    // Create activities using the CSV start_hour and finish_hour.
    public List<Activity> getDatasetActivities() {
        List<Activity> activities = new ArrayList<>();

        for (Event event : getEvents()) {
            if (event.getFinishHour() > event.getStartHour()
                    && event.getFinishHour() <= 24) {

                activities.add(new Activity(
                    event.getName(),
                    event.getStartHour(),
                    event.getFinishHour()
                ));
            }
        }

        return activities;
    }

    // Existing Add Event option, retained for compatibility.
    public boolean addEvent(int id, String name, String code,
                            String description, int capacity) {

        if (id <= 0 || capacity <= 0 || name == null
                || name.isBlank() || code == null
                || code.isBlank()) {
            return false;
        }

        if (findEvent(id) != null) {
            return false;
        }

        events.add(new Event(
            id, name.trim(), code.trim(),
            description == null ? "" : description.trim(),
            capacity
        ));

        return true;
    }

    private List<Event> sortedEvents() {
        List<Event> sorted = new ArrayList<>(events);
        sorted.sort(Comparator.comparingInt(Event::getId));
        return sorted;
    }

    public List<Event> getEvents() {
        return sortedEvents();
    }

    // M2: Binary Search works on events sorted by ID.
    public Event findEvent(int id) {
        return BinarySearch.search(sortedEvents(), id);
    }

    // Participant functionality is retained from your original project.
    public boolean addParticipant(String name, String email) {

        if (name == null || name.isBlank()
                || email == null || !email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            return false;
        }

        for (Participant p : participants) {
            if (p.getEmail().equalsIgnoreCase(email.trim())) {
                return false;
            }
        }

        participants.add(new Participant(
            nextParticipantId++, name.trim(), email.trim()
        ));

        return true;
    }

    public List<Participant> getParticipants() {
        return new ArrayList<>(participants);
    }

    public boolean registerParticipant(int eventId, String email) {

        if (email == null) {
            return false;
        }

        Event event = findEvent(eventId);

        if (event == null) {
            return false;
        }

        Participant participant = null;

        for (Participant p : participants) {
            if (p.getEmail().equalsIgnoreCase(email.trim())) {
                participant = p;
                break;
            }
        }

        if (participant == null) {
            return false;
        }

        for (Registration r : registrations) {
            if (r.getEventId() == eventId
                    && r.getParticipantId() == participant.getId()) {
                return false;
            }
        }

        if (!event.register()) {
            return false;
        }

        String bookingCode = "EF-" + bookingNumber++;

        registrations.add(new Registration(
            eventId, participant.getId(), bookingCode
        ));

        System.out.println(
            "Registration successful! Booking code: " + bookingCode
        );

        return true;
    }

    public List<Registration> getRegistrations() {
        return new ArrayList<>(registrations);
    }

    // M1: KMP searches dataset event names, descriptions and categories.
    public List<Event> searchKeyword(String keyword) {

        List<Event> result = new ArrayList<>();

        for (Event event : events) {
            if (KMP.search(event.getName(), keyword)
                    || KMP.search(event.getDescription(), keyword)
                    || KMP.search(event.getCategory(), keyword)) {
                result.add(event);
            }
        }

        return result;
    }

    // M1: Trie autocomplete is built from dataset event names.
    public List<String> autocomplete(String prefix) {

        EventTries trie = new EventTries();

        for (Event event : events) {
            trie.insert(event.getName());
        }

        return trie.autocomplete(prefix);
    }

    // M1: Edit Distance compares input with dataset event names.
    public String suggestEvent(String input) {

        if (input == null || input.isBlank()) {
            return "Please enter an event name.";
        }

        Event best = null; 
        
        int bestDistance = Integer.MAX_VALUE;

        for (Event event : events) {
            int distance = EditDistance.calculate(
                input.trim(), event.getName()
            );

            if (distance < bestDistance) {
                bestDistance = distance;
                best = event;
            }
        }

        if (best != null && bestDistance <= 3) {
            return best.getName();
        }

        return "No close event name found.";
    }
    
}