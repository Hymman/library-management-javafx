package com.librarywithjavafx.ui;

import com.librarywithjavafx.DAO.BookDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DeleteBookForm {

    public void display() {
        Stage window = new Stage();
        window.setTitle("DELETE Book");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label idLabel = new Label("Delete- Book ID :");
        TextField idInput = new TextField();

        Button gobackButton = new Button("Go Back");
        gobackButton.setOnAction(e->{
            window.close();
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idInput.getText());

                BookDAO bookDAO = new BookDAO();
                bookDAO.deleteBook(id);

                System.out.println("The Book Deleted successfully: ID = " + id);

                window.close();
            } catch (Exception ex) {
                System.out.println("An Error occured during delete: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        grid.add(idLabel, 0, 0);
        grid.add(idInput, 1, 0);
        grid.add(deleteButton, 1, 1);
        grid.add(gobackButton, 1, 4);

        Scene scene = new Scene(grid, 300, 150);
        window.setScene(scene);
        window.show();
    }
}
