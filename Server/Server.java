import java.net.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.event.*;

public class Server implements ActionListener {

    private ArrayList<Utente> tantiUtenti = new ArrayList<Utente>();
    private ArrayList<Messaggio> tantiMessaggi = new ArrayList<Messaggio>();
    private ArrayList<String> tantiLog = new ArrayList<String>();
    private GeneratoreStringa genStringa = new GeneratoreStringa();
    private Timer t;
    private Timer tt;

    private ServerSocket serverSocket;
    private BufferedReader inFromClient;
    private BufferedWriter outToClient;

    public Server (ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

        t = new Timer(30000, this); 
        t.setActionCommand("Timeout"); 
        t.start();

        tt = new Timer(300000, this);
        tt.setActionCommand("Log");
        tt.start();
    }

    public void startServer() {
        System.out.println("Server aperto");
        tantiLog.add(new Data().toString()+"--> "+"Server aperto");
        try {
            while (!serverSocket.isClosed()) {
                Socket connectionSocket = serverSocket.accept(); 
                System.out.println("Client connesso! IP: " + connectionSocket.getRemoteSocketAddress());
                tantiLog.add(new Data().toString()+"--> "+"Client connesso! IP: " + connectionSocket.getRemoteSocketAddress());
                this.inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                this.outToClient = new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));

                while (!connectionSocket.isClosed()) {
                    String ricez = riceviMessaggio();
                    System.out.println(ricez);
                    tantiLog.add(new Data().toString()+"--> "+ricez);
                    if (controlloStringa(ricez)) {
                        String msg[] = ricez.split("§");
                        String comando = msg[0];
                        System.out.println("Comando selezionato da " + msg[1] + ": " + comando);
                        tantiLog.add(new Data().toString()+"--> "+"Comando selezionato da " + msg[1] + ": " + comando);

                        //if (!controlloTimeout(msg[1])) {

                            switch (comando) {
                                case "login": Utente x = new Utente(msg[1]);
                                                if (utenteIscritto(x) && tantiUtenti.size()>0) {
                                                    inviaMessaggio("unavailable");
                                                }
                                                else {
                                                    x.setID(genStringa.ottieniStringa(10));
                                                    x.setData(new Data());
                                                    tantiUtenti.add(x);
                                                    inviaMessaggio("welcome§"+x.getID());
                                                }
                                                chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                    break;

                                case "logout": Utente y = new Utente(msg[1], "ID");
                                                if (utenteIscrittoID(new Utente(msg[1], "ID")) && tantiUtenti.size()>0) {
                                                    for (int i=0;i<tantiUtenti.size();i++) {
                                                        if (y.equals(tantiUtenti.get(i))) {
                                                            tantiUtenti.remove(i);
                                                            //eliminaMessaggi(y);
                                                            inviaMessaggio("byeBye§"+y.getID());
                                                            chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                            break;
                                                        }
                                                    }
                                                }
                                                else {
                                                    inviaMessaggio("error");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                    break;

                                case "update": if (utenteIscrittoID(new Utente(msg[1], "ID")) && tantiUtenti.size()>0) {
                                                    String update = "msgs§";
                                                    if (tantiMessaggi.size()>0) {
                                                            for (int i=posMessaggio(recuperaUtenteID(msg[1]).getLastMessage())+1;i<tantiMessaggi.size();i++) {
                                                                if (!tantiMessaggi.get(i).getMittente().equals(recuperaUtenteID(msg[1]))) {
                                                                    if (tantiMessaggi.get(i).getPrivato()) {
                                                                        if (tantiMessaggi.get(i).getDestinatario().getNickname().equals(recuperaUtenteID(msg[1]).getNickname())) {
                                                                            update = update + tantiMessaggi.get(i).toString() + "§";
                                                                        }
                                                                    }
                                                                    else {
                                                                        update = update + tantiMessaggi.get(i).toString() + "§";
                                                                    }
                                                            }
                                                        }
                                                    }
                                                    else {
                                                        update = update + "Chat vuota!§finish";
                                                        recuperaUtenteID(msg[1]).setData(new Data());
                                                        inviaMessaggio(update);
                                                        chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                        break;
                                                    }
                                                    recuperaUtenteID(msg[1]).setLastMessage(tantiMessaggi.get(tantiMessaggi.size()-1));
                                                    recuperaUtenteID(msg[1]).setData(new Data());
                                                    update = update + "finish";
                                                    inviaMessaggio(update);
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                                else {
                                                    inviaMessaggio("error");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                    break;
                                
                                case "sendPublic": if (utenteIscrittoID(new Utente(msg[1], "ID")) && tantiUtenti.size()>0 && msg.length==3) {
                                                    tantiMessaggi.add(new Messaggio(msg[2], "public", recuperaUtenteID(msg[1]), null));
                                                    recuperaUtenteID(msg[1]).setData(new Data());
                                                    inviaMessaggio("messageReceived");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                                else {
                                                    inviaMessaggio("error");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }      
                                    break;
                                    
                                case "sendPrivate": if (utenteIscrittoID(new Utente(msg[1], "ID")) && tantiUtenti.size()>0 && utenteIscritto(new Utente(msg[2])) && msg.length==4) {
                                                    tantiMessaggi.add(new Messaggio(msg[3], "private", recuperaUtenteID(msg[1]), recuperaUtenteNick(msg[2])));
                                                    recuperaUtenteID(msg[1]).setData(new Data());
                                                    inviaMessaggio("messageReceived");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                                else {
                                                    inviaMessaggio("error");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                    break; 

                                case "showUsers": if (utenteIscrittoID(new Utente(msg[1], "ID")) && tantiUtenti.size()>0) {
                                                    String users="users§";
                                                    for (int i=0;i<tantiUtenti.size();i++) {
                                                        if (!tantiUtenti.get(i).getID().equals(msg[1])) {
                                                            users = users + tantiUtenti.get(i).getNickname() + "§";
                                                        }
                                                    }
                                                    users = users + "finish";
                                                    recuperaUtenteID(msg[1]).setData(new Data());
                                                    inviaMessaggio(users);
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                                else {
                                                    inviaMessaggio("error");
                                                    chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                                }
                                    break;

                                default: inviaMessaggio("error");
                                        chiudiConnessione(connectionSocket, inFromClient, outToClient);
                                    break;
                            }
                        /*}
                        else {
                            inviaMessaggio("timeout");
                            chiudiConnessione(connectionSocket, inFromClient, outToClient);
                        }*/
                    }
                    else {
                        inviaMessaggio("error");
                        chiudiConnessione(connectionSocket, inFromClient, outToClient);
                    }
                }
                System.out.println("Client disconnesso!");
                System.out.println();
                tantiLog.add(new Data().toString()+"--> "+"Client disconnesso!");
                tantiLog.add("");
            }
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed (ActionEvent e) {
        String comando = e.getActionCommand();

        switch(comando) {
            case "Timeout": Data d = new Data();
                            for (int i=0;i<tantiUtenti.size();i++) {
                                if (d.getDifference(tantiUtenti.get(i).getData())>300) {
                                    System.out.println("Utente eliminato: " + tantiUtenti.get(i).getNickname());
                                    tantiLog.add(new Data().toString()+"--> "+"Utente eliminato: " + tantiUtenti.get(i).getNickname());
                                    //eliminaMessaggi(tantiUtenti.get(i));
                                    tantiUtenti.remove(i);
                                    break;
                                }
                        }
                break;

            case "Log": Data dd = new Data();
                        String path = "Log "+dd.toString()+".txt";
                        writeFile(path, tantiLog);
                break;  
        }
    }

    public static void writeFile (String path, ArrayList<String> s) {
        try {
            File file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i=0;i<s.size();i++) {
                bw.write(s.get(i) +"\n");
            }
            bw.flush();
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public boolean controlloTimeout (String id) {
        if (id.length()==10) {
            Data d = new Data();
            if (utenteIscrittoID(new Utente(id, "ID")) && tantiUtenti.size()>0 && d.getDifference(recuperaUtenteID(id).getData())<1) {
                for (int i=0;i<tantiUtenti.size();i++) {
                    if (tantiUtenti.get(i).equals(recuperaUtenteID(id)) && tantiUtenti.get(i).getRichiamo()>5) {
                        System.out.println("Utente eliminato: " + tantiUtenti.get(i).getNickname());
                        tantiLog.add(new Data().toString()+"--> "+"Utente eliminato: " + tantiUtenti.get(i).getNickname());
                        //eliminaMessaggi(tantiUtenti.get(i));
                        tantiUtenti.remove(i);
                        return true;
                    }
                    else if (tantiUtenti.get(i).equals(recuperaUtenteID(id)) && tantiUtenti.get(i).getRichiamo()<=5) {
                        System.out.println("Utente richiamato: " + tantiUtenti.get(i).getNickname());
                        tantiLog.add(new Data().toString()+"--> "+"Utente richiamato: " + tantiUtenti.get(i).getNickname());
                        tantiUtenti.get(i).setRichiamo(tantiUtenti.get(i).getRichiamo()+1);
                        return false;
                    }
                }
            }
        }
        return false;
    }*/

    public boolean controlloStringa(String s) {
        boolean check = false;
        char c = '0';
        s = s + "x";
        for (int i=0;i<s.length();i++) {
            if (s.charAt(i)=='§') {
                check = true;
                c = s.charAt(i+1);
                break;
            }
        }
        if (check && c!='x') {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean utenteIscritto (Utente x) {
        for (int i=0;i<tantiUtenti.size();i++) {
            if (tantiUtenti.get(i).getNickname().equals(x.getNickname())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean utenteIscrittoID (Utente x) {
        for (int i=0;i<tantiUtenti.size();i++) {
            if (tantiUtenti.get(i).equals(x)) {
                return true;
            }
        }
        return false;
    }

    public int posMessaggio (Messaggio msg) {
        for (int i=0;i<tantiMessaggi.size();i++) {
            if (tantiMessaggi.get(i).equals(msg)) {
                return i;
            }
        }
        return -1;
    }

    public void eliminaMessaggi (Utente x) {
        for (int i=0;i<tantiMessaggi.size();i++) {
            if (tantiMessaggi.get(i).getMittente().equals(x)) {
                tantiMessaggi.remove(i);
            }
        }
    }

    public Utente recuperaUtenteID (String ID) {
        for (int i=0;i<tantiUtenti.size();i++) {
            if (tantiUtenti.get(i).getID().equals(ID)) {
                return tantiUtenti.get(i);
            }
        }
        return null;
    }

    public Utente recuperaUtenteNick (String nick) {
        for (int i=0;i<tantiUtenti.size();i++) {
            if (tantiUtenti.get(i).getNickname().equals(nick)) {
                return tantiUtenti.get(i);
            }
        }
        return null;
    }

    public void inviaMessaggio (String msg) {
        try {
                outToClient.write(msg);
                outToClient.newLine();
                outToClient.flush();
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public String riceviMessaggio () {
        try {
            String msg = inFromClient.readLine();
            return msg;
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void chiudiConnessione(Socket socket, BufferedReader in, BufferedWriter out) {
        try {
            if (inFromClient != null) {
                in.close();
            }
            if (outToClient != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } 
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void chiudiServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        }
        catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}