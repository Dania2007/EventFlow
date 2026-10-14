package eventflow.algorithms;

import java.util.*;
import eventflow.model.Activity;

public class BitmaskDP {
    public static List<Activity> select(List<Activity> activities,
                                         int timeBudget) {
        int n = activities.size();
        if (n > 20) {
            throw new IllegalArgumentException(
                "Bitmask DP supports at most 20 activities."
            );
        }

        int states = 1 << n;
        int[] duration = new int[n];
        int[] total = new int[states];
        boolean[] valid = new boolean[states];
        valid[0] = true;

        for (int i = 0; i < n; i++) {
            duration[i] = activities.get(i).getFinish()
                        - activities.get(i).getStart();
        }

        int bestMask = 0;
        for (int mask = 1; mask < states; mask++) {
            int bit = Integer.numberOfTrailingZeros(mask);
            int previous = mask & (mask - 1);

            if (valid[previous]) {
                total[mask] = total[previous] + duration[bit];
                valid[mask] = total[mask] <= timeBudget;

                if (valid[mask] && total[mask] > total[bestMask]) {
                    bestMask = mask;
                }
            }
        }

        List<Activity> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                result.add(activities.get(i));
            }
        }
        return result;
    }
}