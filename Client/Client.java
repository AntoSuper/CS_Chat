import java.io.*;
import java.net.*;

public class Client {

    private Socket clientSocket;
    private BufferedReader inFromServer;
    private BufferedWriter outToServer;
    private Utente utente;

    public Client (Socket socket, Utente utente) {
        try {
            this.clientSocket = socket;
            this.inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.utente = utente;
        }
        catch (java.io.IOException e) {
            chiudiConnessione(clientSocket, inFromServer, outToServer);
        }
    }

    public void iscrizioneChat () {
        inviaMessaggio("login§"+utente.getNickname());
        String msg[] = riceviMessaggio().split("§");

        if (msg[0].equals("unavailable")) {
            System.out.println("Nickname non disponibile! Iscrizione non avvenuta.");
        }
        else {
            utente.setID(msg[1]);
        }
        
        chiudiConnessione(clientSocket, inFromServer, outToServer);
    }

    public void disiscrizioneChat () {
        inviaMessaggio("logout§"+utente.getID());
        chiudiConnessione(clientSocket, inFromServer, outToServer);
    }

    public String[]  updateChat () {
        inviaMessaggio("update§"+utente.getID());

        String msg = riceviMessaggio();
        System.out.println(msg);
        String users[] = msg.split("§");

        chiudiConnessione(clientSocket, inFromServer, outToServer);
        return users;
    }

    public String[] showUsers () {
        inviaMessaggio("showUsers§"+utente.getID());
        
        String msg = riceviMessaggio();
        String users[] = msg.split("§");

        chiudiConnessione(clientSocket, inFromServer, outToServer);
        return users;
    }

    public void sendPublic (String msg) {
        inviaMessaggio("sendPublic§"+utente.getID()+"§"+msg);
        chiudiConnessione(clientSocket, inFromServer, outToServer);
    }

    public void sendPrivate (String msg, String nickname) {
        inviaMessaggio("sendPrivate§"+utente.getID()+"§"+nickname+"§"+msg);
        chiudiConnessione(clientSocket, inFromServer, outToServer);
    }

    public String riceviMessaggio () {
        if (clientSocket.isConnected()) {
            try {
                return inFromServer.readLine();
            } 
            catch (java.io.IOException e) {
                e.printStackTrace();
                chiudiConnessione(clientSocket, inFromServer, outToServer);
            }
        }
        return null;
    }

    public void inviaMessaggio (String msg) {
        if (clientSocket.isConnected()) {
            try {
                outToServer.write(msg);
                outToServer.newLine();
                outToServer.flush();

            } 
            catch (java.io.IOException e) {
                e.printStackTrace();
                chiudiConnessione(clientSocket, inFromServer, outToServer);
            }
        }
    }

    public void chiudiConnessione(Socket socket, BufferedReader in, BufferedWriter out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
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
}
