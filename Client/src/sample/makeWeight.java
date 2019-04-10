package sample;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Makes Weight graph by closing previous windows as per requirement
 */
public class makeWeight {
    private Tokimon[] tokimontoParse;
    private final CategoryAxis xAxis = new CategoryAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private Stage viewAll = new Stage();
    private GridPane gridPane = new GridPane();

    private final BarChart<String, Number> bc =
            new BarChart<String, Number>(xAxis, yAxis);

    //Constructor
    makeWeight() throws FileNotFoundException {
        HandleUI();
        Gson gson = new Gson();
        //loads data by making requests
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
            }
            connection.disconnect();

        } catch (IOException e) {

        }

        //makes Graph

        for (int i = 0; i < tokimontoParse.length; i++) {
            XYChart.Series series = new XYChart.Series();
            series.setName(tokimontoParse[i].getName());
            series.getData().add(new XYChart.Data(tokimontoParse[i].getName(), tokimontoParse[i].getWeight()));
            bc.getData().addAll(series);
        }

        Label back = new Label("Back");
        back.setUnderline(true);
        back.setFont(Font.font("Cambria", 15));
        gridPane.add(back, 0, 2);
        GridPane.setMargin(back, new Insets(0, 40, 60, 230));
        GridPane.setMargin(bc, new Insets(0, 0, 70, 0));
        gridPane.setAlignment(Pos.CENTER_RIGHT);


        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                viewAll.close();
            }
        });

        //Buttons
        Button HEIGHT = new Button("View By Height");
        HEIGHT.setStyle("-fx-background-color: #e64d4d");
        HEIGHT.setMaxWidth(Double.MAX_VALUE);
        HEIGHT.setFont(Font.font("Cambria"));
        Button WEIGHT = new Button("View By Weight");
        WEIGHT.setStyle("-fx-background-color: #e64d4d");
        WEIGHT.setMaxWidth(Double.MAX_VALUE);
        WEIGHT.setFont(Font.font("Cambria"));
        Button STRENGTH = new Button("Weight By Strength");
        STRENGTH.setStyle("-fx-background-color: #e64d4d");
        STRENGTH.setFont(Font.font("Cambria"));
        STRENGTH.setMaxWidth(Double.MAX_VALUE);

        //Buttons on click handler

        HEIGHT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new makeHeightGraph();
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        WEIGHT.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new makeWeight();
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        STRENGTH.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    new ViewAllTokimons();
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(HEIGHT, WEIGHT, STRENGTH);

        hBox.setSpacing(10);

        gridPane.add(hBox, 0, 0);
        GridPane.setMargin(hBox, new Insets(0, 0, 0, 80));

        FileInputStream inputstream = new FileInputStream("pikachu.png");
        Image image = new Image(inputstream);
        gridPane.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        Scene scene = new Scene(gridPane, 900, 600);
        viewAll.setScene(scene);
        scene.setFill(Color.web("#0076a3"));
        viewAll.show();
    }

    //Handles UI
    private void HandleUI() {
        viewAll.setTitle("All Tokimons");

        bc.setTitle("Tokimon Summary");
        xAxis.setLabel("Name of Tokimons");
        yAxis.setLabel("Weight");
        gridPane.add(bc, 0, 1);

    }


}
