package model;

public final class Palette {
    private String name;
    private final String[] hexes = {"", "", "", "", "", "", "", "", ""};
    private String category;

    public Palette(String name) {
        this.name = name;
    }

    public String getName(){return name;}

    public void setName(String name){this.name = name;}

    public String getCategory() {return category;}

    public void setCategory(String category){this.category = category;}

    public void setHex(int i, String hex){hexes[i] = hex;}

    public String getHex(int i){return hexes[i];}

    public String getHexes(){
        String result = hexes[0];
        for (int i = 1; hexes[i] != ""; i++)
            result += ',' + hexes[i];
        return result;
    }

    public String getHexesForSql(){
        String result = hexes[0] + "'";
        for (int i = 1; i < 9; i++){
            if (hexes[i] != "")
                result += ",'" + hexes[i] + "'";
            else
                result += ",NULL";
        }
        if (!result.contains("NULL"))
            result += "'";
        return result;
    }
    public int getHexesLength(){return hexes.length;}
}
