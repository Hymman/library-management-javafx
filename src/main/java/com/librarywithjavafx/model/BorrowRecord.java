package com.librarywithjavafx.model;

import java.sql.Timestamp;

public class BorrowRecord {
    private int id;
    private int bookId;
    private String borrowerName;
    private String borrowerIdNumber;
    private Timestamp borrowDate;
    private Timestamp returnDate;

    public BorrowRecord(int id, int bookId, String borrowerName, String borrowerIdNumber, Timestamp borrowDate, Timestamp returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.borrowerName = borrowerName;
        this.borrowerIdNumber = borrowerIdNumber;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBorrowerIdNumber() {
        return borrowerIdNumber;
    }

    public Timestamp getBorrowDate() {
        return borrowDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }
}
