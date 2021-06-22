package sample;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Palette;
import model.PaletteDAO;
import servis.Connect;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class SettingsController {

    protected Scene scene;
    protected int stylesheetNumber;

    public void initialize() {
        System.out.println(scene);
        //stylesheetNumber = Integer.parseInt(scene.getStylesheets().get(0).substring(14, 15));
        //if (stylesheetNumber > 3)
        //    stylesheetNumber = 0;
        //scene.getStylesheets().add("resources/mainStylesheet" + stylesheetNumber + ".css");
    }

}

