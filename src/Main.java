class Room {
    int roomNumber;
    String type;

    Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
    }
}

public class Main {
    public static void main(String[] args) {
        Room r1 = new Room(101, "Single");
        Room r2 = new Room(102, "Double");

        System.out.println("Room " + r1.roomNumber + " Type: " + r1.type);
        System.out.println("Room " + r2.roomNumber + " Type: " + r2.type);
    }
}