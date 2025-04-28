package com.librarywithjavafx;

import com.librarywithjavafx.DAO.BookDAO;
import com.librarywithjavafx.model.Book;

import java.util.List;

public class LibraryTest {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();

        // 1. Yeni kitap ekleyelim
        //Book yeniKitap = new Book(0, "Kemirgen", "Sincap sincapgil", 190, false);
        //bookDAO.addBook(yeniKitap);

        bookDAO.deleteBook(2);



        // 2. Tüm kitapları listeleyelim
        List<Book> kitaplar = bookDAO.getAllBooks();
        for (Book book : kitaplar) {
            System.out.println(book.getId() + " - " + book.getTitle() + " - " + book.getAuthor());
        }


    }
}
