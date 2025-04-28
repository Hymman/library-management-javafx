# Library Management System (JavaFX + MySQL)

This is a simple Library Management System developed using **JavaFX** for the user interface and **MySQL** for the database backend.

## Features

-  Add new books to the library
-  Update existing book details
-  Delete books from the catalog
-  Borrow and return books
-  Search books dynamically by title or author
-  View borrowing history with borrower information
-  Dynamic UI updates with color indicators (Borrowed / Available)
-  Alert popups for error, success and warning messages

## Technologies Used

- Java 22
- JavaFX 20.0.2
- MySQL
- JDBC
- Maven

## Project Structure

src/ └── main/ └── java/ └── com.librarywithjavafx/ ├── DAO/ ├── model/ ├── ui/ ├── util/


## Database

The database is a simple MySQL schema containing two tables:

- **books**: Stores book details (`id`, `title`, `author`, `page_count`, `is_borrowed`)
- **borrow_records**: Stores borrow transaction details (`id`, `book_id`, `borrower_name`, `borrower_id_number`, `borrow_date`, `return_date`)

>  **Note**: The actual database connection credentials (`username`, `password`) are masked for security reasons.

## How to Run

- Ensure you have Java 22 and MySQL installed.
- Clone this repository.
- Set up your database with the provided table structure.
- Update your `DBConnection.java` file with your own MySQL credentials.
- Build and run the project via your IDE or Maven.


## License

This project is for educational purposes.

---


