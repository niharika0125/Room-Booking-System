import java.util.*;

// Reservation class (same as UC5)
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

// Booking Request Queue (FIFO)
class BookingRequestQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// Inventory Service
class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        // Initial room availability
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
        inventory.put("Standard", 2);
    }

    public boolean isAvailable(String roomType) {
        return inventory.getOrDefault(roomType, 0) > 0;
    }

    public void reduceInventory(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " -> " + inventory.get(type));
        }
    }
}

// Booking Service (Core of UC6)
class BookingService {

    private InventoryService inventoryService;

    // Tracks all allocated room IDs (global uniqueness)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Maps room type to allocated room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    public BookingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Generate unique room ID
    private String generateRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 2).toUpperCase() + new Random().nextInt(1000);
        } while (allocatedRoomIds.contains(roomId)); // ensure uniqueness

        return roomId;
    }

    // Process booking request
    public void processReservation(Reservation reservation) {

        String roomType = reservation.getRoomType();

        System.out.println("\nProcessing: " + reservation);

        // Step 1: Check availability
        if (!inventoryService.isAvailable(roomType)) {
            System.out.println("❌ No rooms available for type: " + roomType);
            return;
        }

        // Step 2: Generate unique room ID
        String roomId = generateRoomId(roomType);

        // Step 3: Store in Set (prevent duplicates)
        allocatedRoomIds.add(roomId);

        // Step 4: Map room type → room IDs
        roomAllocations.putIfAbsent(roomType, new HashSet<>());
        roomAllocations.get(roomType).add(roomId);

        // Step 5: Reduce inventory (atomic step)
        inventoryService.reduceInventory(roomType);

        // Step 6: Confirm booking
        System.out.println("✅ Booking Confirmed!");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + roomType);
        System.out.println("Allocated Room ID: " + roomId);
    }

    public void showAllocations() {
        System.out.println("\nRoom Allocations:");
        for (String type : roomAllocations.keySet()) {
            System.out.println(type + " -> " + roomAllocations.get(type));
        }
    }
}

// Main Class
public class BookMyStayApp  {

    public static void main(String[] args) {

        // Step 1: Create queue (from UC5)
        BookingRequestQueue queue = new BookingRequestQueue();

        // Add requests
        queue.addRequest(new Reservation("Alice", "Deluxe"));
        queue.addRequest(new Reservation("Bob", "Suite"));
        queue.addRequest(new Reservation("Charlie", "Deluxe"));
        queue.addRequest(new Reservation("David", "Suite")); // should fail

        // Step 2: Services
        InventoryService inventory = new InventoryService();
        BookingService bookingService = new BookingService(inventory);

        // Step 3: Process all requests FIFO
        while (!queue.isEmpty()) {
            Reservation r = queue.getNextRequest();
            bookingService.processReservation(r);
        }

        // Step 4: Show final state
        inventory.showInventory();
        bookingService.showAllocations();
    }
}