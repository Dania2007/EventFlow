
package eventflow.main;

import java.util.*;
import eventflow.model.*;
import eventflow.service.EventManager;
import eventflow.algorithms.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final EventManager manager = new EventManager();

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n========== EVENTFLOW ==========");
            System.out.println("1. Load / Reload CSV Dataset");
            System.out.println("2. View All Events");
            System.out.println("3. Find Event by ID (Binary Search)");
            System.out.println("4. Add Participant");
            System.out.println("5. View Participants");
            System.out.println("6. Register for Event");
            System.out.println("7. View Registrations");
            System.out.println("8. Search Events (KMP)");
            System.out.println("9. Event Name Autocomplete (Trie)");
            System.out.println("10. Suggest Event Name (Edit Distance)");
            System.out.println("11. Greedy Activity Selection");
            System.out.println("12. Bitmask DP Activity Planning");
            System.out.println("13. Optimize Large-Scale Event Resource Allocation (Dinic)");
            System.out.println("14. Assign Volunteers to Event Activities (Bipartite Matching)");

            System.out.println("15. Validate Event Scheduling Constraints (3-SAT)");
            System.out.println("16. Find Compatible Event Plans (3-SAT to CLIQUE)");
            System.out.println("17. Identify Conflict-Free Event Choices (CLIQUE to Independent Set)");
            System.out.println("18. Identify Conflicts to Address (Independent Set to Vertex Cover)");
            System.out.println("19. Find a Small Set of Scheduling Conflicts to Review (Vertex Cover Approximation)");

