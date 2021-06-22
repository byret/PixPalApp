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
    public TreeTableColumn<Palette, String> colour1;
    public TreeTableColumn<Palette, String> colour2;
    public TreeTableColumn<Palette, String> colour3;
    public TreeTableColumn<Palette, String> colour4;
    public TreeTableColumn<Palette, String> colour5;
    public TreeTableColumn<Palette, String> colour6;
    public TreeTableColumn<Palette, String> colour7;
    public TreeTableColumn<Palette, String> colour8;
    public TreeTableColumn<Palette, String> colour9;

    public void initialize() {
        category.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));

        colour1.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(0))
        );
        colour2.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(1))
        );
        colour3.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(2))
        );
        colour4.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(3))
        );
        colour5.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(4))
        );
        colour6.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(5))
        );
        colour7.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(6))
        );
        colour8.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(7))
        );
        colour9.setCellValueFactory((param) ->
                new ReadOnlyStringWrapper(param.getValue().getValue().getHex(8))
        );

        colour1.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour2.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour3.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour4.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour5.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour6.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour7.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour8.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });

        colour9.setCellFactory((TreeTableColumn<Palette, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Palette, String>(){
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null && item.length() == 6)
                        super.setStyle("-fx-background-color: #" + item);
                    else
                        super.setStyle("-fx-background-color: #ffffff");
                }
            };
            return cell;
        });


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
        TreeItem categoryItem = new TreeItem(new Palette("Categories"));
        File newFile = new File("src/sample/resources/custom_palettes.txt");
        if (newFile.length() != 0) {
            categoryItem.getChildren().add((new TreeItem(new Palette("Local custom"))));
            try (Stream<String> lines = Files.lines(Paths.get("src/sample/resources/custom_palettes.txt"), Charset.defaultCharset())) {
                lines.forEachOrdered(line -> {
                    linesList.add(line);
                });
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
            ((TreeItem)categoryItem.getChildren().get(0)).getChildren().add(new TreeItem(palette));
            i += 1;
        }
        linesList.clear();

        try {
            ObservableList<Palette> list = PaletteDAO.selectAll();
            if (!list.isEmpty()){
                String category = list.get(0).getCategory();
                categoryItem.getChildren().add(new TreeItem(new Palette(category)));
                int i = 0;
                for (Palette palette : list){
                    if (!palette.getCategory().equals(category)){
                        category = palette.getCategory();
                        categoryItem.getChildren().add(new TreeItem(new Palette(category)));
                        i++;
                    }
                    ((TreeItem)categoryItem.getChildren().get(newFile.length() == 0 ? i : i + 1)).getChildren().add(new TreeItem(palette));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (Stream<String> lines = Files.lines(Paths.get("src/sample/resources/colour_palettes.txt"), Charset.defaultCharset())) {
            lines.forEachOrdered(line -> {
                linesList.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        int x = 1;
        for (int i = 0; i < linesList.size(); i++){
            String line = linesList.get(i);
            if (line.charAt(0) == '#'){
                categoryItem.getChildren().add(new TreeItem(new Palette(line.substring(1))));
                x++;
            }
            else {
                Palette palette = new Palette(line);
                int j;
                String lineTmp = linesList.get(i + 1);
                for (j = i; lineTmp.charAt(0) != '#' && j < linesList.size(); j++){
                    for (int k = 0; lineTmp.contains(","); k++){
                        palette.setHex(k, lineTmp.substring(0, lineTmp.indexOf(',')));
                        lineTmp = lineTmp.substring(lineTmp.indexOf(',') + 1);
                        if (lineTmp.indexOf(',') < 0)
                            palette.setHex(k + 1, lineTmp);
                    }
                }
                ((TreeItem)categoryItem.getChildren().get(x - 1)).getChildren().add(new TreeItem(palette));
                i += 1;
            }
        }*/
        ttView.setRoot(categoryItem);
    }
}

