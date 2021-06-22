package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Palette;
import servis.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PaletteDAO {
    public static void insert(Palette palette, String category) throws SQLException {
        String sql = "INSERT INTO palettes(name, category, hex1, hex2, hex3, hex4, hex5, hex6, hex7, hex8, hex9) " +
                "VALUES('" + palette.getName() + "','" + category + "','" + palette.getHexesForSql() + ")";
        System.out.println(sql);
        try {
            Connect.executeDML(sql);
        } catch (SQLException throwables) {
            throw throwables;
        }
    }



    public static ObservableList<Palette> selectAll() throws SQLException {
        String sql = "SELECT * FROM palettes ORDER BY category";
        ObservableList<Palette> list = null;

        try {
            ResultSet resultSet = Connect.getData(sql);
            list = toObservableList(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw throwables;
        }

        return list;
    }

    private static ObservableList<Palette> toObservableList(ResultSet resultSet) {
        ObservableList<Palette> list = FXCollections.observableArrayList();
        try {
            while(resultSet.next()){
                Palette palette = new Palette(resultSet.getString("name"));
                palette.setCategory(resultSet.getString("category"));
                for (int i = 0; i < 8 && resultSet.getString("hex" + (i + 1)) != "null"; i++){
                    palette.setHex(i, resultSet.getString("hex" + (i + 1)));
                }
                list.add(palette);
            }
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
