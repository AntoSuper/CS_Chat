import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class Login extends JFrame implements KeyListener, ActionListener {

    private JLabel indirizzo = new JLabel("Indirizzo IP: ", JLabel.CENTER);
    private JLabel username = new JLabel("Nome utente: ", JLabel.CENTER);
    private JTextField IP = new JTextField("173.249.0.76");
    private JTextField nickname = new JTextField();

    public static void main(String[] args) {
        new Login();
    }

    public Login() {
        super("Login chat");
        this.setSize(250,100);
        setLayout(new GridLayout(2,2));
        
        
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);

        indirizzo.setOpaque(true);
        indirizzo.setBackground(new Color(173,216,230));
        this.add(indirizzo);

        IP.setBorder(null);
        IP.setBackground(new Color(173,216,230));
        this.add(IP);

        username.setBackground(new Color(173,216,230));
        username.setOpaque(true);
        this.add(username);

        nickname.setBorder(null);
        nickname.setBackground(new Color(173,216,230));
        this.add(nickname);
        
        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                nickname.requestFocusInWindow();
            }
        });
        
        IP.addKeyListener(this);
        nickname.addKeyListener(this);
        
        MyDefaultMetalTheme a=new MyDefaultMetalTheme();
        
        MetalLookAndFeel.setCurrentTheme(a);
        try {
          UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (Exception e) {
          e.printStackTrace();
        }
    
        SwingUtilities.updateComponentTreeUI(this);

        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    

    public void actionPerformed(ActionEvent e) {

    }

    public void keyPressed(KeyEvent e) {  
        if(e.getKeyCode()==10) {
            try {
                new Chat(IP.getText(), nickname.getText());
                setVisible(false);
                dispose();
            }
            catch (Exception g) {
                g.printStackTrace();
            }
        }
    }

    public void keyReleased(KeyEvent e){

    }

    public void keyTyped(KeyEvent e){

    }
}
