import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String category;
    private double price;
    private boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber=" + roomNumber +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

class Reservation {
    private static int idCounter = 1;
    private int reservationId;
    private String guestName;
    private Room room;
    private int nights;

    public Reservation(String guestName, Room room, int nights) {
        this.reservationId = idCounter++;
        this.guestName = guestName;
        this.room = room;
        this.nights = nights;
    }

    public int getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public Room getRoom() {
        return room;
    }

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", guestName='" + guestName + '\'' +
                ", room=" + room +
                ", nights=" + nights +
                '}';
    }
}

class PaymentProcessor {
    public static boolean processPayment(String guestName, double amount) {
        // Simulate payment processing
        System.out.println("Processing payment for " + guestName + " of amount $" + amount);
        return true; // Payment is always successful in this simulation
    }
}

class Hotel {
    private ArrayList<Room> rooms;
    private HashMap<Integer, Reservation> reservations;

    public Hotel() {
        rooms = new ArrayList<>();
        reservations = new HashMap<>();
        // Add some initial rooms
        rooms.add(new Room(101, "Single", 100.0));
        rooms.add(new Room(102, "Double", 150.0));
        rooms.add(new Room(103, "Suite", 250.0));
    }

    public void displayAvailableRooms() {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    public Room getAvailableRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                return room;
            }
        }
        return null;
    }

    public void makeReservation(String guestName, int roomNumber, int nights) {
        Room room = getAvailableRoom(roomNumber);
        if (room == null) {
            System.out.println("Room not available.");
            return;
        }

        double amount = room.getPrice() * nights;
        if (PaymentProcessor.processPayment(guestName, amount)) {
            room.setAvailable(false);
            Reservation reservation = new Reservation(guestName, room, nights);
            reservations.put(reservation.getReservationId(), reservation);
            System.out.println("Reservation successful: " + reservation);
        } else {
            System.out.println("Payment failed. Reservation not completed.");
        }
    }

    public void viewReservation(int reservationId) {
        Reservation reservation = reservations.get(reservationId);
        if (reservation != null) {
            System.out.println("Reservation Details:");
            System.out.println(reservation);
        } else {
            System.out.println("Reservation not found.");
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Search for Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hotel.displayAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter guest name: ");
                    String guestName = scanner.next();
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter number of nights: ");
                    int nights = scanner.nextInt();
                    hotel.makeReservation(guestName, roomNumber, nights);
                    break;
                case 3:
                    System.out.print("Enter reservation ID: ");
                    int reservationId = scanner.nextInt();
                    hotel.viewReservation(reservationId);
                    break;
                case 4:
                    scanner.close();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}