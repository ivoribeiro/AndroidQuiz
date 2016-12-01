package pt.ipp.estg.cmu.db.repositories;

import java.util.ArrayList;

interface RepositoryInterface<T> {

    public ArrayList<T> getAll();

    public T getById(int id);

    public T insertInto(T t);


}
