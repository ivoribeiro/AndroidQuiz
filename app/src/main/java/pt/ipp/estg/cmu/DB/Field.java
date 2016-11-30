package pt.ipp.estg.cmu.DB;

/**
 * Created by ivoribeiro on 30-11-2016.
 */

public class Field {
    private String name;
    private boolean primary;
    private String type;

    public Field(boolean primary, String name, String type) {
        this.primary = primary;
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        String primary = this.primary ? "PRIMARY KEY" : "";
        return this.name + " " + this.type + primary;
    }
}
