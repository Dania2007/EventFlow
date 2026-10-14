package eventflow.model;

public class Event {

    private int id;
    private String name;
    private String code;
    private String description;
    private int capacity;
    private int registered;
    private String category;
    private int startHour;
    private int finishHour;

    // Constructor used when an event is added manually.
    public Event(int id, String name, String code,
                 String description, int capacity) {
        this(id, name, code, description, capacity,
             0, "General", 9, 10);
    }

    // Constructor used when an event is loaded from the CSV.
    public Event(int id, String name, String code,
                 String description, int capacity,
                 int registered, String category,
                 int startHour, int finishHour) {

        this.id = id;
        this.name = name == null ? "" : name;
        this.code = code == null ? "" : code;
        this.description = description == null ? "" : description;
        this.capacity = Math.max(0, capacity);
        this.registered = Math.max(
            0, Math.min(registered, capacity)
        );
        this.category = category == null ? "General" : category;
        this.startHour = startHour;
        this.finishHour = finishHour;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getRegistered() {
        return registered;
    }

    public int getAvailableSeats() {
        return capacity - registered;
    }

    public String getCategory() {
        return category;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getFinishHour() {
        return finishHour;
    }

    public boolean register() {
        if (registered < capacity) {
            registered++;
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "\nEvent ID: " + id
            + "\nName: " + name
            + "\nCode: " + code
            + "\nDescription: " + description
            + "\nCategory: " + category
            + "\nCapacity: " + capacity
            + "\nRegistered: " + registered
            + "\nAvailable seats: " + getAvailableSeats()
            + "\nTime: " + startHour + ":00 - "
            + finishHour + ":00";
    }
}