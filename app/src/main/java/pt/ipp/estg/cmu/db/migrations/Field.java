package pt.ipp.estg.cmu.db.migrations;


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

    @Override
    public String toString() {
        String primary = this.primary ? "PRIMARY KEY" : "";
        String increment = this.autoincrement ? " autoincrement" : "";
        return this.name + " " + this.type + primary+increment;
    }
}
