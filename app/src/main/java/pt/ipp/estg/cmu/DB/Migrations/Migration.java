package pt.ipp.estg.cmu.DB.Migrations;

import java.util.ArrayList;

public class Migration {

    private String table;
    private ArrayList<Field> fields;

    private String create;
    private String upgrade;

    public Migration(String tableName) {
        this.table = tableName;
        fields = new ArrayList<Field>();
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void addField(Field value) {
        this.fields.add(value);
    }

    public String generateCreate() {
        String str = "CREATE TABLE " + this.table + " (";
        for (int i = 0; i < this.fields.size(); i++) {
            str = str + fields.get(i).toString();
        }
        return str + ")";
    }

    public String generateUpgrade() {
        String str = "DROP TABLE IF EXISTS " + this.table;
        return str;
    }
}
