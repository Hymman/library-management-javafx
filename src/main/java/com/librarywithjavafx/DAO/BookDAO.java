package com.librarywithjavafx.DAO;

import com.librarywithjavafx.model.Book;
import com.librarywithjavafx.util.DBConnection;
import com.librarywithjavafx.DAO.BookDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class BookDAO {

    public void addBook(Book book) {
        String sql = "INSERT INTO books (title, author, page_count, is_borrowed) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setInt(3, book.getPageCount());
                stmt.setBoolean(4, book.isBorrowed());

                stmt.executeUpdate();
                System.out.println("The Book has been added succesfully");

            }
        } catch (SQLException e) {
            System.out.println("An error has occured during addbook acitivity");
            e.printStackTrace();
        }


    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DBConnection.getConnection()) {
            assert conn != null;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getInt("page_count"),
                            rs.getBoolean("is_borrowed")
                    );
                    books.add(book);
                }

            }
        } catch (SQLException e) {
            System.out.println("An error occured while getting all books ");
        }
        return books;

    }


    public void deleteBook(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("The Book has been deleted successfully");
            } else {
                System.out.println("No id found such as " + id + " the book couldn't be deleted");
            }

        } catch (SQLException e) {
            System.out.println("an Error has occured during deleting from database");
            e.printStackTrace();
        }

    }

    public void updateBook(Book book) {
        String sql = "UPDATE books SET title = ?, author = ?, page_count = ?, is_borrowed = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getPageCount());
            stmt.setBoolean(4, book.isBorrowed());
            stmt.setInt(5, book.getId());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("The Book has been updated successfully");
            } else {
                System.out.println("No such and id found.The book did not updated");
            }

        } catch (SQLException e) {
            System.out.println("An error occured during update book");
            e.printStackTrace();
        }
    }

    // ui de borrow ve return için isborrowed statüyü değiştiren metod
    public void updateBorrowStatus(int id, boolean isBorrowed){
        String sql = "UPDATE books SET  is_borrowed = ? WHERE id = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setBoolean(1, isBorrowed);
            stmt.setInt(2,id);

            stmt.executeUpdate();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}