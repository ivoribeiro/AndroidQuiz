package pt.ipp.estg.cmu.db.repositories;

import java.util.ArrayList;

import pt.ipp.estg.cmu.models.Nivel;

public interface RepositoryInterface<T> {

    ArrayList<T> query(String[] tableColumns, String whereClause, String[] whereArgs, String orderBy);

    ArrayList<T> getAll();

    ArrayList<T> getAllByFields(String[] fields, String[] values);

    ArrayList<T> getAllByField(String field, String value);

    T getById(int id);

    T insertInto(T t);

    void deleteById(int id);

    T update(T resource);


}
