import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class MainFrame extends JFrame{
    JPanel row1, row2, row3, row4, row5, row6;
    static JTextField textfield_diff, textfield_gd, textfield_gsc, textfield_usefull, textfield_name, textfield_fin;
    JLabel label1, label2, label3, label4, label5, label6, info, playtime, drops, ratio;
    JButton insertButton, printButton, deleteButton;
    JMenuBar mainMenu;

    Connection con;
    static User user;
    private Database database;
    Timer timer;

    private String version = "1.0.0";
    private final int WIDTH = 330;
    private final int HEIGTH = 550;
    private final int BUTTON_LEFT_DIST = 80, LABEL_LEFT_DIST = 50, TEXTF_LEFT_DIST = 130;
    private final Color PENDING = Color.black;
    private final Color SUCCESS = Color.green.darker();
    private final Color FAIL = Color.red;
    private final String DEFAULT_STATUS = "Waiting...";
    
    MainFrame(Connection con, User user, Database database) throws SQLException {
        this.con = con;
        this.user = user;
        this.database = database;
        // Design and properties of the main frame
        setLayout(null);
        setTitle("WoW Dungeon DB" + " " + version);
        setIconImage(new ImageIcon("res/main_icon.png").getImage());
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Design and properties of the components
        mainMenu = new MainFrameMenu();
        
        textfield_diff = new JTextField(); textfield_diff.setBounds(TEXTF_LEFT_DIST, 50, 125, 25);
        textfield_gd = new JTextField(); textfield_gd.setBounds(TEXTF_LEFT_DIST, 100, 125, 25);
        textfield_gsc = new JTextField(); textfield_gsc.setBounds(TEXTF_LEFT_DIST, 150, 125, 25);
        textfield_usefull = new JTextField(); textfield_usefull.setBounds(TEXTF_LEFT_DIST, 200, 125, 25);
        textfield_name = new JTextField(); textfield_name.setBounds(TEXTF_LEFT_DIST, 250, 125, 25);
        textfield_fin = new JTextField(); textfield_fin.setBounds(TEXTF_LEFT_DIST, 300, 125, 25);
        label1 = new JLabel("Difficulty: "); label1.setBounds(LABEL_LEFT_DIST, 50, 125, 25);
        label2 = new JLabel("Geardrop: "); label2.setBounds(LABEL_LEFT_DIST, 100, 125, 25);
        label3 = new JLabel("Gearscore: "); label3.setBounds(LABEL_LEFT_DIST, 150, 125, 25);
        label4 = new JLabel("Usefull: "); label4.setBounds(LABEL_LEFT_DIST, 200, 125, 25);
        label5 = new JLabel("Name: "); label5.setBounds(LABEL_LEFT_DIST, 250, 125, 25);
        label6 = new JLabel("Finished in: "); label6.setBounds(LABEL_LEFT_DIST, 300, 125, 25);

        info = new JLabel(DEFAULT_STATUS);
        info.setBounds(LABEL_LEFT_DIST, 370, 125, 25);
        info.setForeground(PENDING);
        playtime = new JLabel("Total playtime: " + Functions.playtime(con, database));
        playtime.setBounds(LABEL_LEFT_DIST, 400, 250, 25);
        drops = new JLabel("Drops: " + Functions.countDropped(con, database) + " . No drops: " + Functions.countUndropped(con, database) + ".");
        drops.setBounds(LABEL_LEFT_DIST, 415, 250, 25);
        ratio = new JLabel("Ratio: " + Functions.dropRatio(Functions.countDropped(con, database), Functions.countUndropped(con, database), Functions.total(con, database)));
        ratio.setBounds(LABEL_LEFT_DIST, 430, 250, 25);
        //Declare button action
        insertButton = new JButton(new AbstractAction("insert") { 
            @Override
            public void actionPerformed(ActionEvent e) {
                insert();
            }
        }); 
        insertButton.setBounds(LABEL_LEFT_DIST+50, 350, 75, 25);
        
        printButton = new JButton(new AbstractAction("print") { 
            @Override
            public void actionPerformed(ActionEvent e) {
                new TableFrame(con, user, database);
            }
        }); 
        printButton.setBounds(LABEL_LEFT_DIST+130, 350, 80, 25);

        deleteButton = new JButton(new AbstractAction("↺") { 
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        }); 
        deleteButton.setBounds(LABEL_LEFT_DIST, 350, 45, 25); 
        //Adding components to the frame
        add(label1); add(textfield_diff);

        add(label2); add(textfield_gd);

        add(label3); add(textfield_gsc);

        add(label4); add(textfield_usefull);

        add(label5); add(textfield_name);

        add(label6); add(textfield_fin);

        add(info);
        add(playtime);
        add(drops);
        add(ratio);
        add(insertButton); add(printButton); add(deleteButton);
        
        setJMenuBar(mainMenu);
        setVisible(false);
    }

    private void insert() {
        Boolean wasSuccessfull = true;
        
        try {
            Functions.insert(con, database, textfield_diff.getText(), Functions.returnDate(), textfield_gd.getText(), textfield_gsc.getText(), textfield_usefull.getText(), textfield_name.getText(), textfield_fin.getText());
        } catch (SQLException e1) {
            e1.printStackTrace();
            wasSuccessfull = false;
        }
        
        if( wasSuccessfull ) {
            info.setForeground(SUCCESS);
            info.setText("Insertion successfull.");
        } else {
            info.setForeground(FAIL);
            info.setText("Insertion failed.");
        }
         // Sets up a delayed timer which executes the code after 2000ms
        Timer timer = new Timer(2000, event -> {
            info.setText(DEFAULT_STATUS);
            info.setForeground(PENDING);
        });
        timer.setRepeats(false);
        timer.start();
        resetTextfields();
    }

    private void delete() {
        Boolean wasSuccessfull = true;
        
        try {
            Functions.delete(con, database);
        } catch (SQLException e1) {
            e1.printStackTrace();
            wasSuccessfull = false;
        }
        
        if( wasSuccessfull ) {
            info.setForeground(SUCCESS);
            info.setText("Deletion successfull.");
        } else {
            info.setForeground(FAIL);
            info.setText("Deletion failed.");
        }
         // Sets up a delayed timer which executes the code after 2000ms
        Timer timer = new Timer(2000, event -> {
            info.setText(DEFAULT_STATUS);
            info.setForeground(PENDING);
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void resetTextfields() {
        MainFrame.textfield_diff.setText("");
        MainFrame.textfield_gd.setText(""); MainFrame.textfield_gsc.setText(""); MainFrame.textfield_usefull.setText(""); MainFrame.textfield_fin.setText("");
        MainFrame.textfield_name.setText("");
    }
}