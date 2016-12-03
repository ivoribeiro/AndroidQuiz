package pt.ipp.estg.cmu.db.repositories;

import java.util.ArrayList;

interface RepositoryInterface<T> {

    ArrayList<T> getAll();

    ArrayList<T> getAllByField(String field, String value);

    T getById(int id);


    T insertInto(T t);


}
