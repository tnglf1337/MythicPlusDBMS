import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * To do:
 * Retrieve column names of mysql table automatically for global use
 */
public final class Functions {

    public static final int PRINT_QUANTITY = 30;


    /** 
     * Creates a connection with the desired MySQL-database and login information
     * @param className This is the String variable DRIVERPATH
     * @param url I dont know what this is exactly but it is needed
     * @param user This is the username established for the MySQL connection 'localhost'
     * @param password This is the password defined for the said user
     * @return Connection
     */
    public static Connection createConnection(String className, String url, String user, String password) {
        try {
            Class.forName(className);
            Connection con = DriverManager.getConnection(url, user, password);

            return con;
        } catch (ClassNotFoundException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
        return null; 
    }


    /** 
     * Inserts a row into the table 
     * @param con Connection to the database
     * @param db Database-Object
     * @param table Name of the table
     * @param difficulty Difficulty of the finished run
     * @param date  Date when the run was finished
     * @param geardrop 'Y' for a a run with dropped gear, 'N' für a run with no dropped gear
     * @param gearscore 0, if there was no dropped gear, else value of gearscore of dropped gear
     * @param usefull 'Y', if dropped gear was better than current piece, 'N' if gear is not better than current piece
     * @param name Name of the character 
     * @param finishedIn Time needed to finish the dungeon
     * @throws SQLException
     */
    public static void insert(Connection con, Database db, String difficulty, String date, String geardrop, String gearscore, String usefull, String name, String finishedIn) throws SQLException {
            Statement stmnt = con.createStatement();
            String querie = String.format("INSERT INTO %s(difficulty, run_date, gear_drop, gsc_of_drop, usefull_drop, char_name, finished_in) VALUE('%s', '%s', '%s', %s, '%s', '%s', %s)", db.getTable(), difficulty, date, geardrop, gearscore, usefull, name, finishedIn);

            stmnt.execute(querie);
    }


    /** 
     * Deletes the last row in a table and resets the auto_increment
     * @param con Connection to the database
     * @param db Database-Object
     * @throws SQLException
     */
    public static void delete(Connection con, Database db) throws SQLException {
        Statement stmnt = con.createStatement();
        int lastElement = total(con, db);
        String deleteQuerie = String.format("DELETE FROM %s WHERE run_id = %d", db.getTable(), lastElement);
        String resetQuerie = String.format("ALTER TABLE %s AUTO_INCREMENT = %d", db.getTable(), lastElement - 1);

        stmnt.execute(deleteQuerie);
        stmnt.execute(resetQuerie);
    }
    
    
    /** 
     * Calculates the sum of all finished dungeon runs
     * @return palytime in minutes
     * @throws SQLException
     */
    public static String playtime(Connection con, Database db) throws SQLException {
        Statement stmnt = con.createStatement();
        String query = String.format("SELECT SUM(finished_in) FROM %s", db.getTable());
        ResultSet rs =  stmnt.executeQuery(query);
        int playtime = 0;
        while(rs.next()) {
            playtime = rs.getInt(1);
        }
        return ConvertSectoDay( playtime * 60);
    }

    
    /** 
     * @param con Connection to the database
     * @param db Database-Object
     * @return Amount of piece dropped
     * @throws SQLException
     */
    public static int countDropped(Connection con, Database db) throws SQLException {
        Statement stmnt = con.createStatement();
        String query = String.format("SELECT COUNT(run_id) FROM %s WHERE gear_drop = 'Y'", db.getTable());
        ResultSet rs =  stmnt.executeQuery(query);
        int count = 0;
        while(rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    
    /** 
     * @param con Connection to the database
     * @param db Database-Object
     * @return Amount of no piece dropped
     * @throws SQLException
     */
    public static int countUndropped(Connection con, Database db) throws SQLException {
        Statement stmnt = con.createStatement();
        String query = String.format("SELECT COUNT(run_id) FROM %s WHERE gear_drop = 'N'", db.getTable());
        ResultSet rs =  stmnt.executeQuery(query);
        int count = 0;
        while(rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    
    /** 
     * @param x Amount of dropped pieces
     * @param y Amount of undropped pieces
     * @param z Total runs
     * @return The ratio of dropped and undropped in percentage
     */
    public static String dropRatio(int x, int y, int z) {
        double a = (double) x / z;
        double b = (double) y / z;
        double c = 100 * (a / b);

        String res = String.format("%.2f%%", c);
        return res;
    }

    
    /** 
     * @param n seconds
     * @return Formated String
     */
    private static String ConvertSectoDay(int n) {
        int day = n / (24 * 3600);
      
        n = n % (24 * 3600);
        int hour = n / 3600;
      
        n %= 3600;
        int minutes = n / 60 ;
     
        return String.format("%dd, %dh, %dm.", day, hour, minutes);
    }

    
    /** 
     * @param con Connection to the database
     * @param db Database-Object
     * @return Sum of all runs
     * @throws SQLException
     */
    public static int total(Connection con, Database db) throws SQLException {
        Statement stmnt = con.createStatement();
        String query = String.format("SELECT COUNT(run_id) FROM %s", db.getTable());
        ResultSet rs =  stmnt.executeQuery(query);
        int count = 0;
        while(rs.next()) {
            count = rs.getInt(1);
        }
        return count;
    }

    
    /** 
     * Fetches the current system date and returns it
     * @return String in yyyy-mm-dd format
     */
    public static String returnDate() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        
        return date;
    }


    /** 
     * Fetches all data of the table in a String-Array
     * @param con Connection to the database
     * @param db Database-Object
     * @return String[]
     */
    public static String[] getRowData(Connection con, Database db) {
        try {
            String currentRow;
            String[] tableArray = new String[total(con, db)];
            Statement stmnt = con.createStatement();
            
            ResultSet rs =  stmnt.executeQuery("SELECT * FROM " + db.getTable());
            
            for(int i = 0; rs.next(); i++) {
                currentRow = "";

                int run_id = rs.getInt("run_id");
                String diff = rs.getString("difficulty");
                String date = rs.getString("run_date");
                String gd = rs.getString("gear_drop");
                String gsc = rs.getString("gsc_of_drop");
                String usefull = rs.getString("usefull_drop");
                String name = rs.getString("char_name");
                int fin = rs.getInt("finished_in");

                currentRow = run_id + " " + diff + " " + date + " " + gd + " " + gsc + " " + usefull + " " + name + " " + fin;
                tableArray[i] = currentRow;
            } 
            
            String[] finArray = new String[PRINT_QUANTITY];
            for(int i = tableArray.length-1,  j = 0; i >= tableArray.length - finArray.length && j < PRINT_QUANTITY ; i--, j++) {
                finArray[j] = tableArray[i];
                j++;
            }
            return finArray;
        } catch (SQLException e) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, e);
        }
        return new String[0];
    }
}