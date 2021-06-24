package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Palette;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

public class Controller {
    public Canvas canvas;
    public LinkedList<WritableImage> images = new LinkedList<>();
    public MenuButton palette;
    public FlowPane coloursFlowPane;
    public Color activeColour;
    public Button activeButton;
    public String activePalette;
    public Spinner<Integer> spinner;
    public int cellWidth = 64;
    public ColorPicker colourPicker;
    public Slider sliderSaturation;
    public Label saturationLabel;
    public Slider sliderRinB;
    public Label RinBlabel;
    public Slider sliderBrightness;
    public Label labelBrightness;
    public int undoIndex = 1;
    public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public Pane pane;
    public Pane canvasPane;
    public Button plusButton;
    public Button savePaletteButton;

    public void initialize() {
        Font font = Font.loadFont(getClass().getResourceAsStream("resources/rainyhearts.ttf"),18);
        savePaletteButton.setFont(font);
        canvasPane.setPrefSize(width / 2,width / 2);
        coloursFlowPane.setPrefSize(width / 16,width / 16);
        canvas.setWidth(width / 2);
        canvas.setHeight(width / 2);
        spinner.setPrefWidth(width / 16);
        palette.setPrefWidth(width / 16);
        colourPicker.setPrefWidth(width / 16);
        sliderBrightness.setPrefWidth(width / 16);
        sliderSaturation.setPrefWidth(width / 16);
        sliderRinB.setPrefWidth(width / 16);
        savePaletteButton.setMaxWidth(width / 16);
        canvas.getGraphicsContext2D().setFill(Color.TRANSPARENT);
        plusButton.setLayoutX(coloursFlowPane.getLayoutX());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        images.addLast(canvas.snapshot(params, null));
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 256, 16) {
            @Override
            public void decrement(int i) {
                setValue(getValue() / 2);
            }

            @Override
            public void increment(int i) {
                setValue(getValue() * 2);
            }
        };
        spinner.setValueFactory(valueFactory);
        spinner.valueProperty().addListener((obs, oldValue, newValue) ->
                cellWidth = (int) (canvas.getWidth() / spinner.getValue()));
        sliderSaturation.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (activePalette) {
                        case "mono" -> mono(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "analog" -> analog(colourPicker.getValue().getHue(), newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "compl" -> compl(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "square" -> square(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "rect" -> rectangle(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "triadic" -> triadic(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "splitCompl" -> splitCompl(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                        case "dsplitCompl" -> dsplitCompl(colourPicker.getValue().getHue() / 360, newValue.intValue() / 100.0, sliderBrightness.getValue() / 100.0);
                    }
                });

        sliderBrightness.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (activePalette) {
                        case "mono" -> mono(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "analog" -> analog(colourPicker.getValue().getHue(), sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "compl" -> compl(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "square" -> square(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "rect" -> rectangle(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "triadic" -> triadic(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "splitCompl" -> splitCompl(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                        case "dsplitCompl" -> dsplitCompl(colourPicker.getValue().getHue() / 360, sliderSaturation.getValue() / 100.0, newValue.intValue() / 100.0);
                    }
                });

        sliderRinB.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (activePalette) {
                        case "mono" -> mono(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "analog" -> analog(colourPicker.getValue().getHue(), sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "compl" -> compl(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "square" -> square(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "rect" -> rectangle(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "triadic" -> triadic(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "splitCompl" -> splitCompl(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                        case "dsplitCompl" -> dsplitCompl(colourPicker.getValue().getHue() / 360.0, sliderSaturation.getValue() / 100.0, colourPicker.getValue().getBrightness());
                    }
                });
        /*rangeSlider.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (activePalette == "mono")
                        mono(colourPicker.getValue().getHue()/360.0, sliderSaturation.getValue()/100.0, colourPicker.getValue().getBrightness());
                    else if (activePalette == "analog")
                        analog(colourPicker.getValue().getHue(), sliderSaturation.getValue()/100.0, colourPicker.getValue().getBrightness());
                    else if (activePalette == "compl")
                        compl(colourPicker.getValue().getHue()/360.0, sliderSaturation.getValue()/100.0, colourPicker.getValue().getBrightness());
                    else if (activePalette == "square")
                        square(colourPicker.getValue().getHue()/360.0, sliderSaturation.getValue()/100.0, colourPicker.getValue().getBrightness());
                    else if (activePalette == "triadic")
                        triadic(colourPicker.getValue().getHue()/360.0, sliderSaturation.getValue()/100.0, colourPicker.getValue().getBrightness());
                });*/
    }

    public void rysuj(MouseEvent mouseEvent) {
        if (!coloursFlowPane.getChildren().isEmpty()) {
            double x = Math.floor(mouseEvent.getX() / cellWidth);
            double y = Math.floor(mouseEvent.getY() / cellWidth);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setFill(activeColour);
            gc.fillRect(x * cellWidth, y * cellWidth, cellWidth, cellWidth);
        }
    }

    public void CGAselected() {
        if (!activePalette.equals("CGA")) {
            activePalette = "CGA";
            palette.setText("CGA");
            if (!coloursFlowPane.getChildren().isEmpty()) {
                inactivate();
            }
            coloursFlowPane.setHgap(0);
            for (int i = 0; i < 16; i++) {
                Button button = new Button();
                button.setPrefSize(width / 32, width / 32);
                coloursFlowPane.getChildren().add(button);
                button.setOnAction(e -> buttonActivation(button));
            }

            coloursFlowPane.getChildren().get(0).setStyle("-fx-background-radius: 0; -fx-background-color: #000000");
            coloursFlowPane.getChildren().get(1).setStyle("-fx-background-radius: 0; -fx-background-color: #0000AA");
            coloursFlowPane.getChildren().get(2).setStyle("-fx-background-radius: 0; -fx-background-color: #00AA00");
            coloursFlowPane.getChildren().get(3).setStyle("-fx-background-radius: 0; -fx-background-color: #00AAAA");
            coloursFlowPane.getChildren().get(4).setStyle("-fx-background-radius: 0; -fx-background-color: #AA0000");
            coloursFlowPane.getChildren().get(5).setStyle("-fx-background-radius: 0; -fx-background-color: #AA00AA");
            coloursFlowPane.getChildren().get(6).setStyle("-fx-background-radius: 0; -fx-background-color: #AA5500");
            coloursFlowPane.getChildren().get(7).setStyle("-fx-background-radius: 0; -fx-background-color: #AAAAAA");
            coloursFlowPane.getChildren().get(8).setStyle("-fx-background-radius: 0; -fx-background-color: #555555");
            coloursFlowPane.getChildren().get(9).setStyle("-fx-background-radius: 0; -fx-background-color: #5555FF");
            coloursFlowPane.getChildren().get(10).setStyle("-fx-background-radius: 0; -fx-background-color: #55FF55");
            coloursFlowPane.getChildren().get(11).setStyle("-fx-background-radius: 0; -fx-background-color: #55FFFF");
            coloursFlowPane.getChildren().get(12).setStyle("-fx-background-radius: 0; -fx-background-color: #FF5555");
            coloursFlowPane.getChildren().get(13).setStyle("-fx-background-radius: 0; -fx-background-color: #FF55FF");
            coloursFlowPane.getChildren().get(14).setStyle("-fx-background-radius: 0; -fx-background-color: #FFFF55");
            coloursFlowPane.getChildren().get(15).setStyle("-fx-background-radius: 0; -fx-background-color: #FFFFFF");
        }
    }

    public void EGAselected() {
        if (!activePalette.equals("EGA")) {
            activePalette = "EGA";
            palette.setText("EGA");
            if (!coloursFlowPane.getChildren().isEmpty()) {
                inactivate();
            }
            for (int i = 0; i < 64; i++) {

                Button button = new Button();
                button.setPrefSize(width / 64, width / 64);

                coloursFlowPane.getChildren().add(button);
                button.setOnAction(e -> {
                    String rgbColor = button.getStyle().substring(button.getStyle().indexOf('(') + 1, button.getStyle().lastIndexOf(')'));
                    int r = Integer.parseInt(rgbColor.substring(0, rgbColor.indexOf(',')));
                    int g = Integer.parseInt(rgbColor.substring(rgbColor.indexOf(',') + 1, rgbColor.lastIndexOf(',')));
                    int b = Integer.parseInt(rgbColor.substring(rgbColor.lastIndexOf(',') + 1));
                    activeColour = Color.rgb(r, g, b);
                    button.setStyle(button.getStyle() + "; -fx-border-color:black, white; -fx-border-width: 1, 1; " +
                            "-fx-border-style: solid inside, dotted outside; -fx-border-insets: 0, 1;");

                    if (activeButton != null)
                        activeButton.setStyle(activeButton.getStyle().substring(0, activeButton.getStyle().lastIndexOf(");") + 2));
                    activeButton = button;
                    colourPicker.setValue(activeColour);
                });
            }

            int button = 0;
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++)
                    for (int k = 0; k < 4; k++) {
                        coloursFlowPane.getChildren().get(button).setStyle("-fx-background-radius: 0; -fx-background-color: rgb(" + i * 85 + "," + j * 85 + "," + k * 85 + ");");
                        button++;
                    }
        }
    }

    public void oneBitSelected() {
        if (!activePalette.equals("1bit")){
            activePalette = "1bit";
            palette.setText("1 bit");
            if (!coloursFlowPane.getChildren().isEmpty()) {
                inactivate();
            }

            for (int i = 0; i < 2; i++) {
                Button button = new Button();
                button.setPrefSize(width / 16, width / 16);

                coloursFlowPane.getChildren().add(button);
                button.setOnAction(e -> buttonActivation(button));
            }
            coloursFlowPane.getChildren().get(0).setStyle("-fx-background-radius: 0; -fx-background-color: #000000");
            coloursFlowPane.getChildren().get(1).setStyle("-fx-background-radius: 0; -fx-background-color: #FFFFFF");
        }
    }

    public void twoBitsSelected() {
        if (!activePalette.equals("2bits")){
            activePalette = "2bits";
            palette.setText("2 bits");
            if (!coloursFlowPane.getChildren().isEmpty()) {
                inactivate();
            }
            for (int i = 0; i < 4; i++) {
                Button button = new Button();
                button.setPrefSize(width / 16, width / 16);

                coloursFlowPane.getChildren().add(button);
                button.setOnAction(e -> buttonActivation(button));
            }
            coloursFlowPane.getChildren().get(0).setStyle("-fx-background-radius: 0; -fx-background-color: #000000");
            coloursFlowPane.getChildren().get(1).setStyle("-fx-background-radius: 0; -fx-background-color: #676767");
            coloursFlowPane.getChildren().get(2).setStyle("-fx-background-radius: 0; -fx-background-color: #B6B6B6");
            coloursFlowPane.getChildren().get(3).setStyle("-fx-background-radius: 0; -fx-background-color: #FFFFFF");
        }
    }

    public void monoSelected() {
        if (!activePalette.equals("mono")){
            sliderRinB.setValue(9);
            sliderRinB.setMax(32);
            sliderRinB.setMin(4);
            activePalette = "mono";
            palette.setText("Monochromatic");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                mono(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());
            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    public void colourPicked() {
        activeColour = colourPicker.getValue();
        sliderSaturation.setValue(activeColour.getSaturation() * 100);
        sliderBrightness.setValue(activeColour.getBrightness() * 100);
        switch (activePalette) {
            case "mono" -> mono(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "analog" -> analog(activeColour.getHue(), activeColour.getSaturation(), activeColour.getBrightness());
            case "compl" -> compl(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "splitCompl" -> splitCompl(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "dsplitCompl" -> dsplitCompl(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "square" -> square(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "triadic" -> triadic(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "rect" -> rectangle(activeColour.getHue() / 360.0, activeColour.getSaturation(), activeColour.getBrightness());
            case "custom" -> custom();
            default -> activeButton.setStyle(activeButton.getStyle().substring(0, 48) + FxUtils.toRGBCode(activeColour) + activeButton.getStyle().substring(55));
        }
    }


/*
    public void mono(double hue, double saturation, double brightness){
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();

        double numOfColors = sliderRinB.getValue();
        double range = rangeSlider.getValue() / numOfColors ;
        System.out.println(range);
        double min;
        boolean b;
        double tmp1 = brightness;
        double tmp2 = brightness;
        for (int i = 1; true; i++){
            if (brightness - i * range < 1){
                min = tmp1;
                b = true;
                break;
            }
            else if (brightness + i * range > 99){
                min = tmp2;
                b = false;
                break;
            }
            tmp1 = brightness - i * range / 100.0;
            tmp2 = brightness + i * range / 100.0;
        }
        if (!b)
            min = min - range * numOfColors / 100.0;
       // System.out.println(brightness +  " " + min);
        //while (min > range)
        //    min -= range;
        double br = min;
        for (int i = 0; i < numOfColors; i++){
            int[] rgb;
            //rgb = FxUtils.HSLToRGB(hue, saturation, br / 100);

            Button button = new Button();
            if (numOfColors < 10)
                button.setPrefSize(100, 80);
            else if (numOfColors < 30)
                button.setPrefSize(50, 50);
            else if (numOfColors < 70)
                button.setPrefSize(33, 33);
            else
                button.setPrefSize(25, 25);

            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> {
                activeColour = Color.web(button.getStyle().substring(48, 55));
                button.setStyle(button.getStyle() + "; -fx-border-color:black, white; -fx-border-width: 1, 1; " +
                        "-fx-border-style: solid inside, dotted outside; -fx-border-insets: 0, 1;");
                if (activeButton != null)
                    activeButton.setStyle(activeButton.getStyle().substring(0, 55));
                activeButton = button;
            });
            button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.HSLToRGB(hue, saturation, br / 100));

            br += range;
        }
        setMonoActive(true);
    }
*/

    public void mono(double hue, double saturation, double brightness){
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();

        double range = 33 - sliderRinB.getValue();
        double min = brightness;
        while (min > range)
            min -= range;
        double br = min;
        for (int i = 0; br < 101 - range; i++){
            br += range;
            Button button = new Button();
            if ((100 - min) / range < 11)
                button.setPrefSize(width / 16, width / 24);
            else if ((100 - min) / range < 30)
                button.setPrefSize(width / 32, width / 32);
            else if ((100 - min) / range < 60)
                button.setPrefSize(width / 48, width / 48);
            else
                button.setPrefSize(width / 64, width / 64);

            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));
            button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.HSLToRGB(hue, saturation, br / 100));

        }
        setActive(true);
        labelBrightness.setDisable(true);
        sliderBrightness.setDisable(true);
    }

    public void setActive(boolean b){
        sliderSaturation.setVisible(b);
        saturationLabel.setVisible(b);
        sliderBrightness.setVisible(b);
        labelBrightness.setVisible(b);
        labelBrightness.setDisable(!b);
        sliderBrightness.setDisable(!b);
        RinBlabel.setVisible(b);
        sliderRinB.setVisible(b);
        colourPicker.setVisible(b);
    }

    public void inactivate(){
        coloursFlowPane.getChildren().removeAll();
        coloursFlowPane.getChildren().clear();
        coloursFlowPane.setMinSize(0,0);
        plusButton.setVisible(false);
        savePaletteButton.setVisible(false);
        if (activePalette != "mono"){
            setActive(false);
        }
        if (!(activePalette.equals("analog") || activePalette.equals("compl")))
            setActive(false);
    }

    public void analogousSelected() {
        if (activePalette != "analog"){
            sliderRinB.setValue(5);
            sliderRinB.setMax(9);
            sliderRinB.setMin(3);
            activePalette = "analog";
            palette.setText("Analogous");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                analog(colourPicker.getValue().getHue(), sliderSaturation.getValue() / 100,colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    public void analog(double hue, double saturation, double brightness){
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double numOfColors = sliderRinB.getValue();
        double range = 30;
        double minHue = hue - range * numOfColors / 2;
        if (minHue < 0){
            minHue += 360;
        }
        else if (minHue > 360)
            minHue -= 360;
        for (int i = 0; i < numOfColors; i++){
            while (minHue + i * range > 360)
                minHue -= 360;

            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);

            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));
            button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((minHue + i * range)/360.0, saturation, brightness));
        }
        setActive(true);
    }

    public void complementarySelected() {
        if (activePalette != "compl"){
            sliderRinB.setValue(8);
            sliderRinB.setMax(8);
            sliderRinB.setMin(2);
            activePalette = "compl";
            palette.setText("Complementary");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                compl(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    public void compl(double hue, double saturation, double brightness){
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double brightness2;
        double numOfColors = sliderRinB.getValue();
        double range = .1;
        for (int i = 0; i < numOfColors; i++){
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));

            brightness2 = brightness > .5 ? brightness - range * (i % 4) : brightness + range * (i % 4) ;
            if (i < numOfColors / 2)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb(hue, saturation, brightness2));
            else
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .5) % 1.0, saturation, brightness2));

        }
        setActive(true);
    }

    public void squareSelected() {
        if (activePalette != "square"){
            sliderRinB.setValue(8);
            sliderRinB.setMax(10);
            sliderRinB.setMin(4);
            activePalette = "square";
            palette.setText("Square");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                square(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    public void square(double hue, double saturation, double brightness){
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double brightness2;
        double numOfColors = sliderRinB.getValue();
        double range = .1;
        for (int i = 0; i < numOfColors; i++){
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));

            brightness2 = brightness > .5 ? brightness - range * (i % 4) : brightness + range * (i % 4) ;
            if (i < numOfColors / 4.0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb(hue, saturation, brightness2));
            else if (i < numOfColors / 2.0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .25) % 1, saturation, brightness2));
            else if (i < numOfColors / 1.5)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .5) % 1, saturation, brightness2));
            else
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .75) % 1, saturation, brightness2));
        }
        setActive(true);
    }

    public void rectSelected() {
        if (activePalette != "rect"){
            sliderRinB.setValue(8);
            sliderRinB.setMax(10);
            sliderRinB.setMin(4);
            activePalette = "rect";
            palette.setText("Rectangle");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                rectangle(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    private void rectangle(double hue, double saturation, double brightness) {
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double brightness2;
        double numOfColors = sliderRinB.getValue();
        double range = .1;
        for (int i = 0; i < numOfColors; i++){
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));

            brightness2 = brightness > .5 ? brightness - range * (i % 4) : brightness + range * (i % 4) ;
            if (i < numOfColors / 4.0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb(hue, saturation, brightness2));
            else if (i < numOfColors / 2.0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .166666666667) % 1, saturation, brightness2));
            else if (i < numOfColors / 1.5)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .5) % 1, saturation, brightness2));
            else
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .666666666667) % 1, saturation, brightness2));
        }
        setActive(true);
    }

