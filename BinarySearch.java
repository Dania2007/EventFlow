package eventflow.algorithms;

import java.util.List;
import eventflow.model.Event;

public class BinarySearch {
    public static Event search(List<Event> events, int id) {
        int low = 0;
        int high = events.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int currentId = events.get(mid).getId();

            if (currentId == id) {
                return events.get(mid);
            } else if (currentId < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }
}