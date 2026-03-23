import java.util.*;

class Room {
    int roomNumber;
    boolean isAvailable;

    Room(int roomNumber, boolean isAvailable) {
        this.roomNumber = roomNumber;
        this.isAvailable = isAvailable;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room(101, true));
        rooms.add(new Room(102, false));

        System.out.println("Available Rooms:");
        for (Room r : rooms) {
            if (r.isAvailable) {
                System.out.println("Room " + r.roomNumber + " is available");
            }
        }
    }
}