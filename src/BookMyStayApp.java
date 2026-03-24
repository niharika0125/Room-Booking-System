import java.util.LinkedList;
import java.util.Queue;

// Reservation class
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Reservation [Guest=" + guestName + ", RoomType=" + roomType + "]";
    }
}

// Booking Request Queue class
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added: " + reservation);
    }

    // View all requests
    public void viewRequests() {
        if (queue.isEmpty()) {
            System.out.println("No booking requests available.");
            return;
        }

        System.out.println("\nBooking Request Queue:");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }

    // Peek next request
    public Reservation peekNext() {
        return queue.peek();
    }

    // Process next request
    public Reservation processNext() {
        return queue.poll();
    }
}

// Main Application
public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // UC5: Simulating multiple booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Deluxe"));
        bookingQueue.addRequest(new Reservation("Bob", "Suite"));
        bookingQueue.addRequest(new Reservation("Charlie", "Standard"));

        // Display queue
        bookingQueue.viewRequests();

        // Show next request (FIFO)
        System.out.println("\nNext request to process: " + bookingQueue.peekNext());

        // Process requests one by one
        System.out.println("\nProcessing booking requests...");
        while (bookingQueue.peekNext() != null) {
            Reservation r = bookingQueue.processNext();
            System.out.println("Processing: " + r);
        }

        // Final check
        bookingQueue.viewRequests();
    }
}