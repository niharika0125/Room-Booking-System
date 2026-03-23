import java.util.*;

class Room {
    int roomNumber;
    boolean isAvailable;

    Room(int roomNumber) {
        this.roomNumber = roomNumber;
        this.isAvailable = true;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room(101));
        rooms.add(new Room(102));

        int requestedRoom = 101;

        for (Room r : rooms) {
            if (r.roomNumber == requestedRoom && r.isAvailable) {
                r.isAvailable = false;
                System.out.println("Room " + r.roomNumber + " booked successfully!");
                return;
            }
        }

        System.out.println("Room not available");
    }
}