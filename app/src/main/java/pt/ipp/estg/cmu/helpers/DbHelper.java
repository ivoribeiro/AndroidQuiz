package pt.ipp.estg.cmu.helpers;

public class DbHelper {

    public static String deleteByFieldQueryString(String table, String field, String value) {
        return "DELETE FROM " + table + " WHERE " + field + "=" + value + ";";
    }

    public static String whereClause(String[] fields) {
        String whereClause = "";
        for (String field : fields) {
            whereClause += " "+field + "=? AND";
        }
        int index = whereClause.lastIndexOf(" ");
        whereClause = whereClause.substring(0, index);
        return whereClause;
    }
}
