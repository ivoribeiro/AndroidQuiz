package pt.ipp.estg.dblib.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipp.estg.dblib.DbHandler;
import pt.ipp.estg.dblib.helpers.DbHelper;

public abstract class Repo<T> extends DbHandler {

    private String table;

    public Repo(Context context, String table) {
        super(context, DbHandler.DATABASE_NAME);
        this.table = table;
    }

    public String getTable() {
        return this.table;
    }


    public ArrayList<T> query(String[] tableColumns, String whereClause, String[] whereArgs, String orderBy) {
        return null;
    }

    public ArrayList<T> getAll() {
        return this.query(null, null, null, null);
    }

    public ArrayList<T> getAllByFields(String[] fields, String[] values) {
        return this.query(null, DbHelper.whereClause(fields), values, null);
    }

    public ArrayList<T> getAllByField(String field, String value) {
        String[] fields = {field};
        String[] values = {value};
        return getAllByFields(fields, values);
    }

    public T getById(int id) {
        return this.getAllByField("id", "" + id).get(0);
    }

    public void deleteById(int id) {
        String query = DbHelper.deleteByFieldQueryString(this.getTable(), "id", "" + id);
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL(query);
        db.close();
    }
}
