import java.sql.Connection;
import java.sql.SQLException;


/**
 * @author T.N
 * @version 1.0.0
 * To do:
 *
 * Info:
 * "Login System" is ok-ish implemented, to activate, change visibility in MainFrame to "false" and uncoment line 20
 */
public class App {
    public static void main(String[] args) throws SQLException {
        User user = new User("", "");
        Database wow = new Database("", "");
        Connection con = Functions.createConnection(wow.DRIVERPATH, wow.URL, user.getName(), user.getPassword());
        
        MainFrame mainFrame = new MainFrame(con, user, wow);
        new LoginFrame(user, mainFrame);
    }
}