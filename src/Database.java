public class Database {
    private final String database;
    private final String table; 
    public final String DRIVERPATH = "com.mysql.cj.jdbc.Driver";
    public final String URL;

    public Database(String database, String table) {
        this.database = database;
        this.table = table;
        URL = "jdbc:mysql://localhost:3306/" + database;
    }

    public String getDatabase() {
        return database;
    }

    public String getTable() {
        return table;
    }
}