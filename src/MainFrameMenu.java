import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrameMenu extends JMenuBar implements ActionListener{
    JMenu helpMenu, infoMenu, quickMenu;
    JMenuItem use, about, setDefault, setDefaultNoDrop, setLogin;

    public MainFrameMenu() {
        quickMenu = new JMenu("Quick");
        setDefault = new JMenuItem("Set failed run");
        setDefaultNoDrop = new JMenuItem("Set nodrop");
        setLogin = new JMenuItem("Set login information");
        helpMenu = new JMenu("Help");
        use = new JMenuItem("How to use");
        infoMenu = new JMenu("Info");
        about = new JMenuItem("Information");

        quickMenu.add(setDefault);
        quickMenu.add(setDefaultNoDrop);
        quickMenu.add(setLogin);
        helpMenu.add(use);
        infoMenu.add(about);

        setDefault.addActionListener(this);
        setDefaultNoDrop.addActionListener(this);
        setLogin.addActionListener(this);
        add(quickMenu);
        add(helpMenu);
        add(infoMenu);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == setDefault) {
            MainFrame.textfield_diff.setText("+");
            MainFrame.textfield_gd.setText("null"); MainFrame.textfield_gsc.setText("null"); MainFrame.textfield_usefull.setText("null"); MainFrame.textfield_fin.setText("null");
            MainFrame.textfield_name.setText("Vyvari");
        } else if(e.getSource() == setDefaultNoDrop) {
            MainFrame.textfield_diff.setText("+");
            MainFrame.textfield_gd.setText("N"); MainFrame.textfield_gsc.setText("null"); MainFrame.textfield_usefull.setText("null");
            MainFrame.textfield_name.setText("Vyvari");
        } else if(e.getSource() == setLogin) {
            LoginFrame.input_username.setText(MainFrame.user.getName());
            LoginFrame.input_password.setText(MainFrame.user.getPassword());
        }
    }
}
