# Hotel Management System

This is a simple Hotel Management System implemented in Java using JDBC to interact with a MySQL database.

## Prerequisites
- JDK installed
- MySQL server installed and running
- MySQL Connector/J library in the classpath

## Setup
1. Clone the repository: `git clone https://github.com/akashdgowda/Hotel_Management_JDBC.git`
2. Open the project in your preferred Java IDE.
3. Make sure to have the MySQL Connector/J library in your classpath.
4. Update the database connection details in `HotelManagement.java`:
   ```java
   private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
   private static final String username = "root";
   private static final String password = "MySQLPassword";

## Usage

1. Run the `HotelManagement` class.
2. The system will display a menu with the following options:

HOTEL MANAGEMENT SYSTEM
1. Reserve a room
2. View Reservation
3. Get Room Number
4. Update Reservation
5. Delete Reservation
0. Exit
Choose an option:


## Features

- **Reserve a room:** Add a new reservation with guest name, room number, and contact number.
- **View Reservation:** Display a list of all current reservations.
- **Get Room Number:** Retrieve the room number for a given reservation ID and guest name.
- **Update Reservation:** Modify the details of an existing reservation.
- **Delete Reservation:** Remove a reservation based on the reservation ID.

## Database Schema

The system assumes a MySQL database with the following table structure:

```sql
CREATE TABLE reservations (
 reservation_id INT PRIMARY KEY AUTO_INCREMENT,
 guest_name VARCHAR(255) NOT NULL,
 room_number INT NOT NULL,
 contact_number VARCHAR(15) NOT NULL,
 reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Feel free to adapt the database schema according to your requirements.

This project uses the MySQL database for data storage.
Special thanks to the [MySQL](https://www.mysql.com/) and [Java](https://www.oracle.com/java/) communities.
