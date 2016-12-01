package pt.ipp.estg.cmu.db.repositories;

import java.util.HashMap;

import pt.ipp.estg.cmu.db.Field;

public class Repo {

    private String table;
    private HashMap<String, Field> fields;

    public Repo(String table) {
        this.table = table;
    }

    public String getTable() {
        return this.table;
    }

    public void addField(String key, String value) {
        this.fields.put(key, new Field(value));
    }

    public String getField(String key) {
        return this.fields.get(key).getName();
    }
}
