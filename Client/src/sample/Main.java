package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static javafx.geometry.Pos.CENTER_RIGHT;

/**
 * Home Controller that have all the buttons to navigate
 * Contains only buttons no requests
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Label label = new Label("Welcome to Tokimon Finder!");
        label.setFont(Font.font("Cambria", 30));
        label.setTextFill(Color.web("#0076a3"));
        label.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setScaleX(1.2);
                label.setScaleY(1.2);
            }
        });

        label.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                label.setScaleX(1);
                label.setScaleY(1);
            }
        });

        //Buttons
        Button Add = new Button("Add New Tokimon");
        Add.setStyle("-fx-background-color: #e64d4d");
        Add.setMaxWidth(Double.MAX_VALUE);
        Add.setFont(Font.font("Cambria"));

        //Buttons
        Button Delete = new Button("Delete a Tokimon");
        Delete.setStyle("-fx-background-color: #e64d4d");
        Delete.setMaxWidth(Double.MAX_VALUE);
        Delete.setFont(Font.font("Cambria"));

        //Buttons
        Button displayTokimon = new Button("Display Tokimon");
        displayTokimon.setStyle("-fx-background-color: #e64d4d");
        displayTokimon.setFont(Font.font("Cambria"));
        displayTokimon.setMaxWidth(Double.MAX_VALUE);

        //Buttons
        Button displayAllTokimon = new Button("Display All Tokimons");
        displayAllTokimon.setStyle("-fx-background-color: #e64d4d");
        displayAllTokimon.setFont(Font.font("Cambria"));
        displayAllTokimon.setMaxWidth(Double.MAX_VALUE);


        GridPane gridpane = new GridPane();
        gridpane.add(label, 0, 0);
        gridpane.add(Add, 0, 1);
        gridpane.add(Delete, 0, 2);
        gridpane.add(displayTokimon, 0, 3);
        gridpane.add(displayAllTokimon, 0, 4);


        gridpane.setAlignment(CENTER_RIGHT);
        GridPane.setMargin(Add, new Insets(0, 20, 0, 0));
        GridPane.setMargin(Delete, new Insets(0, 20, 0, 0));
        GridPane.setMargin(displayTokimon, new Insets(0, 20, 0, 0));
        GridPane.setMargin(displayAllTokimon, new Insets(0, 20, 0, 0));
        GridPane.setMargin(label, new Insets(0, 20, 0, 0));
        gridpane.setVgap(30);

        FileInputStream inputstream = new FileInputStream("pikachu.png");
        Image image = new Image(inputstream);
        gridpane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        Scene scene = new Scene(gridpane, 900, 600); // (parent, hor, vert)
        scene.setFill(Color.web("#0076a3"));


        primaryStage.setScene(scene);
        primaryStage.setTitle("Tokimon Finder");
        primaryStage.show();


        //Button On click Handlers
        Add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new AddNewTokimonPage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        displayTokimon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new ViewTokimon();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        Delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new DeleteTokimonPage();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        displayAllTokimon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new ViewAllTokimons();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
