public class Messaggio {
    
    private String body;
    private String type;
    private Utente mittente;
    private Utente destinatario;

    public Messaggio (String body, String type, Utente mittente, Utente destinatario) {
        this.body=body;
        this.type=type;
        this.mittente=mittente;
        
        if (type.equals("private")) {
            this.destinatario=destinatario;
        }
        else {
            this.destinatario=null;
        }
    }

    public Utente getMittente () {
        return mittente;
    }

    public Utente getDestinatario () {
        return destinatario;
    }

    public boolean getPrivato () {
        if (this.type.equals("private")) {
            return true;
        }
        return false;
    }

    public String toString () {
            return mittente.getNickname() + ": " + body + " --> " + type;
    }

    public boolean equals (Messaggio msg) {
        if (this.toString().equals(msg.toString())) {
            return true;
        }
        return false;
    }
}
