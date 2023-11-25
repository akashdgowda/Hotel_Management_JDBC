import java.sql.*;
import java.util.Scanner;

public class HotelManagement {
    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "MySQLPassword";
    public static void main(String[] args) throws ClassNotFoundException, SQLException,InterruptedException {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            while(true){
                Scanner in = new Scanner(System.in);
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room Number");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("Choose an option: ");
                int choice = in.nextInt();
                switch(choice){
                    case 1:
                        reserveRoom(connection, in, statement);
                        break;
                    case 2:
                        viewReservation(connection ,statement);
                        break;
                    case 3:
                        getRoomNumber(connection, in, statement);
                        break;
                    case 4:
                        updateReservation(connection, in, statement);
                        break;
                    case 5:
                        deleteReservation(connection, in, statement);
                        break;
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }

    }

    private static void deleteReservation(Connection connection, Scanner in, Statement statement) {
        try{
            System.out.print("Enter reservation ID: ");
            int reservationID = in.nextInt();
            if(!reservationExists(connection, reservationID ,statement)){
                System.out.println("Reservation not found for the given ID");
                return;
            }
            String query = "DELETE FROM reservations WHERE reservation_id = "+reservationID;
            int affectedRows = statement.executeUpdate(query);
            if(affectedRows > 0){
                System.out.println("Reservation Deleted Successfully");
            }else{
                System.out.println("Reservation deletion failed.");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }



    private static void updateReservation(Connection connection, Scanner in, Statement statement) {
        try{
            System.out.print("Enter reservation ID to update: ");
            int reservationID = in.nextInt();
            in.nextLine(); // Consume newLine character
            if(!reservationExists(connection, reservationID ,statement)){
                System.out.println("Reservation not found for the given ID");
                return;
            }
            System.out.println("Enter new guest name: ");
            String newGuestName = in.nextLine();
            System.out.println("Enter new room number: ");
            int newRoomNumber = in.nextInt();
            System.out.println("Enter new contact number: ");
            String newContactNumber = in.next();
            String query = "UPDATE reservations SET guest_name = '" + newGuestName + "' , " +
                    "room_number = " +newRoomNumber+
                    ", contact_number = '"+newContactNumber+"' " +
                    "WHERE reservation_id = "+ reservationID;

            int affectRows = statement.executeUpdate(query);
            if(affectRows > 0){
                System.out.println("Reservation Updated Successfully");
            }else{
                System.out.println("Reservation Update Failed");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void getRoomNumber(Connection connection, Scanner in, Statement statement) {
        try{
            System.out.println("Enter Reservation ID: ");
            int reservationID = in.nextInt();
            System.out.println("Enter Guest Name");
            String guestName = in.next();
            String query = "SELECT room_number FROM reservations WHERE reservation_id =" + reservationID +
                    "AND guest_name = '" + guestName +"'";
            try(ResultSet resultSet =statement.executeQuery(query)){
                if(resultSet.next()){
                    int roomNumber = resultSet.getInt("room_number");
                    System.out.println("Room Number for Reservation ID: " + reservationID + "and Name of guest: "+ guestName
                    + "is " + roomNumber);
                }else{
                    System.out.println("Reservation Not found for the given ID and guest Name.");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void viewReservation(Connection connection, Statement statement) {
        String query = "SELECT reservation_id, guest_name, room_number, contact_number, reservation_date FROM reservations";
        try(ResultSet resultSet = statement.executeQuery(query)){
            System.out.println("Current Reservations: ");
            System.out.println("+----------------+---------------------+--------------+-------------------+----------------------+");
            System.out.println("| Reservation ID |        Guest        | Room Number  | Contact Number    | Reservation Date     |");
            System.out.println("+----------------+---------------------+--------------+-------------------+----------------------+");
            while(resultSet.next()){
                int reservationID = resultSet.getInt("reservation_id");
                String guestName = resultSet.getString("guest_name");
                int roomNumber = resultSet.getInt("room_number");
                String contactNumber = resultSet.getString("contact_number");
                String reservationDate = resultSet.getTime("reservation_date").toString();

                System.out.printf("| %-14d | %-17s | -%12d | -%17s | %-20s |\n",
                        reservationID, guestName,roomNumber,contactNumber,reservationDate);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    private static void reserveRoom(Connection connection, Scanner in, Statement statement) {
        try{
            System.out.println("Enter guest Name");
            String guestName = in.next();
            System.out.println("Enter room number");
            int roomNumber = in.nextInt();
            System.out.println("Enter Contact number");
            String contactNumber = in.next();
            // Query
            String query = "INSERT INTO reservations(guest_name, room_number, contact_number)" +
                    "VALUES('"+ guestName +"', "+ roomNumber +", '"+ contactNumber +"')";
            //try(Statement statement = connection.createStatement()){
                int rowsAffected = statement.executeUpdate(query);
                if(rowsAffected > 0){
                    System.out.println("Reservation Successful");
                }else{
                    System.out.println("Reservation Failed");
                }
            //}

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
