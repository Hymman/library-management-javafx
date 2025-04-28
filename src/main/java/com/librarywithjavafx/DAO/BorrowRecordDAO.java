package com.librarywithjavafx.DAO;

import com.librarywithjavafx.model.BorrowRecord;
import com.librarywithjavafx.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowRecordDAO {

    public void addBorrowRecord(int bookId, String borrowerName,String borrowerIdNumber ){
        String sql = "INSERT INTO borrow_records(book_id, borrower_name, borrower_id_number) VALUES (?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,bookId);
            stmt.setString(2,borrowerName);
            stmt.setString(3,borrowerIdNumber);

            stmt.executeUpdate();
            System.out.println("Borrow record added succesfully");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateReturnDate(int bookId){
        String sql = "UPDATE borrow_records SET return_date = CURRENT_TIMESTAMP WHERE book_id = ? AND return_date IS NULL";


        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1,bookId);
            stmt.executeUpdate();

            System.out.println("Return date updated successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<BorrowRecord> getAllRecords() {
        List<BorrowRecord> borrowRecords = new ArrayList<>();
        String sql = "SELECT * FROM borrow_records";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BorrowRecord record = new BorrowRecord(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getString("borrower_name"),
                        rs.getString("borrower_id_number"),
                        rs.getTimestamp("borrow_date"),
                        rs.getTimestamp("return_date")
                );
                borrowRecords.add(record);
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while retrieving borrowers data");
            e.printStackTrace();
        }

        return borrowRecords;
    }

    public BorrowRecord findActiveBorrowRecordByBookId(int bookId) {
        String sql = "SELECT * FROM borrow_records WHERE book_id = ? AND return_date IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new BorrowRecord(
                        rs.getInt("id"),
                        rs.getInt("book_id"),
                        rs.getString("borrower_name"),
                        rs.getString("borrower_id_number"),
                        rs.getTimestamp("borrow_date"),
                        rs.getTimestamp("return_date")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }










}
