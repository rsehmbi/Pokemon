package sample;

import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Make a pie chart to individually represent the Tokimon
 * Contains legends and Labels to differentiate between Strength, Weight and Height
 */
public class ViewTokimon {
    private Tokimon[] tokimontoParse;
    private HashMap<String, Tokimon> HashMapTokimon = new HashMap<>();
    private Double Height = 0.0;
    private Double Weight = 0.0;
    private Double Strength = 0.0;
    private Arc pieSlice1 = new Arc();
    private Arc pieSlice2 = new Arc();
    private Arc pieSlice3 = new Arc();
    private Arc pieSlice4 = new Arc();
    private ListView<String> ListOfTokimons = new ListView<>();
    private VBox vBox = new VBox();
    private HBox hBox = new HBox();

    //Constructor

    ViewTokimon() throws FileNotFoundException {
        Stage viewAll = new Stage();
        viewAll.setTitle("All Tokimons");


        Label list_of_all_tokimons = new Label("List Of All Tokimons");
        list_of_all_tokimons.setFont(Font.font("Cambria", 36));
        list_of_all_tokimons.setTextFill(Color.web("#0076a3"));
        list_of_all_tokimons.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                list_of_all_tokimons.setScaleX(1.3);
                list_of_all_tokimons.setScaleY(1.3);
            }
        });

        list_of_all_tokimons.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                list_of_all_tokimons.setScaleX(1);
                list_of_all_tokimons.setScaleY(1);
            }
        });

        Label descriptionLabel = new Label("Description");
        descriptionLabel.setFont(Font.font("Cambria", 36));
        descriptionLabel.setTextFill(Color.web("#0076a3"));
        descriptionLabel.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                descriptionLabel.setScaleX(1.3);
                descriptionLabel.setScaleY(1.3);
            }
        });

        descriptionLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                descriptionLabel.setScaleX(1);
                descriptionLabel.setScaleY(1);
            }
        });


        //Loading Data
        Gson gson = new Gson();
        try {
            URL url = new URL("http://localhost:8080/api/tokimon/all");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connection.getResponseCode();
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                tokimontoParse = gson.fromJson(input, Tokimon[].class);
                for (int i = 0; i < tokimontoParse.length; i++) {
                    //System.out.println(tokimontoParse[i]);
                    ListOfTokimons.getItems().addAll(tokimontoParse[i].getName());
                    HashMapTokimon.put(tokimontoParse[i].getName(), tokimontoParse[i]);
                }
            }
            connection.disconnect();

        } catch (IOException e) {

        }

        makePieCharts();

        GridPane makeStagePane = new GridPane();
        makeStagePane.setAlignment(Pos.CENTER_LEFT);
        makeStagePane.add(list_of_all_tokimons, 0, 0);
        makeStagePane.add(descriptionLabel, 1, 0);
        makeStagePane.add(ListOfTokimons, 0, 1);
        makeStagePane.add(vBox, 1, 1);
        GridPane.setMargin(hBox, new Insets(0, 60, 0, 0));
        GridPane.setMargin(vBox, new Insets(0, 0, 40, 150));
        GridPane.setMargin(list_of_all_tokimons, new Insets(20, 0, 40, 60));
        GridPane.setMargin(descriptionLabel, new Insets(20, 0, 40, 160));
        GridPane.setMargin(ListOfTokimons, new Insets(0, 0, 40, 60));

        Label closelabel = new Label("Back");
        closelabel.setUnderline(true);
        closelabel.setFont(Font.font("Cambria", 15));
        makeStagePane.add(closelabel, 0, 2);
        GridPane.setMargin(closelabel, new Insets(20, 0, 40, 60));

        closelabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewAll.close();
            }
        });


        FileInputStream inputstream = new FileInputStream("pikachu.png");
        Image image = new Image(inputstream);
        makeStagePane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        Scene subScene = new Scene(makeStagePane, 900, 600);
        subScene.setFill(Color.web("#0076a3"));
        viewAll.setScene(subScene);
        viewAll.show();

    }

    //Make Pie Chart to show to User

    private void makePieCharts() {

        Label Name = new Label();
        Label ID = new Label();

        ListOfTokimons.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> ov,
                                        String old_val, String new_val) {
                        Name.setText("Name Of Tokimon :    " + new_val);
                        Name.setFont(Font.font("Cambria", 17));
                        Name.setTextFill(Paint.valueOf("#e64d4d"));

                        String id = String.valueOf(HashMapTokimon.get(new_val).getId());
                        ID.setText("ID of Tokimon :     " + id);
                        ID.setFont(Font.font("Cambria", 17));
                        ID.setTextFill(Paint.valueOf("#e64d4d"));

                        Height = (HashMapTokimon.get(new_val).getHeight() % 90);
                        Weight = (HashMapTokimon.get(new_val).getWeight() % 90);
                        Strength = Double.valueOf((HashMapTokimon.get(new_val).getStrength()) % 90);
                        //System.out.println(Height +" "+Weight+" "+Strength);

                        final double X = 100.0;
                        final double Y = 160.0;
                        final double X_RAD = 100.00;
                        final double Y_RAD = 100.0;

                        pieSlice1.setCenterX(X);
                        pieSlice1.setCenterY(Y);
                        pieSlice1.setRadiusX(X_RAD);
                        pieSlice1.setRadiusY(Y_RAD);
                        pieSlice1.setStartAngle(0);
                        pieSlice1.setLength(Height);
                        pieSlice1.setFill(Paint.valueOf("#cc9933"));
                        pieSlice1.setType(ArcType.ROUND);

                        pieSlice2.setCenterX(X);
                        pieSlice2.setCenterY(Y);
                        pieSlice2.setRadiusX(X_RAD);
                        pieSlice2.setRadiusY(Y_RAD);
                        pieSlice2.setStartAngle(Height);
                        pieSlice2.setLength(Height + Weight);
                        pieSlice2.setFill(Paint.valueOf("#669966"));
                        pieSlice2.setType(ArcType.ROUND);

                        pieSlice3.setCenterX(X);
                        pieSlice3.setCenterY(Y);
                        pieSlice3.setRadiusX(X_RAD);
                        pieSlice3.setRadiusY(Y_RAD);
                        pieSlice3.setStartAngle(Height + Weight);
                        pieSlice3.setLength(Height + Weight + Strength);
                        pieSlice3.setFill(Paint.valueOf("#8099ff"));
                        pieSlice3.setType(ArcType.ROUND);

                        pieSlice4.setCenterX(X);
                        pieSlice4.setCenterY(Y);
                        pieSlice4.setRadiusX(X_RAD);
                        pieSlice4.setRadiusY(Y_RAD);
                        pieSlice4.setStartAngle(Height + Weight + Strength);
                        pieSlice4.setLength(360 - (Height + Weight + Strength));
                        pieSlice4.setFill(Paint.valueOf(HashMapTokimon.get(new_val).getColor()));
                        pieSlice4.setType(ArcType.ROUND);
                    }
                });

        //Handles Pane

        Pane pane = new Pane(pieSlice1, pieSlice2, pieSlice3, pieSlice4);
        Button brown = new Button();
        brown.setStyle("-fx-background-color: #cc9933");
        Button green = new Button();
        green.setStyle("-fx-background-color: #669966");
        Button blue = new Button();
        blue.setStyle("-fx-background-color: #8099ff");

        Label brownl = new Label("Height");
        Label greenl = new Label("Weight");
        Label bluel = new Label("Strength");


        hBox.getChildren().addAll(brown, brownl, green, greenl, blue, bluel);
        hBox.setSpacing(20);


        vBox.getChildren().addAll(Name, ID, hBox, pane);
        vBox.setSpacing(20);

    }

}
