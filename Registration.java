package eventflow.model;

public class Registration {
    private int eventId;
    private int participantId;
    private String bookingCode;

    public Registration(int eventId, int participantId,
                        String bookingCode) {
        this.eventId = eventId;
        this.participantId = participantId;
        this.bookingCode = bookingCode;
    }

    public int getEventId() { return eventId; }
    public int getParticipantId() { return participantId; }
    public String getBookingCode() { return bookingCode; }

    @Override
    public String toString() {
        return "Event ID: " + eventId
            + " | Participant ID: " + participantId
            + " | Booking code: " + bookingCode;
    }
}