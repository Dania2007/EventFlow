
package eventflow.algorithms;

public class IndependentSetToVertexCover {

    public static void runDemo() {
        // Vertices represent event-planning choices.
        // Edges represent conflicts between choices.

        int[][] edges = {
            {0, 1},  // Volunteer assignment conflict
            {1, 2},  // Room scheduling conflict
            {2, 3}   // Session time conflict
        };

        String[] choices = {
            "Volunteer A assignment",
            "Room A reservation",
            "Session 1 time slot",
            "Session 2 time slot"
        };

        // Independent set: vertices 0 and 2.
        boolean[] independent = {
            true, false, true, false
        };

        // The complement of an independent set is a vertex cover.
        boolean[] cover = new boolean[independent.length];

        for (int i = 0; i < independent.length; i++) {
            cover[i] = !independent[i];
        }

        boolean allConflictsCovered = true;

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (!cover[u] && !cover[v]) {
                allConflictsCovered = false;
                break;
            }
        }

        System.out.println(
            "\n===== EventFlow: Scheduling Conflict Coverage =====");

        System.out.println("Conflict-free planning choices:");

        for (int i = 0; i < independent.length; i++) {
            if (independent[i]) {
                System.out.println("- " + choices[i]);
            }
        }

        System.out.println("\nChoices included in the conflict cover:");

        int count = 0;

        for (int i = 0; i < cover.length; i++) {
            if (cover[i]) {
                System.out.println("- " + choices[i]);
                count++;
            }
        }

        System.out.println("Conflict cover size: " + count);

        System.out.println(
            allConflictsCovered
                ? "Every scheduling conflict is covered."
                : "Some scheduling conflicts remain uncovered.");
    }
}
