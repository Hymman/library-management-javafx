package com.librarywithjavafx.ui;

import com.librarywithjavafx.DAO.BookDAO;
import com.librarywithjavafx.DAO.BorrowRecordDAO;
import com.librarywithjavafx.model.Book;
import com.librarywithjavafx.model.BorrowRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ListBooksForm {

    private TableView<Book> table;
    private Stage window; // the window is field now

    public void loadData() {
        BookDAO bookDAO = new BookDAO();
        List<Book> books = bookDAO.getAllBooks();
        ObservableList<Book> data = FXCollections.observableArrayList(books);
        if (table != null) {
            table.setItems(data);
        }
    }
    // dynamic search for both author and title
    private void filterBooks(String keyword){
        BookDAO bookDAO = new BookDAO();
        List<Book> allBooks = bookDAO.getAllBooks();
        if(keyword == null || keyword.isEmpty()){
            ObservableList<Book> data = FXCollections.observableArrayList(allBooks);
            table.setItems(data);
            return;
        }
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        keyword = keyword.toLowerCase();

        for (Book book :allBooks){
            if(book.getTitle().toLowerCase().contains(keyword) ||
            book.getAuthor().toLowerCase().contains(keyword)){
                filteredBooks.add(book);
            }
        }
        table.setItems(filteredBooks);

    }

    public void display() {
        if (window == null) { // if the window has not been opened before
            window = new Stage();
            window.setTitle("List All Books");

            table = new TableView<>();

            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    System.out.println("Chosen book :" + newSelection.getTitle() + " (" + newSelection.getId() + ")");
                }
            });

            TableColumn<Book, Integer> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

            TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

            TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

            TableColumn<Book, Integer> pageCountColumn = new TableColumn<>("Page Count");
            pageCountColumn.setCellValueFactory(new PropertyValueFactory<>("pageCount"));

            TableColumn<Book, Boolean> isBorrowedColumn = new TableColumn<>("Borrow Status");
            //isBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
            isBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("borrowed"));

            isBorrowedColumn.setCellFactory(column -> new TableCell<Book, Boolean>(){
                @Override
                protected void updateItem(Boolean item, boolean empty){
                    super.updateItem(item,empty);
                    if(empty|| item == null){
                        setText(null);
                        setStyle("");
                    }else{
                        //setText(item ?"Borrowed" : "Available");
                        if(item){ // borrowed = true
                            setText("Borrowed");
                            setStyle("-fx-text-fill: red; -fx-font-weight: bold;");}
                        else{
                            setText("Available");
                            setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

                        }
                    }
                }
            });

            table.getColumns().addAll(idColumn, titleColumn, authorColumn, pageCountColumn, isBorrowedColumn);

            TextField searchField = new TextField();
            searchField.setPromptText("Search by Title or Author");
            searchField.setMinWidth(200);
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filterBooks(newValue);
            });




            // use it set on action style:
            // searchField.setOnAction(e -> {
            //     filterBooks(searchField.getText());
            // });


            // borrower info show
            Button borrowerInfoButton = new Button("Show Borrower Info");
            borrowerInfoButton.setDisable(true); // Başta kapalı

            //define borrow and return
            Button borrowbtn = new Button("Borrow Book");

            Button returnBtn = new Button("Return Book");

            //default disable :
            borrowbtn.setDisable(true);
            returnBtn.setDisable(true);

            //enables when selected
            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)->
            {boolean disableButtons = (newSelection==null);
            borrowbtn.setDisable(disableButtons);
            returnBtn.setDisable(disableButtons);

            if(newSelection != null && newSelection.isBorrowed()){
                borrowerInfoButton.setDisable(false);
            }else{
                borrowerInfoButton.setDisable(true);
            }



            });




            table.setStyle(
                    " -fx-selection-bar: #a5d6a7; " +    // ligth green selectet row color
                            " -fx-selection-bar-text: black;"    // dark text
            );

            //borrow and return setonactions

            borrowbtn.setOnAction(e->{
                Book selectedBook = table.getSelectionModel().getSelectedItem();
                if(selectedBook != null){
                    int seletedId = selectedBook.getId();
                    boolean borrowStatus = selectedBook.isBorrowed();
                    if(!borrowStatus){
                        BookDAO bookDAO = new BookDAO();
                        bookDAO.updateBorrowStatus(seletedId,true);
                        BorrowForm borrowForm = new BorrowForm();
                        borrowForm.display(seletedId,this::loadData );


                    }else {
                        showAlert(Alert.AlertType.WARNING, "Borrow Failed", "This book is already borrowed!");

                    }

                }else {
                    showAlert(Alert.AlertType.WARNING,"No Book Selected", "Please select a book to borrow.");};
            });

            returnBtn.setOnAction(e->{
                Book selectedBook = table.getSelectionModel().getSelectedItem();
                if(selectedBook != null) {
                    int selectedId = selectedBook.getId();
                    boolean borrowStatus = selectedBook.isBorrowed();
                    if(borrowStatus){
                        BookDAO bookDAO = new BookDAO();
                        bookDAO.updateBorrowStatus(selectedId,false);

                        BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
                        borrowRecordDAO.updateReturnDate(selectedId);
                        loadData();
                        showAlert(Alert.AlertType.INFORMATION,"Returned","Book has been returned Successfully");
                    }else{
                        showAlert(Alert.AlertType.WARNING,"Return Failed", "This book is already available in the library!");
                    }

                }else{
                    showAlert(Alert.AlertType.WARNING,"No Book Selected", "Please select a book to return.");
                }
            });

            //borrower info button set on action
            borrowerInfoButton.setOnAction(e -> {
                Book selectedBook = table.getSelectionModel().getSelectedItem();
                if (selectedBook != null && selectedBook.isBorrowed()) {
                    int selectedBookId = selectedBook.getId();

                    BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();
                    BorrowRecord activeBorrow = borrowRecordDAO.findActiveBorrowRecordByBookId(selectedBookId);

                    if (activeBorrow != null) {
                        String message = "Borrower Name: " + activeBorrow.getBorrowerName() +
                                "ID Number: " + activeBorrow.getBorrowerIdNumber() +
                                "Borrow Date: " + activeBorrow.getBorrowDate();
                        showAlert(Alert.AlertType.INFORMATION,"Borrower Info", message);
                    } else {
                        showAlert(Alert.AlertType.INFORMATION,"Info", "No active borrow record found.");
                    }
                }
            });



            Button refreshButton = new Button("Refresh");
            refreshButton.setOnAction(e -> loadData());





            HBox topBar = new HBox(10); // 10 piksel boşluk
            topBar.getChildren().addAll(searchField, refreshButton);
            topBar.getChildren().addAll(borrowbtn,returnBtn,borrowerInfoButton);


            VBox vbox = new VBox(10);
            vbox.setPadding(new Insets(20));
            vbox.getChildren().addAll(topBar, table);




            Scene scene = new Scene(vbox, 600, 400);
            window.setScene(scene);
        }

        loadData(); // reload everytime
        window.show(); //
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
