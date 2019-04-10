package sample;

import com.google.gson.Gson;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import static javafx.geometry.Pos.CENTER_RIGHT;

/**
 * Adds a new Tokimon by making client requests
 * Handle errors if no name is added
 * UI functionality contained in this file
 */
public class AddNewTokimonPage {
    AddNewTokimonPage() throws FileNotFoundException {
        Stage subStage = new Stage();
        subStage.setTitle("Add New Tokiman Here");

        Label Topic = new Label("Enter New Tokimon Here !!");
        Topic.setFont(Font.font("Cambria", 20));
        Topic.setTextFill(Color.web("#0076a3"));
        Topic.setPadding(new Insets(0, 0, 20, 30));

        Label nameLabel = new Label("Name: ");
        nameLabel.setFont(Font.font("Cambria", 20));
        nameLabel.setPrefWidth(100);
        nameLabel.setPadding(new Insets(0, 0, 0, 30));

        Label WeightLabel = new Label("Weight(lb): ");
        WeightLabel.setFont(Font.font("Cambria", 20));
        WeightLabel.setPadding(new Insets(0, 0, 0, 30));

        Label HeightLabel = new Label(" Height(cm): ");
        HeightLabel.setFont(Font.font("Cambria", 20));
        HeightLabel.setPadding(new Insets(0, 0, 0, 30));

        Label AbilityLabel = new Label("Ability: ");
        AbilityLabel.setFont(Font.font("Cambria", 20));
        AbilityLabel.setPadding(new Insets(0, 0, 0, 30));

        Label StrengthLabel = new Label("Strength: ");
        StrengthLabel.setFont(Font.font("Cambria", 20));
        StrengthLabel.setPadding(new Insets(0, 0, 0, 30));

        Label ColorLabel = new Label("Color: ");
        ColorLabel.setFont(Font.font("Cambria", 20));
        ColorLabel.setPadding(new Insets(0, 0, 0, 30));

        Label closelabel = new Label("Back");
        closelabel.setUnderline(true);


        TextField nameField = new TextField();
        TextField WeightField = new TextField();
        TextField HeightField = new TextField();
        TextField AbilityField = new TextField();
        TextField StrengthField = new TextField();
        TextField ColorField = new TextField();

        Button submit = new Button("Submit");
        submit.setStyle("-fx-background-color: #e64d4d");


        GridPane subGrid = new GridPane();
        subGrid.add(Topic, 0, 0);
        subGrid.add(nameLabel, 0, 1);
        subGrid.add(WeightLabel, 0, 2);
        subGrid.add(HeightLabel, 0, 3);
        subGrid.add(AbilityLabel, 0, 4);
        subGrid.add(StrengthLabel, 0, 9);
        subGrid.add(ColorLabel, 0, 10);
        subGrid.add(closelabel, 1, 13);


        subGrid.add(nameField, 1, 1);
        final Double[] Weight = {0.0};
        Slider WeightSlider = new Slider();
        WeightSlider.setMin(0);
        WeightSlider.setMax(500);
        WeightSlider.setShowTickLabels(true);
        WeightSlider.setShowTickMarks(true);

        WeightSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Weight[0] = t1.doubleValue();
            }
        });
        subGrid.add(WeightSlider, 1, 2);

        final Double[] Height = {0.0};
        Slider HeightSlider = new Slider();
        HeightSlider.setMin(0);
        HeightSlider.setMax(1000);
        HeightSlider.setShowTickLabels(true);
        HeightSlider.setShowTickMarks(true);

        HeightSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Height[0] = t1.doubleValue();
            }
        });
        subGrid.add(HeightSlider, 1, 3);

        final ToggleGroup group = new ToggleGroup();

        RadioButton fire = new RadioButton("Fire");
        fire.setToggleGroup(group);
        fire.setUserData("Fire");
        fire.setSelected(true);

        RadioButton fly = new RadioButton("Fly");
        fly.setToggleGroup(group);
        fly.setUserData("Fly");


        RadioButton water = new RadioButton("Water");
        water.setToggleGroup(group);
        water.setUserData("Water");

        RadioButton electric = new RadioButton("Electric");
        electric.setToggleGroup(group);
        electric.setUserData("Electric");

        RadioButton freeze = new RadioButton("Freeze");
        freeze.setToggleGroup(group);
        freeze.setUserData("Freeze");

        final String[] Ability = {""};
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    group.getSelectedToggle().getUserData().toString();
                    Ability[0] = new_toggle.getUserData().toString();
                }
            }
        });


        subGrid.add(fire, 1, 4);
        subGrid.add(fly, 1, 5);
        subGrid.add(water, 1, 6);
        subGrid.add(electric, 1, 7);
        subGrid.add(freeze, 1, 8);


        final int[] Strength = {0};
        Slider strengthSlider = new Slider();
        strengthSlider.setMin(0);
        strengthSlider.setMax(100);
        strengthSlider.setShowTickLabels(true);
        strengthSlider.setShowTickMarks(true);

        strengthSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Strength[0] = t1.intValue();
            }
        });

        subGrid.add(strengthSlider, 1, 9);

        final String[] Color = {""};
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(new EventHandler() {
            public void handle(Event t) {
                Color[0] = colorPicker.getValue().toString();
            }
        });

        subGrid.add(colorPicker, 1, 10);
        subGrid.setAlignment(CENTER_RIGHT);
        subGrid.add(submit, 1, 11);

        Label labeliferror = new Label();
        labeliferror.setFont(Font.font("Cambria", 15));
        labeliferror.setTextFill(Paint.valueOf("#e64d4d"));
        subGrid.add(labeliferror, 1, 12);

        closelabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                subStage.close();
            }
        });


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (!nameField.getText().isEmpty()) {
                    try {
                        labeliferror.setText("Added !");
                        URL url = new URL("http://localhost:8080/api/tokimon/add");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setDoOutput(true);
                        connection.setRequestProperty("Content-Type", "application/json");


                        Tokimon tokimon = new Tokimon();
                        if (nameField.getText().isEmpty()) {
                            nameField.setText("No Name yet!!");
                        }
                        tokimon.setName(nameField.getText().toString());
                        tokimon.setStrength(Strength[0]);
                        tokimon.setAbility(Ability[0]);
                        tokimon.setWeight(Weight[0]);
                        tokimon.setHeight(Height[0]);
                        tokimon.setColor(Color[0]);

                        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());

                        Gson gson = new Gson();
                        String str = gson.toJson(tokimon);

                        wr.write(str);
                        wr.flush();
                        wr.close();
                        connection.connect();
                        connection.getResponseCode();
                        connection.disconnect();
                    } catch (IOException e) {
                        e.getMessage();
                    }
                } else {
                    labeliferror.setText("Please Enter the Name!!");
                }
            }
        });

        FileInputStream inputstream = new FileInputStream("pikachu.png");
        Image image = new Image(inputstream);
        subGrid.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        GridPane.setMargin(nameLabel, new Insets(0, 30, 20, 0));
        GridPane.setMargin(nameField, new Insets(0, 30, 20, 0));

        GridPane.setMargin(WeightLabel, new Insets(0, 30, 20, 0));
        GridPane.setMargin(WeightSlider, new Insets(0, 30, 20, 0));

        GridPane.setMargin(HeightLabel, new Insets(0, 30, 20, 0));
        GridPane.setMargin(HeightSlider, new Insets(0, 30, 20, 0));

        GridPane.setMargin(freeze, new Insets(3, 0, 0, 0));
        GridPane.setMargin(fly, new Insets(3, 0, 0, 0));
        GridPane.setMargin(electric, new Insets(3, 0, 0, 0));
        GridPane.setMargin(water, new Insets(3, 0, 0, 0));

        GridPane.setMargin(StrengthLabel, new Insets(20, 30, 20, 0));
        GridPane.setMargin(strengthSlider, new Insets(20, 30, 20, 0));

        GridPane.setMargin(ColorLabel, new Insets(0, 30, 20, 0));
        GridPane.setMargin(colorPicker, new Insets(0, 30, 20, 0));

        GridPane.setMargin(submit, new Insets(0, 30, 20, 0));
        submit.setMaxWidth(Double.MAX_VALUE);

        Scene subScene = new Scene(subGrid, 900, 600);
        subScene.setFill(Paint.valueOf("#0076a3"));
        subStage.setScene(subScene);
        subStage.show();

    }

}
