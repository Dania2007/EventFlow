
package eventflow.algorithms;

public class ThreeSATToClique {

    // Opposite literals cannot both be selected.
    private static boolean compatible(int a, int b) {
        return a != -b;
    }

    private static boolean findClique(
            int[][] clauses, int clauseIndex, int[] chosen) {

        if (clauseIndex == clauses.length) {
            return true;
        }

        for (int literal : clauses[clauseIndex]) {
            boolean valid = true;

            for (int i = 0; i < clauseIndex; i++) {
                if (!compatible(literal, chosen[i])) {
                    valid = false;
                    break;
                }
            }

            if (valid) {
                chosen[clauseIndex] = literal;

                if (findClique(clauses, clauseIndex + 1, chosen)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static String describeChoice(int literal) {
        String[] requirements = {
            "Volunteer available",
            "Suitable room available",
            "Time slot available"
        };

        String name = requirements[Math.abs(literal) - 1];

        return literal > 0 ? name : "NOT (" + name + ")";
    }

    public static void runDemo() {
        int[][] clauses = {
            {1, 2, -3},
            {-1, 2, 3},
            {1, -2, 3}
        };

        int[] chosen = new int[clauses.length];

        System.out.println(
            "\n===== EventFlow: Compatible Scheduling Choices =====");

        System.out.println(
            "Converting scheduling constraints into a compatibility graph...");

        if (findClique(clauses, 0, chosen)) {
            System.out.println(
                "Compatible choices found for all " +
                clauses.length + " scheduling constraints:");

            for (int i = 0; i < chosen.length; i++) {
                System.out.println(
                    "Constraint " + (i + 1) + ": " +
                    describeChoice(chosen[i]));
            }

            System.out.println(
                "A clique of size " + clauses.length +
                " exists in the constructed graph.");
        } else {
            System.out.println(
                "No compatible selection was found.");
        }
    }
}
