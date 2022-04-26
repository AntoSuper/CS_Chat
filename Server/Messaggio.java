public class Messaggio {
    
    private String body;
    private String type;
    private Utente mittente;
    private Utente destinatario;
    private Data orario;

    public Messaggio (String body, String type, Utente mittente, Utente destinatario) {
        this.body=body;
        this.type=type;
        this.mittente=mittente;
        this.orario = new Data();
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
            return mittente.getNickname()+"&"+type+"&"+body+"&"+orario.getOre()+"&"+orario.getMinuti();
    }

    public boolean equals (Messaggio msg) {
        if (this.toString().equals(msg.toString())) {
            return true;
        }
        return false;
    }
}
