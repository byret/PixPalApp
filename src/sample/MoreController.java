package sample;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import model.Palette;
import model.PaletteDAO;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MoreController {

    public TreeTableView<Palette> ttView;
    public TreeTableColumn<Palette, String> category;

    public void initialize() {
        category.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));


        for (int i = 0; i < 9; i++) {
            TreeTableColumn<Palette, String> ttc = new TreeTableColumn<>();
            int finalI = i;
            ttc.setCellValueFactory((param) ->
                    new ReadOnlyStringWrapper(param.getValue().getValue().getHex(finalI))
            );
            ttc.setCellFactory((TreeTableColumn<Palette, String> param) -> new TreeTableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            });
            ttc.setPrefWidth(70.0);
            ttView.getColumns().add(ttc);
        }

        ttView.setRowFactory(tv -> {
            TreeTableRow<Palette> row = new TreeTableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Palette rowData = row.getItem();
                    if (rowData.getHexesLength() != 0 && rowData.getHex(0) != ""){
                        Controller controller = Main.getController();
                        controller.loadPalette(rowData);
                        Stage stage = (Stage) ttView.getScene().getWindow();
                        stage.close();
                    }
                }
            });
            return row ;
        });

        List<String> linesList = new ArrayList<>();
        TreeItem<Palette> categoryItem = new TreeItem<>(new Palette("Categories"));
        File newFile = new File("src/sample/resources/custom_palettes.txt");
        if (newFile.length() != 0) {
            categoryItem.getChildren().add((new TreeItem<>(new Palette("Local custom"))));
            try (Stream<String> lines = Files.lines(Paths.get("src/sample/resources/custom_palettes.txt"), Charset.defaultCharset())) {
                lines.forEachOrdered(linesList::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < linesList.size(); i++){
            String line = linesList.get(i);
            Palette palette = new Palette(line);
            int j;
            String lineTmp = linesList.get(i + 1);
            for (j = i; j < linesList.size(); j++){
                for (int k = 0; lineTmp.contains(","); k++){
                    palette.setHex(k, lineTmp.substring(0, lineTmp.indexOf(',')));
                    lineTmp = lineTmp.substring(lineTmp.indexOf(',') + 1);
                    if (lineTmp.indexOf(',') < 0)
                        palette.setHex(k + 1, lineTmp);
                }
            }
            (categoryItem.getChildren().get(0)).getChildren().add(new TreeItem<>(palette));
            i += 1;
        }
        linesList.clear();

        try {
            ObservableList<Palette> list = PaletteDAO.selectAll();
            if (!list.isEmpty()){
                String category = list.get(0).getCategory();
                categoryItem.getChildren().add(new TreeItem<>(new Palette(category)));
                int i = 0;
                for (Palette palette : list){
                    if (!palette.getCategory().equals(category)){
                        category = palette.getCategory();
                        categoryItem.getChildren().add(new TreeItem<>(new Palette(category)));
                        i++;
                    }
                    (categoryItem.getChildren().get(newFile.length() == 0 ? i : i + 1)).getChildren().add(new TreeItem<>(palette));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        ttView.setRoot(categoryItem);
    }

}

