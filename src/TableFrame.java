import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TableFrame extends JFrame{
    User user;
    Database db;
    Connection con;
    JLabel[] labelArray;
    String[] rows;

    private final int STATIC_X = 10;
    private final int DYNAMIC_Y = 5;

    public TableFrame(Connection con, User user, Database db) {
        this.con = con;
        this.user = user;
        this.db = db;

        setTitle("MySQL table: '" + db.getTable() + "'");
        setIconImage(new ImageIcon("res/main_icon.png").getImage());
        setSize(350, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(1180, 301);
            
        labelArray = new JLabel[Functions.PRINT_QUANTITY];  
        rows = Functions.getRowData(con, db);
       
        insertIntoFrame(labelArray, rows, DYNAMIC_Y);
        setVisible(true);  
    }

    private void insertIntoFrame(JLabel[] labelArray, String[] rows, int dynamic_y) {
        for(int i = 0; i < labelArray.length; i++) {
            labelArray[i] = new JLabel();
            labelArray[i].setText(rows[i]);
            labelArray[i].setBounds(STATIC_X, dynamic_y, 1000, 25);
            dynamic_y += 15;
            add(labelArray[i]);
        }
    }
}
