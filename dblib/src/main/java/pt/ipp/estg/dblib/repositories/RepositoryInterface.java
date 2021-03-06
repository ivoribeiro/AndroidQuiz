package pt.ipp.estg.dblib.repositories;

import java.util.ArrayList;

public interface RepositoryInterface<T> {

    ArrayList<T> query(String[] tableColumns, String whereClause, String[] whereArgs, String orderBy);

    ArrayList<T> getAll();

    ArrayList<T> getAllByFields(String[] fields, String[] values);

    ArrayList<T> getAllByField(String field, String value);

    T getById(int id);

    T insertInto(T t);

    void deleteById(int id);

    T update(T resource);


    boolean canDelete(T resource);
}
