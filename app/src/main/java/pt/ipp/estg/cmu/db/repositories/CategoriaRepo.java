package pt.ipp.estg.cmu.db.repositories;

public class CategoriaRepo extends Repo {


    public CategoriaRepo() {
        super("categoria");
        this.setFields();
    }

    private void setFields(){
        this.addField("ID","id");
        this.addField("NOME","nome");
    }


}
