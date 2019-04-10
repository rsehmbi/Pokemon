package sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static javafx.geometry.Pos.CENTER;

/**
 * Deletes a tokimon based on the ID
 * User has to input Id in the text field in order to delete the Tokimon
 */
public class DeleteTokimonPage {

    DeleteTokimonPage() throws FileNotFoundException {
        Stage viewAll = new Stage();
        viewAll.setTitle("Delete Tokimon");
        ListView<String> ListOfTokimonss = new ListView<>();


        Label list_of_alltokimons = new Label("Please Enter ID of Tokimon You want to Delete !");
        Label ID = new Label("ID: ");
        ID.setFont(Font.font("Cambria", 20));
        ID.setTextFill(Color.web("#0076a3"));


        TextField ID_FIELD = new TextField();

        list_of_alltokimons.setFont(Font.font("Cambria", 25));
        list_of_alltokimons.setTextFill(Color.web("#0076a3"));
        list_of_alltokimons.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                list_of_alltokimons.setScaleX(1.5);
                list_of_alltokimons.setScaleY(1.5);
            }
        });

        list_of_alltokimons.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                list_of_alltokimons.setScaleX(1);
                list_of_alltokimons.setScaleY(1);
            }
        });


        //Loads Data
        Gson gson = new Gson();
        try {
            URL url = new URL("http://localhost:8080/api/tokimon/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            //  System.out.println(connection.getResponseCode());
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                Tokimon[] tokimontoParse = gson.fromJson(input, Tokimon[].class);
                for (int i = 0; i < tokimontoParse.length; i++) {
                    String id = String.valueOf(tokimontoParse[i].getId());
                    ListOfTokimonss.getItems().addAll("Name: " + tokimontoParse[i].getName() + "               ID: " + id);

                }
            }
            connection.disconnect();

        } catch (IOException e) {

        }
        //Label to ask user to enter data

        Label EnterId = new Label("");
        EnterId.setFont(Font.font("Cambria", 20));
        EnterId.setTextFill(Color.web("#0076a3"));

        // Button On click handler

        Button button = new Button("Delete");
        button.setStyle("-fx-background-color: #e64d4d");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //System.out.println(ID_FIELD.getText().toString());
                if (!ID_FIELD.getText().isEmpty()) {
                    try {
                        int idtoDelete = Integer.parseInt(ID_FIELD.getText().toString());
                        //System.out.println(idtoDelete);
                        URL url = new URL("http://localhost:8080/api/tokimon/" + idtoDelete);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("DELETE");
                        int responseCode = connection.getResponseCode();
                        //System.out.println(responseCode);
                        new DeleteTokimonPage();
                        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                    } catch (IOException e) {

                    }

                } else {
                    EnterId.setText("Please Enter ID !!");
                }
            }

        });

        //hbox to make Ui User-Friendly
        HBox hBox = new HBox();
        hBox.getChildren().addAll(ID, ID_FIELD, EnterId);
        hBox.setSpacing(30);

        Label BackLabel = new Label("Back");
        BackLabel.setUnderline(true);
        BackLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewAll.close();
            }
        });


        GridPane makeStagePane = new GridPane();
        makeStagePane.setAlignment(CENTER);
        makeStagePane.add(list_of_alltokimons, 0, 0);

        makeStagePane.add(hBox, 0, 1);
        makeStagePane.add(button, 0, 3);
        makeStagePane.add(BackLabel, 0, 4);

        makeStagePane.add(ListOfTokimonss, 0, 2);
        GridPane.setMargin(list_of_alltokimons, new Insets(0, 0, 40, 0));
        GridPane.setMargin(hBox, new Insets(0, 0, 20, 0));
        GridPane.setMargin(button, new Insets(10, 50, 0, 50));
        GridPane.setMargin(BackLabel, new Insets(5, 0, 0, 260));
        button.setMaxWidth(Double.MAX_VALUE);


        FileInputStream inputstream = new FileInputStream("pikachu.png");
        Image image = new Image(inputstream);
        makeStagePane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        Scene subScene = new Scene(makeStagePane, 900, 600);
        subScene.setFill(Color.web("#0076a3"));
        viewAll.setScene(subScene);
        viewAll.show();

    }
}

