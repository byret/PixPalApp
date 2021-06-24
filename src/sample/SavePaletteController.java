package sample;

import javafx.event.ActionEvent;
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

public class SavePaletteController {

    public CheckBox checkBox;
    public Palette palette;
    public TextField name;
    public TextField category;
    public Label categoryLabel;

    public void initialize() {

    }

    public void save() {
        palette.setName(name.getText());
        if (!checkBox.isSelected()){
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/sample/resources/custom_palettes.txt", true));
                writer.append(palette.getName()).append('\n').append(palette.getHexes()).append('\n');
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                Connect.connect();
                PaletteDAO.insert(palette, capitalizeString(category.getText()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        Stage stage = (Stage) checkBox.getScene().getWindow();
        stage.close();
    }

    public void setPalette(Palette palette){
        this.palette = palette;
    }

    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }

    public void checkBoxPressed(ActionEvent actionEvent) {
        categoryLabel.setDisable(!checkBox.isSelected());
        category.setDisable(!checkBox.isSelected());
    }
}

