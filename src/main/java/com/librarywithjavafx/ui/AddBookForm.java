package com.librarywithjavafx.ui;

import com.librarywithjavafx.DAO.BookDAO;
import com.librarywithjavafx.model.Book;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AddBookForm {

    public void display(){
        Stage window = new Stage();
        window.setTitle("Add Book");

        //Gripane - satır sütün
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        //labels ands texts
        Label titleLabel = new Label("Book Name: ");
        TextField titleInput = new TextField();

        Label authorLabel = new Label("Author");
        TextField authorInput = new TextField();

        Label pageCountLabel = new Label("Page Count:");
        TextField pageCountInput = new TextField();

        Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(e->{
            window.close();
        });


        Button saveButton = new Button("Save");
        saveButton.setOnAction(e-> {
            try {
                String title = titleInput.getText();
                String author = authorInput.getText();
                int pageCount = Integer.parseInt(pageCountInput.getText());

                // Yeni Book nesnesi oluşturma
                Book addNewBook = new Book(0, title, author, pageCount, false);

                // DAO kullanarak veritabanına kaydedilir
                BookDAO bookDAO = new BookDAO();
                bookDAO.addBook(addNewBook);

                System.out.println("Book has been added successfully: " + title);


                window.close(); // Başarıyla eklenirse pencereyi kapatır
            } catch (Exception ex) {
                System.out.println("An error has occured: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        // Elemanları Grid'e yerleştiriyoruz
        grid.add(titleLabel, 0, 0);
        grid.add(titleInput, 1, 0);

        grid.add(authorLabel, 0, 1);
        grid.add(authorInput, 1, 1);

        grid.add(pageCountLabel, 0, 2);
        grid.add(pageCountInput, 1, 2);

        grid.add(saveButton, 1, 3);

        grid.add(gobackButton, 1, 5);

        Scene scene = new Scene(grid, 300, 250);
        window.setScene(scene);
        window.show();






    }
}