System.out.println("20. Rank Events by Registrations (Randomized QuickSort)");
System.out.println("21. Calculate Parallel Event Statistics (Parallel Reduce)");


            System.out.print("Enter choice: ");

            choice = readInt();

            switch (choice) {
                case 1 -> loadDataset();
                case 2 -> viewEvents();
                case 3 -> findEvent();
                case 4 -> addParticipant();
                case 5 -> viewParticipants();
                case 6 -> register();
                case 7 -> viewRegistrations();
                case 8 -> searchEvents();
                case 9 -> autocomplete();
                case 10 -> suggestEvent();
                case 11 -> greedyPlanning();
                case 12 -> bitmaskPlanning();
                case 13 -> DinicAlgorithm.runDemo();
                case 14 -> BipartiteMatching.runDemo();

                case 15 -> ThreeSATSolver.runDemo();
                case 16 -> ThreeSATToClique.runDemo();
                case 17 -> CliqueToIndependentSet.runDemo();
                case 18 -> IndependentSetToVertexCover.runDemo();
                case 19 -> VertexCoverApproximation.runDemo();

case 20 -> RandomizedQuickSort.runDemo();
case 21 -> ParallelReduce.runDemo();


                case 0 -> System.out.println(
                    "Thank you for using EventFlow!"
                );
                default -> System.out.println("Invalid choice.");
            }

        } while (choice != 0);

        sc.close();
    }

    private static int readInt() {
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            sc.nextLine();
        }

        int value = sc.nextInt();
        sc.nextLine();
        return value;
    }

    // OPTION 1: Load or reload the CSV dataset.
    private static void loadDataset() {
        try {
            int count = manager.reloadEventsFromDataset();

            System.out.println(
                "Dataset loaded successfully!"
            );
            System.out.println(
                "Total events loaded: " + count
            );

        } catch (java.io.IOException e) {
            System.out.println(
                "Could not load dataset: " + e.getMessage()
            );
        }
    }

    // OPTION 2: View dataset events.
    private static void viewEvents() {
        List<Event> events = manager.getEvents();

        if (events.isEmpty()) {
            System.out.println(
                "No events available. Select option 1 to load the dataset."
            );
            return;
        }

        for (Event event : events) {
            System.out.println(event);
            System.out.println("------------------------------");
        }
    }

    // OPTION 3: Binary Search by event ID.
    private static void findEvent() {
        System.out.print("Enter event ID: ");
        int id = readInt();

        Event event = manager.findEvent(id);

        if (event == null) {
            System.out.println("Event not found.");
        } else {
            System.out.println(event);
        }
    }

    // OPTION 4: Add a participant.
    private static void addParticipant() {
        System.out.print("Participant name: ");
        String name = sc.nextLine();

        System.out.print("Email address: ");
        String email = sc.nextLine();

        if (manager.addParticipant(name, email)) {
            System.out.println(
                "Participant added successfully!"
            );
        } else {
            System.out.println(
                "Invalid details or email already exists."
            );
        }
    }

    // OPTION 5: View participants.
    private static void viewParticipants() {
        List<Participant> participants =
            manager.getParticipants();

        if (participants.isEmpty()) {
            System.out.println("No participants registered.");
            return;
        }

        for (Participant p : participants) {
            System.out.println(p);
        }
    }

    // OPTION 6: Register a participant for a dataset event.
    private static void register() {
        System.out.print("Enter event ID: ");
        int eventId = readInt();

        System.out.print("Enter participant email: ");
        String email = sc.nextLine();

        if (!manager.registerParticipant(eventId, email)) {
            System.out.println(
                "Registration failed. Check the event, participant, "
                + "capacity, or duplicate registration."
            );
        }
    }

    // OPTION 7: View registrations.
    private static void viewRegistrations() {
        List<Registration> registrations =
            manager.getRegistrations();

        if (registrations.isEmpty()) {
            System.out.println("No registrations yet.");
            return;
        }

        for (Registration r : registrations) {
            System.out.println(r);
        }
    }

    // OPTION 8: KMP search in dataset fields.
    private static void searchEvents() {
        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        List<Event> results =
            manager.searchKeyword(keyword);

        if (results.isEmpty()) {
            System.out.println("No matching events found.");
        } else {
            System.out.println("Matching events:");
            results.forEach(System.out::println);
        }
    }

    // OPTION 9: Trie autocomplete from dataset event names.
    private static void autocomplete() {
        System.out.print(
            "Enter beginning of event name: "
        );
        String prefix = sc.nextLine();

        List<String> results =
            manager.autocomplete(prefix);

        if (results.isEmpty()) {
            System.out.println("No suggestions found.");
        } else {
            System.out.println("Suggestions:");
            results.forEach(System.out::println);
        }
    }

    // OPTION 10: Edit Distance suggestion from dataset names.
    private static void suggestEvent() {
        System.out.print(
            "Enter event name (possibly misspelled): "
        );
        String input = sc.nextLine();

        System.out.println(
            "Suggestion: " + manager.suggestEvent(input)
        );
    }

    // Get activity data from the CSV through EventManager.
    private static List<Activity> datasetActivities() {
        List<Activity> activities =
            manager.getDatasetActivities();

        if (activities.isEmpty()) {
            System.out.println(
                "No dataset activities available. "
                + "Load the CSV using option 1."
            );
        }

        return activities;
    }

    // OPTION 11: Greedy Activity Selection.
    private static void greedyPlanning() {
        List<Activity> activities = datasetActivities();

        if (activities.isEmpty()) {
            return;
        }

        List<Activity> selected =
            GreedyActivitySelection.select(activities);

        System.out.println(
            "Selected non-overlapping dataset activities:"
        );

        if (selected.isEmpty()) {
            System.out.println("No activities selected.");
        } else {
            selected.forEach(System.out::println);
        }
    }

    // OPTION 12: Bitmask DP Activity Planning.
    private static void bitmaskPlanning() {
        List<Activity> activities = datasetActivities();

        if (activities.isEmpty()) {
            return;
        }

        // Limit the number of activities for bitmask performance.
        if (activities.size() > 20) {
            activities = new ArrayList<>(
                activities.subList(0, 20)
            );

            System.out.println(
                "Using the first 20 activities for Bitmask DP."
            );
        }

        System.out.print(
            "Available time budget (hours): "
        );
        int budget = readInt();

        if (budget < 0) {
            System.out.println(
                "Time budget cannot be negative."
            );
            return;
        }

        try {
            List<Activity> selected =
                BitmaskDP.select(activities, budget);

            int totalHours = selected.stream()
                .mapToInt(a ->
                    a.getFinish() - a.getStart()
                )
                .sum();

            System.out.println(
                "Selected activities within budget:"
            );

            if (selected.isEmpty()) {
                System.out.println(
                    "No activities fit within the time budget."
                );
            } else {
                selected.forEach(System.out::println);
            }

            System.out.println(
                "Total duration: " + totalHours + " hours"
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Bitmask DP error: " + e.getMessage()
            );
        }
    }
}