    public void triadicSelected() {
        if (activePalette != "triadic"){
            sliderRinB.setValue(9);
            sliderRinB.setMax(10);
            sliderRinB.setMin(4);
            activePalette = "triadic";
            palette.setText("Triadic");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                triadic(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    public void triadic(double hue, double saturation, double brightness){
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double brightness2;
        int numOfColors = (int) sliderRinB.getValue();
        double range = .1;
        for (int i = 0; i < numOfColors; i++){
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));

            brightness2 = brightness > .5 ? brightness - range * (i % 4) : brightness + range * (i % 4) ;
            if (i < numOfColors / 3.0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb(hue, saturation, brightness2));
            else if (i < numOfColors / 1.5)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .333333333333) % 1, saturation, brightness2));
            else
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .666666666666) % 1, saturation, brightness2));

        }
        setActive(true);
    }

    public void splitComplSelected() {
        if (activePalette != "splitCompl"){
            sliderRinB.setValue(9);
            sliderRinB.setMax(10);
            sliderRinB.setMin(3);
            activePalette = "splitCompl";
            palette.setText("Split complementary");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                splitCompl(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    private void splitCompl(double hue, double saturation, double brightness) {
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double brightness2;
        int numOfColors = (int) sliderRinB.getValue();
        double range = .05;
        for (int i = 0; i < numOfColors; i++){
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));

            brightness2 = brightness > .5 ? brightness - range * (i % 4) : brightness + range * (i % 4) ;
            if (i < numOfColors / 3.0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb(hue, saturation, brightness2));
            else if (i < numOfColors / 1.5)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .416666666667) % 1, saturation, brightness2));
            else
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .583333333333) % 1, saturation, brightness2));

        }
        setActive(true);
    }

    public void doubleSplitComplSelected() {
        if (activePalette != "dsplitCompl"){
            sliderRinB.setValue(9);
            sliderRinB.setMax(10);
            sliderRinB.setMin(5);
            activePalette = "dsplitCompl";
            palette.setText("Double split complementary");
            if (colourPicker.isVisible() && !coloursFlowPane.getChildren().isEmpty())
                dsplitCompl(colourPicker.getValue().getHue()/360, sliderSaturation.getValue() / 100, colourPicker.getValue().getBrightness());

            else if (!coloursFlowPane.getChildren().isEmpty())
                inactivate();
            colourPicker.setVisible(true);
        }
    }

    private void dsplitCompl(double hue, double saturation, double brightness) {
        if (!coloursFlowPane.getChildren().isEmpty())
            inactivate();
        double brightness2;
        int numOfColors = 5;
        double range = .05;
        for (int i = 0; i < numOfColors; i++){
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));

            brightness2 = brightness > .5 ? brightness - range * (i % 4) : brightness + range * (i % 4) ;
            if (i == 0)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb(hue, saturation, brightness2));
            else if (i == 1)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .416666666667) % 1, saturation, brightness2));
            else if (i == 2)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .416666666667 + .5) % 1, saturation, brightness2));
            else if (i == 3)
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .583333333333) % 1, saturation, brightness2));
            else
                button.setStyle("-fx-background-radius: 0; -fx-background-color: " + FxUtils.hsvToRgb((hue + .583333333333 + .5) % 1, saturation, brightness2));

        }
        setActive(true);
        sliderRinB.setVisible(false);
        RinBlabel.setVisible(false);
    }

    public void moreSelected()  {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("more.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Palettes");
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root, 840, 600);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("resources/stylesheet2.css")).toExternalForm());
            stage.setScene(scene);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("resources/icon.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPalette(Palette rowPalette){
        if (!coloursFlowPane.getChildren().isEmpty()) {
            inactivate();
        }
        if (activePalette != "other")
            activePalette = "other";

        palette.setText(rowPalette.getName());
        for (int i = 0; i < 9 && rowPalette.getHex(i) != "" && rowPalette.getHex(i) != null; i++) {
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            button.setStyle("-fx-background-radius: 0; -fx-background-color: #" + rowPalette.getHex(i));
            coloursFlowPane.getChildren().add(button);
            button.setOnAction(e -> buttonActivation(button));
        }
    }

    public void addCanvas() {
        while (undoIndex > 1){
            images.removeLast();
            undoIndex--;
        }
        if(images.size() > 9)
            images.removeFirst();
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage image = canvas.snapshot(params, null);
        images.addLast(image);
    }

    public void undo() {
        if (undoIndex < images.size()){
            WritableImage image = images.get(images.size() - undoIndex - 1);
            undoIndex++;
            canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        }
    }

    public void redo() {
        if (undoIndex > 1){
            WritableImage image = images.get(images.size() - undoIndex + 1);
            undoIndex--;
            canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        }
    }

    public void save() {
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = canvas.snapshot(parameters, null);
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(Stage.getWindows().get(0));
        try {
            if(file != null)
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        addCanvas();
    }

    public void customSelected() {
        if (!coloursFlowPane.getChildren().isEmpty()) {
            inactivate();
        }
        colourPicker.setVisible(true);
        if (activePalette != "custom"){
            activePalette = "custom";
            palette.setText("Custom");
        }
        plusButton.setStyle(plusButton.getStyle());
        plusButton.setPrefSize(width / 16, width / 24);
        plusButton.setLayoutY(coloursFlowPane.getLayoutY() + coloursFlowPane.getChildren().size() * width / 24);
        plusButton.setVisible(true);
    }

    private void custom() {
        savePaletteButton.setVisible(true);
        if (activeButton == null){
            plusButton.setLayoutY(coloursFlowPane.getLayoutY() + (coloursFlowPane.getChildren().size() + 1) * width / 24);
            Button button = new Button();
            button.setPrefSize(width / 16, width / 24);
            coloursFlowPane.getChildren().add(button);
            button.setStyle("-fx-background-radius: 0; -fx-background-color: #" + activeColour.toString().substring(2, 8) + ' ');

            button.setOnAction(e -> {
                plusButton.setText("+");
                plusButton.setStyle(plusButton.getStyle().substring(0, 32));
                buttonActivation(button);
            });
        }
        else{
            activeButton.setStyle(activeButton.getStyle().substring(0, 49) + activeColour.toString().substring(2, 8) + activeButton.getStyle().substring(55));
        }
    }

    public void plus() {
        if (activeButton != null){
            activeButton.setStyle(activeButton.getStyle().substring(0, 55));
            activeButton = null;
            plusButton.setText("");
            plusButton.setStyle(plusButton.getStyle() + "-fx-border-color: white");
        }
    }

    public void buttonActivation(Button button){
        activeColour = Color.web(button.getStyle().substring(48, 55));
        button.setStyle(button.getStyle() + "; -fx-border-color:black, white; -fx-border-width: 1, 1; " +
                "-fx-border-style: solid inside, dotted outside; -fx-border-insets: 0, 1;");
        if (activeButton != null)
            activeButton.setStyle(activeButton.getStyle().substring(0, 56));
        activeButton = button;
        colourPicker.setValue(activeColour);
    }

    public void savePalette() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPalette.fxml"));
            Parent root = loader.load();
            SavePaletteController controller = loader.getController();
            int numOfColours = coloursFlowPane.getChildren().size();
            Palette palette = new Palette("");
            for (int i = 0; i < numOfColours; i++)
                palette.setHex(i, coloursFlowPane.getChildren().get(i).getStyle().substring(49, 55));
            controller.setPalette(palette);
            Stage stage = new Stage();
            stage.setTitle("Save");
            stage.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(root, 210, 220);
            scene.getStylesheets().add(pane.getScene().getStylesheets().get(1));
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("resources/saveStylesheet.css")).toExternalForm());
            stage.setScene(scene);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("resources/icon.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void settings() {
        int index = pane.getScene().getStylesheets().get(1).indexOf(".css");
        int stylesheetNumber = Integer.parseInt(pane.getScene().getStylesheets().get(1).substring(index - 1, index));
        if (stylesheetNumber > 2)
            stylesheetNumber = 0;
        pane.getScene().getStylesheets().remove(1);
        pane.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("resources/mainStylesheet" + (stylesheetNumber + 1) + ".css")).toExternalForm());
    }
}

