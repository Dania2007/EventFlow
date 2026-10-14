package eventflow.algorithms;

import java.util.*;
import eventflow.model.Activity;

public class GreedyActivitySelection {
    public static List<Activity> select(List<Activity> activities) {
        List<Activity> sorted = new ArrayList<>(activities);
        sorted.sort(Comparator.comparingInt(Activity::getFinish));

        List<Activity> selected = new ArrayList<>();
        int lastFinish = -1;

        for (Activity activity : sorted) {
            if (activity.getStart() >= lastFinish) {
                selected.add(activity);
                lastFinish = activity.getFinish();
            }
        }
        return selected;
    }
}