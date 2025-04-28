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

public class UpdateBookForm {

    public void display() {
        Stage window = new Stage();
        window.setTitle("Modify Books");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label idLabel = new Label("The Book ID :");
        TextField idInput = new TextField();

        Label titleLabel = new Label("New Book Name:");
        TextField titleInput = new TextField();

        Label authorLabel = new Label("New Author:");
        TextField authorInput = new TextField();

        Label pageCountLabel = new Label("New Page Count:");
        TextField pageCountInput = new TextField();

        Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(e->{
            window.close();
        });

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText());
                String title = titleInput.getText();
                String author = authorInput.getText();
                int pageCount = Integer.parseInt(pageCountInput.getText());

                Book theUpdateBook = new Book(id, title, author, pageCount, false);

                BookDAO bookDAO = new BookDAO();
                bookDAO.updateBook(theUpdateBook);

                System.out.println("The book has been updated successfully: " + title);

                window.close();
            } catch (Exception ex) {
                System.out.println("An error occured during update: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        grid.add(idLabel, 0, 0);
        grid.add(idInput, 1, 0);

        grid.add(titleLabel, 0, 1);
        grid.add(titleInput, 1, 1);

        grid.add(authorLabel, 0, 2);
        grid.add(authorInput, 1, 2);

        grid.add(pageCountLabel, 0, 3);
        grid.add(pageCountInput, 1, 3);

        grid.add(updateButton, 1, 4);
        grid.add(gobackButton, 1, 6);

        Scene scene = new Scene(grid, 350, 300);
        window.setScene(scene);
        window.show();
    }
}
