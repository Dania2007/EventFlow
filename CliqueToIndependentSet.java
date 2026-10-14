
package eventflow.algorithms;

public class CliqueToIndependentSet {

    public static void runDemo() {
        // Compatibility graph:
        // 0 = Volunteer A assigned to Session 1
        // 1 = Room A reserved for Session 1
        // 2 = Session 1 scheduled in Slot A
        // 3 = Session 2 scheduled in Slot B

        int[][] graph = {
            {0, 1, 1, 0},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {0, 0, 1, 0}
        };

        int n = graph.length;
        int[][] complement = new int[n][n];

        // Convert the compatibility graph into a conflict graph.
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    complement[i][j] = 1 - graph[i][j];
                }
            }
        }

        // Vertices 0, 1 and 2 form a clique in the original graph.
        int[] clique = {0, 1, 2};
        boolean independent = true;

        for (int i = 0; i < clique.length; i++) {
            for (int j = i + 1; j < clique.length; j++) {
                if (complement[clique[i]][clique[j]] == 1) {
                    independent = false;
                }
            }
        }

        System.out.println(
            "\n===== EventFlow: Conflict-Free Event Choices =====");

        System.out.println(
            "Converted the compatibility graph into a conflict graph.");

        if (independent) {
            System.out.println(
                "Conflict-free choices: Volunteer A, Room A, Session 1 Slot A");

            System.out.println(
                "Independent set size: " + clique.length);
        } else {
            System.out.println(
                "The selected choices contain a conflict.");
        }
    }
}
