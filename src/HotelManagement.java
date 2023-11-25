import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
                }
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
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
