import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    
    private String mittente;
    private String type;
    private String body;

    private JPanel superiore;
    private JPanel inferiore;
    private JPanel centrale;

    private JLabel utente;
    private JLabel corpo;
    private JLabel tipo;
    private JLabel orario;

    public MessagePanel (String x, int cont, String Type) {
        if(cont==0) {
            String msg[] = x.split("&");
            mittente = msg[0];
            type = msg[1];
            body = msg[2];
            utente = new JLabel(mittente+ ": ");
            corpo = new JLabel(body);
            tipo = new JLabel("");
            orario = new JLabel(msg[3]+":"+msg[4]);

            if (type.equals("private")) {
                tipo.setText("Privato");
            }
            else {
                tipo.setText("Pubblico");
            }
        }
        else {
            utente = new JLabel("Me stesso:");
            corpo = new JLabel(x);
            Data d = new Data();
            orario = new JLabel(d.getOre()+":"+d.getMinuti());
            if (!Type.equals("Pubblico")) {
                tipo = new JLabel("Destinatario: " + Type);
            }
            else {
                tipo = new JLabel(Type);
            }
        }

        this.setLayout(new BorderLayout());

        superiore = new JPanel();
        if(cont==0)
            superiore.setBackground(new Color(105,96,236));
        else
            superiore.setBackground(new Color(169,164,246));
        superiore.setLayout(new BorderLayout());
        add(superiore, BorderLayout.NORTH);

        centrale = new JPanel();
        if(cont==0)
            centrale.setBackground(new Color(105,96,236));
        else
            centrale.setBackground(new Color(169,164,246));
        centrale.setLayout(new BorderLayout());
        add(centrale, BorderLayout.CENTER);

        inferiore = new JPanel();
        if(cont==0)
            inferiore.setBackground(new Color(105,96,236));
        else
            inferiore.setBackground(new Color(169,164,246));
        inferiore.setLayout(new BorderLayout());
        add(inferiore, BorderLayout.SOUTH);
        
        utente.setOpaque(true);
        if(cont==0)
            utente.setBackground(new Color(105,96,236));
        else
            utente.setBackground(new Color(169,164,246));
        utente.setFont(new Font("Serif", Font.ITALIC, 20));
        superiore.add(utente, BorderLayout.WEST);
        
        corpo.setOpaque(true);
        if(cont==0)
            corpo.setBackground(new Color(105,96,236));
        else
            corpo.setBackground(new Color(169,164,246));
        corpo.setFont(new Font("Serif", Font.PLAIN, 20));
        centrale.add(corpo, BorderLayout.CENTER);

        tipo.setOpaque(true);
        if(cont==0) {
            tipo.setBackground(new Color(105,96,236));
        }
        else {
            tipo.setBackground(new Color(169,164,246));
        }
        tipo.setFont(new Font("Serif", Font.ITALIC, 20));
        inferiore.add(tipo, BorderLayout.EAST);

        orario.setOpaque(true);
        if(cont==0) {
            orario.setBackground(new Color(105,96,236));
        }
        else {
            orario.setBackground(new Color(169,164,246));
        }
        tipo.setFont(new Font("Serif", Font.ITALIC, 20));
        inferiore.add(orario, BorderLayout.WEST);

    }
    public void setUtente(String Utente) {
        utente.setText(Utente+":");
    }

    public void setCorpo(String Mex) {
        corpo.setText(Mex);
    }

    public void setTipo(String Tipo) {
        tipo.setText(Tipo);
    }

    public int getAltezza() {
        return superiore.getHeight()+centrale.getHeight()+inferiore.getHeight();
    }
}
