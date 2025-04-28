package com.librarywithjavafx.model;

public class Book {

    private int id;
    private String title;
    private String author;
    private int pageCount;
    private boolean isBorrowed;



    public Book(int id, String title, String author, int pageCount, boolean isBorrowed){
        this.id = id;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.isBorrowed = isBorrowed;
    }
    //Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageCount() {
        return pageCount;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }



    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setIsBorrowed(boolean isBorrowed) {
        this.isBorrowed = isBorrowed;
    }
}
