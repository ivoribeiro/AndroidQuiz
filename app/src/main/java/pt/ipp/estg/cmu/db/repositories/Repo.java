package pt.ipp.estg.cmu.db.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

import pt.ipp.estg.cmu.db.DbHandler;
import pt.ipp.estg.cmu.db.Field;

public class Repo<T> extends DbHandler {

    private String table;
    private HashMap<String, Field> fields;

    public Repo(Context context, String table) {
        super(context, "androidQuiz.db");
        this.table = table;
        this.fields = new HashMap<>();
    }

    public HashMap<String, Field> getFields() {
        return fields;
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

    public String getAllQueryString() {
        return "SELECT * FROM " + this.table + " ;";
    }

    public String getByIdQueryString(int id) {
        return "SELECT * FROM " + this.table + " WHERE id=" + id + ";";
    }
    public String getAllByFieldQueryString(String field, String value) {
        return "SELECT * FROM " + this.table + " WHERE " + field + "=" + value + ";";
    }
}
