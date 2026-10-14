
package eventflow.algorithms;

import java.util.Arrays;

public class BipartiteMatching {

    private static boolean findMatch(
            boolean[][] eligible,
            int volunteer,
            boolean[] visited,
            int[] activityOwner) {

        for (int activity = 0;
             activity < eligible[volunteer].length;
             activity++) {

            if (eligible[volunteer][activity]
                    && !visited[activity]) {

                visited[activity] = true;

                if (activityOwner[activity] == -1
                        || findMatch(
                            eligible,
                            activityOwner[activity],
                            visited,
                            activityOwner)) {

                    activityOwner[activity] = volunteer;
                    return true;
                }
            }
        }

        return false;
    }

    public static int maximumMatching(boolean[][] eligible) {
        if (eligible == null) {
            throw new IllegalArgumentException(
                "Volunteer eligibility matrix cannot be null.");
        }

        int volunteers = eligible.length;

        if (volunteers == 0) {
            return 0;
        }

        if (eligible[0] == null) {
            throw new IllegalArgumentException(
                "Eligibility matrix rows cannot be null.");
        }

        int activities = eligible[0].length;

        for (boolean[] row : eligible) {
            if (row == null || row.length != activities) {
                throw new IllegalArgumentException(
                    "Eligibility matrix must be rectangular.");
            }
        }

        int[] activityOwner = new int[activities];
        Arrays.fill(activityOwner, -1);

        int matched = 0;

        for (int volunteer = 0;
             volunteer < volunteers;
             volunteer++) {

            boolean[] visited = new boolean[activities];

            if (findMatch(
                    eligible, volunteer,
                    visited, activityOwner)) {
                matched++;
            }
        }

        return matched;
    }

    public static void runDemo() {

        String[] volunteers = {
            "Volunteer A",
            "Volunteer B",
            "Volunteer C"
        };

        String[] activities = {
            "Registration Desk",
            "Technical Support",
            "Event Coordination"
        };

        // true means the volunteer is eligible for that activity.
        boolean[][] eligible = {
            {true, true, false},
            {true, false, true},
            {false, true, true}
        };

        int[] activityOwner = new int[activities.length];
        Arrays.fill(activityOwner, -1);

        System.out.println(
            "\n===== EventFlow: Volunteer Assignment =====");

        System.out.println("Checking volunteer eligibility...");

        int matched = 0;

        for (int volunteer = 0;
             volunteer < volunteers.length;
             volunteer++) {

            boolean[] visited = new boolean[activities.length];

            if (findMatch(
                    eligible, volunteer, visited, activityOwner)) {
                matched++;
            }
        }

        System.out.println("\nVolunteer assignments:");

        for (int activity = 0;
             activity < activities.length;
             activity++) {

            if (activityOwner[activity] != -1) {
                System.out.println(
                    activities[activity] + " <- "
                    + volunteers[activityOwner[activity]]);
            } else {
                System.out.println(
                    activities[activity] + " <- No volunteer assigned");
            }
        }

        System.out.println(
            "\nMaximum volunteer-activity matches: " + matched);

        System.out.println(
            "Each activity receives at most one volunteer, "
            + "and each volunteer is assigned to at most one activity.");
    }
}
