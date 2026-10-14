
package eventflow.algorithms;

public class ThreeSATSolver {

    // Positive numbers represent requirements; negative numbers mean NOT.
    // 1 = Volunteer available
    // 2 = Suitable room available
    // 3 = Time slot available

    private static boolean isSatisfied(
            int variables, int[][] clauses, int mask) {

        for (int[] clause : clauses) {
            boolean clauseTrue = false;

            for (int literal : clause) {
                int variable = Math.abs(literal) - 1;
                boolean value = (mask & (1 << variable)) != 0;

                if (literal < 0) {
                    value = !value;
                }

                if (value) {
                    clauseTrue = true;
                    break;
                }
            }

            if (!clauseTrue) {
                return false;
            }
        }

        return true;
    }

    public static void runDemo() {
        int variables = 3;

        // Sample event-scheduling constraints:
        // Each clause represents an alternative set of conditions.
        int[][] clauses = {
            {1, 2, -3},
            {-1, 2, 3},
            {1, -2, 3}
        };

        String[] requirements = {
            "Volunteer available",
            "Suitable room available",
            "Time slot available"
        };

        System.out.println("\n===== EventFlow: Scheduling Constraint Validation =====");

        int total = 1 << variables;

        for (int mask = 0; mask < total; mask++) {
            if (isSatisfied(variables, clauses, mask)) {

                System.out.println("Valid scheduling plan found:");

                for (int i = 0; i < variables; i++) {
                    boolean value = (mask & (1 << i)) != 0;

                    System.out.println(
                        requirements[i] + ": " +
                        (value ? "YES" : "NO")
                    );
                }

                System.out.println(
                    "Result: All sample scheduling constraints are satisfied."
                );
                return;
            }
        }

        System.out.println(
            "Result: No plan satisfies all scheduling constraints."
        );
    }
}
