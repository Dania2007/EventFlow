
package eventflow.algorithms;

public class VertexCoverApproximation {

    public static void runDemo() {
        // Edges represent conflicts between event-planning choices.
        int[][] edges = {
            {0, 1},
            {0, 2},
            {1, 2},
            {1, 3},
            {2, 3}
        };

        String[] choices = {
            "Volunteer assignment A",
            "Room reservation A",
            "Session time slot A",
            "Session time slot B"
        };

        boolean[] selected = new boolean[choices.length];
        boolean[] covered = new boolean[edges.length];

        // Repeatedly choose an uncovered edge and select both endpoints.
        for (int i = 0; i < edges.length; i++) {
            if (!covered[i]) {
                int u = edges[i][0];
                int v = edges[i][1];

                selected[u] = true;
                selected[v] = true;

                // Mark every edge incident to either selected endpoint.
                for (int j = 0; j < edges.length; j++) {
                    int a = edges[j][0];
                    int b = edges[j][1];

                    if (selected[a] || selected[b]) {
                        covered[j] = true;
                    }
                }
            }
        }

        boolean allCovered = true;

        for (boolean status : covered) {
            if (!status) {
                allCovered = false;
                break;
            }
        }

        System.out.println(
            "\n===== EventFlow: Scheduling Conflict Review =====");

        System.out.println(
            "Approximate vertex cover for event-planning conflicts:");

        int count = 0;

        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                System.out.println("- " + choices[i]);
                count++;
            }
        }

        System.out.println("Selected choices: " + count);

        System.out.println(
            allCovered
                ? "All sample scheduling conflicts are covered."
                : "Some scheduling conflicts remain uncovered.");
    }
}
