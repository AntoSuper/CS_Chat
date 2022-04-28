import java.net.*;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.BevelBorder;

public class Chat extends JFrame implements ActionListener, KeyListener, WindowListener {
    
    private int cont = 2;
    private int pannelli = 0;
 
    private static String IP;
    private static int port;
    private static Utente utente;

    private static Socket socket;
    private static Client client;

    private JTextField msg;
    private JTextField selected;

    private final ListPanel msgs;
    private final ListPanel utenti;

    private String nickname;

    public Chat(String indirizzo, String nickname) throws Exception {
        super("Chat");
        setSize(600,600);
        addKeyListener(this);
        addWindowListener(this);
        
        this.setUndecorated(true);
        this.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        
        IP = indirizzo;
        port = 2000;
        utente = new Utente(nickname);
        
        apriConnessione(IP, port, utente);
        client.iscrizioneChat();
        
        Timer updateTimer = new Timer(2000,this);
        updateTimer.setActionCommand("update");
        updateTimer.start();
        
        Timer utentiTimer = new Timer(2000,this);
        utentiTimer.setActionCommand("users");
        utentiTimer.start();
        
        JPanel messaggistica = new JPanel();
        JPanel etichette =new JPanel();
        JPanel interfaccia=new JPanel();
        
        messaggistica.setLayout(new GridLayout(2,1));
        etichette.setLayout(new GridLayout(2,1));
        interfaccia.setLayout(new BorderLayout());
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(500, 500));
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        msgs = new ListPanel();
        utenti = new ListPanel();
        utenti.setBackground(new Color(173,216,230));
        
        JPanel panel = new JPanel();
        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel.setBackground(new Color(173,216,230));
        JLabel ut=new JLabel("              UTENTI              ");
        ut.setFont(new Font("Serif", Font.BOLD, 15));
        ut.setBackground(new Color(173,216,230));
        ut.setOpaque(true);
        panel.add(ut);
        utenti.addPanel(panel,1);
        
        JPanel panel1 = new JPanel();
        panel1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        panel1.setBackground(new Color(173,216,230));
        JLabel ms=new JLabel("              MESSAGGI              ");
        ms.setFont(new Font("Serif", Font.BOLD, 15));
        ms.setBackground(new Color(173,216,230));
        ms.setOpaque(true);
        panel1.add(ms);
        msgs.addPanel(panel1,1);
        
        msg = new JTextField();
        msg.setBackground(new Color(173,216,230));
        msg.setBorder(null);
        msg.setFont(new Font("Serif", Font.PLAIN, 20));
        msg.addKeyListener(this);

        selected = new JTextField();
        selected.setBackground(new Color(173,216,230));
        selected.setBorder(null);
        selected.setFont(new Font("Serif", Font.PLAIN, 20));
        selected.addKeyListener(this);
        
        
        JLabel s1=new JLabel("Scrivi qui:                   ");
        s1.setOpaque(true);
        s1.setBackground(new Color(173,216,230));
        s1.setBorder(null);
        s1.setForeground(Color.GRAY);
        s1.setFont(new Font("Serif", Font.ITALIC, 20));
        
        JLabel s2=new JLabel("Utente:                       ");
        s2.setOpaque(true);
        s2.setBackground(new Color(173,216,230));
        s2.setBorder(null);
        s2.setForeground(Color.GRAY);
        s2.setFont(new Font("Serif", Font.ITALIC, 20));
        
        etichette.add(s1);
        messaggistica.add(msg);
        etichette.add(s2);
        messaggistica.add(selected);
        
        interfaccia.add(etichette,BorderLayout.WEST);
        interfaccia.add(messaggistica,BorderLayout.CENTER);
    
        JButton r = new JButton("Cancella Messaggi");
        r.setBackground(new Color(173,216,230));
        r.setBorder(null);
        r.setFont(new Font("Serif", Font.BOLD, 20));
        r.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent paramActionEvent)
            {
                for(int j=pannelli-1;j>0;j--)
                {
                    msgs.removePanel(j);
                }
                msgs.removePanel(0);
            }
        });
        //getContentPane().add(r, BorderLayout.NORTH);

        getContentPane().add(interfaccia, BorderLayout.SOUTH); 
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(msgs);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JScrollPane scrollPane1 = new JScrollPane();
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setViewportView(utenti);
        getContentPane().add(scrollPane1, BorderLayout.WEST);

        addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                msg.requestFocusInWindow();
            }
        });

        setVisible(true);
    }
    
    public static void apriConnessione (String IP, int port, Utente utente) throws java.io.IOException {
        socket = new Socket(IP, port);
        client = new Client(socket, utente);
    }
    
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        switch(comando)
        {
            case "update":
                try {
                    apriConnessione(IP, port, utente);
                    String users[]=client.updateChat();
                    int i = 1 ;
            
                    while (!users[i].equals("finish") && !users[i].equals("Chat vuota!")) {
                        MessagePanel panel = new MessagePanel(users[i],0,"");
                        msgs.addPanel(panel,panel.getAltezza());
                        i++;
                        pannelli++;
                    }
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                break;

            case "users":
                try {
                    utenti.removePanels();

                    apriConnessione(IP, port, utente);
                    String users[]=client.showUsers();
                    int i = 1;
    
                    while (!users[i].equals("finish")) {
                        JPanel panel = new JPanel();
                        panel.setLayout(null);
                        panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

                        JButton a=new JButton(users[i]);
                        a.setHorizontalAlignment(SwingConstants.CENTER);
                        a.setVerticalAlignment(SwingConstants.CENTER);
                        a.setLocation(0,0);
                        a.setSize(200,30);
                        a.setBackground(new Color(173,216,230));
                        a.addActionListener(this);
                        panel.add(a);

                        utenti.addPanel(panel,30);
                        i++;
                    }
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                break;

            default:
                selected.setText(e.getActionCommand());
                cont=1;
                break;
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==10 && cont == 2) {
            String mex = msg.getText();
            try
            {
                apriConnessione(IP, port, utente);
                client.sendPublic(mex);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            String m=msg.getText();
            MessagePanel panel = new MessagePanel(m,1,"Pubblico");
            msgs.addPanel(panel,panel.getAltezza());
            pannelli++;
            msg.setText("");
        }
        if(e.getKeyCode()==127)
        {
            if(pannelli>=1)
            {
                for(int j=1;j<=pannelli;j++)
                {
                    msgs.removePanel(j);
                }
            }
        }
        if (e.getKeyCode()==10 && cont == 1) {
            String mex = msg.getText();
            nickname = selected.getText();
            try
            {
                apriConnessione(IP, port, utente);
                client.sendPrivate(mex, nickname);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
            String m=msg.getText();
            String mm=selected.getText();
            MessagePanel panel = new MessagePanel(m,1,mm);
            msgs.addPanel(panel,panel.getAltezza());
            selected.setText("");
            msg.setText("");
            cont=2;
            pannelli++;
        }
    }

    public void keyReleased(KeyEvent e){

    }

    public void windowClosed(WindowEvent e){

    }

    public void windowClosing(WindowEvent e) {
        try {
            apriConnessione(IP, port, utente);
            client.disiscrizioneChat();
            System.exit(0);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void windowActivated(WindowEvent e){

    }

    public void windowDeactivated(WindowEvent e){

    }

    public void windowDeiconified(WindowEvent e){

    }

    public void windowIconified(WindowEvent e){

    }

    public void windowOpened(WindowEvent e){
    
    }

    public void keyTyped(KeyEvent e){

    }
}
