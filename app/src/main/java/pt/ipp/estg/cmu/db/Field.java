package pt.ipp.estg.cmu.db;


public class Field {
    private String name;
    private boolean primary;
    private boolean autoincrement;
    private String type;

    public Field(boolean primary, boolean autoincrement, String name, String type) {
        this.primary = primary;
        this.autoincrement = autoincrement;
        this.name = name;
        this.type = type;
    }

    public Field(String name){
        this.name=name;
    }

    @Override
    public String toString() {
        String primary = this.primary ? "PRIMARY KEY" : "";
        String increment = this.autoincrement ? "AUTOINCREMENT" : "";
        return " "+ this.name + " " + this.type + " " + primary + " " + increment+",";
    }

    public String getName() {
        return name;
    }
}
